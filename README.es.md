# Employee Hub
Este repositorio contiene la implementación del backend para el sistema de gestión de empleados, desarrollado en **Java** usando **Spring Boot**. La API está documentada a través de **bump.sh**, lo que permite a los usuarios explorar fácilmente los endpoints.

[en](https://img.shields.io/badge/lang-en-red)](https://github.com/darioplazaleon/Employee-Hub/blob/master/README.md)
[![es](https://img.shields.io/badge/lang-es-yellow)](https://github.com/darioplazaleon/Employee-Hub/blob/master/README.es.md)

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

### Configuracion

Antes de ejecutar la aplicación con Docker, asegúrate de actualizar las variables de entorno en el archivo `docker-compose.yml` bajo la sección `backend:`. Estas variables incluyen detalles de conexión a la base de datos y configuraciones específicas de la aplicación.

Aquí están las variables que necesitas modificar:

- `SPRING_DATASOURCE_URL`: La URL para la base de datos PostgreSQL. Puedes modificar esto, pero asegúrate de también modificar el archivo `application.yml` y lo mismo para las siguientes variables.
- `SPRING_DATASOURCE_USERNAME`: El nombre de usuario para la base de datos PostgreSQL.
- `SPRING_DATASOURCE_PASSWORD`: La contraseña para la base de datos PostgreSQL.
- `APPLICATION_EMAIL_USERNAME`: El nombre de usuario del correo electrónico para enviar notificaciones. Puedes usar una cuenta de Gmail.
- `APPLICATION_EMAIL_PASSWORD`: La contraseña del correo electrónico para enviar notificaciones. Necesitas obtener una contraseña de aplicación de tu proveedor de correo electrónico.
- `APPLICATION_SECURITY_JWT_SECRET_KEY`: La clave secreta para la autenticación JWT. Para generar una, puedes usar esta [herramienta](https://jwtsecret.com/generate).
- `APPLICATION_SECURITY_JWT_EXPIRATION`: El tiempo de expiración para los tokens JWT. No olvides agregar la unidad de tiempo (por ejemplo, 86400000ms para 24 horas).
- `APPLICATION_SECURITY_JWT_REFRESH_TOKEN_EXPIRATION`: El tiempo de expiración para los tokens de actualización JWT. Lo mismo de arriba aplica, debe ser mayor que `JWT_EXPIRATION`.

Ejemplo:
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

### Uso

La aplicación se iniciará en el puerto 8080 por defecto. Puedes acceder a la API en http://localhost:8080.  
Cuando inicies la aplicación por primera vez, se creará un usuario ADMIN con las siguientes credenciales:

- **Correo electrónico: jhondoe@example.com**
- **Contraseña: password**

Si deseas modificar estas credenciales antes de iniciar la aplicación, debes actualizar el código en la clase `IUserService` en la función `registerAdmin`. Aquí tienes el fragmento de código relevante de `IUserService`:

```java
public EmployeeDTO registerAdmin() {
  Position adminPosition = positionRepository.findByName("ADMIN")
          .orElseGet(() -> {
            Position position = new Position();
            position.setName("ADMIN");
            return positionRepository.save(position);
          }); // este código crea la posición ADMIN si no existe

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

  userRepository.save(user); // este código guarda el usuario en la base de datos

  return new EmployeeDTO(user);
}
```

## Contacto

Para cualquier consulta o soporte, por favor contacta a [contact@plazaleon.tech](mailto:contact@plazaleon.tech).

Gracias por usar Employee Hub! Esperamos que te ayude a gestionar a tus empleados de manera eficiente.