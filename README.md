# Employee Hub

This repository contains the backend implementation for the employee management system, developed in **Java** using **Spring Boot**. The API is documented through **bump.sh**, allowing users to easily explore the endpoints.

[![en](https://img.shields.io/badge/lang-en-red)](https://github.com/darioplazaleon/Employee-Hub/blob/master/README.md)
[![es](https://img.shields.io/badge/lang-es-yellow)](https://github.com/darioplazaleon/Employee-Hub/blob/master/README.es.md)

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

### Configuration

Before running the application with Docker, make sure to update the environment variables in the `docker-compose.yml` file under the `backend:` section. These variables include database connection details and application-specific settings.

Here are the variables you need to modify:

- `SPRING_DATASOURCE_URL`: The URL for the PostgreSQL database. You can modify this but make sure you also modify the `application.yml` file and the same for below.
- `SPRING_DATASOURCE_USERNAME`: The username for the PostgreSQL database.
- `SPRING_DATASOURCE_PASSWORD`: The password for the PostgreSQL database.
- `APPLICATION_EMAIL_USERNAME`: The email username for sending notifications. You can use a Gmail account.
- `APPLICATION_EMAIL_PASSWORD`: The email password for sending notifications. You need to get an app password from your email provider.
- `APPLICATION_SECURITY_JWT_SECRET_KEY`: The secret key for JWT authentication. To make one you can use this [tool](https://jwtsecret.com/generate).
- `APPLICATION_SECURITY_JWT_EXPIRATION`: The expiration time for JWT tokens. Don't forget to add the time unit (e.g., 86400000ms for 24 hours).
- `APPLICATION_SECURITY_JWT_REFRESH_TOKEN_EXPIRATION`: The expiration time for JWT refresh tokens. The same from above applies, it needs to be higher than `JWT_EXPIRATION`.

Example:
```yaml
backend:
  environment:
    SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/employees_system
    SPRING_DATASOURCE_USERNAME: postgres
    SPRING_DATASOURCE_PASSWORD: admin
    APPLICATION_EMAIL_USERNAME: jhondoe@example.com
    APPLICATION_EMAIL_PASSWORD: aprx uior jkxv yzwq
    APPLICATION_SECURITY_JWT_SECRET_KEY: d318c37807f51bdb8d809ad09952347af028e59cbed15d6727c1fa1744c2e3c3 Don't use this one, generate your own
    APPLICATION_SECURITY_JWT_EXPIRATION: 86400000  # 24 hours
    APPLICATION_SECURITY_JWT_REFRESH_TOKEN_EXPIRATION: 604800000 # 7 days
```

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


### Usage

The application will start on port 8080 by default. You can access the API at http://localhost:8080.  
When you start the application for the first time, an ADMIN user will be created with the following credentials:  
 
- **Email: jhondoe@example.com**
- **Password: password**

If you want to modify these credentials before starting the application, you should update the code in the IUserService class in the registerAdmin function.  Here is the relevant code snippet from IUserService:

```java
public EmployeeDTO registerAdmin() {
  Position adminPosition = positionRepository.findByName("ADMIN")
          .orElseGet(() -> {
            Position position = new Position();
            position.setName("ADMIN");
            return positionRepository.save(position);
          }); // this code creates the ADMIN position if it doesn't exist

  var user =
      User.builder()
          .email("jhondoe@example.com")
          .position(adminPosition)
          .role(Role.ADMIN)
          .salary(100000L)
          .name("Jhon Doe")
          .active(true)
          .password(passwordEncoder.encode("password"))
          .build();

  userRepository.save(user); // this code saves the user in the database

  return new EmployeeDTO(user);
}
```

## Contact

For any inquiries or support, please contact [contact@plazaleon.tech](mailto:contact@plazaleon.tech).

Thank you for using Employee Hub! We hope it helps you manage your employees efficiently.
