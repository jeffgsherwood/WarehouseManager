package com.example.demo.controllers;

import com.example.demo.entities.Product;
import com.example.demo.entities.Warehouse;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.repositories.WarehouseRepository; // Import WarehouseRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Import HttpStatus
import org.springframework.http.ResponseEntity; // Import ResponseEntity
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional; // Import Optional

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private WarehouseRepository warehouseRepo; // Inject WarehouseRepository

    // Endpoint to retrieve all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    // Endpoint to retrieve a specific product by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        Optional<Product> product = productRepo.findById(id);
        return product.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to create a new product
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        // Basic validation: Check if the associated warehouse exists
        if (product.getWarehouse() == null || product.getWarehouse().getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        }

        Optional<Warehouse> warehouseOptional = warehouseRepo.findById(product.getWarehouse().getId());
        if (warehouseOptional.isEmpty()) {
            return ResponseEntity.badRequest().build(); // Warehouse not found
        }

        Product savedProduct = productRepo.save(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    // Endpoint to update a specific product by its ID
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product productDetails) {
        Optional<Product> productOptional = productRepo.findById(id);

        if (productOptional.isEmpty()) {
            return ResponseEntity.notFound().build(); // Product not found
        }

        Product existingProduct = productOptional.get();

        // Update fields that are provided in the request body
        if (productDetails.getName() != null) {
            existingProduct.setName(productDetails.getName());
        }
        if (productDetails.getDescription() != null) {
            existingProduct.setDescription(productDetails.getDescription());
        }
        if (productDetails.getQuantity() != null) {
            existingProduct.setQuantity(productDetails.getQuantity());
        }

        // If a new warehouse is provided, check if it exists
        if (productDetails.getWarehouse() != null && productDetails.getWarehouse().getId() != null) {
            Optional<Warehouse> newWarehouseOptional = warehouseRepo.findById(productDetails.getWarehouse().getId());
            if (newWarehouseOptional.isEmpty()) {
                return ResponseEntity.badRequest().build(); // New warehouse not found
            }
            existingProduct.setWarehouse(newWarehouseOptional.get());
        }

        Product updatedProduct = productRepo.save(existingProduct);
        return ResponseEntity.ok(updatedProduct);
    }

    // Endpoint to delete a specific product by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        if (productRepo.existsById(id)) {
            productRepo.deleteById(id);
            return ResponseEntity.ok("Product with ID " + id + " has been deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with ID " + id + " not found.");
        }
    }
}