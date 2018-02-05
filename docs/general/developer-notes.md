# Nikita developer notes


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
