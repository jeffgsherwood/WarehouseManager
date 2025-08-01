package com.example.demo.services; 

import com.example.demo.WarehouseCapacityExceededException;
import com.example.demo.entities.Product;
import com.example.demo.entities.Warehouse;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service 
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    // Creates a new product
    public Product createProduct(Product product) {
        if (product.getWarehouse() == null || product.getWarehouse().getId() == null) {
            throw new IllegalArgumentException("Product needs a valid warehouse ID.");
        }
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(product.getWarehouse().getId());
        if (warehouseOptional.isEmpty()) {
            throw new IllegalArgumentException("No warehouse found with ID " + product.getWarehouse().getId() + ".");
        }
        Warehouse warehouse = warehouseOptional.get();

        checkCapacityForAddition(warehouse, product.getQuantity());
        return productRepository.save(product);
    }

    // Updates an existing product
    public Product updateProduct(Integer id, Product productDetails) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            return null;
        }

        Product existingProduct = productOptional.get();
        Warehouse oldWarehouse = existingProduct.getWarehouse();
        Integer newQuantity = existingProduct.getQuantity();

        if (productDetails.getQuantity() != null) {
            newQuantity = productDetails.getQuantity();
            existingProduct.setQuantity(newQuantity);
        }
        if (productDetails.getName() != null) {
            existingProduct.setName(productDetails.getName());
        }
        if (productDetails.getDescription() != null) {
            existingProduct.setDescription(productDetails.getDescription());
        }

        if (productDetails.getWarehouse() != null && productDetails.getWarehouse().getId() != null) {
            Integer newWarehouseId = productDetails.getWarehouse().getId();
            if (!newWarehouseId.equals(oldWarehouse.getId())) {
                Optional<Warehouse> newWarehouseOptional = warehouseRepository.findById(newWarehouseId);
                if (newWarehouseOptional.isEmpty()) {
                    throw new IllegalArgumentException("No warehouse found with ID " + newWarehouseId + ".");
                }
                Warehouse newWarehouse = newWarehouseOptional.get();
                existingProduct.setWarehouse(newWarehouse);
                checkCapacityForAddition(newWarehouse, newQuantity);
            } else {
                checkCapacityForUpdateInSameWarehouse(oldWarehouse, newQuantity, existingProduct.getId());
            }
        } else {
            checkCapacityForUpdateInSameWarehouse(oldWarehouse, newQuantity, existingProduct.getId());
        }

        return productRepository.save(existingProduct);
    }

    // Deletes a product
    public boolean deleteProduct(Integer id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Checks if warehouse has space for new product
    private void checkCapacityForAddition(Warehouse targetWarehouse, Integer quantityToAdd) {
        Integer currentOccupied = productRepository.findTotalQuantityByWarehouseId(targetWarehouse.getId()).orElse(0);
        if (currentOccupied + quantityToAdd > targetWarehouse.getCapacity()) {
            throw new WarehouseCapacityExceededException(
                    "Can't add " + quantityToAdd + " to warehouse '" + targetWarehouse.getName() + "' (ID: " + targetWarehouse.getId() + "). " +
                    "Current: " + currentOccupied + ", Capacity: " + targetWarehouse.getCapacity() + ". " +
                    "Over by " + ((currentOccupied + quantityToAdd) - targetWarehouse.getCapacity()) + " units."
            );
        }
    }

    // Checks if updating quantity in same warehouse is okay
    private void checkCapacityForUpdateInSameWarehouse(Warehouse warehouse, Integer newQuantity, Integer productId) {
        Integer occupiedByOthers = productRepository.findTotalQuantityByWarehouseIdExcludingProduct(warehouse.getId(), productId).orElse(0);
        if (occupiedByOthers + newQuantity > warehouse.getCapacity()) {
            throw new WarehouseCapacityExceededException(
                    "Can't update quantity to " + newQuantity + " in warehouse '" + warehouse.getName() + "' (ID: " + warehouse.getId() + "). " +
                    "Current (excluding this product): " + occupiedByOthers + ", Capacity: " + warehouse.getCapacity() + ". " +
                    "Over by " + ((occupiedByOthers + newQuantity) - warehouse.getCapacity()) + " units."
            );
        }
    }
    // Filters products by warehouse
    public List<Product> getProductsByWarehouse(Integer warehouseId) {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(warehouseId);
        return productRepository.findByWarehouse(warehouse);
    	   
    }

}