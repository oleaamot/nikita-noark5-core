# Clarification of _links generation during serialisation

There is a need to have a formal agreed strategy on when _links are generated. We think the description of this is 
lacking in the current version of the standard. This text will describe how nikita interprets _links.

## Links on errors
 
Exceptional circumstances (that raised an exception in the core) will generate a response that does not include a
_links. 




Should a 

## 1. All requests generate _links
 
## Singular entity requests
A request that results in a singular entity being returned must include a _links section.

What if the result of the request is empty? i.e the object does not exist?