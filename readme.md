
# 📡 Real-Time Notification Broadcasting Service

**Spring Boot | WebSockets | Redis Pub/Sub | PostgreSQL | Docker**

A distributed, low-latency real-time notification service that receives events from publishers and broadcasts them instantly to connected clients using WebSockets.  
Designed to act as a **central event fan-out layer** for any microservice-based system.

Ideal for use cases like:

- Live status updates
    
- Notifications
    
- Chat/message delivery
    
- Workflow progress updates
    
- E-commerce order status events
        

---

# 🚀 Features

### 🔔 **Real-Time Event Broadcasting**

Instantly pushes notifications to all subscribed WebSocket clients with <100 ms latency.

### 📨 **REST API for Event Ingestion**

Producers publish events using a simple JSON payload.

### 📡 **WebSocket Delivery**

Clients subscribe to a live WebSocket channel to receive notifications as they occur.

### 🔁 **Redis Pub/Sub for Horizontal Scalability**

Multiple instances of the service can publish/receive events using Redis as the event bus.

### 🐳 **Dockerized Setup**

Run the entire stack (service + Redis + DB + UI) with a single command using Docker Compose.

### 📈 **Kubernetes-Ready**

Includes optional manifests for running in a distributed cluster.

---

# 🧱 Architecture

```
                ┌───────────────────────┐
                │   Event Producer(s)   │
                │ (Any microservice/API)│
                └───────────┬───────────┘
                            │ POST /api/v1/events/produce/{channel}
                            ▼
                ┌────────────────────────────┐
                │ Real-Time Notification     │
                │  Broadcasting Service      │
                │  (Spring Boot)             │
                └───────────┬────────────────┘
                            │ Publish
                            ▼
                ┌────────────────────────────┐
                │         Redis Pub/Sub      │
                └───────────┬────────────────┘
                            │ Fan-out
                            ▼
                ┌────────────────────────────┐
                │  All Notification Service  │
                │     Instances (scaled)     │
                └───────────┬────────────────┘
                            │ WebSocket Push 
                            | WebSocket /ws/events/poll/{channel}
                            ▼
                ┌────────────────────────────┐
                │   Connected Clients (UI)   │
                │ Angular / Web / Mobile     │
                └────────────────────────────┘
```

---

# 📦 Tech Stack

|Component|Technology|
|---|---|
|Backend|Spring Boot 4, Java 21|
|Real-time|WebSockets|
|Messaging|Redis (pub/sub)|
|Deployment|Docker, Docker Compose|

---

# 📥 API Endpoints

## **POST /api/v1/events/produce/{channel}**

Publish a new event into the system.

### Request body:

```json
{
  "message": "Order #87521 placed successfully",
}
```

### Response:

```json
{
  "status": "queued"
}
```

---

# 🔌 WebSocket Endpoint

### Connect to:

```
ws://localhost:8080/ws/events/poll/{channel}
```

### Incoming message example:

```json
{
  "message": "Order #87521 placed successfully",
}
```

Clients will receive a live push whenever any producer publishes an event.

---

# ▶️ Running the Project

## **Using Docker Compose (Recommended)**

```sh
docker-compose up --build
```

This starts:

- Redis
    
- PostgreSQL
    
- Notification Service
    
- Optional Angular client
    

Service will be available at:

```
http://localhost:8080
```

WebSocket at:

```
ws://localhost:8080/ws/events/poll/{channel}
```

---

# 🧪 How to Test

### **2. Send event using Postman**

POST → `http://localhost:8080/api/v1/events/produce/{channel}
```json
{
  "message": "SLA breached for ticket #5521"
}
```
Websocket → ws://localhost:8080/ws/notifications

```json
{
  "message": "SLA breached for ticket #5521"
}
```
---

# 📚 Use Cases

    
- E-commerce order tracking
    
- Real-time chat signal delivery
    
- Workflow automation & approval systems
    
- Notification hub for microservice events
    

---
