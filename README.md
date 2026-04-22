Smart Campus REST API
Overview

This project implements a RESTful API using Java, JAX-RS (Jersey), and the Grizzly HTTP server as part of the Client-Server Architectures coursework .

The system models a Smart Campus infrastructure, providing a scalable interface for managing:

Rooms
Sensors
Sensor Readings

The implementation follows RESTful architectural principles, including resource-oriented design, stateless communication, hierarchical resource structuring, and appropriate use of HTTP methods and status codes.

System Architecture and Design

The API is structured around three core resource models:

Room

Represents a physical space within the campus. Each room maintains a collection of associated sensor identifiers, reflecting the physical deployment of devices.

Sensor

Represents a device deployed within a room. Each sensor maintains its operational status and most recent recorded value.

SensorReading

Represents historical data captured by a sensor, including timestamped measurements.

Design Considerations

The system uses in-memory data structures (HashMap and ArrayList) for storage, as explicitly required by the coursework specification .

This design ensures simplicity and fast access but requires careful handling of shared data due to concurrent request processing.

Base URL
http://localhost:8081/api/v1/
Build and Execution Instructions
1. Clone the repository
git clone https://github.com/your-username/SmartCampus_API.git
cd SmartCampus_API
2. Build the project
mvn clean install
3. Run the application

Run the Main.java class using NetBeans
or execute:

mvn exec:java
4. Testing

The API can be tested using Postman or curl commands.

API Endpoints
Discovery Endpoint
GET /api/v1/

Returns metadata including version information, contact details, and resource links.

Room Management
Get all rooms
GET /api/v1/rooms
Create a room
POST /api/v1/rooms
{
  "id": "R1",
  "name": "Main Lab",
  "capacity": 50
}
Get room by ID
GET /api/v1/rooms/{id}
Delete room
DELETE /api/v1/rooms/{id}

A room cannot be deleted if sensors are assigned. This ensures referential integrity and prevents orphaned resources.

Sensor Operations
Create sensor
POST /api/v1/sensors
{
  "id": "S1",
  "type": "Temperature",
  "status": "ACTIVE",
  "currentValue": 25,
  "roomId": "R1"
}

If the referenced room does not exist, the API returns HTTP 422.

Get all sensors
GET /api/v1/sensors
Filter sensors
GET /api/v1/sensors?type=Temperature

Query parameters are used to enable flexible and optional filtering of resource collections.

Sensor Readings (Sub-resource)
Get readings
GET /api/v1/sensors/{id}/readings
Add reading
POST /api/v1/sensors/{id}/readings
{
  "id": "READ1",
  "timestamp": 1710000000,
  "value": 26.5
}

A successful reading update also updates the parent sensor’s currentValue, ensuring consistency across resources.

Error Handling Strategy

The API implements structured error handling using custom exceptions and ExceptionMapper classes.

Scenario	HTTP Status	Justification
Room has assigned sensors	409 Conflict	Prevents deletion of dependent resources
Invalid room reference	422 Unprocessable Entity	Request is valid but contains invalid data
Sensor under maintenance	403 Forbidden	Operation not allowed due to system state
Resource not found	404 Not Found	Requested resource does not exist
Unexpected error	500 Internal Server Error	Fallback for unhandled exceptions
Conceptual Discussion
Resource Lifecycle in JAX-RS

By default, JAX-RS creates a new instance of a resource class for each request. This request-scoped lifecycle ensures isolation between requests. However, shared data structures such as HashMaps must be carefully managed to avoid race conditions and ensure thread safety in concurrent environments.

HATEOAS and Hypermedia

Hypermedia (HATEOAS) enables APIs to include navigational links within responses. This approach allows clients to dynamically discover available actions, reducing dependency on external documentation and improving adaptability to API changes.

Returning IDs vs Full Objects

Returning only resource identifiers reduces bandwidth usage but increases the number of client requests. Returning full objects improves usability and reduces client-side processing at the cost of larger payload sizes.

DELETE Idempotency

The DELETE operation is idempotent. Repeating the same DELETE request results in the same system state after the initial deletion. Subsequent requests return HTTP 404 without further changes.

Media Type Handling

The use of @Consumes(MediaType.APPLICATION_JSON) ensures that only JSON payloads are accepted. If a client sends data in a different format, the API responds with HTTP 415 (Unsupported Media Type).

Query Parameters vs Path Parameters

Query parameters are more suitable for filtering operations because they are optional and allow flexible querying. Path parameters are typically used to uniquely identify resources.

Sub-resource Locator Pattern

The sub-resource locator pattern improves modularity by delegating nested resource handling to separate classes. This reduces complexity and enhances maintainability in large-scale APIs.

HTTP 422 vs 404

HTTP 422 is more semantically accurate when the request structure is valid but contains invalid data, such as referencing a non-existent resource within the payload.

Security Considerations

Exposing internal stack traces can reveal implementation details such as class structures and file paths, which may be exploited by attackers. Therefore, all exceptions are mapped to controlled responses.

Logging and Cross-Cutting Concerns

Logging is implemented using JAX-RS filters, which handle request and response logging centrally. This approach separates cross-cutting concerns from business logic, improves maintainability, and avoids code duplication.

Sample cURL Commands
curl http://localhost:8081/api/v1/rooms
curl -X POST http://localhost:8081/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{"id":"R1","name":"Lab","capacity":40}'
curl http://localhost:8081/api/v1/sensors
curl -X POST http://localhost:8081/api/v1/sensors \
-H "Content-Type: application/json" \
-d '{"id":"S1","type":"Temperature","status":"ACTIVE","currentValue":25,"roomId":"R1"}'
curl -X POST http://localhost:8081/api/v1/sensors/S1/readings \
-H "Content-Type: application/json" \
-d '{"id":"READ1","timestamp":1710000000,"value":26.5}'
Technologies Used
Java
JAX-RS (Jersey)
Grizzly HTTP Server
Maven



