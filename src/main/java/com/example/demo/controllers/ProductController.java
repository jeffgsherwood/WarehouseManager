package com.example.demo.controllers;

import com.example.demo.entities.Product;
import com.example.demo.services.ProductService;
import com.example.demo.WarehouseCapacityExceededException;
import com.example.demo.dto.ProductSummary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// This is the controller class for handling product-related HTTP requests
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
            return ResponseEntity.notFound().build(); // 404 if product not found 
        }
        return ResponseEntity.ok(product); // 200 with product data
    }
    
    // Get products by warehouse ID
    @GetMapping("/warehouses/{id}/products")
    public ResponseEntity<List<Product>> getProductsByWarehouseId(@PathVariable Integer id) {
        List<Product> products = productService.getProductsByWarehouse(id);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }
    
    // Get summaries of all products including names and quantities
    @GetMapping("/names-and-quantities")
    public List<ProductSummary> getAllProductSummaries() {
        return productService.getAllProductSummaries();
    }
    
    // Get product summaries for a specific warehouse
    @GetMapping("/warehouses/{id}/products/names-and-quantities")
    public List<ProductSummary> getProductSummariesByWarehouseId(@PathVariable Integer id) {
        return productService.getProductSummariesByWarehouseId(id);
    }
    
    // Search products by name
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String name) {
        return productService.searchProductsByName(name);
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
                // Product not found, return 404 with message
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