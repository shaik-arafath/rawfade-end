# ---- BUILD STAGE ----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies first (for caching)
COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:go-offline

# Copy the full source and build the project
COPY src ./src
RUN mvn -q -DskipTests clean package

# ---- RUN STAGE ----
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy built jar from build stage
COPY --from=build /app/target/ecommerce-0.0.1-SNAPSHOT.jar app.jar

# Use Render’s PORT
ENV PORT=8080
EXPOSE 8080

# Run the app
CMD ["sh", "-c", "java -Dserver.port=$PORT -jar app.jar"]

