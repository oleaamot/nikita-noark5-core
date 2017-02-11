package nikita.config;


public final class N5ResourceMappings {

    // Commonly used entities for REST request mappings
    public static final String FONDS = "arkiv";
    public static final String SUB_FONDS = "underarkiv";
    public static final String SERIES = "arkivdel";
    public static final String SERIES_EN = "series";
    public static final String SERIES_REFERENCE = "referanseArkivdel";
    public static final String SERIES_REFERENCE_EN = "referenceSeries";
    public static final String FILE = "mappe";
    public static final String CASE_FILE = "saksmappe";
    public static final String REGISTRATION = "registrering";
    public static final String BASIC_RECORD = "basisregistrering";
    public static final String REGISTRY_ENTRY = "journalpost";
    public static final String DOCUMENT_DESCRIPTION = "dokumentbeskrivelse";
    public static final String DOCUMENT_OBJECT = "dokumentobjekt";
    public static final String STORAGE_LOCATION = "oppbevaringsted";
    public static final String STORAGE_LOCATION_EN = "storageLocation";
    public static final String KEYWORD = "noekkelord";
    public static final String KEYWORD_EN = "keyword";

    public static final String CODE = "kode";
    public static final String CODE_EN = "code";

    // For the sessions endpoint
    public static final String SESSIONS = "sessions";


    //Common to many entities column/attribute names
    public static final String TITLE = "tittel";
    public static final String TITLE_EN = "title";
    public static final String DESCRIPTION = "beskrivelse";
    public static final String DESCRIPTION_EN = "description";
    public static final String SYSTEM_ID = "systemID";
    public static final String SYSTEM_ID_EN = "systemId";
    public static final String CREATED_DATE = "opprettetDato";
    public static final String CREATED_DATE_EN = "createdDate";
    public static final String CREATED_BY = "opprettetAv";
    public static final String CREATED_BY_EN = "createdBy";
    public static final String FINALISED_DATE = "avsluttetDato";
    public static final String FINALISED_DATE_EN = "finalisedDate";
    public static final String FINALISED_BY = "avsluttetAv";
    public static final String FINALISED_BY_EN = "finalisedBy";
    public static final String DOCUMENT_MEDIUM = "dokumentmedium";
    public static final String DOCUMENT_MEDIUM_EN = "documentMedium";

    // Fonds
    public static final String FONDS_STATUS = "arkivstatus";
    public static final String FONDS_STATUS_EN = "fondsStatus";

    // Series
    public static final String SERIES_STATUS = "arkivdelstatus";
    public static final String SERIES_STATUS_EN = "seriesStatus";
    public static final String SERIES_START_DATE = "arkivperiodeStartDato";
    public static final String SERIES_START_DATE_EN = "seriesStartDate";
    public static final String SERIES_END_DATE = "arkivperiodeSluttDato";
    public static final String SERIES_END_DATE_EN = "seriesEndDate";
    public static final String SERIES_SUCCESSOR = "arvtager";
    public static final String SERIES_SUCCESSOR_EN = "successor";
    public static final String SERIES_PRECURSOR = "forloeper";
    public static final String SERIES_PRECURSOR_EN = "precursor";

    // ClassificationSystem
    public static final String CLASSIFICATION_SYSTEM = "klassifikasjonssystem";
    public static final String CLASSIFICATION_SYSTEM_EN = "classificationSystem";

    //  Class
    public static final String CLASS = "klasse";
    public static final String CLASS_EN = "class";
    public static final String CLASS_ID = "klasseID";
    public static final String CLASS_ID_EN = "classId";

    // File
    public static final String FILE_ID = "mappeID";
    public static final String FILE_ID_EN = "fileId";
    public static final String FILE_PUBLIC_TITLE = "offentligTittel";
    public static final String FILE_PUBLIC_TITLE_EN = "officialTitle";

