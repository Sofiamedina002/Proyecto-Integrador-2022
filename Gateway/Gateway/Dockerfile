FROM bellsoft/liberica-openjdk-alpine-musl:17

COPY target/Gateway-0.0.1-SNAPSHOT.jar /app/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/Gateway-0.0.1-SNAPSHOT.jar"]