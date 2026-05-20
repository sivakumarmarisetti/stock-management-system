# Stock Management System

A **production-ready, enterprise-grade inventory and stock tracking application** built with Spring Boot 3.5, featuring secure authentication, multi-tenant support, real-time stock management, and comprehensive audit logging.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-green)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![License](https://img.shields.io/badge/License-Apache%202.0-brightgreen)

## 🎯 Overview

The Stock Management System is designed for organizations requiring centralized inventory operations with secure access control, scalable REST APIs, and multi-tenant business workflows. It provides real-time visibility into stock levels, comprehensive audit trails, and role-based access control.

### Key Features

- ✅ **JWT-based Authentication** — Secure token-based login with expiration
- ✅ **Role-Based Access Control (RBAC)** — 5 predefined roles with granular permissions
- ✅ **Multi-Tenant Isolation** — Complete data isolation between tenants
- ✅ **Stock Management** — Real-time stock tracking with history
- ✅ **Product Management** — Full CRUD operations with search and filtering
- ✅ **Audit Logging** — Track all user actions with timestamps
- ✅ **Rate Limiting** — Prevent API abuse with request throttling
- ✅ **Database Migrations** — Liquibase for version-controlled schema changes
- ✅ **Swagger/OpenAPI** — Interactive API documentation
- ✅ **Global Exception Handling** — Standardized error responses
- ✅ **Soft Delete** — Logical deletion without losing data
- ✅ **Pagination & Search** — Efficient data retrieval with sorting

---

## 🏗️ Architecture

```
Controller Layer (REST APIs)
        ↓
Service Layer (Business Logic)
        ↓
Repository Layer (Data Access)
        ↓
Database (MySQL)
```

### Tech Stack

| Layer | Technology |
|---|---|
| **Backend** | Java 17, Spring Boot 3.5 |
| **Framework** | Spring MVC, Spring Data JPA |
| **ORM** | Hibernate 6.x |
| **Database** | MySQL 8.0 |
| **Security** | Spring Security, JWT (JJWT) |
| **Validation** | Jakarta Bean Validation |
| **API Docs** | Springdoc OpenAPI 2.8.8 |
| **Database Migration** | Liquibase |
| **Mapping** | MapStruct |
| **Build Tool** | Maven 3.9+ |
| **Testing** | JUnit 5, Mockito |

---

## 📋 Modules

### 1. **Authentication Module**
- User login with credentials
- JWT token generation and validation
- Token expiration handling
- Login activity tracking

**Endpoints:**
- `POST /api/v1/auth/login` — Authenticate user
- `POST /api/v1/auth/refresh` — Refresh token (future)

### 2. **User Management Module**
- Create, read, update users
- Enable/disable user accounts
- Assign roles to users
- Login activity tracking

**Endpoints:**
- `GET /api/v1/users` — Get all users
- `GET /api/v1/users/{id}` — Get user by ID
- `POST /api/v1/users/register` — Register new user
- `PUT /api/v1/users/{id}` — Update user
- `DELETE /api/v1/users/{id}` — Delete user

### 3. **Role & Permission Management**
- Predefined roles: `SUPER_ADMIN`, `ADMIN`, `MANAGER`, `INVENTORY_OPERATOR`, `VIEWER`
- Permission-based authorization
- Endpoint-level access control

### 4. **Product Management Module**
- Create, read, update, delete products
- Search products by name/code
- Filter by type, price range
- Pagination support

**Endpoints:**
- `GET /api/v1/products` — Get all products (paginated)
- `GET /api/v1/products/{id}` — Get product by ID
- `POST /api/v1/products` — Create product
- `PUT /api/v1/products/{id}` — Update product
- `DELETE /api/v1/products/{id}` — Soft delete product
- `GET /api/v1/products/search?keyword=` — Search products
- `GET /api/v1/products/filter` — Filter products

### 5. **Stock Management Module**
- Real-time stock tracking
- Add/reduce stock with validation
- Stock history maintenance
- Inventory valuation

**Endpoints:**
- `POST /api/v1/stocks/add` — Add stock quantity
- `POST /api/v1/stocks/reduce` — Reduce stock quantity
- `GET /api/v1/stocks/history/{productId}` — Get stock movement history

### 6. **Tenant Management Module**
- Multi-tenant support with complete data isolation
- Tenant onboarding and configuration
- Tenant-specific operations

**Endpoints:**
- `GET /api/v1/tenants` — Get all tenants
- `GET /api/v1/tenants/{id}` — Get tenant by ID
- `POST /api/v1/tenants` — Create tenant

### 7. **Audit & Logging Module**
- Track all user actions
- Record creation/modification metadata
- Login attempt logging
- Comprehensive audit trails

---

## 🚀 Getting Started

### Prerequisites

- **Java 17** or higher
- **MySQL 8.0** or higher
- **Maven 3.9** or higher
- **Git**

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/sivakumarmarisetti/stock-management-system.git
   cd stock-management-system
   ```

2. **Configure database connection**
   
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/stock_management_system?createDatabaseIfNotExist=true
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

   The application will start on `http://localhost:8080`

---

## 📚 API Documentation

Once the application is running, access the interactive Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

Or view the OpenAPI spec:
```
http://localhost:8080/v3/api-docs
```

---

## 🔐 Authentication

### Login

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "shiva",
    "password": "admin123"
  }'
```

**Response:**
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "type": "Bearer"
  }
}
```

### Using the Token

Add the JWT token to all protected requests:

```bash
curl -X GET http://localhost:8080/api/v1/products \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

---

## 📊 Example Usage

### 1. Register a User

```bash
curl -X POST http://localhost:8080/api/v1/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "John Doe",
    "email": "john@example.com",
    "username": "johndoe",
    "password": "SecurePass123",
    "role": "INVENTORY_OPERATOR"
  }'