    // CaseFile
    public static final String CASE_YEAR = "saksaar";
    public static final String CASE_YEAR_EN = "caseYear";
    public static final String CASE_SEQUENCE_NUMBER = "sakssekvensnummer";
    public static final String CASE_SEQUENCE_NUMBER_EN= "caseSequenceNumber";
    public static final String CASE_DATE = "saksdato";
    public static final String CASE_DATE_EN = "caseDate";
    public static final String CASE_ADMINISTRATIVE_UNIT = "administrativEnhet";
    public static final String CASE_ADMINISTRATIVE_UNIT_EN = "administrativeUnit";
    public static final String CASE_RESPONSIBLE = "saksansvarlig";
    public static final String CASE_RESPONSIBLE_EN = "caseResponsible";
    public static final String CASE_RECORDS_MANAGEMENT_UNIT = "journalenhet";
    public static final String CASE_RECORDS_MANAGEMENT_UNIT_EN = "recordsManagementUnit";
    public static final String CASE_STATUS = "saksstatus";
    public static final String CASE_STATUS_EN = "caseStatus";
    public static final String CASE_LOANED_DATE = "utlaantDato";
    public static final String CASE_LOANED_DATE_EN = "loanedDate";
    public static final String CASE_LOANED_TO = "utlaantTil";
    public static final String CASE_LOANED_TO_EN = "loanedTo";

    // CaseParty
    public static final String CASE_PARTY = "saksPart";
    public static final String CASE_PARTY_EN = "caseParty";
    public static final String CASE_PARTY_ID = "saksPartID";
    public static final String CASE_PARTY_ID_EN = "casePartyId";
    public static final String CASE_PARTY_NAME = "sakspartNavn";
    public static final String CASE_PARTY_NAME_EN = "casePartyName";
    public static final String CASE_PARTY_ROLE = "sakspartRolle";
    public static final String CASE_PARTY_ROLE_EN = "casePartyRole";
    public static final String CASE_PARTY_POSTAL_ADDRESS = "postadresse";
    public static final String CASE_PARTY_POSTAL_ADDRESS_EN = "postalAddress";
    public static final String CASE_PARTY_POST_CODE = "postnummer";
    public static final String CASE_PARTY_POST_CODE_EN = "postCode";
    public static final String CASE_PARTY_POSTAL_TOWN = "poststed";
    public static final String CASE_PARTY_POSTAL_TOWN_EN= "postalTown";
    public static final String CASE_PARTY_FOREIGN_ADDRESS = "utenlandsadresse";
    public static final String CASE_PARTY_FOREIGN_ADDRESS_EN = "foreignAddress";
    public static final String CASE_PARTY_EMAIL_ADDRESS = "utenlandsadresse";
    public static final String CASE_PARTY_EMAIL_ADDRESS_EN = "foreignAddress";
    public static final String CASE_PARTY_TELEPHONE_NUMBER = "";
    public static final String CASE_PARTY_TELEPHONE_NUMBER_EN = "";
    public static final String CASE_PARTY_CONTACT_PERSON = "kontaktperson";
    public static final String CASE_PARTY_CONTACT_PERSON_EN = "contactPerson";

    // Record
    public static final String RECORD_ARCHIVED_BY = "arkivertAv";
    public static final String RECORD_ARCHIVED_BY_EN = "archivedBy";
    public static final String RECORD_ARCHIVED_DATE = "arkivertDato";
    public static final String RECORD_ARCHIVED_DATE_EN = "archivedDate";

    // BasicRecord
    public static final String BASIC_RECORD_ID = "registreringsID";
    public static final String BASIC_RECORD_ID_EN = "recordId";

    // RegistryEntry
    public static final String REGISTRY_ENTRY_DATE = "journaldato";
    public static final String REGISTRY_ENTRY_DATE_EN = "recordDate";
    public static final String REGISTRY_ENTRY_DOCUMENT_DATE = "dokumentetsDato";
    public static final String REGISTRY_ENTRY_DOCUMENT_DATE_EN = "documentDate";
    public static final String REGISTRY_ENTRY_RECEIVED_DATE = "mottattDato";
    public static final String REGISTRY_ENTRY_RECEIVED_DATE_EN = "receivedDate";
    public static final String REGISTRY_ENTRY_RECORD_FREEDOM_ASSESSMENT_DATE = "offentlighetsvurdertDato";
    public static final String REGISTRY_ENTRY_FREEDOM_ASSESSMENT_DATE_EN = "freedomAssessmentDate";
    public static final String REGISTRY_ENTRY_SENT_DATE = "sendtDato";
    public static final String REGISTRY_ENTRY_SENT_DATE_EN = "sentDate";
    public static final String REGISTRY_ENTRY_DUE_DATE = "forfallsdato";
    public static final String REGISTRY_ENTRY_DUE_DATE_EN = "dueDate";
    public static final String REGISTRY_ENTRY_NUMBER_OF_ATTACHMENTS = "antallVedlegg";
    public static final String REGISTRY_ENTRY_NUMBER_OF_ATTACHMENTS_EN = "numberOfAttachments";
    public static final String REGISTRY_ENTRY_YEAR = "journalaar";
    public static final String REGISTRY_ENTRY_YEAR_EN = "recordYear";
    public static final String REGISTRY_ENTRY_SEQUENCE_NUMBER = "journalsekvensnummer";
    public static final String REGISTRY_ENTRY_SEQUENCE_NUMBER_EN = "recordSequenceNumber";
    public static final String REGISTRY_ENTRY_NUMBER = "journalpostnummer";
    public static final String REGISTRY_ENTRY_NUMBER_EN = "registryEntryNumber";
    public static final String REGISTRY_ENTRY_TYPE = "journalposttype";
    public static final String REGISTRY_ENTRY_TYPE_EN = "registryEntryType";
    public static final String REGISTRY_ENTRY_STATUS = "journalstatus";
    public static final String REGISTRY_ENTRY_STATUS_EN = "recordStatus";

