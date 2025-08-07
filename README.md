# WarehouseManager

WarehouseManager is a backend inventory management system designed to help administrators oversee products across multiple warehouses. It provides a normalized MySQL database schema and a fully functional REST API built with Java Spring Boot. The system supports product tracking, CRUD operations, and edge case handling for warehouse capacity limits.

## Features

- ✅ Normalized MySQL database schema
- ✅ Tables for `warehouses` and `products`, with foreign key relationships
- ✅ `schema.sql` for table creation
- ✅ `data.sql` for reproducible test data (2 warehouses, 20 products)
- ✅ REST API with working `GET /warehouses` and `GET /products` endpoints
- ✅ Full CRUD support for `/products`
- ✅ Capacity constraints checked before product insertion and updates
- ✅ Custom exception handling for warehouse capacity exceeded
- ✅ Endpoints for product summaries (name and quantity)
- ✅ Endpoint for searching products by name or partial name

## How to Use

1. Create a MySQL schema named `warehouse_manager`.
2. Run `schema.sql` to set up the database tables.
3. Run `data.sql` to seed the database with test warehouses and products.
4. Start the Spring Boot application via STS or `mvn spring-boot:run`.
5. Access endpoints locally at `http://localhost:8080`.

## API Endpoints

| Endpoint | Method | Status | Description |
|---|---|---|---|
| `/warehouses` | GET | ✅ Done | Retrieves all warehouse records |
| `/warehouses/{id}` | GET | ✅ Done | Retrieves a warehouse by its ID |
| `/warehouses` | POST | ⬜ To Do | Creates a new warehouse |
| `/warehouses/{id}` | DELETE | ⬜ To Do | Deletes a warehouse by its ID |
| `/products` | GET | ✅ Done | Retrieves all product records |
| `/products/{id}` | GET | ✅ Done | Retrieves a product by its ID |
| `/products` | POST | ✅ Done | Adds a new product (with capacity check) |
| `/products/{id}` | PUT | ✅ Done | Updates product details (with capacity check) |
| `/products/{id}` | DELETE | ✅ Done | Deletes a product from the database |
| `/warehouses/{id}/products` | GET | ⬜ To Do | Filters products by warehouse ID |
| `/products/names-and-quantities` | GET | ✅ Done | Returns a list of all products with only their names and quantities |
| `/warehouses/{id}/products/names-and-quantities` | GET | ✅ Done | Returns a list of product names and quantities for a specific warehouse |
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
- Endpoints for creating new warehouses (Bonus Challenge)

## Author

**Jeff Sherwood**
