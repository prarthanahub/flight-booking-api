
# ✈️ Flight Booking API

A Spring Boot 3 REST API for searching flights and managing passenger bookings, with JWT authentication and in-memory H2 database.

## ✅ Features

- Search available flights
- Retrieve flight details by ID
- Create and update passenger bookings (JWT protected)
- Cancel bookings


## 🛠️ Technologies

- Java 21
- Spring Boot 3
- Gradle
- Spring Security (JWT)
- H2 Database
- JUnit + Mockito
- Swagger (OpenAPI)



## 🚀 Getting Started

### 1. Clone the project

```bash
git clone <repository-url>
cd FlightBooking
```


### 2. Build and run the app

```bash
./gradlew bootRun
```
Alternative the project can be imported in any IDE and the application can be run as a SpringBootApplication
The app will start at: `http://localhost:8080`

### 3. Access H2 Database (for testing)

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- User: `sa`, Password: *(leave blank)*

### 4. Access Swagger UI

- URL: `http://localhost:8080/swagger-ui/index.html`

---

## 🔐 JWT Authentication

For booking-related endpoints (`/api/bookings/**`), include a JWT token.

You can provide 'dummy-jwt' as JWT for testing purpose.

```
Authorization: Bearer dummy-jwt
```

> In real-world usage, we will integrate a proper login/auth system to issue JWTs.

---

## 🧪 Running Tests

### Unit + Component Tests

```bash
./gradlew test
```
Alternatively the test class can be run from the IDE
Tests cover both success and failure scenarios for the API endpoints.

---

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/acmeair/flightbooking/
│   │   ├── controller/     # REST Controllers
│   │   ├── model/          # DTOs
│   │   ├── entity/         # Entities
│   │   ├── service/        # Business logic
│   │   ├── repository/     # Repositories
│   │   ├── config/         # Security & Swagger Config
│   │   └── exception/      # Global exception handler
│   └── resources/
│       ├── application.properties # App configuration
│       └── data.sql               # Initial flight data
├── test/
│   └── java/com/acmeair/flightbooking/
│       └── FlightComponentTest.java  # Component tests
```

---

## ✨ Example API Endpoints

- `GET /api/flights/search?origin=Wellington&destination=Auckland`
- `GET /api/flights/{id}`
- `POST /api/bookings?flightId=1` (JWT required)
- `PUT /api/bookings/{bookingId}` (JWT required)
- `DELETE /api/bookings/{bookingId}` (JWT required)

---


## 📄 License

This project is open source and available for any use.
