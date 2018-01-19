# Nikita developer notes

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
