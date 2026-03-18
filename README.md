# Insurance Platform – Event-Driven Choreography SAGA

## Overview

This repository demonstrates a **microservices-based insurance platform** implementing a **choreography-based SAGA**
using **Kafka** for asynchronous event-driven communication.

We migrated from a traditional REST orchestration SAGA to a fully decoupled, event-driven architecture.

Key features:

- Policy Service, Underwriting Service, Payment Service (to be completed)
- Event-driven SAGA using Kafka topics
- Asynchronous, loosely-coupled service communication
- Safe, decoupled lifecycle of policies
- Modular branch structure to preserve orchestration SAGA (`orchestrator-saga`) for reference

---

## Architecture

User
↓
PolicyService
→ saves policy (UNDER_REVIEW)
→ publishes PolicyCreatedEvent → Kafka (policy-created)
↓
UnderwritingService
→ consumes PolicyCreatedEvent
→ applies business rules
→ publishes RiskEvaluatedEvent → Kafka (risk-evaluated)
↓
PolicyService
→ consumes RiskEvaluatedEvent
→ updates status:

- APPROVED → PAYMENT_PENDING
- REJECTED → REJECTED
  ↓
  PaymentService (next)
  → consumes RiskEvaluatedEvent
  → processes payment
  → publishes PaymentCompletedEvent → Kafka (payment-completed)
  ↓
  PolicyService
  → consumes PaymentCompletedEvent
  → updates status → ACTIVE

---

## Services

### 1. Policy Service

- Manages policies and lifecycle status
- Publishes `PolicyCreatedEvent`
- Listens for `RiskEvaluatedEvent` and `PaymentCompletedEvent`
- Status flow:

UNDER_REVIEW → PAYMENT_PENDING → ACTIVE / REJECTED

### 2. Underwriting Service

- Listens to `PolicyCreatedEvent`
- Evaluates risk based on business rules
- Publishes `RiskEvaluatedEvent`
- Example rule:
- Premium > 100,000 → REJECTED
- Premium ≤ 100,000 → APPROVED

### 3. Payment Service (to be implemented)

- Listens to `RiskEvaluatedEvent` for approved policies
- Processes payment
- Publishes `PaymentCompletedEvent`
- Updates PolicyService to ACTIVE status

---

## Kafka Topics

| Topic               | Description                                          |
|---------------------|------------------------------------------------------|
| `policy-created`    | PolicyService publishes new policies                 |
| `risk-evaluated`    | Underwriting publishes risk evaluation results       |
| `payment-completed` | Payment service publishes payment completion results |

---

## Technology Stack

- Java 17
- Spring Boot 3
- Spring Kafka
- H2 Database (for local development)
- Docker & Docker Compose (Kafka & Zookeeper)
- IntelliJ IDEA Community Edition
- GitHub for version control

---

## Branching Strategy

| Branch              | Purpose                                                 |
|---------------------|---------------------------------------------------------|
| `orchestrator-saga` | Original REST orchestration SAGA (kept for reference)   |
| `choreography-saga` | Event-driven choreography SAGA (current default branch) |

---

## Setup & Run Locally

1. **Clone repository**

 ```bash
 git clone <your-repo-url>
 cd insurance-platform

Start Kafka & Zookeeper

docker compose up -d

Start Services

Policy Service:

mvn spring-boot:run -f policy-service

Underwriting Service:

mvn spring-boot:run -f underwriting-service

Test Flow

Create policy using Postman:

POST http://localhost:8082/policies
{
  "customerId": 1,
  "policyType": "HEALTH",
  "premiumAmount": 5000
}

Observe status changes asynchronously via Kafka events.

Stop Kafka elegantly

docker compose down
Lessons Learned

Event-driven SAGA decouples services for scalability and resilience.

JSON serialization/deserialization must be carefully configured for cross-service communication.

Hybrid flows (REST + events) during migration can cause conflicts; remove REST orchestration fully.

Kafka topics serve as the backbone for chaining events across services.

Branching allows safe preservation of old orchestration flow while developing event-driven SAGA.

Next Steps

Convert Payment Service to fully event-driven

Implement PaymentCompletedEvent handling

Add retries, dead-letter topics, and idempotency

Optional: Introduce shared event-contract module for enterprise-grade versioning

Authors

Amey Latkar – Software Architect – latkaramey@gmail.com

License

This project is for educational and architectural demonstration purposes.
