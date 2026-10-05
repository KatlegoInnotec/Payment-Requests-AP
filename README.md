# Payment Requests API

A small Spring Boot REST API for managing internal payment requests.
Staff submit requests; a manager approves or rejects them.

---

## 1. Setup

### Prerequisites

- **Java 21+** — check with `java -version`
- **Maven** — included via the `mvnw` wrapper, no separate install needed

### Clone and install dependencies

```bash
git clone <your-repo-url>
cd payment-requests-demo
./mvnw clean install
```

> On Windows PowerShell, use `.\mvnw.cmd clean install`

### Database

SQLite is used automatically. The database file is created on first run at:

```
${user.home}/payments.db
```

- **Windows:** `C:\Users\<you>\payments.db`
- **macOS/Linux:** `/Users/<you>/payments.db`

No manual database setup required — the schema is created by Hibernate on startup
(`spring.jpa.hibernate.ddl-auto=update`).

---

## 2. Run the Application

```bash
./mvnw spring-boot:run
```

The API starts on **http://localhost:8080**.

To confirm it's running:

```bash
curl http://localhost:8080/payment-requests
```

You should get `200 OK` with `[]` (empty array) on first run.

---

## 3. Run the Tests

```bash
./mvnw test
```

Expected output:

```
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

### What the tests cover

| Test | Proves |
|------|--------|
| `create_shouldSetStatusPendingAndTimestamp` | New requests start as `PENDING` |
| `approve_shouldFailWhenRequestIsNotPending` | **A request cannot be approved twice** (409) |
| `reject_shouldSetStatusAndReason_whenPending` | Rejection stores the reason |
| `findOne_shouldThrowWhenNotFound` | Missing id returns 404 |

Run a single test class:

```bash
./mvnw test -Dtest=PaymentRequestServiceTest
```

---

## 4. Example Requests (curl)

Base URL: `http://localhost:8080`

### 4.1 Create a payment request

```bash
curl -X POST http://localhost:8080/payment-request \
  -H "Content-Type: application/json" \
  -d '{
    "requesterName": "Lerato Dlamini",
    "amount": 4500.00,
    "description": "Printing of A1 posters - supplier invoice INV-2231"
  }'
```

**201 Created**

```json
{
  "id": 1,
  "requesterName": "Lerato Dlamini",
  "amount": 4500.00,
  "description": "Printing of A1 posters - supplier invoice INV-2231",
  "status": "PENDING",
  "createdAt": "2026-10-05T09:15:00Z",
  "rejectionReason": null
}
```

### 4.2 List all requests

```bash
curl http://localhost:8080/payment-request
```

### 4.3 Filter by status

```bash
curl "http://localhost:8080/payment-request?status=PENDING"
```

Allowed values: `PENDING`, `APPROVED`, `REJECTED`

### 4.4 Get one request

```bash
curl http://localhost:8080/payment-request/1
```

### 4.5 Approve a request

```bash
curl -X POST http://localhost:8080/payment-request/1/approve
```

**200 OK** — status becomes `APPROVED`

### 4.6 Reject a request

```bash
curl -X POST http://localhost:8080/payment-request/1/reject \
  -H "Content-Type: application/json" \
  -d '{ "rejectionReason": "Duplicate invoice" }'
```

**200 OK** — status becomes `REJECTED`, `rejectionReason` is stored

### 4.7 Try to approve twice (business rule)

```bash
curl -X POST http://localhost:8080/payment-requests/1/approve
```

**409 Conflict**

```json
{
  "timestamp": "2026-10-05T10:00:00Z",
  "status": 409,
  "error": "Conflict",
  "message": "Only PENDING requests can be approved. This request is already APPROVED"
}
```

---

## 5. HTTP Status Codes