```

### 2. Create a Product

```bash
curl -X POST http://localhost:8080/api/v1/products \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{
    "productCode": "PROD001",
    "productName": "Laptop",
    "productType": "Electronics",
    "rate": 50000.00,
    "volume": 1.0,
    "quantity": 10
  }'
```

### 3. Add Stock

```bash
curl -X POST http://localhost:8080/api/v1/stocks/add \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{
    "productId": 1,
    "quantity": 5
  }'
```

### 4. Get Products with Pagination

```bash
curl -X GET "http://localhost:8080/api/v1/products?page=0&size=10&sortBy=id&sortDirection=asc" \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### 5. Search Products

```bash
curl -X GET "http://localhost:8080/api/v1/products/search?keyword=laptop" \
  -H "Authorization: Bearer YOUR_TOKEN"
```

---

## 🛡️ Security Features

### JWT Configuration
- **Secret Key**: Configurable in `application.properties`
- **Token Expiration**: 24 hours (configurable)
- **Algorithm**: HS256

### Rate Limiting
- Prevents API abuse with request throttling
- Customizable rate limits per endpoint
- Returns `429 Too Many Requests` when exceeded

### Authorization
- **Role-Based Access Control (RBAC)**
- **Method-Level Security** with `@PreAuthorize`
- **Endpoint Protection** with Spring Security

### Password Security
- **BCrypt Encryption** for password storage
- **No plaintext passwords** in database

---

## 📁 Project Structure

```
stock-management-system/
├── src/main/
│   ├── java/com/stockmanagement/
│   │   ├── controller/          # REST API endpoints
│   │   ├── service/             # Business logic
│   │   ├── repository/          # Data access layer
│   │   ├── entity/              # JPA entities
│   │   ├── dto/                 # Data Transfer Objects
│   │   ├── mapper/              # MapStruct mappers
│   │   ├── security/            # JWT & security filters
│   │   ├── exception/           # Custom exceptions
│   │   ├── config/              # Configuration classes
│   │   └── util/                # Utility classes
│   └── resources/
│       ├── application.properties
│       ├── db/changelog/        # Liquibase migrations
│       └── templates/
├── pom.xml                      # Maven configuration
└── README.md
```

---

## 🗄️ Database Schema

### Major Tables

| Table | Purpose |
|---|---|
| `users` | User account information |
| `roles` | Role definitions |
| `permissions` | Permission definitions |
| `user_roles` | User-role mapping |
| `products` | Product catalog |
| `stock` | Current inventory levels |
| `stock_history` | Stock movement history |
| `tenants` | Multi-tenant configurations |
| `login_tracking` | Login audit records |
| `audit_logs` | Comprehensive audit trail |

---

## 🧪 Testing

Run the test suite:

```bash
mvn test
```

Run specific test class:

```bash
mvn test -Dtest=ProductServiceTest
```

---

## 📝 Configuration

### Application Properties

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/stock_management_system
spring.datasource.username=root
spring.datasource.password=root

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

# JWT
jwt.secret=YourSecretKeyHere
jwt.expiration=86400000

# Server
server.port=8080

# Swagger
springdoc.swagger-ui.enabled=true
springdoc.api-docs.enabled=true

# Liquibase
spring.liquibase.enabled=true
spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.yaml
```

---

## 🚨 Error Handling

All API responses follow a standardized format:

### Success Response
```json
{
  "success": true,
  "message": "Operation successful",
  "data": { ... }
}
```

### Error Response
```json
{
  "success": false,
  "message": "Product not found",
  "data": null
}
```

---

## 📊 Performance Metrics

- **API Response Time**: < 2 seconds
- **Concurrent Users**: Tested for 100+ concurrent requests
- **Database Queries**: Optimized with indexes
- **Pagination**: Supports up to 1000 records per page

---

## 🔄 Database Migrations

Liquibase automatically applies migrations on startup:

```
src/main/resources/db/changelog/
├── db.changelog-master.yaml
└── changes/
    ├── 001-create-audit-logs-table.yaml
    ├── 002-add-deleted-column-to-products.yaml
    └── ...
```

---

## 🐛 Known Issues & Limitations

- None currently documented

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the **Apache License 2.0** — see the LICENSE file for details.

---

## 👨‍💻 Author

**Sivakumar Marisetti**
- GitHub: [@sivakumarmarisetti](https://github.com/sivakumarmarisetti)
- Email: marisettisivakumar2000@gmail.com

---

## 🎓 Learning Resources

- [Spring Boot Official Docs](https://spring.io/projects/spring-boot)
- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [JWT Introduction](https://jwt.io/introduction)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [REST API Best Practices](https://restfulapi.net/)

---

## 📞 Support

For issues, questions, or suggestions:
1. Check existing [GitHub Issues](https://github.com/sivakumarmarisetti/stock-management-system/issues)
2. Create a new issue with detailed description
3. Contact the author

---

## ✨ Future Enhancements

- [ ] Refresh token support
- [ ] Logout API with token blacklisting
- [ ] Advanced analytics and reporting
- [ ] Email notifications for stock alerts
- [ ] CSV/Excel export functionality
- [ ] Mobile app integration
- [ ] Warehouse management features
- [ ] ERP system integration
- [ ] Real-time WebSocket updates
- [ ] Kubernetes deployment configs

---

## 📊 Project Status

**Status**: ✅ Production Ready

**Last Updated**: May 2026

**Version**: 1.0.0

---

## 🎉 Acknowledgments

- Spring Boot community
- OpenAPI/Swagger team
- Liquibase contributors
- All open-source projects used in this application

---

**Made with ❤️ by Shiva**
