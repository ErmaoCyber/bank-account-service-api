# Bank Account Management System

> A Spring Boot REST API implementing banking account management with transactional consistency and layered architecture.

---

## Features

* Create & manage bank accounts (Savings / Checking)
* Deposit, withdraw, and transfer funds
* Transaction history (audit‑style records)
* Overdraft protection (business rule enforcement)
* Validation handled in service layer (not controllers)

---

## Tech Stack

* **Java 17**
* **Spring Boot**
* **Spring Data JPA (Hibernate)**
* **PostgreSQL / H2**
* **Maven**
* RESTful API

---

## Architecture

Layered architecture:

```
Controller → Service → Repository → Database
```

* Controllers: request/response only
* Services: business rules & transactions
* Repositories: persistence

Key design choice: financial operations are executed in the **service layer** to guarantee atomic updates and prevent inconsistent balances.

---

## Core Domain

* **Customer** – account owner
* **Account** – balance & status
* **Transaction** – immutable financial record

Business rules:

* No withdrawal beyond balance
* Transfers update two accounts atomically
* Transactions are never edited (auditability)

---

## Run Locally

```bash
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
```

Server:

```
http://localhost:8080
```

(Optional) Configure PostgreSQL in `application.properties`.

---

## Example API

**Create Account**

```http
POST /api/accounts
```

**Deposit**

```http
POST /api/transactions/deposit
```

**Transfer**

```http
POST /api/transactions/transfer
```

---

## What This Project Shows

* Backend system design with Spring Boot
* Separation of concerns (layered architecture)
* Transactional consistency
* Real‑world business rule implementation
* Ready for future React admin frontend

---

Swagger UI: http://localhost:8080/swagger-ui/index.html

## API Preview

![Swagger UI](images/swagger.png)