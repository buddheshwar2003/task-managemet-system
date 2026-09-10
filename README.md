# Task Management API

A simple Task Management REST API built with **Spring Boot**, **Spring Data JPA**, and **PostgreSQL**.

## Tech Stack

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* PostgreSQL
* Jakarta Validation
* JUnit 5
* Mockito
* Maven

## Features

* Create a task
* Get all tasks
* Get task by ID
* Update a task
* Complete a task
* Delete a task
* DTO validation
* Custom exception handling
* Unit testing

## Requirements

Make sure you have installed:

* Java 21
* Maven
* PostgreSQL

Check Java:

```bash
java -version
```

Check Maven:

```bash
mvn -version
```

Check PostgreSQL:

```bash
psql --version
```

## Database Setup

This project uses **PostgreSQL**.

### 1. Create the database

Open PostgreSQL:

```bash
psql -U postgres
```

Create the database:

```sql
CREATE DATABASE task_management;
```

Check the database:

```sql
\l
```

Exit:

```sql
\q
```

You don't need to manually create the tables. Hibernate will create/update them when the application starts.

## Configure PostgreSQL

Open:

```text
src/main/resources/application.properties
```

Add:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/task_management
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Replace `YOUR_PASSWORD` with your PostgreSQL password.

For example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/task_management
spring.datasource.username=postgres
spring.datasource.password=postgres
```

If PostgreSQL is running on a different port, change `5432`.

## Run the Application

Clone the project:

```bash
git clone <your-repository-url>
```

Go into the project:

```bash
cd task-management
```

Run the application:

```bash
mvn spring-boot:run
```

Or, if you are using the Maven wrapper:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The application will run at:

```text
http://localhost:8080
```

## API Endpoints

| Method | Endpoint               | Description    |
| ------ | ---------------------- | -------------- |
| GET    | `/tasks`               | Get all tasks  |
| GET    | `/tasks/{id}`          | Get task by ID |
| POST   | `/tasks`               | Create task    |
| PUT    | `/tasks/{id}`          | Update task    |
| PATCH  | `/tasks/{id}/complete` | Complete task  |
| DELETE | `/tasks/{id}`          | Delete task    |

### Create Task

```http
POST /tasks
Content-Type: application/json
```

```json
{
  "title": "Learn Spring Boot",
  "description": "Learn Spring Boot and JPA",
  "dueDate": "2026-09-20",
  "status": "pending"
}
```

### Update Task

```http
PUT /tasks/{id}
```

```json
{
  "title": "Updated task",
  "description": "Updated description",
  "dueDate": "2026-09-25",
  "status": "pending"
}
```

### Complete Task

```http
PATCH /tasks/{id}/complete
```

### Delete Task

```http
DELETE /tasks/{id}
```

## Validation

Task request data is validated using Jakarta Bean Validation.

For example:

```java
@NotBlank(message = "Title is required")
private String title;

@NotNull(message = "Due date is required")
private LocalDate dueDate;

@NotNull(message = "Status is required")
private TaskStatus status;
```

Invalid requests return a `400 Bad Request` response.

Example:

```json
{
  "status": 400,
  "message": "Title is required",
  "timestamp": "2026-09-10T15:00:00"
}
```

## Exception Handling

The application uses a global exception handler with `@RestControllerAdvice`.

If a task is not found:

```json
{
  "status": 404,
  "message": "Task not found",
  "timestamp": "2026-09-10T15:00:00"
}
```

## Running Tests

Run all tests:

```bash
mvn test
```

Run a specific test:

```bash
mvn -Dtest=TaskServiceTest test
```

or:

```bash
mvn -Dtest=TaskControllerTest test
```

The project uses **JUnit 5** and **Mockito**.

## Project Structure

```text
src
├── main
│   ├── java
│   │   └── com.backend.task_management
│   │       ├── controller
│   │       ├── dtos
│   │       ├── entities
│   │       ├── enums
│   │       ├── exceptions
│   │       ├── repository
│   │       └── service
│   │
│   └── resources
│       └── application.properties
│
└── test
    └── java
        └── com.backend.task_management
            ├── controller
            └── service
```

## Troubleshooting

### PostgreSQL connection error

Check that PostgreSQL is running and verify:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/task_management
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD
```

Also make sure the database exists:

```sql
CREATE DATABASE task_management;
```

### Tables are not created

Check:

```properties
spring.jpa.hibernate.ddl-auto=update
```

and make sure the PostgreSQL connection is working.

### Port already in use

Change the application port:

```properties
server.port=8081
```

Then open:

```text
http://localhost:8081
```

## Quick Start

```bash
# Clone the project
git clone <your-repository-url>

# Create PostgreSQL database
psql -U postgres

CREATE DATABASE task_management;

# Configure application.properties

# Start the application
mvn spring-boot:run

# Run tests
mvn test
```

The API will be available at:

```text
http://localhost:8080/tasks
```
