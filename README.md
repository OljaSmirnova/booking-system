# Movie Ticket Booking System - Backend

A RESTful backend system for managing movies, showtimes, users, and ticket bookings using Spring Boot. Built with a layered architecture and secure authentication, this system enables both admins and customers to interact efficiently with the movie ticket booking system.

---
##  Project Overview

This project is a backend API for a movie ticket booking system. It allows:

- **Admins** to manage movies and showtimes.
- **Customers** to view available shows and book tickets.
- **Secure login and registration** for all users.
- **Constraint handling** such as preventing overlapping showtimes or double-booking seats.

---
## Functional Features

### Movie Management
- Add, update, delete movies.
- Retrieve all or specific movie details.

### Showtime Management
- Add, update, delete showtimes.
- View showtimes by movie or theater.
- ✅ **Constraint**: No overlapping showtimes per theater.

### User Management
- User registration and login.
- Role-based access (Admin / Customer).
- Admins manage content; Customers book tickets.

### Ticket Booking System
- Book tickets for specific showtimes.
- Track bookings by user, showtime, seat, and price.
- ✅ **Constraint**: No double booking per seat.
- ✅ **Configurable maximum seats per showtime**.
  
---
##  Tech Stack

| Component            | Tech                        |
|----------------------|-----------------------------|
| Backend Framework    | Spring Boot                 |
| Database             | PostgreSQL (H2 for tests)   |
| Authentication       | Spring Security             |
| Testing              | JUnit, Mockito, Testcontainers |
| API Documentation    | Postman Collection (included) |

---

## Getting Started

### Prerequisites

- Java 17+
- Maven 4.0+
- PostgreSQL (running locally)
- IDE (Eclipse)

### Setup Instructions

1. **Clone the repository**  
   ```bash
   git clone https://github.com/OljaSmirnova/booking-system.git
   cd movie-ticket-booking-system

## Authentication & Roles
JWT-based authentication with login/registration.
Admin: can manage movies, showtimes.
Customer: can browse and book tickets.

## Postman Collection
You can use the provided Postman Collection to test all API endpoints.
Steps to use:
Open Postman.
Click on Import → Upload Files → Select the .json collection.
Set environment variables such as {{baseUrl}}, {{token}}.

## Testing
JUnit 5 for unit and integration tests.
Mockito for mocking service/repo layers.
Testcontainers for PostgreSQL test isolation.

##Project Structure
├── controller/         # REST API controllers
├── service/            # Service layer interfaces and implementations
├── repository/         # Spring Data JPA repositories
├── model/              # JPA entity classes
├── dto/                # Data Transfer Objects
├── config/             # Security and application configuration
├── exception/          # Global exception handling
├── resources/
│   ├── application.properties
│   └── data.sql        # (Optional) Preload data

##Notes
data.sql can be used to preload sample data into the database.
Server port is intentionally not hardcoded in README to avoid conflicts in different environments.
