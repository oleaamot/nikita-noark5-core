# docker build says: reference format: repository name must be lowercase
project ?=hioa-abi/nikita-noark5-core
repo_tester_dir ?=../noark5-tester
repo_tester ?=https://github.com/petterreinholdtsen/noark5-tester

all: run

build:
	mvn -Dmaven.test.skip=true clean install
run: build
	mvn -f core-webapp/pom.xml spring-boot:run

# This target should be run after spinning up elasticsearch and the application.
# The tester might have more dependencies but you should at least have installed
# python-mechanize.
tt:
	if ! test -d $(repo_tester_dir); then \
	  git clone $(repo_tester) $(repo_tester_dir); \
	fi
	cd $(repo_tester_dir) && ./runtest

# This target is only expected to be run once.  If you have created the
# container but it's not running use `docker start elasticsearch` instead of
# rerunning this target.
es:
	docker run -d --name=elasticsearch -p 9200:9200 elasticsearch:2.4.4 -Des.network.host=0.0.0.0
	# It might take some seconds to get up the elasticsearch container
	sleep 10
	curl http://localhost:9200
	curl -XPUT 'localhost:9200/_template/replicate_template' -d '{ "template" : "*", "settings" : {"number_of_replicas" : 0 } }'
docker:
	docker build -t ${project} .
docker_deploy: docker docker_push
	echo "Pushed to docker"
docker_run: docker
	docker start elasticsearch
	docker run -dit --link elasticsearch ${project}
docker_push:
	docker push ${project}
# Prepare a package which can be used to deploy the application
package:
	mvn -Dmaven.test.skip=true package spring-boot:repackage