    // CorrespondencePart
    public static final String CORRESPONDENCE_PART  = "korrespondansepart";
    public static final String CORRESPONDENCE_PART_EN  = "correspondancePart";
    public static final String CORRESPONDENCE_PART_TYPE = "korrespondanseparttype";
    public static final String CORRESPONDENCE_PART_TYPE_EN = "correspondancePartType";
    public static final String CORRESPONDENCE_PART_NAME = "korrespondansepartNavn";
    public static final String CORRESPONDENCE_PART_NAME_EN = "correspondancePartName";
    public static final String CORRESPONDENCE_PART_POSTAL_ADDRESS = "postadresse";
    public static final String CORRESPONDENCE_PART_POSTAL_ADDRESS_EN = "postalAddress";
    public static final String CORRESPONDENCE_PART_POST_CODE = "postnummer";
    public static final String CORRESPONDENCE_PART_POST_CODE_EN = "postCode";
    public static final String CORRESPONDENCE_PART_POSTAL_TOWN = "poststed";
    public static final String CORRESPONDENCE_PART_POSTAL_TOWN_EN = "postalTown";
    public static final String CORRESPONDENCE_PART_COUNTRY = "land";
    public static final String CORRESPONDENCE_PART_COUNTRY_EN = "country";
    public static final String CORRESPONDENCE_PART_EMAIL_ADDRESS = "epostadresse";
    public static final String CORRESPONDENCE_PART_EMAIL_ADDRESS_EN = "emailAddress";
    public static final String CORRESPONDENCE_PART_TELEPHONE_NUMBER = "telefonnummer";
    public static final String CORRESPONDENCE_PART_TELEPHONE_NUMBER_EN = "telephoneNumber";
    public static final String CORRESPONDENCE_PART_CASE_HANDLER = "saksbehandler";
    public static final String CORRESPONDENCE_PART_CASE_HANDLER_EN = "caseHandler";
    public static final String CORRESPONDENCE_PART_CONTACT_PERSON = "kontaktperson";
    public static final String CORRESPONDENCE_PART_CONTACT_PERSON_EN = "contactPerson";
    public static final String CORRESPONDENCE_PART_ADMINISTRATIVE_UNIT = "administrativEnhet";
    public static final String CORRESPONDENCE_PART_ADMINISTRATIVE_UNIT_EN = "contactPerson";

    // DocumentDescription
    public static final String DOCUMENT_DESCRIPTION_ASSOCIATION_DATE = "tilknyttetDato";
    public static final String DOCUMENT_DESCRIPTION_ASSOCIATION_DATE_EN = "associationDate";
    public static final String DOCUMENT_DESCRIPTION_DOCUMENT_TYPE = "dokumenttype";
    public static final String DOCUMENT_DESCRIPTION_DOCUMENT_TYPE_EN = "documentType";
    public static final String DOCUMENT_DESCRIPTION_ = "dokumentstatus";
    public static final String DOCUMENT_DESCRIPTION_DOCUMENT_STATUS_EN = "documentStatus";
    public static final String DOCUMENT_DESCRIPTION_ASSOCIATED_WITH_RECORD_AS = "tilknyttetRegistreringSom";
    public static final String DOCUMENT_DESCRIPTION_ASSOCIATED_WITH_RECORD_AS_EN = "associatedWithRecordAs";
    public static final String DOCUMENT_DESCRIPTION_DOCUMENT_NUMBER = "dokumentnummer";
    public static final String DOCUMENT_DESCRIPTION_DOCUMENT_NUMBER_EN = "documentNumber";
    public static final String DOCUMENT_DESCRIPTION_ASSOCIATED_BY = "tilknyttetAv";
    public static final String DOCUMENT_DESCRIPTION_ASSOCIATED_BY_EN = "associatedBy";

