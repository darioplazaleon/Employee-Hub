# Employee Hub

This repository contains the backend implementation for the employee management system, developed in **Java** using **Spring Boot**. The API is documented through **bump.sh**, allowing users to easily explore the endpoints.

[![en](https://img.shields.io/badge/lang-en-red)]('https://github.com/darioplazaleon/Employee-Hub/blob/master/README.md')
[![es](https://img.shields.io/badge/lang-es-yellow)]('https://github.com/darioplazaleon/Employee-Hub/blob/master/README.es.md')

## Description

Employee System Backend offers the following main features:
- **Employee Management:** Register, view, update, and delete employee records.
- **Vacation Management:** Submit, approve, reject, and view vacation requests.
- **Authentication and Security:** Handles login, token refresh, password changes, and validation using JWT.
- **Email Notifications:** Sends notifications using Java Mail Sender.
- **Roles and Permissions:** The system defines different roles (e.g., ADMIN, MANAGER, and USER) that determine access to permitted endpoints.
- **General Statistics:** Displays system metrics and aggregated data.

## Technologies

- **Java 17+**
- **Spring Boot**
- **Spring Data JPA**
- **Spring Security**
- **Lombok**
- **Jakarta EE**
- **Swagger/OpenAPI** (documentation via bump.sh)
- **H2** (for testing)
- **JWT** (for authentication)
- **Java Mail Sender** (for email notifications)
- **Docker & Docker Compose** (for containerization and deployment)

## Project Structure

- **/src/main/java:** Main source code, organized into packages (controllers, services, repositories, DTOs, entities, etc.).
- **/src/main/resources:** Application configuration files (e.g., application.properties).
- **/src/test/:** Unit and integration tests for existing services (e.g., `VacationService` and `UserService`).

## API Documentation

The complete API documentation is generated via **[bump.sh](https://bump.sh/dario-alessandro/doc/employee-hub/)**. Below is a brief overview of each section:

- **Users:** Endpoints for employee management (creation, retrieval, update, and deletion).
- **Vacations:** Endpoints to manage vacation requests (submission, approval, rejection,inst and retrieval).
- **Positions:** Management of company positions and roles.
- **Authentication:** Endpoints for login, token refresh, and password changes.
- **Statistics:** Retrieve aggregated data and system metrics.

Additionally, the documentation highlights that access to each endpoint is role-based. Depending on the user's role (e.g., ADMIN, MANAGER, or USER), access to certain functionalities is granted or restricted. Refer to the complete documentation on [bump.sh](https://bump.sh/dario-alessandro/doc/employee-hub/) for details on each endpoint and the permitted roles.

## Prerequisites

- **Java 17 or higher**
- **Maven** or **Gradle** (depending on your build tool)
- **Docker & Docker Compose** (optional, but recommended for easy deployment)

## Installation and Execution

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your_username/employee-system-back.git
   cd employee-system-back

2. **Build the project:**
   ```bash
   mvn clean install

3. **Run the application:**
    - **Using Maven:**
      ```bash
      mvn spring-boot:run

    - **With Docker:**
        ```bash
        docker-compose up --build

The application will start on port 8080 by default. You can access the API at `http://localhost:8080`.
   