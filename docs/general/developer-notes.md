# Nikita developer notes

## 2018-03-20

 - Project structure
 - Continued OData
 - Testing framework
 - Upgrade to spring-boot 2

###Project structure

We changed the project from a multi-module maven project to a single module 
maven project. The reason for this is that we were never able to get spring 
working "just right" as a multi-module project. We weren't for example able to 
get spring testing working properly. We missed out on a lot of springs 
autoconfiguration.

With this change we got rid of a number of annotations and now let spring 
automagically configure everything.

We keep the webapp separate from common logic using package names, so if anyone 
wants to reuse a lot of of the ORM layer it should be possible to take it from  
nikita.common.

We used this opportunity to change the package naming as well.  The old 
naming was no.arkivlab.hioa.nikita. This naming came from an initiative to try to
create a community for public archives software called arkivlab. Nothing ever 
came of this initiative and the project is firmly anchored at OsloMet 
(earlier HiOA) so the need for package naming to include organisation is 
redundant. HiOA is now called OsloMet so removing hioa from the codebase 
makes sense.

###Continued OData
OData support continues. The code is now able to create simple SQL/HQL 
statements from simple OData syntax. The following examples are supported:

    http://localhost/noark5v4/api/arkivstruktur/arkiv?$filter=contains(tittel, 'Oslo') and tittel eq 'goodbye'$top=2$skip=4$orderby=tittel desc
    http://localhost/noark5v4/api/arkivstruktur/arkiv?$filter=tittel eq 'hello'$top=2$orderby=tittel desc
    http://localhost/noark5v4/api/arkivstruktur/arkiv?$top=2$skip=4$filter=tittel eq 'hello'$orderby=beskrivelse desc
    http://localhost/noark5v4/api/arkivstruktur/mappe?$filter=tittel lt 'hello'
    http://localhost/noark5v4/api/arkivstruktur/arkiv?$filter=startsWith(tittel, 'hello')
    http://localhost/noark5v4/api/arkivstruktur/arkiv?$filter=contains(beskrivelse, 'hello')
    http://localhost/noark5v4/api/arkivstruktur/mappe?$filter=tittel eq 'hello'

There is still a bit more work to be done and quality control. But this is a 
good start. Going forward, we will integrate this to the controller, service and
persistence layers. There is an ongoing clean-up of the controller / service 
layers so we will gradually introduce this for all Noark entities.

### Testing framework 

The Testing framework has definitely been one of this project achilles heel. 
I was never able to get a testing framework to work, partly because I have no
experience with testing, our project structure was such that I could not get 
started and I did not really see the difference between unit tests and 
integration tests.

This has also resulted in our approach to CI being worthless as up to now 
everything passes travisCI. So hopefully now we can latch the test framework 
properly into travisCI so future development is properly controlled.  

This is something we're looking at [principles](https://blog.parasoft.com/start-to-love-spring-testing-with-unit-test-assistant-for-java)

###Upgrade to spring-boot 2

We decided that now was probably a good time to upgrade to spring-boot 2. By
doing this we move the project to a base with better long term support and we
gain access to spring-security 5, which is a major improvement.  Native JWT 
support is included in spring-security 5. This leaves the codebase without any
security implementation. So the current builds are starting nikita but it is 
not possible to log on until we get security working.

## 2018-02-23

 - OData support

### OData support
We finally managed to get some OData support into the codebase. We are using
antlr4 and are developing our own OData description in antlr4. This file can 
be found in the odata directory under resources and this is where all future 
OData handling will be derived from. 

An example is to be able to take 

     `startsWith(tittel,'hello')`

and it gets turned into a 

`select * from fonds where ownedBy ='admin' and title LIKE 'hello%'`

We're seeing a mismatch between column names (owned_by) and variable names 
(ownedBy). HQL/ES require object names, while SQL requires column names. We 
started to deal with this but it's going to be a tedious job!

