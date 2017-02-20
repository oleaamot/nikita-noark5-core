FROM openjdk:8

# Environment variables
ENV APP_ROOT "/srv/nikita-noark5-core"

# Install dependencies
RUN apt-get update
RUN apt-get install -y maven git

# Clone source and change the working directory
RUN git clone https://github.com/HiOA-ABI/nikita-noark5-core.git $APP_ROOT
WORKDIR $APP_ROOT

# Get the application running
RUN mvn -Dmaven.test.skip=true clean install
RUN mvn -f core-webapp/pom.xml spring-boot:run

EXPOSE 8092 8082
