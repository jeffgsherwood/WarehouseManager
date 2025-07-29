package com.example.demo.controllers;

import com.example.demo.entities.Product;
import com.example.demo.ProductService;
import com.example.demo.WarehouseCapacityExceededException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products") // Base URL for all endpoints in this controller
public class ProductController {

    @Autowired
    private ProductService productService; // Connects to our service layer

    // Get all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Get a single product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build(); // 404 if product not found (no body needed here as per original design)
        }
        return ResponseEntity.ok(product); // 200 with product data
    }

    // Create a new product
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        try {
            Product savedProduct = productService.createProduct(product);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED); // 201 on success
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 400 for validation errors
        } catch (WarehouseCapacityExceededException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); // 400 for capacity issues
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage()); // 500 for other errors
        }
    }

    // Update an existing product
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody Product productDetails) {
        try {
            Product updatedProduct = productService.updateProduct(id, productDetails);
            if (updatedProduct == null) {
                // CORRECTED LINE 58: Use status().body() for a 404 with a message
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with ID " + id + " not found.");
            }
            return ResponseEntity.ok(updatedProduct); // 200 with updated product
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 400 for validation errors
        } catch (WarehouseCapacityExceededException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); // 400 for capacity issues
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage()); // 500 for other errors
        }
    }

    // Delete a product
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        if (productService.deleteProduct(id)) {
            return ResponseEntity.ok("Product with ID " + id + " deleted successfully."); // 200 on success
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with ID " + id + " not found."); // 404 if not found
        }
    }
}