FROM eclipse-temurin:17-jdk-jammy AS builder

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x gradlew && ./gradlew clean build -x test

FROM eclipse-temurin:17-jre-jammy AS production

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

FROM production AS layered
COPY --from=production /app/dependencies/ ./
COPY --from=production /app/spring-boot-loader/ ./
COPY --from=production /app/snapshot-dependencies/ ./
COPY --from=production /app/application/ ./

FROM production

LABEL maintainer="schliffen@mail.ru"
LABEL version="1.0"
LABEL description="Spring Boot Application"

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75 -XX:InitialRAMPercentage=75 -XX:-OmitStackTraceInFastThrow"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app/app.jar"]
