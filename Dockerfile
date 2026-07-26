# Stage 1: Build JAR dengan Maven
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Salin Maven wrapper dan pom.xml terlebih dahulu untuk caching dependensi
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

# Salin seluruh kode sumber dan jalankan build paket JAR
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime Image dengan Alpine JRE 21
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Buat folder uploads untuk file media
RUN mkdir -p uploads

# Salin hasil kompilasi JAR dari stage build
COPY --from=build /app/target/*.jar app.jar

# Dynamic Port Binding untuk Render ($PORT)
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
