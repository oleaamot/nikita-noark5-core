# Teaching use-cases for nikita
## About
nikita is a free and open source noark 5 core that has been developed to aid teaching and R&D at the Department of 
Archive, Library and Information Science at HiOA. There are two main goals in the project. 1. Develop a FOSS 
Noark 5 core and 2. Develop a FOSS user interface to teach students case-handling, organisational structure, 
classification as well as the technical side of understanding a recordkeeping system. The project has existed since 
around 2010 and has had various test implementations, both in PHP and Java. We decided in 2016 to concentrate on JavaEE 
and based the project on [spring](https://www.spring.io/). 

A Noark system should only be approved against the latest version of the standard, which currently is Noark 5v4 and the 
standard has an associated Hateaoas/REST interface. So we have developed the core, as far as possible, in accordance 
with this interface standard.

## Introduction
This document describes the use-case for how we intend to use the user-interface (GUI) for teaching purposes. We require
a tool like nikita as it is important to teach students the basics of record keeping independent of any commercial 
system. Our students should not associate the record keeping archive with any particular Noark system, rather they 
should understand the basic principles of record keeping, how Noark relates to an underlying database structure and XML
extractions and be able to apply them.  

There are four particular areas that the GUI must support as well as a description of basic requirements.
 
 1. Basic requirements and setup
 2. Organisational structure
 3. Classificationsystem development 
 4. Case-handling
 5. Retention and deletion schedules and implementation
 
## Basic requirements and setup
### Background
A Noark system contains a subset of an organisations records. In record keeping theory we call all records belonging
to an organisation a fonds (arkiv). The goal of record keeping as such is then capture all records and organise them. 
This situation is not obtainable today due to the distributed nature of where records manifest themselves, in email 
servers, SMS message, Facebook posts, Excel spreadsheets etc. As such the goal of a Noark record keeping system is to 
reach out and capture as many of the records belonging to the organisations fonds as possible.

### Description
When a user logs on to nikita for the first time, they are assigned an organisation in nikita that they control

#### Feide integration
It should be possible to integrate Feide as the authentication mechanism. When visting nikita.hioa.no, if a student 
isn't logged, they are brought to the feide login page where they authenticate themselves

#### Feide integration

## Organisational structure 

In order to 
### Description
Students

It should be possible to 
 * create a new fonds
 * create a new subfonds associated with a fonds

#### Metadata

The following metadata fields are controlled by a user during the creation of fonds

The following metadata fields are controlled by a user during the creation of fonds

The following status that can be changed by a user are

A user cannot update any fields if the fonds status is set to "Avsluttet"
## Classificationsystem development
 
## Case-handling

## Retention and deletion schedules and implementation

