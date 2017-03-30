project ?=nikita5/web
port ?=3000
instance_name=nikita_web

setup:
	npm install
	npm install nodemon

setup-debian:
	apt-get install -y libjs-angularjs node-express node-body-parser wget
	mkdir -pv node_modules
	ln -s /usr/share/javascript/angular.js node_modules/angular
	ln -s /usr/lib/nodejs/express/ node_modules/express
	ln -s /usr/lib/nodejs/body-parser node_modules/body-parser
	# getmdl is not packaged for debian
	mkdir -pv node_modules/material-design-lite
	cd node_modules/material-design-lite
	wget https://code.getmdl.io/1.3.0/material.min.css
	wget https://code.getmdl.io/1.3.0/material.min.js


run:
	DEBUG=web:* npm start
run-debian:
	nodejs server.js
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