I'm still not sure how to deal with HQL. HQL looks like it wants to use a 
Query object as I guess SQL is not an issue then. But it also supports a SQL 
like syntax that can be used. But using Query means taking the database 
closer into the Parser requiring a CriteriaBuilder. It just seems messy! 

We added a file called odata_samples.txt and a simple java application called
TestODataApp under nikita.webapp.run. We also added our own 
walker currently called NikitaODataWalker. There are subclasses of this class
that take care of the actual conversion from OData filter syntax to SQL/HQL 
as well as Elasticsearch query JSON. 

I've decided to invest a little time on OData to HQL/SQL/ES as I think the 
code is applicable for use by others. I've seen a good few requests for this 
on the Internet but no solution other than Apache Olingo, but that requires 
you to use their stack. We should also considering making this part of the 
code available under a BSD-style license.  

Once this code matures a little, it will be used at the service level in 
order to filter the results of incoming requests. But we are not there yet. I
think we need develop a solution and think about it. I see e.g that we are 
parsing the [contextPath][api] portion of a OData URL, when we just really want
to take the command part.


## 2018-02-16

 - OData support
 - Minor additions
 
### OData support
We finally started looking at OData and it might not be as bad as initially 
feared.  I've been playing with antlr4 trying to figure it out, watched some 
tutorials, downloaded resources etc. I managed to get a parser that can 
handle some of the OData syntax working.

