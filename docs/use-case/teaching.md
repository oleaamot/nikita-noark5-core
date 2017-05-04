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
When a user logs on to nikita for the first time, they are assigned their own organisation (arkivskaper) in nikita that they control. 
This organisation gets a UUID (SystemID) as an identifier and the user can assign whatever name they want to the organisation. 

Can they assign others users so they can interact with their organisation?

### Feide integration
It should be possible to integrate Feide as the authentication mechanism. When visiting nikita.hioa.no, if a user 
isn't logged, they are brought to the feide login page where they authenticate themselves. They are then brought back to 
the applicable dashboard for the role they have logged in.

### Dashboard

Each role (arkivansvarlig, arkivar, leder, saksbehandler) has their own dashboard that shows functionality relevant for 
their role.
  
#### Case handler   

This dashboard makes use of information related to statistics defined in the Noark 5 standard. Here they can see 
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
 * Set status values (ended) for fonds, series

These are all tasks related to administrative context, and the interface standard hints at some of these being defined
under the group of administrative RELs.

After completing a lesson on this students should be able to understand how to create an archive structure and can 
reason about various ways an archive can be structured (arkivoppbygging). Students should see that if there is no place
for various record keeping functions in the fond structure, then they are missing on a formal definition of the entire
set of records for the organisation, i.e the fonds is only a subset of what it should be. This can also lead to a class 
case study / discussion on fonds and why we are missing formal and clear definitions of records. This would include 
records being stored in email systems, Excel documents etc.

Note: This is required for the autumn 2017 semester.

## Classification system development
Classification is an important area of knowledge that the students need to understand. The goal with lessons here is to 
take classification from being an abstract concept to being something more concrete where they are actually understand
the implication of their decisions through implementing a classification system and classes as a use case.

### Description
First students will develop a classification system (on paper) for a well defined functional area, e.g kinder garden 
applications where they perhaps take in the understanding of the processes defined in ISO-15489. Then they come to
nikita and log on as records manager and create the classification system as well as all the classes. This is followed
by a quick case-handling session where they have to undertake a case-handling process where they use the classes that
they created. This is functional classification. The students should also know what object classification is and when 
and how to use it. Students will create an object based classification system to be used with e.g. buildings and 
create files and records associated with buildings. We could follow up with objects being people and either create
objects for a school fonds or a personnel fonds. A course work here could be a reflection note on the entire process.
This should lead to discussions on various types of classification, lifetime expectancy of classes (buildings versus 
people), creating cross-references between files in series using different classification system. 

Users should be able to manipulate classification systems and undertake the following:

 * create a classification system / classes
 * Set status values (ended) for fonds, series, classification system and class

Note: This is not required to be implemented before the spring 2018 semester.

## Case-handling
By far the largest functionality both in the core and GUI. Students may one day be responsible for the record keeping
process in an organisation and should be aware of the entire process. This functionality is something that was already 
implemented in edudots and we know it works well in class (except the woeful GUI!). There are a number of ways we wish 
to expose students to the case-handling process. 

## Description
### Use-case 1. Simple introduction
First students will login with the case-handler role and create a case file, registry entry (journalpost) and upload a 
provided document. They mark the document as an incoming document and assign a classification code. Note, some 
organisations only allow the record keeper assign a class. They then create a reply to this incoming document and create
a registry entry and assign it as outgoing. This leads to discussion related to the process, explanations of 
metadata e.g. class, document type. Typically we introduce the student to PDF/A here and get them convert to PDF/A from
Word/LibreOffice

### Use-case 2. Case-handling via email
This is a more complex use-case. Students log on to an email server (postmottak) and import a mail message. They do an
appraisal on the contents and decide whether or not to formally create a case-file and assign it to a leader or 
case-handler, assign the decision to a leader to take, or do nothing with the mail. This is a good point to ensure
discussion around the concept of postmottak before describing the process of case-handling from postmottak.

Assuming the mail is assigned to a case-handler, the case-handler will see the mail or an empty shell of a case-file 
and begin case-handling. Typically we send a copy of the latest version of the Noark standard to the email server and 
say that the standard is out for comments (på høring) and the students have to give comments on the standard. The
students write their comments and upload the document to nikita within a journalpost. They set status to "F" (If I 
remember correctly). A status F on an outgoing document should then pop up in the leaders GUI. The leader then goes
in and approves the outgoing letter (status G, if I remember correctly) and the case is finished. The records keeper
then sees that this has occurred and goes in and check that everything is OK and formally ends the case-file.

For this use-case, we require the use of the record-keeper, leader and case-handler roles. Repeating the description, 
there is a loop, where record keeper starts the process by accepting the mail as a case-file, the case-handler handles 
(saksbehandler) it and the leader approves the outgoing letter, before the  loop is complete and the records keeper 
formally ends the case-file.

Typically this is a group project where the students work in a group of three and each take one of the roles and we go
through the process step by step. In edudots, there was just one archive so it was unproblematic to allow the students
cooperate. In the new setup with nikita, each student owns their own archive/organisation. So it becomes a little more
complicated to let them work in a group project like this.

  
Note. Petter has a much better use-case where nikita automatically copies email to a temporary series / mappe and the
records keeper sorts post directly from here. Spam contents can be deleted or assigned to a daily deletion schedule, 
while other documents are assigned and moved to the correct location.   

Note: Both use-cases are required for the autumn 2017 semester.

## Retention and deletion schedules and implementation
Retention and deletion schedules are an important thing to teach students. Some records must be kept, while some must be 
deleted after a period of time. Our understanding is that practice related to this varies in Norway with some IKA 
recommending to municipalities to delete nothing. A few years ago, we got new retention and deletion guidelines and 
these will be used as a basis for teaching. This is by far the weakest part of technical Noark understanding and 
requires more understanding and development before we can undertake teaching in this. 

### Description
Users should be able to assign retention and deletions schedules on records in the core. We need to create a use-case
where we apply both retention and deletion metadata to a class. The students can then wait e.g a week and see the list
of records to be deleted and see which metadata is created when such function is undertaken. They should also see that
only the documents are deleted, not metadata. It also raises questions of who is in charge of deletion, when records
have been transferred to the archive. How will records that you are legally required to delete, be deleted from an
OAIS SIP/AIP, when these are typically read only objects. 

Note: This is not something that we will prioritise for autumm 17 teaching. Nor will it find any place in spring 18.
 

# Final note
The above use-case expose all roles within Noark in a nice way. There is a clear defined separation of roles and 
use-cases. There are other areas that can be exposed e.g comments, cross-references, keyword, meeting and committee 
records. These are things that can explored after we review how well nikita works as a teaching tool. 

This document does not cover the entirely of how nikita will be used as a teaching tool. We will also use to teach
students about the importance of understanding that the database is the archive and to see beyond a 'system' or GUI
being the archive. This will then lead to discussion on data quality and how the system plays an important role in
ensuring the data quality is high. Students will also create an extraction of the records they have created and this 
will be used to help them understand XML and relationship between databases an XML with Noark / nikita as a use case. 
We also think we will use nikita as a innsynsløsning and import the extractions students previously had created. Finally 
we may use this as an aid in MBIB4140 Metadata and interoperability when we teach students REST, JSON etc. This document only
covers the GUI part of using nikita as a teaching tool.
