package com.example.demo; 

public class WarehouseCapacityExceededException extends RuntimeException {
    public WarehouseCapacityExceededException(String message) {
        super(message);
    }
}