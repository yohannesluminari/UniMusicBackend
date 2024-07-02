# Usa un'immagine base di OpenJDK
FROM openjdk:17-jdk-alpine

# Imposta la directory di lavoro all'interno del container
WORKDIR /app

# Copia il file JAR della tua applicazione nel container
COPY target/UniMuiscBackend-0.0.1-SNAPSHOT.jar /app/app.jar

# Esponi la porta 8080
EXPOSE 8080

# Definisci il comando di avvio
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

