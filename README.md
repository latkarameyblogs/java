Insurance Microservices Platform

A distributed microservices-based insurance platform built using Spring Boot 3, Java 17, and Resilience4j, demonstrating enterprise-grade patterns like SAGA (Orchestration), Circuit Breaker, and domain-driven microservice boundaries.

🚀 Architecture Overview

This system models an insurance policy issuance workflow using independent microservices.

🧩 Services
Service	Port	Responsibility
Customer Service	8081	Manages customer data
Policy Service	8082	Orchestrates policy lifecycle (SAGA)
Underwriting Service	8083	Performs risk evaluation
Payment Service	8084	Processes premium payments

Each service:

Has its own database (H2)

Runs independently

Communicates via REST APIs

Implements fault tolerance via Circuit Breaker

🏛 Architecture Pattern
🔹 SAGA Pattern (Orchestration-Based)

The PolicyService orchestrates the distributed transaction:

Validate Customer

Create Policy (UNDER_REVIEW)

Call Underwriting Service

If approved → Process Payment

Activate policy or compensate

No 2PC used — eventual consistency ensured via state transitions.

🔹 Circuit Breaker (Resilience4j)

Applied on:

UnderwritingClient

PaymentClient

If a downstream service fails:

Fallback logic is triggered

Policy status transitions safely

📊 Policy Lifecycle
UNDER_REVIEW
→ REJECTED (if underwriting fails)
→ PAYMENT_PENDING
→ CANCELLED (if payment fails)
→ ACTIVE (if successful)
🛠 Tech Stack

Java 17

Spring Boot 3

Spring Data JPA

H2 Database

Maven Multi-Module Project

Resilience4j

RestTemplate

Docker (installed, optional)

AWS Account (ready for deployment)

▶️ Running the Application
1️⃣ Clone Repository
git clone <your-repo-url>
cd insurance-platform
2️⃣ Build All Modules
mvn clean install
3️⃣ Start Services (In Separate Terminals)
cd customer-service
mvn spring-boot:run
cd policy-service
mvn spring-boot:run
cd underwriting-service
mvn spring-boot:run
cd payment-service
mvn spring-boot:run
🧪 Testing via Postman
1️⃣ Create Customer

POST
http://localhost:8081/customers

{
  "name": "Amey Latkar",
  "email": "amey@example.com"
}
2️⃣ Create Policy

POST
http://localhost:8082/policies

{
  "customerId": 1,
  "policyType": "HEALTH",
  "premiumAmount": 5000
}

Expected Result:

status = ACTIVE
🔬 Failure Testing
Scenario	Expected Result
High premium	REJECTED
Stop Payment Service	CANCELLED
🧠 What This Project Demonstrates

✔ Distributed SAGA orchestration
✔ Fault tolerance with circuit breaker
✔ Insurance domain modeling
✔ Microservice boundaries
✔ Status-driven workflow
✔ REST-based inter-service communication

🔮 Future Enhancements

Event-driven SAGA (Kafka)

CDC using Debezium

CQRS pattern

API Gateway + AWS Cognito

Docker Compose

AWS ECS / EKS Deployment

Observability (Prometheus + Grafana)

Distributed tracing (Zipkin)

👨‍💻 Author

Amey Latkar
Software Architect | Microservices | Cloud | Distributed Systems
