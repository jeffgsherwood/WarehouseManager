package com.example.demo.controllers;

import com.example.demo.entities.Warehouse;
import com.example.demo.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/warehouses")
public class WarehouseController {

    @Autowired
    private WarehouseRepository warehouseRepo;

    @GetMapping
    public List<Warehouse> getAllWarehouses() {
        return warehouseRepo.findAll();
    }

    @GetMapping("/{id}")
    public Warehouse getWarehouseById(@PathVariable Integer id) {
        return warehouseRepo.findById(id).orElse(null);
    }
}