I have successfully configured the MaxWeight project for the Matter preview environment:

**Warmup Script** - Installs Maven and builds the project with dependencies cached.

**Runtime Script** - Runs the Spring Boot web application on port 8080 using `mvn spring-boot:run`.

The application will be accessible at the preview URL once the environment starts. You'll see a web interface where you can:
- Select a week range (1-6)
- Enter your maximum bench press weight
- Generate a training program based on Professor Yuri Verkhoshansky's methodology

The REST API will also be available at `/api/program/{weekNumber}?maxWeight=X` and `/api/programs?fromWeek=X&toWeek=Y&maxWeight=Z`.