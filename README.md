# Tremetric-app

Tremetric-app is a real-time, data-driven application built with Spring Boot, GraphQL, MQTT, and WebSocket technologies. It provides a flexible API for querying and mutating data, while supporting real-time communication and IoT device integration.

## Technologies

- **Spring Boot**: Main application framework
- **GraphQL**: API query language
- **MQTT**: Lightweight messaging protocol for IoT devices
- **WebSocket**: Real-time, full-duplex communication

## Prerequisites

- Java JDK 11 or higher
- Maven 3.6 or higher
- An MQTT broker (e.g., Mosquitto, HiveMQ)

## Setup

1. Clone the repository:
   ```
   git clone https://github.com/mugemanebertin2001/tremetric-app.git
   cd tremetric-app
   ```

2. Configure the application:
   - Update `src/main/resources/application.properties` with your MQTT broker details and other configurations.

3. Build the project:
   ```
   mvn clean install
   ```

4. Run the application:
   ```
   mvn spring-boot:run
   ```

The application should now be running on `http://localhost:8080`.

## Usage

### GraphQL API

Access the GraphQL playground at `http://localhost:8080/graphiql` to interact with the API.

Example query:
```graphql
query {
  getAllMetrics {
    id
    name
    value
  }
}
```

### WebSocket

Connect to the WebSocket endpoint at `ws://localhost:8080/ws` to receive real-time updates.

### MQTT

Publish messages to your configured MQTT topics to send data to the application.


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.