# WarehouseManager

WarehouseManager is a backend inventory management system designed to help administrators oversee products across multiple warehouses. It provides a clean database schema and the foundation for a REST API that enables product tracking, CRUD operations, and edge case handling for capacity limits.

## Features

-  Normalized MySQL database schema
-  Tables for `warehouses` and `products`, with foreign key relationships
-  `schema.sql` for table creation
-  `data.sql` (coming soon!) for initial test data
-  Future REST API with full CRUD endpoints via Spring Boot

## How to Use

1. Create a MySQL schema named `warehouse_manager`
2. Run `schema.sql` to set up the database tables
3. (Optional) Seed with test data using `data.sql`

## Coming Soon

-  Java Spring Boot backend API
-  Authentication and role-based access control
-  Cloud deployment with sample frontend integration

## Author

Jeff Sherwood 
