
# 📡 Real-Time Notification Broadcasting Service

**Spring Boot | WebSockets | Redis Pub/Sub | PostgreSQL | Docker**

A distributed, low-latency real-time notification service that receives events from publishers and broadcasts them instantly to connected clients using WebSockets.  
Designed to act as a **central event fan-out layer** for any microservice-based system.

Ideal for use cases like:

- Live status updates
    
- ITSM ticketing notifications
    
- Chat/message delivery
    
- Workflow progress updates
    
- E-commerce order status events
    
- Monitoring & DevOps alerts
    

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

### 💾 **Optional Persistence (PostgreSQL)**

Store notifications for offline users or fetch history.

### 👥 **Client Presence Tracking**

Track online/offline users in Redis for optimized broadcasting.

### 🐳 **Dockerized Setup**

Run the entire stack (service + Redis + DB + UI) with a single command using Docker Compose.

### 📈 **Kubernetes-Ready**

Includes optional manifests for running in a distributed cluster.

---

# 🧱 Architecture

```
                ┌───────────────────────┐
                │   Event Producer(s)    │
                │ (Any microservice/API) │
                └───────────┬───────────┘
                            │ POST /api/events
                            ▼
                ┌────────────────────────────┐
                │ Real-Time Notification      │
                │  Broadcasting Service       │
                │  (Spring Boot)              │
                └───────────┬────────────────┘
                            │ Publish
                            ▼
                ┌────────────────────────────┐
                │         Redis Pub/Sub       │
                └───────────┬────────────────┘
                            │ Fan-out
                            ▼
                ┌────────────────────────────┐
                │  All Notification Service   │
                │     Instances (scaled)      │
                └───────────┬────────────────┘
                            │ WebSocket Push
                            ▼
                ┌────────────────────────────┐
                │   Connected Clients (UI)    │
                │ Angular / Web / Mobile      │
                └────────────────────────────┘
```

---

# 📦 Tech Stack

|Component|Technology|
|---|---|
|Backend|Spring Boot 3, Java 17|
|Real-time|WebSockets|
|Messaging|Redis (pub/sub)|
|Database (optional)|PostgreSQL|
|Frontend (example)|Angular|
|Deployment|Docker, Docker Compose|
|Scaling (optional)|Kubernetes|

---

# 📥 API Endpoints

## **POST /api/events**

Publish a new event into the system.

### Request body:

```json
{
  "type": "ORDER_PLACED",
  "target": "user123",
  "message": "Order #87521 placed successfully",
  "metadata": {
    "priority": "HIGH",
    "timestamp": "2025-01-10T10:15:30"
  }
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
ws://localhost:8080/ws/notifications
```

### Incoming message example:

```json
{
  "eventId": "e1739ab2",
  "type": "ORDER_PLACED",
  "message": "Order #87521 placed successfully",
  "timestamp": "2025-01-10T10:15:30"
}
```

Clients will receive a live push whenever any producer publishes an event.

---

# 🗄️ Database Schema (Optional Persistence)

Table: **notifications**

|Column|Type|Description|
|---|---|---|
|id|UUID|Notification ID|
|target_user|varchar|Recipient|
|type|varchar|Event type|
|message|text|Notification text|
|created_at|timestamp|Timestamp|
|read|boolean|Read/unread status|

You can include message history or offline sync using this table.

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
ws://localhost:8080/ws/notifications
```

---

# 🧪 How to Test

### **1. Open two browser tabs**

Both connect to WebSocket endpoint.

### **2. Send event using Postman**

POST → `http://localhost:8080/api/events`

```json
{
  "type": "TICKET_UPDATED",
  "message": "SLA breached for ticket #5521"
}
```

### **3. Both browser tabs instantly receive the notification**

Shows fan-out & broadcasting.

### **4. Start multiple backend instances**

Redis ensures events broadcast across all nodes.

---

# 📚 Use Cases

- Live ITSM ticket alerts
    
- Real-time dashboards (DevOps, analytics)
    
- E-commerce order tracking
    
- Real-time chat signal delivery
    
- Workflow automation & approval systems
    
- Notification hub for microservice events
    

---

# 🧩 Folder Structure

```
real-time-notification-service/
 ├── src/
 ├── docker/
 ├── k8s/
 ├── README.md
 ├── docker-compose.yml
 └── ui-client/ (optional Angular client)
```

---

# 🔮 Future Enhancements

- User-specific channels & subscriptions
    
- Group/event-topic channels
    
- Offline notifications & fetch APIs
    
- Priority-based delivery
    
- Rate limiting & throttling
    
- Message retries
    
- Kafka integration for durable event ingestion
    
- Role-based notification routing
    
- Push notifications for mobile clients
    

---

# 🏆 Author

**Alquama Salim**

- GitHub: [https://github.com/alquama00s](https://github.com/alquama00s)
    
- LinkedIn: [https://linkedin.com/in/alquama00s](https://linkedin.com/in/alquama00s)
    

---

# 🎉 Final Note

This service demonstrates:

- Real-time system design
    
- Pub/sub event-driven architecture
    
- Low-latency fan-out
    
- Horizontal scalability
    
- Practical DevOps deployment
    

It’s a perfect project to showcase **backend engineering + distributed systems knowledge** in your résumé.
