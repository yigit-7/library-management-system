# STAGE 1: Build the application with Maven and Java 21
FROM maven:3.9.7-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code and build the package
COPY src ./src
RUN mvn clean package -DskipTests

# STAGE 2: Create the final, lightweight image with a Java 21 runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Install curl for health checks, then create a dedicated user and group.
# Do this as root before switching to the non-privileged user.
RUN apk add --no-cache curl && \
    addgroup -S appgroup && \
    adduser -S appuser -G appgroup

# Copy the built .jar file from the 'build' stage
COPY --from=build /app/target/app.jar app.jar

# Switch to the non-root user
USER appuser

EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]