
#  AniShop — Anime Merchandise E-commerce Backend

Backend RESTful API cho nền tảng thương mại điện tử anime, xây dựng bằng Spring Boot 3 + Java 21.

## Tech Stack

- **Java 21** + **Spring Boot 3**
- **Spring Security 6** + **JWT** + Refresh Token
- **MySQL** + **JPA/Hibernate**
- **ModelMapper** + **Lombok** + **Maven**

## ✨ Features

-  Auth: Đăng ký, đăng nhập, JWT access token + refresh token, phân quyền Role/Permission
-  Shop: CRUD shop, soft delete, toggle trạng thái
-  Product: CRUD sản phẩm, tìm kiếm/lọc đa tiêu chí (tên, giá, category, phân trang)
-  Cart: Thêm/xóa/cập nhật giỏ hàng
-  Order: Đặt hàng, quản lý đơn
-  Review: Đánh giá sản phẩm
-  Anime: Catalogue anime tích hợp

## Project Structure

src/
├── config/          # Security, ModelMapper config
├── controller/      # REST controllers (admin + user)
├── service/         # Business logic
├── repository/      # JPA + Custom JPQL queries
│   ├── entity/      # 13 JPA entities
│   └── custom/      # Dynamic query (Builder pattern)
├── model/
│   ├── request/     # Request DTOs + Validation
│   └── reponse/     # Response DTOs
├── filter/          # JWT Filter
├── exception/       # Global Exception Handler
├── enums/           # Role, Permission
└── util/            # SecurityUtils, MapUtils

##  API Endpoints

| Method | Endpoint | Auth | Mô tả |
|--------|----------|------|-------|
| POST | `/register` | Public | Đăng ký |
| POST | `/login` | Public | Đăng nhập |
| GET | `/api/products` | Public | Danh sách sản phẩm (filter + phân trang) |
| GET | `/api/products/{id}` | Public | Chi tiết sản phẩm |
| POST | `/api/products` | User | Thêm sản phẩm |
| PUT | `/api/products` | User | Sửa sản phẩm |
| DELETE | `/api/products/{id}` | User | Xóa sản phẩm |
| GET | `/api/shops` | Public | Tìm shop theo tên |
| POST | `/api/shops` | User | Tạo shop |
| PATCH | `/api/{shopId}/toggle` | User | Bật/tắt shop |
| GET | `/api/animes` | Public | Danh sách anime |
| POST | `/api/admin/**` | ADMIN | Quản trị |

##  Setup

```bash
# 1. Clone repo
git clone https://github.com/mikun0505/AniShop

# 2. Tạo database MySQL
CREATE DATABASE anishop;

# 3. Cấu hình application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/anishop
spring.datasource.username=your_username
spring.datasource.password=your_password

# 4. Run
./mvnw spring-boot:run
```

## Authentication Flow

1. POST `/register` → tạo tài khoản
2. POST `/login` → nhận `accessToken` + `refreshToken`
3. Gắn header: `Authorization: Bearer <accessToken>`
