# bank-account-service-api

A Spring Boot REST API implementing banking account operations with transactional consistency and layered architecture.

---

## Overview

The **bank-account-service-api** is a backend service that simulates core banking workflows such as account creation, deposits, withdrawals, and inter-account transfers.

The system focuses on enforcing financial business rules and maintaining data integrity. All monetary operations are executed transactionally to ensure balances remain consistent even under concurrent requests.

Key aspects of the project:
- Transactional business logic
- Layered backend architecture
- RESTful API design
- Persistent storage using JPA/Hibernate
- Audit-style transaction history

---

## Key Features

- Create and manage customer bank accounts
- Deposit and withdraw funds
- Transfer money between accounts
- Transaction history records (immutable)
- Overdraft prevention (business rule enforcement)
- Validation handled in the service layer

---

## Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring Data JPA (Hibernate)**
- **PostgreSQL / H2**
- **Maven**
- RESTful API

---

## Architecture

The project follows a layered architecture:

Controller → Service → Repository → Database

Responsibilities:
- **Controllers**: HTTP request/response handling
- **Services**: business rules, transactions, and validation
- **Repositories**: database persistence

Key design decision: financial operations are handled in the **service layer** to guarantee atomic updates and prevent inconsistent balances.

---

## Core Domain

Entities:
- **Customer** — account owner
- **Account** — balance and status
- **Transaction** — immutable financial record

Business Rules:
- Withdrawals cannot exceed available balance
- Transfers update two accounts atomically
- Transactions are never modified (auditability)

---

## Run Locally

### Prerequisites
- Java 17+
- Maven 3.8+

### Steps

```bash
<<<<<<< Updated upstream
git clone https://github.com/ErmaoCyber/meeting-room-reservation-api.git
cd MeetingRoomBooking
```

Restore packages:

```bash
dotnet restore
```

Run the API:

```bash
dotnet run
=======
git clone https://github.com/ErmaoCyber/bank-account-service-api.git
cd bank-account-service-api
mvn spring-boot:run
>>>>>>> Stashed changes
```

The application will start on a local development port.  
Check the console output after running to see the exact URL.

---

## Example API Endpoints

**Create account**
```
POST /api/accounts
```

**Deposit**
```
POST /api/transactions/deposit
```

**Transfer**
```
POST /api/transactions/transfer
```

---

## What This Project Demonstrates

- Backend service design using Spring Boot
- Separation of concerns with layered architecture
- Transactional consistency using Spring transactions
- Implementation of financial business rules
- REST API design ready for frontend integration

---

## API Documentation (Swagger)

The API includes an interactive Swagger UI for exploring endpoints.

After starting the application:

```
mvn spring-boot:run
```

Open your browser and navigate to:

```
http://localhost:<port>/swagger-ui/index.html
```

The port number will be shown in the console when the server starts.

Example (my local environment):
```
http://localhost:8080/swagger-ui/index.html
```

From Swagger you can:
- View available endpoints
- Send requests directly from the browser
- Inspect request and response models

<img src="images/swagger.png" width="900" alt="Swagger UI preview">

---

