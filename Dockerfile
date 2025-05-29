FROM gradle:8.4.0-jdk21 AS builder


RUN gradle bootJar

FROM openjdk:21

COPY --from=builder /home/app/build/libs/*.jar app.jar

ENTRYPOINT["java","-jar","/app.jar"]
