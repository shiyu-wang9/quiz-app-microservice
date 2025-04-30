# QUIZ APP - Microservice in Java

## Project Summary

This project is a microservices-based reimplementation of a previously completed monolithic Spring Boot quiz application (https://github.com/shiyu-wang9/springboot-quiz-app-monolithic). The goal was to explore and apply key microservices concepts through hands-on development.

During this transformation, I used:
- **Spring Boot** to build modular services
- **Spring Cloud Eureka** for service registration and discovery
- **Spring Cloud Gateway** to route all external traffic through a single entry point
- **OpenFeign** to handle inter-service communication declaratively
- **PostgreSQL** as independent data sources for each service

The resulting architecture supports **independent deployment**, **horizontal scalability**, **fault isolation**, and **load-balanced communication** between services.

## Architecture

The system is split into four independently deployable Spring Boot services, with its own configuration and database.

### `question-service`
- Handles all question-related logic
- Owns its own PostgreSQL database
- Registers with Eureka for service discovery
- Designed to scale independently

### `quiz-service`
- Handles quiz creation and scoring
- Communicates with `question-service` via **OpenFeign**
- Stores only quiz metadata (not question details)
- Decoupled architecture allows separate scaling

### `api-gateway-2`
- Central entry point for all client requests
- Uses **Spring Cloud Gateway** to route by service name

### `service-registry (Eureka Server)`
- Enables dynamic service discovery
- Supports **load-balanced communication** between services

## Example Routes via API Gateway

All routes are exposed through the Gateway (`http://localhost:8888`), with services registered under their names:

| Method | Endpoint                                             | Description                              |
|--------|------------------------------------------------------|------------------------------------------|
| GET    | `/question-service/question/allQuestions`            | Get all quiz questions                   |
| GET    | `/question-service/question/category/{category}`     | Get questions by category                |
| POST   | `/question-service/question/add`                     | Add a new question                       |
| POST   | `/quiz-service/quiz/create`                          | Create a new quiz                        |
| GET    | `/quiz-service/quiz/get/{id}`                        | Retrieve quiz by ID                      |
| POST   | `/quiz-service/quiz/submit/{id}`                     | Submit quiz responses and get score      |