Surprisingly I [found](https://tools.oasis-open.org/version-control/browse/wsvn/odata/trunk/spec/grammar/ANTLR/ODataParser.g4)  
an antlr4 (.g4) file hosted by OASIS that can't be parsed by antlr. Apparently
this file has some problems and I believe a SO flow post said it likely had 
automatically been translated from another format.  It's worrying if an 
organisation like oasis open publishes incorrect standards. Unfortunately I 
do not have time to follow up on this issue.

So that parser hasn't been introduced to the code base yet. I need to see how
I dig into the event based listener and start to do some work on translating
the OData syntax to HQL/SQL. I think it might be a good idea to convert to HQL
as hibernate has support for elastic search and then we could hopefully also 
use elastic search without too many coding additions.

If we do manage to get a decent OData2SQL/HQL/Elastic Search notation, we 
should consider releasing it as an own library. I am seeing some people asking
for this on SO so it's obvious I am not alone in looking for this 
functionality. 
 
### Minor additions
To keep the commit rate up, that we constantly are developing even in slow 
weeks, I've added a few metadata entities. There are about 40 metadata 
entities that need to be introduced and it's very much a boring boiler plate 
job.  

## 2018-02-09

 - Architectural issues

### Architectural issues
We continued work on[issue](https://github
.com/HiOA-ABI/nikita-noark5-core/issues/105) and met some design issues. 
Should HTTPRequest be forward to the Service layer* Ideally it should not, but
the way we are dealing with Hateaos links (being generated on a per user, 
per authorities basis) meant that we had to consider it. Previously we were 
basing the outgoing Hateoas links on the incoming HTTP request. This was to 
easily migrate nikita to various hosts. As long as you had the correct 
starting off address, the client could tell you what you should be creating 
as the address and contextPath. However, you **NEVER TRUST THE CLIENT!**. You
really don't! We are also using JWT without any connection to the IP address,
so we were left wondering if there was a possible attack vector here where 
someone could hijack a token in someway and cause some trouble. Initially I 
don't think so, but we were left with the thought of why bother opening for a
potential issue. Just lock the outgoing hateaos link addresses down to what 
the server wants them to be. 

The downside is that this limits nikita to 1 instance per hostname. Currently
nikita is behind a virtual hostname in apache along with the actual hostname,
but outgoing Hateaos links are set to nikita.hioa.no. 
 
So we are making the decision that nikita is limited to 1 instance per 
hostname so we can move forward with one given architecture. However, it 
probably is not very difficult to make multiple hostname supported by a single 
nikita instance. But we are not going to code something, adding complexity, 
breaking a clear line behind controller and service layer for something that 
may be required in the future.

## 2018-02-02

 - Coverity issues
 - Clean codebase

### Coverity issues
We wrapped up nearly all issues in Coverity. The remaining issues should fall
away when we update the latest version of spring. Coverity is reporting
CSRF issues, but I need to think a little more about that. We won't spend more 
time on Coverity now, but try to deal with issues as soon as they are reported.
The codebase has shrunk a little as a result of this work.
  
### Clean codebase
Hateaos handling was looked at and there were a number of approaches that were
attempted. This work is described in [issue](https://github
.com/HiOA-ABI/nikita-noark5-core/issues/105) and consumed most of the time. As
a result nikita should be a little faster and use less memory. 



## 2018-01-26

 - Coverity issues
 - Clean codebase
 - Project management

### Coverity issues
We have started dealing with the backlog of coverity issues. A lot of these 
are minor issues, that I have never really reflected over. While I find some of
them as not being a problem within the codebase, others might reuse parts of
the codebase and then they could be an issue
  
### Clean codebase
We did some minor work on continuing cleaning the codebase, but mostly 
through dealing with Coverity issues  
 
### Project management 
We spent some time looking at travisCI and trying to figure out how this is 
setup. Also looking to see if we can get the Coverity scans to be included as 
part of travisCI. Need to dig a little more. We have completed a mdl course 
on udemy and expect to start improving the GUI   
 
### Going forward 
Focus should be on wrapping up Coverity issues. We should start looking at 
upgrading the project to the latest Spring version. With this JWT will have 
in-built support and we can remove JWT from the code-base. I'd also like to
start on [SAML](https://www.jasha.eu/blogposts/2015/10/saml-authentication-angularjs-spring-security.html) 
integration. SAML was something we tried early on in the project but I was not
able to get SAML/Feide to work with nikita. Hopefully with a little more 
digging and better understanding of spring we will figure it out.

The project also has a lot of warnings reported in Idea. Once the coverity 
issues are dealt with, we should work at removing warnings and perhaps run Ideas
 own bug testing. 
 
An important [issue](https://github.com/HiOA-ABI/nikita-noark5-core/issues/105) 
added this week is to do a rework of the hateoas handler. I think there is an
extra comma appearing in output at the end that makes the JSON not valid and
there may be a mismatch between collection types so that we are copying data 
to new objects when wrapping database results in Hateoas.
 
Long term, we need to get the testing framework moving. Even if it's just a 
single running test.
 

## 2018-01-19
 - Clean code
 - Better approach to profiles
 - Going forward

### Clean codebase
We have started cleaning up the code base to remove redundant code. The code originally came
for a jhipster example and has evolved. One of the things we saw was that there was no need 
for own classes for DataSource configuration. Leave configuration in application-*.yml files.
This also resulted in a better approach to profiles.

### Profiles 
We had dev, prod, test and demo profiles. These were unnecessary and potentialy could lead to 
undetected bugs in production. ccscanf recommended not to use profiles in this manner. So we
have changed our approach to profiles and limit profiles to database choice and whether or not
to switch on swagger.   

### Going forward
We are using Coverity and there are a lot if issues being reported in Coverity. So we need to 
stop adding new code and try to fix the backlog of Coverity issues. 

We need to make the development process more TDD. Previously we had a half hearted approach to 
this where pere kept his compliance testing tool ahead of nikita development. We need nikita 
to drive this forward as well. Testing does seem to be complex in spring. The fact that the 
project is a multi module project might be complicating the issue.

OData support is going to be make or break for nikita. We will try and add support via a 
odata2sql bridge and see if we can resolve it that way. Various implementations of OData
exist, including a javascript version. But I wonder how close they are to the standard.   

The other part of the project we will focus on is GUI. We have bought some udemy courses
on material design and angular. Hopefully this will help us shine the code a little
better after a while. 
