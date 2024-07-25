# Microservices Overview

This documentation provides an overview of the microservices architecture deployed on various ports.

**Note: Please run the services as per below-mentioned order.**

## Eureka Server

- **URL:** [http://localhost:8761/](http://localhost:8761/)
- **Description:** The Eureka Server is a service registry and discovery server. Microservices register themselves with Eureka, which enables service discovery and load balancing between instances of the same service.

## Config Server

- **URL:** [http://localhost:8888/](http://localhost:8888/)
- **Description:** The Config Server centralizes the configuration management for all microservices. It stores configurations in a version-controlled repository and provides them to microservices on request.

## Customer Service

- **URL:** [http://localhost:9001/](http://localhost:9001/)
- **Description:** The Customer Service manages customer-related operations, including creating, retrieving, updating, and deleting customer information. It interacts with the database and other services as needed.

## Account Service

- **URL:** [http://localhost:9002/](http://localhost:9002/)
- **Description:** The Account Service handles account-related functionalities, such as creating accounts, depositing and withdrawing funds, and managing account balances. It communicates with the database and other services for data operations.

## API Gateway

- **URL:** [http://localhost:8080/](http://localhost:8080/)
- **Description:** The API Gateway serves as the entry point for clients to access the microservices. It handles routing, load balancing, other cross-cutting concerns.
