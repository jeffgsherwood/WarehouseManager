package com.example.demo.services;

import com.example.demo.entities.Warehouse;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    public Optional<Warehouse> getWarehouseById(Integer id) {
        return warehouseRepository.findById(id);
    }

    public Warehouse createWarehouse(Warehouse warehouse) {
        if (warehouse.getName() == null || warehouse.getName().isBlank()) {
            throw new IllegalArgumentException("Warehouse name cannot be empty.");
        }
        if (warehouse.getCapacity() == null || warehouse.getCapacity() <= 0) {
            throw new IllegalArgumentException("Warehouse capacity must be a positive number.");
        }
        
        Optional<Warehouse> existingWarehouse = warehouseRepository.findByName(warehouse.getName());
        if (existingWarehouse.isPresent()) {
            throw new IllegalArgumentException("A warehouse with the name '" + warehouse.getName() + "' already exists.");
        }

        return warehouseRepository.save(warehouse);
    }

    public void deleteWarehouseById(Integer id) {
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(id);
        if (warehouseOptional.isEmpty()) {
            throw new IllegalArgumentException("Warehouse with ID " + id + " not found.");
        }

        Integer productCount = productRepository.countByWarehouseId(id);
        if (productCount > 0) {
            throw new IllegalStateException("Cannot delete warehouse with ID " + id + " because it still contains " + productCount + " products.");
        }

        warehouseRepository.deleteById(id);
    }
}