| Code | Meaning | Handled by |
|------|---------|------------|
| `201 Created` | Request created | Controller |
| `200 OK` | Fetch / approve / reject succeeded | Controller |
| `400 Bad Request` | Validation failed (missing field, amount ≤ 0, missing rejection reason) | `GlobalExceptionHandler` |
| `404 Not Found` | Payment request id does not exist | `GlobalExceptionHandler` (`ResourceNotFoundException`) |
| `409 Conflict` | Trying to approve/reject a request that is not `PENDING` | `GlobalExceptionHandler` (`InvalidStateException`) |

Every error response follows the same shape:

```json
{
  "timestamp": "...",
  "status": 409,
  "error": "Conflict",
  "message": "..."
}
```

---

## 6. Postman Collection

Import the collection below to test all endpoints in one click.

**File:** [`payment-requests.postman_collection.json`](./payment-requests.postman_collection.json)

<details>
<summary>Click to view the collection JSON</summary>

```json
{
  "info": {
    "name": "Payment Requests API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "variable": [
    { "key": "baseUrl", "value": "http://localhost:8080" }
  ],
  "item": [
    {
      "name": "Create payment request",
      "request": {
        "method": "POST",
        "header": [{ "key": "Content-Type", "value": "application/json" }],
        "url": "{{baseUrl}}/payment-requests",
        "body": {
          "mode": "raw",
          "raw": "{\n  \"requesterName\": \"Lerato Dlamini\",\n  \"amount\": 4500.00,\n  \"description\": \"Printing of A1 posters - supplier invoice INV-2231\"\n}"
        }
      }
    },
    {
      "name": "List all requests",
      "request": { "method": "GET", "url": "{{baseUrl}}/payment-requests" }
    },
    {
      "name": "Filter by status",
      "request": { "method": "GET", "url": "{{baseUrl}}/payment-requests?status=PENDING" }
    },
    {
      "name": "Get one request",
      "request": { "method": "GET", "url": "{{baseUrl}}/payment-requests/1" }
    },
    {
      "name": "Approve request",
      "request": { "method": "POST", "url": "{{baseUrl}}/payment-requests/1/approve" }
    },
    {
      "name": "Reject request",
      "request": {
        "method": "POST",
        "header": [{ "key": "Content-Type", "value": "application/json" }],
        "url": "{{baseUrl}}/payment-requests/1/reject",
        "body": {
          "mode": "raw",
          "raw": "{\n  \"rejectionReason\": \"Duplicate invoice\"\n}"
        }
      }
    }
  ]
}
```

</details>

### How to import

1. Save the JSON block above as `payment-requests.postman_collection.json` in your project root.
2. Open Postman → **Import** → drag the file in.
3. The collection appears in the sidebar with all 6 requests ready to run.

---

## 7. Project Structure

```
src/main/java/com/example/payment_requests_demo/
├── PaymentRequestsDemoApplication.java   # entry point
├── controller/                           # HTTP layer
│   └── PaymentRequestController.java
├── service/                              # business rules
│   └── PaymentRequestService.java
├── repository/                           # data access
│   └── PaymentRequestRepository.java
├── model/                                # JPA entity
│   └── PaymentRequest.java
├── dto/                                  # request/response shapes
│   ├── CreatePaymentRequestDTO.java
│   └── RejectRequestDTO.java
├── enums/                                # PaymentStatus
│   └── PaymentStatus.java
└── exception/                            # error handling
    ├── GlobalExceptionHandler.java
    ├── ResourceNotFoundException.java
    └── InvalidStateException.java
```

---

## 8. Design Notes

- **Layered architecture** — controller → service → repository.
- **DTOs** decouple the API contract from the database shape.
- **Business rules live in the service layer**, not in controllers.
- **`createdAt` uses `Instant`** for timezone-safe UTC timestamps.
- **Custom exceptions** (`ResourceNotFoundException`, `InvalidStateException`)
  are mapped to the correct HTTP codes by a global `@RestControllerAdvice`,
  keeping controllers clean and error responses consistent.
