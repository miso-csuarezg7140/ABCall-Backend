# Usa una imagen base con Java 21 de Eclipse Temurin
FROM eclipse-temurin:21-jdk-jammy

# Argumentos para la ubicación del JAR y el puerto de la aplicación
ARG JAR_FILE=target/*.jar
ARG APP_PORT=8080

# Directorio de trabajo en el contenedor
WORKDIR /app

# Copia el JAR de tu aplicación al contenedor
COPY ${JAR_FILE} reporteria-0.0.1-SNAPSHOT.jar

# Expone el puerto en el que tu aplicación escucha
EXPOSE ${APP_PORT}

# Comando para ejecutar tu aplicación
ENTRYPOINT ["java","-jar","/app/reporteria-0.0.1-SNAPSHOT.jar"]