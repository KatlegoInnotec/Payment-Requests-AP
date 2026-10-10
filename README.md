Payment Requests API 

A Spring Boot REST API for managing payment requests. Staff create requests; a manager approves or rejects them. 

 

Setup 

Requirements 

Java 21 or newer 

Maven (use the included mvnw) 

Steps 

git clone <your-repo-url> 
cd payment-requests-demo 
./mvnw clean install 
  

On Windows, use .\mvnw.cmd clean install 

Database 

The app uses SQLite. The database file is created automatically on first run at: 

${user.home}/payments.db 
  

No setup needed. 

 

Run the App 

./mvnw spring-boot:run 
  

The API starts at http://localhost:8080. 

 

Run the Tests 

./mvnw test 
  

Expected: 

Tests run: 3, Failures: 0, Errors: 0, Skipped: 0 
BUILD SUCCESS 
  

What the tests check 

Test 

What it proves 

create_shouldSetStatusPendingAndTimestamp 

New requests start as PENDING 

approve_shouldFailWhenRequestIsNotPending 

A request cannot be approved twice 

reject_shouldSetStatusAndReason_whenPending 

Rejecting saves the reason 

 

Example Requests (curl) 

Base URL: http://localhost:8080 

Create a request 

curl -X POST http://localhost:8080/payment-request \ 
  -H "Content-Type: application/json" \ 
  -d '{ 
    "requesterName": "Lerato Dlamini", 
    "amount": 4500.00, 
    "description": "Printing of A1 posters - supplier invoice INV-2231" 
  }' 
  

201 Created 

{ 
  "id": 1, 
  "requesterName": "Lerato Dlamini", 
  "amount": 4500.00, 
  "description": "Printing of A1 posters - supplier invoice INV-2231", 
  "status": "PENDING", 
  "createdAt": "2026-10-05T09:15:00Z", 
  "rejectionReason": null 
} 
  

List all requests 

curl http://localhost:8080/payment-request 
  

Filter by status 

curl "http://localhost:8080/payment-request?status=PENDING" 
  

Values: PENDING, APPROVED, REJECTED 

Get one request 

curl http://localhost:8080/payment-request/1 
  

Approve a request 

curl -X POST http://localhost:8080/payment-request/1/approve 
  

Reject a request 

curl -X POST http://localhost:8080/payment-request/1/reject \ 
  -H "Content-Type: application/json" \ 
  -d '{ "rejectionReason": "Duplicate invoice" }' 
  

 

Postman Collection 

Save the JSON below as payment-request.postman_collection.json in the project root. 

Import steps 

Open Postman → Import → drag the file in. 

The collection appears in the sidebar. 

Click any request → Send. 

{ 
  "info": { 
    "name": "Payment Request API", 
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
        "url": "{{baseUrl}}/payment-request", 
        "body": { 
          "mode": "raw", 
          "raw": "{\n  \"requesterName\": \"Lerato Dlamini\",\n  \"amount\": 4500.00,\n  \"description\": \"Printing of A1 posters - supplier invoice INV-2231\"\n}" 
        } 
      } 
    }, 
    { 
      "name": "List all requests", 
      "request": { "method": "GET", "url": "{{baseUrl}}/payment-request" } 
    }, 
    { 
      "name": "Filter by status", 
      "request": { "method": "GET", "url": "{{baseUrl}}/payment-request?status=PENDING" } 
    }, 
    { 
      "name": "Get one request", 
      "request": { "method": "GET", "url": "{{baseUrl}}/payment-request/1" } 
    }, 
    { 
      "name": "Approve request", 
      "request": { "method": "POST", "url": "{{baseUrl}}/payment-request/1/approve" } 
    }, 
    { 
      "name": "Reject request", 
      "request": { 
        "method": "POST", 
        "header": [{ "key": "Content-Type", "value": "application/json" }], 
        "url": "{{baseUrl}}/payment-request/1/reject", 
        "body": { 
          "mode": "raw", 
          "raw": "{\n  \"rejectionReason\": \"Duplicate invoice\"\n}" 
        } 
      } 
    } 
  ] 
} 
 
