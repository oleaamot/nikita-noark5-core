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

Remember to also read [Test data](Testa-data.md) to understand how you can
populate the core with data.

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

There are several containers required to get the application up, you can start them all
with the script below

    scripts/start-containers

## Vagrant

    make vagrant
    vagrant ssh
    cd /vagrant && make run
