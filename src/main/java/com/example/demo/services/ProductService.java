package com.example.demo.services; 

import com.example.demo.WarehouseCapacityExceededException;
import com.example.demo.dto.ProductSummary;
import com.example.demo.entities.Product;
import com.example.demo.entities.Warehouse;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.repositories.WarehouseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// This is the service class for handling product-related operations
@Service 
public class ProductService {

    // Injecting the product repository for database operations
    @Autowired
    private ProductRepository productRepository;

    // Injecting the warehouse repository to check warehouse details
    @Autowired
    private WarehouseRepository warehouseRepository;

    // Method to retrieve all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Method to get a product by its ID
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    // Creates a new product and checks if the warehouse can hold it
    public Product createProduct(Product product) {
        // First, make sure the product has a warehouse assigned
        if (product.getWarehouse() == null || product.getWarehouse().getId() == null) {
            throw new IllegalArgumentException("Product needs a valid warehouse ID.");
        }
        // Look up the warehouse by ID
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(product.getWarehouse().getId());
        if (warehouseOptional.isEmpty()) {
            throw new IllegalArgumentException("No warehouse found with ID " + product.getWarehouse().getId() + ".");
        }
        Warehouse warehouse = warehouseOptional.get();

        // Check if there's enough space in the warehouse
        checkCapacityForAddition(warehouse, product.getQuantity());
        // Save the new product to the database
        return productRepository.save(product);
    }

    // Updates an existing product, possibly changing quantity or warehouse
    public Product updateProduct(Integer id, Product productDetails) {
        // Find the existing product
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            return null;
        }

        Product existingProduct = productOptional.get();
        Warehouse oldWarehouse = existingProduct.getWarehouse();
        Integer newQuantity = existingProduct.getQuantity();

        // Update quantity if provided
        if (productDetails.getQuantity() != null) {
            newQuantity = productDetails.getQuantity();
            existingProduct.setQuantity(newQuantity);
        }
        // Update name if provided
        if (productDetails.getName() != null) {
            existingProduct.setName(productDetails.getName());
        }
        // Update description if provided
        if (productDetails.getDescription() != null) {
            existingProduct.setDescription(productDetails.getDescription());
        }

        // Handle warehouse change if a new one is provided
        if (productDetails.getWarehouse() != null && productDetails.getWarehouse().getId() != null) {
            Integer newWarehouseId = productDetails.getWarehouse().getId();
            if (!newWarehouseId.equals(oldWarehouse.getId())) {
                // Find the new warehouse
                Optional<Warehouse> newWarehouseOptional = warehouseRepository.findById(newWarehouseId);
                if (newWarehouseOptional.isEmpty()) {
                    throw new IllegalArgumentException("No warehouse found with ID " + newWarehouseId + ".");
                }
                Warehouse newWarehouse = newWarehouseOptional.get();
                existingProduct.setWarehouse(newWarehouse);
                // Check capacity in the new warehouse
                checkCapacityForAddition(newWarehouse, newQuantity);
            } else {
                // If same warehouse, check updated capacity
                checkCapacityForUpdateInSameWarehouse(oldWarehouse, newQuantity, existingProduct.getId());
            }
        } else {
            // No warehouse change, just check current one
            checkCapacityForUpdateInSameWarehouse(oldWarehouse, newQuantity, existingProduct.getId());
        }

        // Save the updated product
        return productRepository.save(existingProduct);
    }

    // Deletes a product if it exists
    public boolean deleteProduct(Integer id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Method to check if adding quantity to a warehouse exceeds capacity
    private void checkCapacityForAddition(Warehouse targetWarehouse, Integer quantityToAdd) {
        // Calculate current occupied space
        Integer currentOccupied = productRepository.findTotalQuantityByWarehouseId(targetWarehouse.getId()).orElse(0);
        if (currentOccupied + quantityToAdd > targetWarehouse.getCapacity()) {
            // Throw exception if over capacity
            throw new WarehouseCapacityExceededException(
                    "Can't add " + quantityToAdd + " to warehouse '" + targetWarehouse.getName() + "' (ID: " + targetWarehouse.getId() + "). " +
                    "Current: " + currentOccupied + ", Capacity: " + targetWarehouse.getCapacity() + ". " +
                    "Over by " + ((currentOccupied + quantityToAdd) - targetWarehouse.getCapacity()) + " units."
            );
        }
    }

    // Method to check capacity when updating quantity in the same warehouse
    private void checkCapacityForUpdateInSameWarehouse(Warehouse warehouse, Integer newQuantity, Integer productId) {
        // Calculate occupied space excluding the current product
        Integer occupiedByOthers = productRepository.findTotalQuantityByWarehouseIdExcludingProduct(warehouse.getId(), productId).orElse(0);
        if (occupiedByOthers + newQuantity > warehouse.getCapacity()) {
            // Throw exception if over capacity
            throw new WarehouseCapacityExceededException(
                    "Can't update quantity to " + newQuantity + " in warehouse '" + warehouse.getName() + "' (ID: " + warehouse.getId() + "). " +
                    "Current (excluding this product): " + occupiedByOthers + ", Capacity: " + warehouse.getCapacity() + ". " +
                    "Over by " + ((occupiedByOthers + newQuantity) - warehouse.getCapacity()) + " units."
            );
        }
    }
    
    // Gets all products in a specific warehouse
    public List<Product> getProductsByWarehouse(Integer warehouseId) {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(warehouseId);
        return productRepository.findByWarehouse(warehouse);
    }
    
    // Retrieves summaries for all products
    public List<ProductSummary> getAllProductSummaries() {
        return productRepository.findAllProductSummaries();
    }
    
    // Gets product summaries for a specific warehouse
    public List<ProductSummary> getProductSummariesByWarehouseId(Integer warehouseId) {
        return productRepository.findProductSummariesByWarehouseId(warehouseId);
    }
    
    // Searches for products by name, ignoring case
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

}