# WarehouseManager

WarehouseManager is a backend inventory management system designed to help administrators oversee products across multiple warehouses. It provides a normalized MySQL database schema and a fully functional REST API built with Java Spring Boot. The system supports product tracking, CRUD operations, and edge case handling for warehouse capacity limits.

## Features

- ✅ Normalized MySQL database schema
- ✅ Tables for `warehouses` and `products`, with foreign key relationships
- ✅ `schema.sql` for table creation
- ✅ `data.sql` for reproducible test data (2 warehouses, 20 products)
- ✅ Full CRUD support for `/products` endpoints
- ✅ Full CRUD support for `/warehouses` endpoints
- ✅ Capacity constraints checked before product insertion and updates
- ✅ Custom exception handling for warehouse capacity exceeded
- ✅ Endpoints for product summaries (name and quantity) for all products or by warehouse
- ✅ Endpoint for searching products by name or partial name (case-insensitive)
- ✅ Basic endpoints for retrieving warehouse data and filtering products by warehouse
- ✅ Endpoints for creating and deleting warehouses

## How to Use

1. Create a MySQL schema named `warehouse_manager`.
2. Run `schema.sql` to set up the database tables.
3. Run `data.sql` to seed the database with test warehouses and products.
4. Start the Spring Boot application via STS or `mvn spring-boot:run`.
5. Access endpoints locally at `http://localhost:8080`.

## API Documentation

Explore all API endpoints, their schemas, and try them out interactively using the Swagger UI.
**Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## API Endpoints for Warehouses

| Endpoint | Method | Status | Description |
|---|---|---|---|
| `/warehouses` | GET | ✅ Done | Retrieves all warehouse records |
| `/warehouses/{id}` | GET | ✅ Done | Retrieves a warehouse by its ID |
| `/warehouses/{id}/products` | GET | ✅ Done | Filters products by warehouse ID |
| `/warehouses/{id}/products/names-and-quantities` | GET | ✅ Done | Returns a list of product names and quantities for a specific warehouse |
| `/warehouses` | POST | ✅ Done | Creates a new warehouse |
| `/warehouses/{id}` | DELETE | ✅ Done | Deletes a warehouse by its ID |

## API Endpoints for Products

| Endpoint | Method | Status | Description |
|---|---|---|---|
| `/products` | GET | ✅ Done | Retrieves all product records |
| `/products/{id}` | GET | ✅ Done | Retrieves a product by its ID |
| `/products` | POST | ✅ Done | Adds a new product (with capacity check) |
| `/products/{id}` | PUT | ✅ Done | Updates product details (with capacity check) |
| `/products/{id}` | DELETE | ✅ Done | Deletes a product from the database |
| `/products/names-and-quantities` | GET | ✅ Done | Returns a list of all products with only their names and quantities |
| `/products/search?name={query}` | GET | ✅ Done | Returns products whose names match or partially match a search term |




## Tech Stack

- Java 17
- Spring Boot 3.5
- MySQL
- Maven
- Spring Data JPA
- RESTful API architecture

## Coming Soon

- Authentication and role-based access control
- Cloud deployment with sample frontend integration
- Swagger documentation for easy endpoint exploration

## Author

**Jeff Sherwood**
