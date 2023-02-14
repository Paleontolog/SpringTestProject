FROM lucasmarfe/gradle-jdk17 as build
COPY . /project
WORKDIR /project
RUN gradle --no-daemon clean build

FROM openjdk:17-alpine
WORKDIR /home
COPY --from=build /project/build/libs/*.jar ./app.jar
COPY --from=build /project/src/main/resources/application.properties ./application.properties
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]