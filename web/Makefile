project ?=nikita5/web
port ?=3000
instance_name=nikita_web

setup:
	npm install
	npm install nodemon
run:
	DEBUG=web:* npm start
# This target will restart server on changes.
tt:
	nodemon server.js

docker:
	docker build -t ${project} .
docker_deploy: docker docker_push
	echo "Pushed to docker, https://hub.docker.com/r/${project}"
docker_run: docker
	-docker stop ${instance_name}
	-docker rm ${instance_name}
	docker run --name=${instance_name} -d -p 3000:3000 ${project}
docker_push:
	docker push ${project}
docker_tail:
	docker logs `docker ps | grep ${project} | awk ' { print $$1 } '`