    // DocumentObject
    public static final String DOCUMENT_OBJECT_VERSION_NUMBER = "versjonsnummer";
    public static final String DOCUMENT_OBJECT_VERSION_NUMBER_EN = "versionNumber";
    public static final String DOCUMENT_OBJECT_VARIANT_FORMAT = "variantformat";
    public static final String DOCUMENT_OBJECT_VARIANT_FORMAT_EN = "variantFormat";
    public static final String DOCUMENT_OBJECT_FORMAT = "format";
    public static final String DOCUMENT_OBJECT_FORMAT_EN = "format";
    public static final String DOCUMENT_OBJECT_FORMAT_DETAILS = "formatDetaljer";
    public static final String DOCUMENT_OBJECT_FORMAT_DETAILS_EN = "formatDetails";
    public static final String DOCUMENT_OBJECT_REFERENCE_DOCUMENT_FILE = "referanseDokumentfil";
    public static final String DOCUMENT_OBJECT_REFERENCE_DOCUMENT_FILE_EN = "referenceDocumentFile";
    public static final String DOCUMENT_OBJECT_CHECKSUM = "sjekksum";
    public static final String DOCUMENT_OBJECT_CHECKSUM_EN = "checksum";
    public static final String DOCUMENT_OBJECT_CHECKSUM_ALGORITHM = "sjekksumAlgoritme ";
    public static final String DOCUMENT_OBJECT_CHECKSUM_ALGORITHM_EN = "checksumAlgorithm";
    public static final String DOCUMENT_OBJECT_FILE_SIZE = "filstoerrelse";
    public static final String DOCUMENT_OBJECT_FILE_SIZE_EN = "fileSize";

    // Other Noark Objects

    // Precedence
    public static final String PRECEDENCE = "presedens";
    public static final String PRECEDENCE_EN = "precedence";
    public static final String PRECEDENCE_DATE = "presedensDato";
    public static final String PRECEDENCE_DATE_EN = "precedenceDate";
    public static final String PRECEDENCE_AUTHORITY = "presedensHjemmel";
    public static final String PRECEDENCE_AUTHORITY_EN = "precedenceAuthority";
    public static final String PRECEDENCE_SOURCE_OF_LAW = "rettskildefaktor";
    public static final String PRECEDENCE_SOURCE_OF_LAW_EN = "sourceOfLaw";
    public static final String PRECEDENCE_APPROVED_DATE = "presedensGodkjentDato";
    public static final String PRECEDENCE_APPROVED_DATE_EN = "precedenceApprovedDate";
    public static final String PRECEDENCE_APPROVED_BY = "presedensGodkjentAv";
    public static final String PRECEDENCE_APPROVED_BY_EN = "precedenceApprovedBy";
    public static final String PRECEDENCE_STATUS = "presedensStatus";
    public static final String PRECEDENCE_STATUS_EN = "precedenceStatus";

    // Disposal
    public static final String DISPOSAL = "kassasjon";
    public static final String DISPOSAL_EN = "disposal";
    public static final String DISPOSAL_DECISION = "kassasjonsvedtak";
    public static final String DISPOSAL_DECISION_EN = "disposalDecision";
    public static final String DISPOSAL_AUTHORITY = "kassasjonshjemmel";
    public static final String DISPOSAL_AUTHORITY_EN = "disposalAuthority";
    public static final String DISPOSAL_PRESERVATION_TIME = "bevaringstid";
    public static final String DISPOSAL_PRESERVATION_TIME_EN = "preservationTime";
    public static final String DISPOSAL_DATE = "kassasjonsdato";
    public static final String DISPOSAL_DATE_EN = "disposalDate";

    // DisposalUndertaken
    public static final String DISPOSAL_UNDERTAKEN = "ufoertKassasjon";
    public static final String DISPOSAL_UNDERTAKEN_EN = "disposalUndertaken";
    public static final String DISPOSAL_UNDERTAKEN_BY = "kassertAv";
    public static final String DISPOSAL_UNDERTAKEN_BY_EN = "disposalBy";
    public static final String DISPOSAL_UNDERTAKEN_DATE = "kassertDato";
    public static final String DISPOSAL_UNDERTAKEN_DATE_EN = "disposalDate";

