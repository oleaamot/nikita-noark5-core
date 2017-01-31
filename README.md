# nikita-noark5-core
ABOUT:
This is an open source Noark 5v4 core. The core is very much under development and should be seen as a alpha product until
releases closer to 0.6. The current version is 0.1 and has implemented the arkivstruktur interface of Noark 5v4.  


INSTALL:
This is a Java/maven based project so you need both maven and Java. The project is developed on a Linux machine with
Apache Maven 3.2.1 and Java 1.8. Please make sure both of these are installed before you attempt to run the project.
You should be able to run the following commands from the commandline before attempting to run the program:

    mvn --version
    java -version

Installing should just be a matter of downloading the source code to a given directory and unpacking it. If you have git
installed all you need to do is run 'git clone https://github.com/HiOA-ABI/nikita-noark5-core.git' in the directory you
want to compile from.

Please note that maven will automatically download all dependencies (jar files) and put them in a directory ~/.m2. If 
you are uncomfortable with this, please check the pom.xml files to find out which jar files will be downloaded.

COMPILE:
To compile the core, cd into the directory containing the source e.g. ~/git/nikita-noark5-core. Issue command

    mvn -Dmaven.test.skip=true clean install

NB! See note below for information maven.test.skip.

The program should compile without issues. The output should be similar to:

 	[INFO] Reactor Summary:
 	[INFO]
 	[INFO] nikita-noark5 ..................................... SUCCESS [  0.672 s]
 	[INFO] core-common ....................................... SUCCESS [  7.222 s]
 	[INFO] core-webapp ....................................... SUCCESS [  3.986 s]
 	[INFO] client-test-webapp-rest ........................... SUCCESS [  0.008 s]
 	[INFO] ------------------------------------------------------------------------
 	[INFO] BUILD SUCCESS
 	[INFO] ------------------------------------------------------------------------
 	[INFO] Total time: 12.618 s
 	[INFO] Finished at: 2016-11-04T13:23:54+01:00
 	[INFO] Final Memory: 42M/509M
 	[INFO] ------------------------------------------------------------------------

RUN:
After that cd to the core-webapp directory
(e.g. ~/git/nikita-noark5-core/core-webapp) and run

    mvn spring-boot:run

You will see a lot of different startup messages, but there should be no exceptions. (Please let us know if there are
any exceptions).

 The program should output the following if everything is successful

 	Application 'HiOA Noark 5 Core (Demo mode)' is running! Access URLs:
 	Local: 			http://localhost:8092
 	External: 		http://127.0.1.1:8092
 	contextPath: 	http://127.0.1.1:8092/noark5v4
 	Application is running with following profile(s): [demo]


*Please note that you have to populate the core with some data. In the directory nikita-noark5-core/core-webapp/src/main/resources/curl
 you will find a script that runs a series of curl commands that will populate the database with some data. On linux all
  you have to is ./run_curl.sh from the nikita-noark5-core/core-webapp/src/main/resources/curl directory. If you want to use this 
  script you have to make sure you have jq installed. jq is a JSON command line processor and can be found at (https://github.com/stedolan/jq)*
 
In accordance with the Noark 5v4 interface standard, the core advertises its services. The can be accessed by:

    curl --header Accept:application/vnd.noark5-v4+json --header Content-Type:application/vnd.noark5-v4+json -X GET http://localhost:8092/noark5v4/

A number of services are reported here, some are still in early development. The service at  
http://localhost:8092/noark5v4/hateoas-api/arkivstruktur is the one you probably are looking for. This is the Noark 5v4 interface.

    {
      "_links" : [ {
        "href" : "http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/",
        "rel" : "http://rel.kxml.no/noark5/v4/api/arkivstruktur"
      } ]
    }

At this point you do need to be logged on to continue. In demo mode there is a user 'admin/password' that you can use 
to login. This can be bypassed by starting with the 'nosecurity' profile
     
Logging on to the core is done with the following command:

    curl -i -X POST -d username=admin -d password=password -c cookie.txt http://localhost:8092/noark5v4/doLogin

This will create a file called *cookie.txt* with your session information. Subsequent calls to the core will use this
session information.

If you then run
    
    curl --header Accept:application/vnd.noark5-v4+json --header Content-Type:application/vnd.noark5-v4+json -X GET  -b cookie.txt  http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/

You should get a list of Noark entities you can interact with.  These are all mapped to findAll calls and are automatically paginated. They do not have a next/previous link at the moment

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

The core supports a number of different profiles. If no profile is set it will default to the demo profile. The core has
 the following profiles:

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

In these modes the core uses a in-memory database (H2) instead of a standalone DBMS. It should be possible to use both
postgres and mysql 'out-of-the-box' in production mode.

FEEDBACK
Feedback is greatly appreciated. The project has an open ethos and welcomes all forms of feedback. The project maintains a
mailing list (https://lists.nuug.no/mailman/listinfo/nikita-noark) and issues can be raised via github (https://github.com/HiOA-ABI/nikita-noark5-core/issues).

NOTE. For configuration purposes, take a look at the resources directory of core-webapp for application-*.yml files for
setting properties in the various profiles.

LICENSE NOTE:
Currently we are using AGPLv3 license, but the code should be published as APLv2 when finished (depending on integrated libraries).

TEST NOTE:
Re maven.test.skip. We are skipping tests as there currently is an issue identifying the logged-in user when running
tests. I am assuming the security context will have the default anonymous user, but it is in fact null. This causes the
tests to fail. Currently there is no point running tests.

THANK YOUS:
Thanks to IntelliJ for an idea license (https://www.jetbrains.com/idea/)
