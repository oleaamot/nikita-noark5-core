# docker build says: reference format: repository name must be lowercase
project ?=hioa-abi/nikita-noark5-core

all: run

build:
	mvn -Dmaven.test.skip=true clean install
run: build
	mvn -f core-webapp/pom.xml spring-boot:run

es:
	docker run -d --name=elasticsearch elasticsearch:2.4.4
docker:
	docker build -t ${project} .
docker_deploy: docker docker_push
	echo "Pushed to docker"
docker_run: docker
	docker start elasticsearch
	docker run -dit --link elasticsearch ${project}
docker_push:
	docker push ${project}