    // Screening
    public static final String SCREENING = "skjerming";
    public static final String SCREENING_EN = "screening";
    public static final String SCREENING_ACCESS_RESTRICTION = "tilgangsrestriksjon";
    public static final String SCREENING_ACCESS_RESTRICTION_EN = "accessRestriction";
    public static final String SCREENING_AUTHORITY = "skjermingshjemmel";
    public static final String SCREENING_AUTHORITY_EN = "screeningAuthority";
    public static final String SCREENING_METADATA = "skjermingMetadata";
    public static final String SCREENING_METADATA_EN = "screeningMetadata";
    public static final String SCREENING_DOCUMENT = "skjermingDokument";
    public static final String SCREENING_DOCUMENT_EN = "screeningDocument";
    public static final String SCREENING_EXPIRES_DATE = "skjermingOpphoererDato";
    public static final String SCREENING_EXPIRES_DATE_EN = "screeningExpiresDate";
    public static final String SCREENING_DURATION = "skjermingsvarighet";
    public static final String SCREENING_DURATION_EN = "screeningDuration";

    // Deletion
    public static final String DELETION = "sletting";
    public static final String DELETION_EN = "deletion";
    public static final String DELETION_TYPE = "slettingstype";
    public static final String DELETION_TYPE_EN = "deletionType";
    public static final String DELETION_BY = "slettetAv";
    public static final String DELETION_BY_EN = "deletionBy";
    public static final String DELETION_DATE = "slettetDato";
    public static final String DELETION_DATE_EN = "deletionDate";

    // Comment
    public static final String COMMENT = "merknad";
    public static final String COMMENT_EN = "comment";
    public static final String COMMENT_TEXT = "merknadstekst";
    public static final String COMMENT_TEXT_EN = "commentText";
    public static final String COMMENT_TYPE = "merknadstype";
    public static final String COMMENT_TYPE_EN = "commentType";
    public static final String COMMENT_DATE = "merknadsdato";
    public static final String COMMENT_DATE_EN = "commentDate";
    public static final String COMMENT_REGISTERED_BY = "merknadRegistrertAv";
    public static final String COMMENT_REGISTERED_BY_EN = "commentRegisteredBy";

    // Classified
    public static final String CLASSIFIED = "gradering"; // root node
    public static final String CLASSIFIED_EN = "classified"; // root node
    public static final String CLASSIFICATION = "gradering"; // property node
    public static final String CLASSIFICATION_EN = "classified";
    public static final String CLASSIFICATION_DATE = "graderingsdato";
    public static final String CLASSIFICATION_DATE_EN = "classificationDate";
    public static final String CLASSIFICATION_BY = "gradertAv";
    public static final String CLASSIFICATION_BY_EN = "classificationBy";
    public static final String CLASSIFICATION_DOWNGRADED_DATE = "nedgraderingsdato";
    public static final String CLASSIFICATION_DOWNGRADED_DATE_EN = "classificationDowngradedDate";
    public static final String CLASSIFICATION_DOWNGRADED_BY = "nedgradertAv";
    public static final String CLASSIFICATION_DOWNGRADED_BY_EN = "classificationDowngradedBy";

    // CrossReference
    public static final String CROSS_REFERENCE = "kryssreferanse";
    public static final String CROSS_REFERENCE_EN = "crossReference";
    public static final String CROSS_REFERENCE_RECORD = "referanseTilRegistrering";
    public static final String CROSS_REFERENCE_RECORD_EN = "referenceToRecord";
    public static final String CROSS_REFERENCE_FILE = "referanseTilMappe";
    public static final String CROSS_REFERENCE_FILE_EN = "referenceToFile";
    public static final String CROSS_REFERENCE_CLASS = "referanseTilKlasse";
    public static final String CROSS_REFERENCE_CLASS_EN = "referenceToClass";

    // ElectronicSignature
    public static final String ELECTRONIC_SIGNATURE = "elektroniskSignatur";
    public static final String ELECTRONIC_SIGNATURE_EN = "electronicSignature";
    public static final String ELECTRONIC_SIGNATURE_SECURITY_LEVEL = "elektroniskSignaturSikkerhetsnivaa";
    public static final String ELECTRONIC_SIGNATURE_SECURITY_LEVEL_EN = "electronicSignatureSecurityLevel";
    public static final String ELECTRONIC_SIGNATURE_VERIFIED = "elektroniskSignaturVerifisert";
    public static final String ELECTRONIC_SIGNATURE_VERIFIED_EN = "electronicSignatureVerified";
    public static final String ELECTRONIC_SIGNATURE_VERIFIED_DATE = "verifisertDato";
    public static final String ELECTRONIC_SIGNATURE_VERIFIED_DATE_EN = "verifiedDate";
    public static final String ELECTRONIC_SIGNATURE_VERIFIED_BY = "verifisertAv";
    public static final String ELECTRONIC_SIGNATURE_VERIFIED_BY_EN = "verifiedBy";

