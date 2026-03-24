# 🏦 Insurance Platform – Microservices Architecture

## 📌 Overview

This project is a **distributed microservices-based insurance platform** implementing modern architectural patterns:

* Saga Orchestration
* Event-driven communication using Kafka
* Transactional Outbox Pattern
* CQRS (Command Query Responsibility Segregation)
* Retry & Dead Letter Queue (DLQ)
* OAuth2/JWT-based security using Keycloak
* Role-Based Access Control (RBAC)

---

## 🧱 Architecture Summary

```
Client → API (Orchestrator) → Saga → Microservices → Kafka → CQRS → Query Service
                                 ↓
                              Keycloak (Auth)
```

---

## ⚙️ Tech Stack

* Java 17
* Spring Boot 3.x
* Spring Kafka
* Docker (Kafka + Keycloak)
* H2 (Dev DB)
* Keycloak 22.0.5 (Identity & Access Management)

---

## 🧩 Microservices

| Service                   | Responsibility               |
| ------------------------- | ---------------------------- |
| customer-service          | Manages customer data        |
| policy-service            | Creates and manages policies |
| underwriting-service      | Risk evaluation              |
| payment-service           | Payment processing           |
| orchestrator-service      | Saga orchestration           |
| policy-query-service      | CQRS read model              |
| insurance-platform-events | Shared event contracts       |

---

## 🔄 Saga Orchestration Flow

The system uses a **centralized Saga orchestrator**:

```
PolicyCreated → RiskEvaluated → PaymentCompleted → PolicyActivated
```

* Orchestrator triggers commands
* Services emit events
* Saga progresses based on events

---

## 📬 Event-Driven Architecture

* Apache Kafka used for async communication
* Topics:

  * policy-created
  * risk-evaluated
  * payment-completed
  * *-dlq (Dead Letter Queues)

---

## 🧾 Transactional Outbox Pattern

To ensure reliability:

* Events are stored in `outbox_event` table
* Background relay publishes to Kafka
* Guarantees **no message loss**

---

## ♻️ Idempotent Consumers

Each service maintains:

```
processed_events
```

* Prevents duplicate processing
* Ensures **exactly-once behavior (logical)**

---

## 🔁 Retry & DLQ

* Configured using Spring Kafka `DefaultErrorHandler`
* Retry: 3 attempts with backoff
* Failed messages → `<topic>-dlq`

---

## 📊 CQRS (Command Query Responsibility Segregation)

### Write Side

* policy-service
* payment-service

### Read Side

* policy-query-service

### Flow

```
PolicyCreatedEvent → PolicyView (UNDER_REVIEW)
PaymentCompletedEvent → PolicyView (ACTIVE / CANCELLED)
```

---

## 🔐 Security Architecture (Keycloak)

### Identity Provider

* Keycloak (OAuth2 + OpenID Connect)

### Authentication Flow

```
Client → Keycloak → JWT → API
```

### JWT Usage

* `sub` → userId (Keycloak user ID)
* Used across services for identity propagation

---

## 👤 Identity Propagation

```
JWT → Orchestrator → Command → Service → Event → Query Model
```

* `userId` (JWT subject) is propagated
* Enables:

  * auditability
  * traceability
  * user-specific queries

---

## 🛡️ Role-Based Access Control (RBAC)

### Roles

* `CUSTOMER` → can view own policies
* `ADMIN` → can view all policies

### Enforcement Example

```
CUSTOMER → /policies → own data
ADMIN → /policies → all data
```

---

## 🔑 Keycloak Setup

### Realm

```
insurance-platform
```

### Client

```
insurance-client (Public)
```

### Grant Type

```
password (for development)
```

### Users

| Username  | Role     |
| --------- | -------- |
| customer1 | CUSTOMER |
| admin1    | ADMIN    |

---

## 🔗 Domain & Identity Mapping

```
Keycloak User (sub) ↔ Customer (domain)
```

* `sub` is stored/used as `userId`
* Future enhancement: map to Customer entity

---

## 🌐 API Security

* All APIs secured using JWT
* Spring Security Resource Server used
* Unauthorized requests → 401

---

## 🧪 Sample API

### Get Policies (Role-aware)

```
GET /policies/policy
Authorization: Bearer <token>
```

Behavior:

* CUSTOMER → own policies
* ADMIN → all policies

---

## 📦 Deployment

### Docker Compose Services

* Zookeeper
* Kafka
* Keycloak (with realm import)

---

## 🔄 Keycloak Backup & Restore

* Exported realm JSON
* Auto-import on startup using:

```
start-dev --import-realm
```

---

## 🚀 Current Status

✅ Saga orchestration
✅ Event-driven architecture
✅ Outbox pattern
✅ Idempotent consumers
✅ Retry + DLQ
✅ CQRS read model
✅ JWT authentication
✅ Role-based access control

---

## 🔜 Future Enhancements

* Replace password grant with Authorization Code + PKCE
* Service-to-service authentication
* Centralized logging & tracing
* WebSocket for real-time saga updates
* Admin APIs for system monitoring
* Customer onboarding via Keycloak Admin API

---

## 🧠 Key Architectural Decisions

* Separation of Identity (Keycloak) and Domain (Customer Service)
* Event-driven communication for scalability
* CQRS for optimized reads
* Saga for distributed transactions
* JWT-based stateless security

---


