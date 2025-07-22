package com.example.demo.controllers;

import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*; 

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepo; 

    // Endpoint to retrieve all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    // Endpoint to retrieve a specific product by its ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productRepo.findById(id).orElse(null);
    }

    // Endpoint to delete a specific product by its ID
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        if (productRepo.existsById(id)) {
            productRepo.deleteById(id);
            return "Product with ID " + id + " has been deleted successfully.";
        } else {
            // Optional: return a more specific HTTP status code like 404 Not Found
            // For now, a simple string message indicates it wasn't found.
            return "Product with ID " + id + " not found.";
        }
    }
}