    // FondsCreator
    public static final String FONDS_CREATOR = "arkivskaper";
    public static final String FONDS_CREATOR_EN = "fondsCreator";
    public static final String FONDS_CREATOR_ID = "arkivskaperID";
    public static final String FONDS_CREATOR_ID_EN  = "fondsCreatorId";
    public static final String FONDS_CREATOR_NAME = "arkivskaperNavn";
    public static final String FONDS_CREATOR_NAME_EN = "fondsCreatorName";

    // SignOff
    public static final String SIGN_OFF  = "avskrivning";
    public static final String SIGN_OFF_EN  = "signOff";
    public static final String SIGN_OFF_DATE  = "avskrivning";
    public static final String SIGN_OFF_DATE_EN  = "signOffDate";
    public static final String SIGN_OFF_BY  = "avskrevetAv";
    public static final String SIGN_OFF_BY_EN  = "signOffBy";
    public static final String SIGN_OFF_METHOD  = "avskrivningsmaate";
    public static final String SIGN_OFF_METHOD_EN  = "signOffMethod";

    // DocumentFlow
    public static final String DOCUMENT_FLOW = "dokumentflyt";
    public static final String DOCUMENT_FLOW_EN = "documentFlow";
    public static final String DOCUMENT_FLOW_FLOW_TO = "flytTil";
    public static final String DOCUMENT_FLOW_FLOW_TO_EN = "flowTo";
    public static final String DOCUMENT_FLOW_FLOW_FROM = "flytFra";
    public static final String DOCUMENT_FLOW_FLOW_FROM_EN = "flowFrom";
    public static final String DOCUMENT_FLOW_FLOW_RECEIVED_DATE = "flytMottattDato";
    public static final String DOCUMENT_FLOW_FLOW_RECEIVED_DATE_EN = "flowReceivedDate";
    public static final String DOCUMENT_FLOW_FLOW_SENT_DATE = "flytSendtDato";
    public static final String DOCUMENT_FLOW_FLOW_SENT_DATE_EN = "flowSentDate";
    public static final String DOCUMENT_FLOW_FLOW_STATUS = "flytStatus";
    public static final String DOCUMENT_FLOW_FLOW_STATUS_EN = "flowStatus";
    public static final String DOCUMENT_FLOW_FLOW_COMMENT = "flytMerknad";
    public static final String DOCUMENT_FLOW_FLOW_COMMENT_EN = "flowComment";

    // Conversion
    public static final String CONVERSION = "konvertering";
    public static final String CONVERSION_EN = "conversion";
    public static final String CONVERTED_DATE = "konvertertDato";
    public static final String CONVERTED_DATE_EN = "convertedDate";
    public static final String CONVERTED_BY = "konvertertAv";
    public static final String CONVERTED_BY_EN = "convertedBy";
    public static final String CONVERTED_FROM_FORMAT = "konvertertFraFormat";
    public static final String CONVERTED_FROM_FORMAT_EN = "convertedFromFormat";
    public static final String CONVERTED_TO_FORMAT = "konvertertTilFormat";
    public static final String CONVERTED_TO_FORMAT_EN = "convertedToFormat";
    public static final String CONVERSION_TOOL = "konverteringsverktoey";
    public static final String CONVERSION_TOOL_EN = "conversionTool";
    public static final String CONVERSION_COMMENT = "konverteringskommentar";
    public static final String CONVERSION_COMMENT_EN = "conversionComment";

    // Author
    public static final String AUTHOR = "forfatter";
    public static final String AUTHOR_EN = "author";


    public static final String SECONDARY_CLASSIFICATION = "sekundaerklassifikasjon";

    // Constant values defined in the Metadata catalogue

    public static final String STATUS_OPEN = "Opprettet";
    public static final String STATUS_CLOSED = "Avsluttet";

    // M300 dokumentmedium
    public static final String DOCUMENT_MEDIUM_PHYSICAL = "Fysisk arkiv";
    public static final String DOCUMENT_MEDIUM_ELECTRONIC ="Elektronisk arkiv";
    public static final String DOCUMENT_MEDIUM_MIXED = "Blandet fysisk og elektronisk arkiv";


    private N5ResourceMappings() {
        throw new AssertionError();
    }

}

