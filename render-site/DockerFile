# ---- build stage ----
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# copy pom first for better caching
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# copy source and build
COPY src ./src
RUN mvn -q -DskipTests package

# ---- run stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app

# Render provides PORT; your application.properties already supports ${PORT:8080}
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
