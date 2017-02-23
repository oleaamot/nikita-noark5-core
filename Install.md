# INSTALL

This document is structured in way to provide you with options on how to get
the application up and running. If you want to get an overview of all options
then you might want to read this document in full. We currently have 4 ways to
run the application via maven, make, docker and vagrant.  If you want to use
something else, please update this document.

You probably know by now that this is a Java so you need both maven and Java.
Depending on which route you pick you might also need to install other
dependencies.  The project is developed on a Linux machine with Apache Maven
3.2.1 and Java 1.8. Please make sure both of these are installed before you
attempt to run the project. You can verify your versions with:

    mvn --version
    java -version

## Getting the code

The latest version of the code is available on Github at
[HiOA-ABI/nikita-noark5-core](https://github.com/HiOA-ABI/nikita-noark5-core).
If you haven't cloned the project then:

    git clone https://github.com/HiOA-ABI/nikita-noark5-core.git

If you already have the code consider synchronizing your local copy:
    
    git fetch --all
    git checkout origin/master

## Makefile

This option is a wrapper around the maven command.

To compile the core and start it automatically, from the top level directory:

    make     
    
## Maven

Please note that maven will automatically download all dependencies (jar files)
and put them in a directory ~/.m2. If you are uncomfortable with this, please
check the pom.xml files to find out which jar files will be downloaded.
 
    mvn -Dmaven.test.skip=true clean install
    mvn spring-boot:run

You will see a lot of different startup messages, but there should be no
exceptions. (Please let us know if there are any exceptions).

 The program should output some thing like the following if everything is successful

 	Application 'HiOA Noark 5 Core (Demo mode)' is running! Access URLs:
 	Local: 			http://localhost:8092
 	External: 		http://127.0.1.1:8092
 	contextPath: 	http://127.0.1.1:8092/noark5v4
 	Application is running with following profile(s): [demo] 

## Docker

    docker build -t nikita5/nikita-noark5-core .
    docker run -d -p 9200:9200 elasticsearch:2.4.4 -Des.network.host=0.0.0.0
    docker run --network=host -dit nikita5/nikita-noark5-core

## Vagrant

    vagrant up
    vagrant ssh
    cd /vagrant && make run

## Test data

*Please note that you have to populate the core with some data. In the
directory nikita-noark5-core/core-webapp/src/main/resources/curl you will find
a script that runs a series of curl commands that will populate the database
with some data. On linux all you have to is ./run_curl.sh from the
nikita-noark5-core/core-webapp/src/main/resources/curl directory. If you want
to use this script you have to make sure you have jq installed. jq is a JSON
command line processor and can be found at (https://github.com/stedolan/jq)*
 
In accordance with the Noark 5v4 interface standard, the core advertises its
services. The can be accessed by:

    curl --header Accept:application/vnd.noark5-v4+json --header Content-Type:application/vnd.noark5-v4+json -X GET http://localhost:8092/noark5v4/

A number of services are reported here, some are still in early development.
The service at  http://localhost:8092/noark5v4/hateoas-api/arkivstruktur is the
one you probably are looking for. This is the Noark 5v4 interface.

    {
      "_links" : [ {
        "href" : "http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/",
        "rel" : "http://rel.kxml.no/noark5/v4/api/arkivstruktur"
      } ]
    }

At this point you do need to be logged on to continue. In demo mode there is a
user 'admin/password' that you can use to login. This can be bypassed by
starting with the 'nosecurity' profile
     
Logging on to the core is done with the following command:

    curl -i -X POST -d username=admin -d password=password -c cookie.txt http://localhost:8092/noark5v4/doLogin

This will create a file called *cookie.txt* with your session information.
Subsequent calls to the core will use this session information.

If you then run
    
    curl --header Accept:application/vnd.noark5-v4+json --header Content-Type:application/vnd.noark5-v4+json -X GET  -b cookie.txt  http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/

You should get a list of Noark entities you can interact with.  These are all
mapped to findAll calls and are automatically paginated. They do not have a
next/previous link at the moment

    {
      "_links" : [ {
        "href" : "http://localhost:8092/noark5v4/hateoas-api/arkiv/",
        "rel" : "http://rel.kxml.no/noark5/v4/api/arkivstruktur/arkiv",
        "templated" : true,
        "reltemplatedSpecified" : true
      } ]
     }

Next you can query the core for the various Noark entities. e.g.

    curl v --header Accept:application/vnd.noark5-v4+json --header Content-Type:application/vnd.noark5-v4+json -X GET  -b cookie.txt http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkiv/


Quick note on profiles.

The core supports a number of different profiles. If no profile is set it will
default to the demo profile. The core has the following profiles:

        demo: demo mode
        dev: developer mode
        test: test mode
        prod: production mode
        nosecurity: Switch off security, REST service is usable without logging in

To run using a different profile, use e.g.

    mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=dev,nosecurity"

This will start the core in developer mode without a need to login.

When starting in dev/demo mode you should see a message like the following:

    Dev/demo mode: In-memory database (H2) in use. See http://localhost:8082 .
    Use following JDBC-string: jdbc:h2:mem:n5DemoDb/jdbc:h2:mem:n5DevDb

In these modes the core uses a in-memory database (H2) instead of a standalone
DBMS. It should be possible to use both postgres and mysql 'out-of-the-box' in
production mode.
