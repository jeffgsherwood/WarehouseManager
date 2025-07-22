# WarehouseManager

WarehouseManager is a backend inventory management system designed to help administrators oversee products across multiple warehouses. It provides a normalized MySQL database schema and a fully functional REST API built with Java Spring Boot. The system supports product tracking, CRUD operations, and edge case handling for warehouse capacity limits.

## Features

- ✅ Normalized MySQL database schema
- ✅ Tables for `warehouses` and `products`, with foreign key relationships
- ✅ `schema.sql` for table creation
- ✅ `data.sql` for reproducible test data (2 warehouses, 20 products)
- ✅ REST API with working `GET /warehouses` and `GET /products` endpoints
- 🔧 Upcoming support for full CRUD on `/products` and `/warehouses`
- 🔒 Capacity constraints checked before product insertion (coming soon)

## How to Use

1. Create a MySQL schema named `warehouse_manager`
2. Run `schema.sql` to set up the database tables
3. Run `data.sql` to seed the database with test warehouses and products
4. Start the Spring Boot application via STS or `mvn spring-boot:run`
5. Access endpoints locally at `http://localhost:8080`

## API Endpoints (In Progress)

| Endpoint              | Method | Status   | Description                                  |
|----------------------|--------|----------|----------------------------------------------|
| `/warehouses`        | GET    | ✅ Done   | Retrieves all warehouse records              |
| `/warehouses/{id}`   | GET    | 🔧 Pending| Retrieves a warehouse by its ID              |
| `/products`          | GET    | ✅ Done   | Retrieves all product records                |
| `/products/{id}`     | GET    | 🔧 Pending| Retrieves a product by its ID                |
| `/products`          | POST   | 🔧 Pending| Adds a new product (with capacity check)     |
| `/products/{id}`     | PUT    | 🔧 Pending| Updates product details                      |
| `/products/{id}`     | DELETE | 🔧 Pending| Deletes a product from the database          |

## Tech Stack

- Java 17
- Spring Boot 3.5
- MySQL
- Maven
- Spring Data JPA
- RESTful API architecture

## Coming Soon

- Full POST/PUT/DELETE endpoint functionality
- Automatic capacity checking during product inserts
- Authentication and role-based access control
- Cloud deployment with sample frontend integration
- Swagger documentation for easy endpoint exploration

## Author

**Jeff Sherwood**  


