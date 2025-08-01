package com.example.demo.repositories;

import com.example.demo.entities.Product;
import com.example.demo.entities.Warehouse; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; 
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional; 

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByWarehouse(Warehouse warehouse);
    Integer countByWarehouseId(Integer warehouseId);
    
    

    // This query finds the total quantity of all products associated with a specific warehouse ID.
    @Query("SELECT SUM(p.quantity) FROM Product p WHERE p.warehouse.id = :warehouseId")
    Optional<Integer> findTotalQuantityByWarehouseId(@Param("warehouseId") Integer warehouseId);

    // Sums quantities of products in a warehouse, EXCLUDING a specific product
    @Query("SELECT SUM(p.quantity) FROM Product p WHERE p.warehouse.id = :warehouseId AND p.id != :productId")
    Optional<Integer> findTotalQuantityByWarehouseIdExcludingProduct(@Param("warehouseId") Integer warehouseId, @Param("productId") Integer productId);
    
    
}