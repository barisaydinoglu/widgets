# Widgets

A web service to work with widgets. It will hold all records in memory and provide REST API.

## Getting Started

### Prerequisites

* [Java 14](http://www.oracle.com/technetwork/java/javase/downloads/index.html) - Programming language

### Running
With in_memory storage
```
./mvnw spring-boot:run -Dspring-boot.run.profiles=in_memory
```
With embedded h2 database
```
./mvnw spring-boot:run -Dspring-boot.run.profiles=h2
```

API endpoint documentation: <http://localhost:8081/api/swagger-ui/>

### Running the tests

```
./mvnw test
```

## Built With

* [Spring Boot](https://projects.spring.io/spring-boot/) - The framework used
* [Maven](https://maven.apache.org) - Dependency management
* [JUnit](https://junit.org) - Test framework
* [Swagger](https://swagger.io) - Used to generate API docs & UI

## Author

* [Baris Aydinoglu](https://github.com/barisaydinoglu)

## License

This project is licensed under the MIT License
