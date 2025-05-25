# MaxWeight

## Description
Program for generating the number of exercises and weights based on the Professor Yuri Verkhoshansky program.

## Features
- Calculates weight percentages for bench press based on your maximum weight
- Generates a complete training program for 1-6 weeks
- Displays exercises for each day with weight and repetition counts
- Web interface with Thymeleaf and Bootstrap for a modern user experience
- RESTful API for programmatic access to training program data
- Uses Undertow as the embedded web server for improved performance and scalability
- Comprehensive logging for debugging and monitoring
- Robust error handling with descriptive error messages

## Code Structure
The application has been refactored to use modern software engineering techniques and design patterns:

### Design Patterns
- **MVC (Model-View-Controller)**: Separates the application into three main components:
  - **Model**: Represents the data and business logic (`Week`, `Day`, `Exercise`, `TrainingProgram`)
  - **View**: Handles the display of data (Thymeleaf templates and `ProgramView` for console)
  - **Controller**: Manages user input and coordinates the model and view (`WebController`, `ApiController`, `ProgramController`)
- **Factory Pattern**: Used to create training programs (`ProgramFactory`)
- **Strategy Pattern**: Different display strategies for different types of weeks
- **Dependency Injection**: Spring's IoC container manages dependencies between components
- **REST**: RESTful API design for programmatic access to training program data

### Package Structure
- `cz.esgaltur.maxweight`: Main package
  - `model`: Contains the data models
  - `view`: Contains the view classes for console output
  - `controller`: Contains the controller classes (web, API, and console)
  - `service`: Contains service classes for data access and program creation
  - `exception`: Contains custom exception classes
  - `util`: Contains utility classes for console output and other helpers
  - `resources/templates`: Contains Thymeleaf templates for the web interface

### Modern Java Features
- Enums for type safety
- Immutable objects
- Proper exception handling with custom exceptions
- Comprehensive input validation
- Single Responsibility Principle
- Structured logging with SLF4J and Logback
- ANSI color codes for console output
- Factory methods for object creation
- StringBuilder for efficient string concatenation
- Spring Boot for rapid application development
- Dependency Injection for loose coupling
- RESTful API design principles
- Thymeleaf templating engine for server-side rendering
- Undertow web server for high-performance, non-blocking I/O

## Building the Project
This project uses Maven for build management. To build the project:

```bash
mvn clean package
```

This will create a JAR file in the `target` directory.

## Running the Application

### Console Application
To run the original console application:

```bash
java -jar target/maxweight-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### Web Application
To run the Spring Boot web application:

```bash
mvn spring-boot:run
```

Then open your browser and navigate to:

```
http://localhost:8080
```

## Using the REST API

### Get a single training program

```
GET /api/program/{weekNumber}?maxWeight=X
```

- `weekNumber`: The week number (1-6)
- `maxWeight`: Your maximum bench press weight

### Get multiple training programs

```
GET /api/programs?fromWeek=X&toWeek=Y&maxWeight=Z
```

- `fromWeek`: The starting week number (1-6)
- `toWeek`: The ending week number (1-6)
- `maxWeight`: Your maximum bench press weight

## Author
MADE BY Sosnovich Dmitriy(с) 2015.
Refactored with modern software engineering techniques in 2023.
Enhanced with logging, colored output, and improved error handling in 2023.
Converted to a Spring Boot web application with Thymeleaf front-end and REST API in 2023.
Switched from Tomcat to Undertow web server for improved performance in 2023.
