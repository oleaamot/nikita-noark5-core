all: run

build:
	mvn -Dmaven.test.skip=true clean install
run: build
	mvn -f core-webapp/pom.xml spring-boot:run

es:
	docker run -d elasticsearch:2.4.4


