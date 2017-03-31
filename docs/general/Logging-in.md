# Logging in

The core uses a [JWT](https://tools.ietf.org/html/rfc7519) approach to logging in. The following [blogpost](https://www.toptal.com/java/rest-security-with-jwt-spring-security-and-java) gives
a nice overview of what this entails. There is an associated github [repository](https://github.com/szerhusenBC/jwt-spring-security-demo) that implements this. 

The following JSON example shows the login payload for a user logging in with the username 'admin' and password 
'password':
  
```
{
  "username" : "admin",
  "password" : "password"
}
```

An example og logging on to the core with curl is:
 
   curl i --header Content-Type:application/json -X POST  --data '{"username" : "admin", "password" : "password"}' http://localhost:8092/noark5v4/auth

This returns the following response that includes the token to be used for subsequent calls:

```
{
   "token" : "fyJhbGciOiJIUzUxMiJ9.eyJzdWIiO.JhZG1pbiIsImF1ZGllbmNlLjoid2ViIiwiY3JlYZRlZCI6MT4QMDk0Nzg5NTAzNywiZXhwIjoxNDkxNTUyNjk1fQ.2KfQBeCuOBZPDKn1VvMm5TIvIcJRdfonUHX1mrmHJ6n9H02qQVECZqu5qDAIxqAH9klCdXpyJVkajdKBSf2R9g"
}
```

Subsequent calls to the core will then use this token by adding it to a header called _Authorization_ in the request:

   curl --header Accept:application/vnd.noark5-v4+json --header -X GET --header Authorization:eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1ZGllbmNlIjoid2ViIiwiY3JlYXRlZCI6MTQ5MDk0Nzg5NTAzNywiZXhwIjoxNDkxNTUyNjk1fQ.2KfQBeCuOBZPDKn1VvMm5TIvIcJRdfonUHX1mrmHJ6n9H02qQVECZqu5qDVIxqAH9klCdQpyJVkajdKBSf2R7g  http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkiv/

