# The use of licensed resources with AGPL

Note, we have included to sets of resources that have licenses associated with
them. These are a list of post codes and a list of countries in ISO 3166 format.

The content has been separated so it is easy to identify it. They are in the 
following: 

     resources/db/countrycode.sql
     resources/db/postcode.sql

The original resources are alos included here.     

## Postcodes
The list of postcode / town used in nikita is taken from [here](https://data.norge.no/data/posten-norge/postnummer-i-norge)

This data is released under the (NLOD 1.0) license. One of the points in this
license is that we may not prevent users from freely reusing the data. The 
AGPL licence may be a problem for this. But we are not sure. Searching the web
did not give a clear answer on this. We believe that by 1. Including the 
original source of the postcodes in csv format and 2. making no claim to 
copyright on the data, then we are in compliance with NLOD. The codebase 
includes a copyright claim on the SQL statements in postcode.sql, but this
does not cover the postcode/town values. 

The original data is in a file called postcode.csv, located in this directory.
 
## Country codes
The list of country codes used in nikita is taken from [here](https://no.wikipedia.org/wiki/ISO_3166-1_alfa-2#Offisielle_koder)

This data is presented with a CC BY-SA 3.0 license. As with the postcodes we 
make the original source after it was processed to csv available in the 
codebase. 

The processed data is in a file called countycode.csv, located in this 
directory.
