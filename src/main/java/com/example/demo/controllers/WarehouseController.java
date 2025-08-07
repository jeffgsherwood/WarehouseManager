package com.example.demo.controllers;

import com.example.demo.dto.ProductSummary;
import com.example.demo.entities.Product;
import com.example.demo.entities.Warehouse;
import com.example.demo.services.WarehouseService;
import com.example.demo.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/warehouses")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Warehouse> getAllWarehouses() {
        return warehouseService.getAllWarehouses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable Integer id) {
        Optional<Warehouse> warehouse = warehouseService.getWarehouseById(id);
        return warehouse.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<Product>> getProductsByWarehouseId(@PathVariable Integer id) {
        List<Product> products = productService.getProductsByWarehouse(id);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/{id}/products/names-and-quantities")
    public ResponseEntity<List<ProductSummary>> getProductSummariesByWarehouseId(@PathVariable Integer id) {
        List<ProductSummary> summaries = productService.getProductSummariesByWarehouseId(id);
        if (summaries.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(summaries);
    }
    
    @PostMapping
    public ResponseEntity<?> createWarehouse(@RequestBody Warehouse warehouse) {
        try {
            Warehouse savedWarehouse = warehouseService.createWarehouse(warehouse);
            return new ResponseEntity<>(savedWarehouse, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWarehouse(@PathVariable Integer id) {
        try {
            warehouseService.deleteWarehouseById(id);
            return ResponseEntity.ok("Warehouse with ID " + id + " deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}