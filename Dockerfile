FROM java:8
VOLUME /tmp

ENV REPO "https://github.com/HiOA-ABI/nikita-noark5-core"
ENV SRC_DIR "/srv/nikita-noark5-core"
ENV BRANCH "master"

RUN apt-get update
RUN apt-get install -y git make maven
RUN git clone $REPO  $SRC_DIR
RUN git -C $SRC_DIR checkout $BRANCH
RUN make -C $SRC_DIR package
RUN cp $SRC_DIR/core-webapp/target/core-webapp-0.1.0-spring-boot.jar app.jar

RUN apt-get clean
RUN rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

RUN bash -c 'touch /app.jar'

ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8092 8082
