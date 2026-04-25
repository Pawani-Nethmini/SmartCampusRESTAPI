# Smart Campus REST API

## Overview

This project is a **RESTful web service** developed using **Java (JAX-RS with Jersey)** and deployed on **Apache Tomcat**.

The API simulates a **Smart Campus system** that manages:

* Rooms
* Sensors
* Sensor Readings

It follows REST principles including:

* Resource-based architecture
* Proper HTTP methods (GET, POST, DELETE)
* Status codes
* Error handling
* Sub-resource design

---

## API Design

### Base URL

```
http://localhost:8080/coursework/api/v1
```

### Resources

| Resource                 | Description            |
| ------------------------ | ---------------------- |
| `/rooms`                 | Manage rooms           |
| `/sensors`               | Manage sensors         |
| `/sensors/{id}/readings` | Manage sensor readings |

---

## Technologies Used

* Java 8+
* JAX-RS (Jersey)
* Apache Tomcat 9
* Maven
* JSON (Jackson)

---

## How to Build & Run

### 1️ Clone the repository

```
git clone https://github.com/your-username/smart-campus-api.git
cd smart-campus-api
```

---

### 2️ Build the project

```
mvn clean install
```

---

### 3️ Deploy to Tomcat

* Copy the generated `.war` file from:

```
target/smart-campus-api-1.0-SNAPSHOT.war
```

* Paste into:

```
Tomcat/webapps/
```

* Start Tomcat server

---

### 4️ Access the API

```
http://localhost:8080/coursework/api/v1
```

---

## Important Note

This system uses **in-memory storage**, meaning:

* Data is lost when the server restarts
* You must recreate resources before testing dependent operations

---

## Sample cURL Commands

### 1. Get API Root

```
curl -X GET http://localhost:8080/coursework/api/v1
```

---

### 2. Create a Room

```
curl -X POST http://localhost:8080/coursework/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{"id":"LAB-101","name":"Computer Lab","capacity":40}'
```

---

### 3. Get All Rooms

```
curl -X GET http://localhost:8080/coursework/api/v1/rooms
```

---

### 4. Create a Sensor

```
curl -X POST http://localhost:8080/coursework/api/v1/sensors \
-H "Content-Type: application/json" \
-d '{"id":"TEMP-1","type":"Temperature","status":"ACTIVE","roomId":"LAB-101"}'
```

---

### 5. Add Sensor Reading

```
curl -X POST http://localhost:8080/coursework/api/v1/sensors/TEMP-1/readings \
-H "Content-Type: application/json" \
-d '{"value":26.5}'
```

---

### 6. Get Sensor Readings

```
curl -X GET http://localhost:8080/coursework/api/v1/sensors/TEMP-1/readings
```

---

### 7. Delete Room (Error Case)

```
curl -X DELETE http://localhost:8080/coursework/api/v1/rooms/LAB-101
```

Expected:

```
409 Conflict (Room has active sensors)
```

---

## Error Handling

The API returns structured JSON errors:

```
{
  "error": "Room not found"
}
```

Common status codes:

* 200 OK
* 201 Created
* 400 Bad Request
* 404 Not Found
* 409 Conflict
* 422 Unprocessable Entity

---

## Features Implemented

* CRUD operations for Rooms and Sensors
* Sub-resource for Sensor Readings
* Query parameter filtering
* Custom exception handling
* Proper HTTP status codes
* JSON-based communication

---

## Author

Pawani Nethmini

---
