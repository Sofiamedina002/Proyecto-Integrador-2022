FROM bellsoft/liberica-openjdk-alpine-musl:17

COPY target/user-profile-0.0.1-SNAPSHOT.jar /app/

ENTRYPOINT ["java", "-jar", "/app/user-profile-0.0.1-SNAPSHOT.jar"]