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
When a user logs on to nikita for the first time, they are assigned their own organisation in nikita that they control. 
This organisation gets a UUID as an identifier and the user can assign whatever name they want to the organisation. 

Can they assign others users so they can interact with their organisation?

### Feide integration
It should be possible to integrate Feide as the authentication mechanism. When visiting nikita.hioa.no, if a user 
isn't logged, they are brought to the feide login page where they authenticate themselves. They are then brought back to 
the applicable dashboard for the role they have logged in.

### Dashboard

Each role (arkivansvarlig, arkivar, leder, saksbehandler) has their own dashboard that shows functionality relevant for 
their role.
  
#### Case handler   

This dashboard makes use of information related to statistics defined in the Noark 5 standard [1]. Here they can see 
things like due-list (restanseliste), case-handling times etc. This is information that has not been specified in the
interface standard, but likely will come later. Other elements here is to show information related to workflow e.g. 
messages informing the case-handler that have been assigned new cases to handle or that leader has approved an outgoing
letter. the third group of functionality is to be able to quickly create new case files and associate them with a 
particular class or series.

#### Leader
The leader will typically have a functional role related to e.g a particular department and have to approve outgoing 
communications before they are dispatched.

This dashboard should allow a leader to come into sub functions
  1. Create a case-file and assign it to a case-handler
  2. See from a list of case-files, which cases are unassigned and assign a case-handler
  3. Look at a case-file after a case-handler has marked it waiting for approval 

The leader should also have a statistics overview showing relevant statistics for various users under their authority.
There should be an information box showing ongoing workflow items that the leader must handle. This might have to show 
fairly large amounts of information.
  
#### records keeper
The records keeper will formally end a case-file and set its status to closed. They have a quality control function for
the case-handling. This dashboard should show statistics for the entire organisation. The dashboard will have a work-flow
box that sees the end of case-handling and the records keeper can go in and update case and formally end them.  Note 
some time case-handlers have the authority to end cases themselves, so they do not need to go via the records keeper

#### records manager   
The records manager can manipulate the archive structure, creating fonds, series, classification systems, classes etc. 
Only the records manager can create extractions, set fonds and series status values. The role includes the classic 
administrator functions related to users adding/removing them.

## Organisational structure 
The organisational structure is defined in fonds and series. There is no clear rules on how this should be set up, but
we will get information from various IKA in Norway describing the fonds/series structure of various municipalities in 
Norway. An example is where a municipality has the following fonds (saksbehandling, byggesaksbehandling, skole, 
barnehage) and a series covers records for given time periods. It's recommended that series follow the municipal 
election cycle, but it is unclear how many municipalities follow this. A series could also be used to cover a 
functional context over records where e.g all records related to chimney sweeping are organised together. 

### Description
Users should be able to manipulate the fonds structure and undertake the following:
 * create a new fonds
 * create a new subfonds associated with a fonds
 * create a series associated with a fonds
 * create a classification system / classes
 * Set status values (ended) for fonds, series, classifciation system and class

These are all tasks related to administrative context, and the interface standard hints at some of these being defined
under the group of administrative RELs.

After completing a lesson on this students should be able to understand how to create an archive structure and can 
reason about various ways an archive can be structured (arkivoppbygging). Students should see that if there is no place
for various record keeping functions in the fond structure, then they are missing on a formal definition of the entire
set of records for the organisation, i.e the fonds is only a subset of what it should be. This can also lead to a class 
case study / discussion on fonds and why we are missing formal and clear definitions of records. This would include 
records being stored in email systems, Excel documents etc.

## Classificationsystem development
 
## Case-handling

## Retention and deletion schedules and implementation

[1] Noark 5 side 