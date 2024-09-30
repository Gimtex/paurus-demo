# Paurus Demo

## Development environment

### Recommended and required software/tools
* JDK 21
* 3.9.8
* PostgreSQL 16.4

### PostgreSQL
The application connects to the PostgreSQL database at `jdbc:postgresql://localhost:5432/paurus_demo`. You can modify the database configuration in the `application.properties` file. Table initialization is simplified and managed by the Hibernate property `spring.jpa.hibernate.ddl-auto=create`.

> WARNING:  
> Also tests depends on `paurus_demo` postgres database.

### Swagger documentation
REST API documentation is available on http://localhost:8080/swagger-ui/index.html