FROM maven:3.6.1-jdk-8-alpine AS build

COPY . /usr/src/simplewebapp

WORKDIR /usr/src/simplewebapp

RUN mvn clean package -DskipTests

#RUN cd target && ls
####################################

FROM openjdk:8-alpine

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

WORKDIR /home/appuser

COPY --from=build /usr/src/simplewebapp/target/*.jar /home/appuser/simplewebapp.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "simplewebapp.jar"]
