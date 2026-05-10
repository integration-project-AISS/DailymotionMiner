# Fase 1: Construcción del ejecutable
# Usamos una imagen de Maven que ya viene con Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
# Copiamos el código fuente al contenedor
COPY . .
# Compilamos y empaquetamos el JAR (saltándonos los tests para ir más rápido)
RUN mvn clean package -DskipTests

# Fase 2: Ejecución
# Usamos una imagen ligera que solo tiene el entorno de ejecución de Java 21
FROM eclipse-temurin:21-jre
WORKDIR /app
# Traemos el archivo .jar generado en la fase anterior
COPY --from=build /app/target/*.jar app.jar
# Exponemos el puerto (Railway usará la variable $PORT que configuramos)
EXPOSE 8080
# Comando para arrancar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]