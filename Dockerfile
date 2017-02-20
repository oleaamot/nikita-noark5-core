FROM java:8
VOLUME /tmp

ADD core-webapp/target/core-webapp-0.1.0-spring-boot.jar app.jar
RUN bash -c 'touch /app.jar'

ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8092 8082
