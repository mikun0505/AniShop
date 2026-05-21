# AniShop — Anime Merchandise E-commerce Backend

> RESTful API backend for an anime merchandise e-commerce platform, built with **Java 21 + Spring Boot 3**.  
> Personal project — goal: practice production-ready backend system design.

---

## Live Demo

| Resource | Link |
|---|---|
| API Base URL | `https://anishop-api.onrender.com` |
| Swagger UI | `https://anishop-api.onrender.com/swagger-ui.html` |

> Note: Free tier on Render — first request may take ~30s to wake up.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language & Framework | Java 21, Spring Boot 3.5, Spring MVC |
| Security | Spring Security 6, JWT, Refresh Token, Role/Permission |
| Database | MySQL 8, JPA/Hibernate, JPQL |
| Cache | Redis 7 — @Cacheable, @CacheEvict, token blacklist, view counter |
| Testing | JUnit 5, Mockito |
| DevOps | Docker, Docker Compose |
| Tools | Git, Postman, Maven |

---

## Architecture

```
Client (Postman / Browser)
        ↓  HTTP request
Spring Security 6 + JWT Filter
        ↓  authenticated request
Controller Layer (35+ REST endpoints)
        ↓
Service Layer (business logic)
     ↙        ↘
Repository     Redis Cache
(JPA/Hibernate) (@Cacheable)
     ↓
MySQL Database (14 tables)
```

---

## Features

- **Auth** — register, login, JWT stateless, 2-layer Role/Permission (`ADMIN_CREATE`, `ADMIN_READ`, `ADMIN_UPDATE`, `ADMIN_DELETE`)
- **Product** — CRUD, multi-criteria search/filter (name, price, category, shop, pagination) via dynamic JPQL + Builder Pattern
- **Shop** — CRUD, toggle active status, soft delete
- **Cart** — add/update/remove items
- **Checkout** — place order, price snapshot at purchase time, prevent buying from own shop
- **Order** — order history, status management
- **Review** — product reviews with rating
- **Anime** — anime catalogue linked to products
- **Redis** — product list caching, token blacklist on logout, real-time view counter per product

---

## API Endpoints

### Auth
| Method | Endpoint | Auth | Description |
|---|---|---|---|
| POST | `/register` | Public | Create account |
| POST | `/login` | Public | Login, receive JWT |
| POST | `/logout` | Bearer | Logout, blacklist token |

### Product
| Method | Endpoint | Auth | Description |
|---|---|---|---|
| GET | `/api/products` | Public | List + filter (name, price, category, shop, page) |
| GET | `/api/products/{id}` | Public | Product detail + increment view counter |
| GET | `/api/products/{id}/views` | Public | Get view count |
| POST | `/api/products` | User | Create product |
| PUT | `/api/products` | User | Update product |
| DELETE | `/api/products/{id}` | User | Soft delete |

### Shop
| Method | Endpoint | Auth | Description |
|---|---|---|---|
| GET | `/api/shops` | Public | Search shops by name |
| POST | `/api/shops` | User | Create shop |
| PATCH | `/api/{shopId}/toggle` | User | Toggle shop active status |

### Cart & Checkout
| Method | Endpoint | Auth | Description |
|---|---|---|---|
| POST | `/api/cart-items` | User | Add to cart |
| PUT | `/api/cart-items/{id}` | User | Update quantity |
| DELETE | `/api/cart-items/{id}` | User | Remove from cart |
| POST | `/api/checkout/{userId}` | User | Place order |

### Admin
| Method | Endpoint | Auth | Description |
|---|---|---|---|
| POST | `/api/admin/**` | `ADMIN_CREATE` | Create |
| PUT | `/api/admin/**` | `ADMIN_UPDATE` | Update |
| DELETE | `/api/admin/**` | `ADMIN_DELETE` | Delete |
| GET | `/api/admin/**` | `ADMIN_READ` | Read |

---

## Database Schema

14 tables: `users` · `roles` · `user_roles` · `shops` · `products` · `product_images` · `caregories` · `genres` · `carts` · `cart_items` · `orders` · `order_details` · `reviews` · `animes` · `refresh_token`

All tables include `deleted` field for soft delete to preserve data history.

---

## Authentication Flow

```
POST /register  →  create account
POST /login     →  { accessToken, refreshToken }
Request         →  Header: Authorization: Bearer <accessToken>
POST /logout    →  token added to Redis blacklist (TTL = remaining token lifetime)
```

---

## Redis Cache Strategy

| Feature | Implementation |
|---|---|
| Product list cache | `@Cacheable(value="allProduct", key="#params.toString()")` |
| Cache invalidation | `@CacheEvict(value="allProduct", allEntries=true)` on create/update/delete |
| Token blacklist | `SET blacklist:<token> 1 EX <remaining_ttl>` on logout |
| View counter | `INCR product:view:<id>` on each `GET /api/products/{id}` |
| TTL | 10 minutes for product cache |

---

## Run Locally

### One-command startup (Docker)

```bash
# Clone
git clone https://github.com/mikun0505/AniShop.git
cd AniShop

# Start everything (Spring Boot + MySQL + Redis)
docker compose up -d

# Check status
docker ps
```

App: `http://localhost:8080`  
Swagger UI: `http://localhost:8080/swagger-ui.html`

Default admin account created on first run:
```
email: duoc@gmail.com
password: cuocAdmin
```

```bash
docker compose down        # stop
docker compose down -v     # stop + delete data
```

---

## Unit Tests

```bash
./mvnw test
```

Coverage: `UserService` — 6 test cases with JUnit 5 + Mockito covering register, login, searchId, searchUser, deleteById — happy path and exception path.

---

## Project Structure

```
src/main/java/com/example/java/anishop/
├── config/          # SecurityConfig, RedisConfig
├── controller/
│   ├── admin/       # Admin endpoints
│   └── user/        # Public/user endpoints
├── service/impl/    # Business logic
├── repository/
│   ├── entity/      # 14 JPA entities
│   └── custom/impl/ # Dynamic JPQL + Builder Pattern
├── model/
│   ├── request/     # Request DTOs + Bean Validation
│   └── reponse/     # Response DTOs (Serializable for Redis)
├── filter/          # JwtFilter
├── exception/       # GlobalExceptionHandler
└── enums/           # Role, Permission, OrderStatus
```

---

## Author

**Tống Quốc Được** — First-year IT student, Nong Lam University HCMC  
[mikun7829@gmail.com](mailto:mikun7829@gmail.com) · [GitHub](https://github.com/mikun0505)