# nikita-noark5-core
ABOUT:
This is an open source Noark 5v4 core. The core is under development and should be seen as a alpha product until
releases closer to 0.6. Please note that this version is 0.0.1-alpha and has been published to just show that the
codebase exists and to give you an idea of where the project is going.


INSTALL:
This is a Java/maven based project so you need both maven and Java. The project is developed on a Linux machine with
Apache Maven 3.2.1 and Java 1.8. Please make sure both of these are installed before you attempt to run the project.
You should be able to run the following commands from the commandline before attempting to run the program:

    mvn --version
    java -version

Installing should just be a matter of downloading the source code to a given directory and unpacking it. If you have git
installed all you need to do is run 'git clone https://github.com/HiOA-ABI/nikita-noark5-core.git' in the directory you
want to compile from.

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

You will see a lot of different startup messages, but there should be no exceptions. (Please let me know if there are
any exceptions).

 The program should output the following if everything is successful

 	Application 'HiOA Noark 5 Core (Demo mode)' is running! Access URLs:
 	Local: 			http://127.0.0.1:8092
 	External: 		http://127.0.1.1:8092
 	contextPath: 	http://127.0.1.1:8092/noark5v4
 	Application is running with following profile(s): [demo]

If you wish to interact with the core you will need to login. In demo mode there is a user 'admin/password' that you can
use to login.

Interacting via curl:

    curl -i -X POST -d username=admin -d password=password -c cookie.txt http://127.0.0.1:8092/noark5v4/doLogin

This will create a file called cookie.txt with your session information. Subsequent calls to the core will use this
session key.

    curl -i --header "Accept:application/vnd.noark5-v4+json" -X GET -b cookie.txt http://127.0.0.1:8092/noark5v4/hateoas-api/arkivstruktur/arkiv

Retrieving a fonds (no:arkiv) or series (no:arkivdel) is pretty much all you can do with the data in demo mode. If you
want to play around a bit more, take a look at the code in client-test-webapp-rest and you can see how to fill in the
entire Noark structure. You can also create your own client and insert/retrieve stuff.

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

    Dev/demo mode: In-memory database (H2) in use. See http://127.0.0.1:8082 .
    Use following JDBC-string: jdbc:h2:mem:n5DemoDb/jdbc:h2:mem:n5DevDb

In these modes the core uses a in-memory database (H2) instead of a standalone DBMS. It should be possible to use both
postgres and mysql 'out-of-the-box' in production mode.


NOTE. For configuration purposes, take a look at the resources directory of core-webapp for application-*.yml files for
setting properties in the various profiles.

LICENSE NOTE:
Currently we are using AGPLv3 license, but it should be Apache v2 when finished (depending on integrated libraries).

TEST NOTE:
Re maven.test.skip. We are skipping tests as there currently is an issue identifying the logged-in user when running
tests. I am assuming the security context will have the default anonymous user, but it is in fact null. This causes the
tests to fail. Currently there is no point running tests.

FINAL NOTE: THIS IS AN EARLY ALPHA RELEASE JUST TO SHARE THE CODEBASE WITH INTERESTED PARTIES AND TO SHOW THE DIRECTION
THE PROJECT IS GOING. THERE IS NO POINT IN FILING BUGS/ISSUES!

THANK YOUS:
Thanks to IntelliJ for an idea license (https://www.jetbrains.com/idea/)