FROM bellsoft/liberica-openjdk-alpine-musl:17

COPY target/config-service-0.0.1-SNAPSHOT.jar /app/

ENTRYPOINT ["java", "-jar", "/app/config-service-0.0.1-SNAPSHOT.jar"]