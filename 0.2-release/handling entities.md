# How nikita interprets entities  
During development we have seen the need to create a formal description of how nikita interprets various entities,
  especially when it comes to serialising Hateoas style results. Nikita interprets that Noark has four type of entities:.

1. Primary entities
2. Secondary entities (non-embeddable)
3. Secondary entities (embeddable)
4. Codes/list entities

## 1. Primary entities
Primary entities are the entities that make up the fondsstructure (arkivstruktur). These are listed as the following
 * Fonds (arkiv)
 * FondsCreator (arkivskaper)
 * Series (arkivdel)
 * ClassifiationSystem (klassifikasjonsystem)
 * Class (class)
 * File -> CaseFile | MeetingFile (mappe, saksmappe, moetemappe)
 * Record -> BasicRecord -> RegistryEntry (registrering, basisregistrering, journalpost)
 * Record -> BasicRecord -> meetingRecord (registrering, basisregistrering, moeteregistrering)
 * DocumentDescription (dokumentbeskrivelse)
 * DocumentObject (dokumentobjekt)
 
 All of the entities are retrievable via HTTP request.
 
 ## 2. Secondary entities (non-embeddable)
  
  Secondary entities are entities that are related to a primary entity, there is little reason for them to exist 
  if they are not related to a primary entity. A primary entity identifier is typically required to create a secondary 
  entity, e.g. to create a new caseParty, the following POST URL is used:
    
    [contextPath][api]/arkivstruktur/sakarkiv/saksmappe/85b85543-d09b-40f7-a768-a4776e127bef/ny-sakspart
   
  to retrieve all caseParties associated with a caseFile, the following GET URL is used:  
  
    [contextPath][api]/arkivstruktur/sakarkiv/saksmappe/85b85543-d09b-40f7-a768-a4776e127bef/saksparter
   
  These secondary entities are retrievable with their own identifiers e.g if you need to update a caseparty. To update 
  a caseparty the following PUT URL can be used
    
    [contextPath][api]/arkivstruktur/sakarkiv/sakspart/a7b447ae-f559-4f47-afc4-a5b2a4b0219e
    
  When retrieving and serialising a primary entity, these secondary entities are not embedded within the serialised 
  output. Rather links to the secondary entities can be found within the _links part of the Hateoas reply. We also
  believe that this is a requirement of the interface standard. We think that the relationship primary:secondary
  is in most cases 1:M. The following JSON example illustrates linking for a non-embeddable secondary entity.
 
  ```
    {
      "mappeID": "2017/0001",
      "tittel": "Jan Jansen : Klage på barnehage tildeling",
      "offentligTittel": "***** *****: Klage på barnehage tildeling ",
      _links [
      "href": "http://localhost/noark5v4/hateoas-api/arkivstruktur/sakarkiv/sakspart/a7b447ae-f559-4f47-afc4-a5b2a4b0219e"
      "rel": http://rel.kxml.no/noark5/v4/api/sakarkiv/saksparter/
      ]
   }
  ```
  
  The list of Noark secondary entities (non-embeddable) is as follows:
  
  * Author (forfatter) [M:M]
  * CaseParty (sakspart) [M:M]
  * Comment (merknad) [M:M]
  * Conversion (konvertering) [M:1] 
  * CorrespondencePart (korrespondansepart) [M:M]
  * CrossReference  (krysreferanse) [M:M]
  * DocumentFlow (dokumentflyt)
  * Keyword (noekkelord) [M:M]
  * Precedence (presedens) [M:M]
  * SignOff (avskriving)
  * StorageLocation [M:M]
 
 Note: Section 6.1.1.6 states that entities can have their data uploaded directly as JSON or indirectly retrieved as a 
   request. This must also apply to secondary entities.
 
    POST http://localhost:49708/api/arkivstruktur/registrering/cf8e1d0d-e94d-4d07-b5ed-
    46ba2df0465e/dokumentbeskrivelse/$ref?$id=http://localhost:49708/api/arkivstruktur/Dokumentb
    eskrivelse/1fa94a89-3550-470b-a220-92dd4d709044
  
 In terms of Nikita support for this, some things are a little unclear. How is the session cookie handled for the GET 
  request? Do we have to support getting non local URLS? What if the machine the core is running cannot speak 
  to the Internet?
    
    [contextPath][api]/arkivstruktur/sakarkiv/saksmappe/85b85543-d09b-40f7-a768-a4776e127bef/ny-sakspart
 
 ## 3. Secondary entities (embeddable)
 
 Secondary entities, that are embeddable, are also entities that are related to a primary entity. We think there are 
 two sub types here, one which acts like a code list (skjerming, gradering) while the other depicts an event (sletting,
   kassasjon).  When serialising a primary entity, embeddable secondary entities are included in the serialised output 

```
  {
    "mappeID": "2017/0001",
    "tittel": "Jan Jansen : Klage på barnehage tildeling",
    "offentligTittel": "***** *****: Klage på barnehage tildeling ",
    skjerming": {
    "skjermingshjemmel": "Offentleglova § 13 ",
    "skjermingMetadata": tittel
    }
 }
```
  
  The list of Noark secondary entities (embeddable) is as follows:
  * Classified (gradering) [M:1]
  * Deletion (sletting) [M:1]
  * Disposal (kassasjon) [M:1]
  * DisposalUndertaken (kassasjonutfoert) [M:1]
  * Screening [M:M]
  * ElectronicSignature (elektronisksignatur) [1:1]
 
 ## 4. Codes/list entities
 The final set of entities are new as entities in Noark5v4. In Noark 5v3, they would have typically been single string 
 fields (a simpleType), as a field as part of a primary entity, but have become complexTypes (using the context 
 description from XML/XSD). In many ways this goes back to the Noark 4 thinking, where these codes would have existed 
 as values in their own tables, something that fell away in Noark 5. 
    
  ```
   {
    "href" : "http://localhost:8092/noark5v4/hateoas-api/metadata/dokumentmedium/",
    "rel" : "http://rel.kxml.no/noark5/v4/api/metadata/dokumentmedium/"
   }
  ```  
  The list of Noark code entities is as follows:
 * Arkivdelstatus
 * Arkivstatus
 * Avskrivningsmåte
 * Dokumentmedium
 * Dokumentstatus
 * Dokumenttype
 * Elektronisk signatur sikkerhetsnivå
 * Elektronisk signaturverifisert
 * Flytstatus
 * Format
 * Graderingskode
 * Hendelsetype
 * Journalposttype
 * Journalstatus
 * Kassasjonsvedtak
 * Klassifikasjonstype
 * Korrespondanseparttype
 * Land
 * Mappetype
 * Merknadstype
 * Møtedeltakerfunksjon
 * Møteregistreringsstatus
 * Møteregistreringstype
 * Møtesakstype
 * Postnummer
 * Presedensstatus
 * Sakspartrolle
 * Saksstatus
 * Skjermingdokument
 * Skjermingmetadata
 * Slettingstype
 * Tilgangskategori
 * Tilgangsrestriksjon
 * Tilknyttet registrering som
 * Variantformat
 
 
 ## Note   
  StorageLocation (M:M) is interpreted as a non-embeddable secondary entity, but may be an embeddable one  
  Author (M:M) is interpreted as a non-embeddable secondary entity, but may be an embeddable one  
  ElectronicSignature (1:1) is interpreted as a embeddable secondary entity, but may be a non-embeddable one
  