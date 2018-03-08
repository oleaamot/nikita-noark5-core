# Test data

*Please note that you have to populate the core with some data. In the
directory nikita-noark5-core/core-webapp/src/main/resources/curl you will find
a script that runs a series of curl commands that will populate the database
with some data. On linux all you have to is ./run_curl.sh from the
nikita-noark5-core/core-webapp/src/main/resources/curl directory. If you want
to use this script you have to make sure you have jq installed. jq is a JSON
command line processor and can be found at (https://github.com/stedolan/jq)*
 
In accordance with the Noark 5v4 interface standard, the core advertises its
services. The can be accessed by:

    curl --header Accept:application/vnd.noark5-v4+json \
      --header Content-Type:application/vnd.noark5-v4+json \
      -X GET http://localhost:8092/noark5v4/

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

    curl -i -X POST -d username=admin -d password=password -c cookie.txt \
      http://localhost:8092/noark5v4/doLogin

This will create a file called *cookie.txt* with your session information.
Subsequent calls to the core will use this session information.

If you then run
    
    curl --header Accept:application/vnd.noark5-v4+json \
      --header Content-Type:application/vnd.noark5-v4+json \
      -X GET -b cookie.txt \
      http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/

You should get a list of Noark entities you can interact with.  These are all
mapped to findAll calls and are automatically paginated. They do not have a
next/previous link at the moment

    {
      "_links" : [ {
        "href" : "http://localhost:8092/noark5v4/hateoas-api/arkiv/",
        "rel" : "http://rel.kxml.no/noark5/v4/api/arkivstruktur/arkiv",
        "templated" : true
      } ]
     }

Next you can query the core for the various Noark entities. e.g.

    curl v --header Accept:application/vnd.noark5-v4+json \
      --header Content-Type:application/vnd.noark5-v4+json \
      -X GET -b cookie.txt \
      http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkiv/


Quick note on profiles.

The core supports different profiles for database access. If no 
profile is set it will use the default profile, which is used 
as a demo profile and use the H2 in-memory database. The core 
has the following profiles:

    mysql
    postgres

These can be configured by editing the resources/application-*.yml.

To run using a different profile, use e.g.

    mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=mysql"

This will start the core in mysql mode.

When starting in  mode you should see a message like the following:

    Dev/demo mode: In-memory database (H2) in use. See http://localhost:8082 .
    Use following JDBC-string: jdbc:h2:mem:n5DemoDb


