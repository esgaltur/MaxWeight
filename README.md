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

### Modulith Architecture
The application has been restructured to follow the modulith architecture principles. A modulith is a modular monolith, where the application is structured into well-defined modules with clear boundaries, but still deployed as a single unit.

#### Modules

The application is structured into the following modules:

##### Core Module (`cz.esgaltur.maxweight.core`)

Contains the domain model classes that represent the core business entities:

- `Day`: Enum representing training days (DAY1, DAY2)
- `Week`: Enum representing training weeks (WEEK1-WEEK6)
- `Exercise`: Represents a single exercise with weight percentage, actual weight, and repetition count
- `TrainingProgram`: Represents a complete training program for a specific week

This module has no dependencies on other modules and is kept free of external dependencies to maintain a clean domain model.

##### Program Module (`cz.esgaltur.maxweight.program`)

Contains services for creating and managing training programs:

- `ProgramDataService`: Provides weight percentages and repetition counts for each week and day
- `ProgramFactory`: Creates training programs using the data from ProgramDataService

Dependencies:
- Core module: Uses the domain model classes from the core module

This module encapsulates the business logic for generating training programs and does not depend on web or presentation concerns.

##### Web Module (`cz.esgaltur.maxweight.web`)

Contains controllers for the web interface and REST API:

- `WebController`: Handles requests for the web interface and returns Thymeleaf views
- `ApiController`: Provides a REST API for accessing training program data programmatically

Dependencies:
- Core module: Uses the domain model classes from the core module
- Program module: Uses the services from the program module to generate training programs

This module is responsible for the presentation layer of the application and does not contain business logic.

#### Event-Based Communication

The application uses Spring's event system to enable communication between modules without direct dependencies. This is a key aspect of the modulith architecture, allowing for loose coupling between modules.

Events are used in the following scenarios:

1. **Program Creation**: When a new training program is created in the `program` module, a `TrainingProgramCreatedEvent` is published. This event can be consumed by any other module that needs to react to program creation.

The event-based communication is implemented as follows:

- **Event Classes**: Domain events are defined in the `core` module (e.g., `TrainingProgramCreatedEvent`).
- **Event Publishing**: Services in the `program` module publish events using Spring's `ApplicationEventPublisher`.
- **Event Listeners**: Components in other modules can listen for events using the `@EventListener` annotation.

This approach has several benefits:
- Modules can communicate without direct dependencies
- New functionality can be added by creating new event listeners without modifying existing code
- The system becomes more extensible and maintainable

#### Benefits of the Modulith Architecture

1. **Clear Module Boundaries**: Each module has a well-defined responsibility and clear boundaries.
2. **Reduced Coupling**: Modules depend only on what they need, reducing coupling between different parts of the application.
3. **Improved Maintainability**: Changes to one module are less likely to affect other modules.
4. **Better Testability**: Modules can be tested in isolation, making tests more focused and reliable.
5. **Easier to Understand**: The application structure is more intuitive, making it easier for developers to understand and work with the codebase.
6. **Evolutionary Path to Microservices**: If needed in the future, modules can be extracted into separate microservices with minimal changes.
7. **Event-Based Communication**: Modules can communicate through events without direct dependencies.

#### Implementation Details

The modulith architecture is implemented using standard Java packages and Spring's component scanning. The main application class (`MaxWeightApplication`) uses component scanning to discover and register beans from each module:

```java
@SpringBootApplication
@ComponentScan(basePackages = {
    "cz.esgaltur.maxweight.core",
    "cz.esgaltur.maxweight.program",
    "cz.esgaltur.maxweight.web",
    "cz.esgaltur.maxweight.config"
})
public class MaxWeightApplication {
    // ...
}
```

Each module has a `package-info.java` file that documents the module's purpose, responsibilities, and dependencies.

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
- Spring Boot 3.2.0 for rapid application development
- Java 17 for improved performance and modern language features
- Spring Modulith 1.3.5 for modular application architecture
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
Restructured to follow modulith architecture principles for improved maintainability and testability in 2023.
Upgraded to Spring Boot 3.2.0, Java 17, and Spring Modulith 1.3.5 for improved performance and modern features in 2023.
