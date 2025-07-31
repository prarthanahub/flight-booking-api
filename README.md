
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


# 🧾 Assumptions – Flight Booking API


## 1. 🛡️ User Authentication

- JWT-based security is implemented.
- No user registration or login endpoints are provided.
- Tokens are assumed to be issued externally (e.g., through test configuration or hardcoded utility).
- All booking-related endpoints (`POST`, `PUT`, `DELETE`) require valid JWT tokens.

---

## 2. 👤 Passenger & Booking Model

- Each booking represents a single passenger on a single flight.
- Multiple passengers per booking are not supported.
- Seat assignments and travel class (e.g., Economy/Business) are not included.

---

## 3. 🛫 Flight Data

- Flights are seeded into the H2 in-memory database using an SQL script or programmatically.
- No CRUD operations are provided for flight management.
- Flight details are static (cannot be updated or deleted).
- Seat availability is not managed.

---

## 4. ✅ Validation

- Basic validations like `@NotBlank`, `@Email` are applied.
- No verification for email format, existing users, or duplicate bookings.
- Only field-level validation is included; no complex business rules.

---

## 5. 🔍 Search Endpoint

- Filtering is done via:
  - `origin`, `destination`, optional `departureTime`
- Pagination and sorting are supported using:
  - `page`, `size`, `sortBy`, `sortDir`
- Only exact matches are supported (no fuzzy search).

---

## 6. 📆 Date Handling

- Dates are handled using the system's local timezone.
- `departureTime` is passed in ISO format: `yyyy-MM-ddthh:mi:ss`
- No timezone conversion logic is included.

---

## 7. ✏️ Booking Update

- Only the passenger's `name` and `email` can be updated.
- Flight ID is immutable once the booking is created.
- Booking cannot be reassigned to a different flight.

---

## 8. ❗ Error Handling

- Global exception handler is used for consistent error responses.
- Handles:
  - Resource not found
  - Invalid request data
  - Unauthorized access

---

## 9. 🔐 Security

- JWT tokens are validated for structure and signature only.
- Token expiry and refresh are not managed within this service.

---

## 10. 🗃️ Database

- Uses H2 in-memory DB for testing and development.
- Data is cleared on every restart.

---



## 📄 License

This project is open source and available for any use.
