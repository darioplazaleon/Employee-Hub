# Employee Hub
Este repositorio contiene la implementación del backend para el sistema de gestión de empleados, desarrollado en **Java** usando **Spring Boot**. La API está documentada a través de **bump.sh**, lo que permite a los usuarios explorar fácilmente los endpoints.


## Descripción

El Backend del Sistema de Empleados ofrece las siguientes características principales:
- **Gestión de Empleados:** Registrar, ver, actualizar y eliminar registros de empleados.
- **Gestión de Vacaciones:** Enviar, aprobar, rechazar y ver solicitudes de vacaciones.
- **Autenticación y Seguridad:** Maneja el inicio de sesión, la actualización de tokens, los cambios de contraseña y la validación usando JWT.
- **Notificaciones por Correo Electrónico:** Envía notificaciones usando Java Mail Sender.
- **Roles y Permisos:** El sistema define diferentes roles (por ejemplo, ADMIN, MANAGER y USER) que determinan el acceso a los endpoints permitidos.
- **Estadísticas Generales:** Muestra métricas del sistema y datos agregados.

## Tecnologías

- **Java 17+**
- **Spring Boot**
- **Spring Data JPA**
- **Spring Security**
- **Lombok**
- **Jakarta EE**
- **Swagger/OpenAPI** (documentación a través de bump.sh)
- **H2** (para pruebas)
- **JWT** (para autenticación)
- **Java Mail Sender** (para notificaciones por correo electrónico)
- **Docker & Docker Compose** (para la contenedorización y despliegue)

## Estructura del Proyecto

- **/src/main/java:** Código fuente principal, organizado en paquetes (controladores, servicios, repositorios, DTOs, entidades, etc.).
- **/src/main/resources:** Archivos de configuración de la aplicación (por ejemplo, application.properties).
- **/src/test/:** Pruebas unitarias e integrales para los servicios existentes (por ejemplo, `VacationService` y `UserService`).

## Documentación de la API

La documentación completa de la API se genera a través de **[bump.sh](https://bump.sh/dario-alessandro/doc/employee-hub/)**. A continuación, se presenta una breve descripción de cada sección:

- **Usuarios:** Endpoints para la gestión de empleados (creación, recuperación, actualización y eliminación).
- **Vacaciones:** Endpoints para gestionar solicitudes de vacaciones (envío, aprobación, rechazo y recuperación).
- **Posiciones:** Gestión de posiciones y roles de la empresa.
- **Autenticación:** Endpoints para inicio de sesión, actualización de tokens y cambios de contraseña.
- **Estadísticas:** Recuperar datos agregados y métricas del sistema.

Además, la documentación destaca que el acceso a cada endpoint se basa en roles. Dependiendo del rol del usuario (por ejemplo, ADMIN, MANAGER o USER), se concede o restringe el acceso a ciertas funcionalidades. Consulta la documentación completa en [bump.sh](https://bump.sh/dario-alessandro/doc/employee-hub/) para obtener detalles sobre cada endpoint y los roles permitidos.

## Requisitos Previos

- **Java 17 o superior**
- **Maven** o **Gradle** (dependiendo de tu herramienta de construcción)
- **Docker & Docker Compose** (opcional, pero recomendado para un despliegue fácil)

## Instalación y Ejecución

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/your_username/employee-system-back.git
   cd employee-system-back

2. **Construi el proyecto:**
   ```bash
   mvn clean install

3. **Inicia la aplicacion:**
    - **Usando Maven:**
      ```bash
      mvn spring-boot:run

    - **Con Docker:**
        ```bash
        docker-compose up --build

La aplicacion iniciara por defecto en el puerto 8080:8080. Vas a poder acceder la API en `http://localhost:8080`.
   