package nikita.config;


public final class N5ResourceMappings {

    // Commonly used entities for REST request mappings
    public static final String FONDS = "arkiv";
    public static final String SUB_FONDS = "underarkiv";
    public static final String SERIES = "arkivdel";
    public static final String REFERENCE_SERIES = "referanseArkivdel";
    public static final String FILE = "mappe";
    public static final String CASE_FILE = "saksmappe";
    public static final String MEETING_FILE = "moetemappe";
    public static final String MEETING_PARTICIPANT = "moetedeltager";
    public static final String MEETING_RECORD = "moeteregistrering";
    public static final String REGISTRATION = "registrering";
    public static final String BASIC_RECORD = "basisregistrering";
    public static final String REGISTRY_ENTRY = "journalpost";
    public static final String DOCUMENT_DESCRIPTION = "dokumentbeskrivelse";
    public static final String DOCUMENT_OBJECT = "dokumentobjekt";
    public static final String STORAGE_LOCATION = "oppbevaringsted";
    // Not part of interface standard
    public static final String STORAGE_LOCATIONS = "oppbevaringsteder";

    public static final String KEYWORD = "noekkelord";

    public static final String CODE = "kode";

    public static final String FILE_TYPE = "mappetype";
    public static final String FLOW_STATUS = "flytstatus";
    public static final String EVENT_TYPE = "hendelsetype";
    public static final String COUNTRY = "land";
    public static final String CLASSIFIED_CODE = "graderingskode";
    public static final String CLASSIFICATION_TYPE = "klassifikasjonstype";
    public static final String ASSOCIATED_WITH_RECORD_AS = "tilknyttetregistreringsom";
    public static final String ACCESS_RESTRICTION = "tilgangsrestriksjon";
    public static final String ACCESS_CATEGORY = "tilgangskategori";
    public static final String ZIP = "postnummer";

    // For the sessions endpoint
    public static final String SESSIONS = "sessions";

    //Common to many entities column/attribute names
    public static final String TITLE = "tittel";
    public static final String DESCRIPTION = "beskrivelse";
    public static final String SYSTEM_ID = "systemID";
    public static final String CREATED_DATE = "opprettetDato";
    public static final String CREATED_BY = "opprettetAv";
    public static final String FINALISED_DATE = "avsluttetDato";
    public static final String FINALISED_BY = "avsluttetAv";
    public static final String DOCUMENT_MEDIUM = "dokumentmedium";

    // Fonds
    public static final String FONDS_STATUS = "arkivstatus";

    // Series
    public static final String SERIES_STATUS = "arkivdelstatus";
    public static final String SERIES_START_DATE = "arkivperiodeStartDato";
    public static final String SERIES_END_DATE = "arkivperiodeSluttDato";
    public static final String SERIES_SUCCESSOR = "arvtager";
    public static final String SERIES_PRECURSOR = "forloeper";

    // ClassificationSystem
    public static final String CLASSIFICATION_SYSTEM = "klassifikasjonssystem";
    // Not known if officaly recognised as part of Noark 5 or just something
    // seen in interface standard
    public static final String SECONDARY_CLASSIFICATION_SYSTEM = "sekundaerklassifikasjonssystem";

    //  Class
    public static final String CLASS = "klasse";
    public static final String CLASS_ID = "klasseID";

    // Note, PARENT_CLASS is not part of the standard
    public static final String PARENT_CLASS = "overordnetklasse";
    public static final String SUB_CLASS = "underklasse";

    // File
    public static final String FILE_ID = "mappeID";
    public static final String FILE_PUBLIC_TITLE = "offentligTittel";

    // CaseFile
    public static final String CASE_YEAR = "saksaar";
    public static final String CASE_SEQUENCE_NUMBER = "sakssekvensnummer";
    public static final String CASE_DATE = "saksdato";
    public static final String CASE_ADMINISTRATIVE_UNIT = "administrativEnhet";
    public static final String CASE_RESPONSIBLE = "saksansvarlig";
    public static final String CASE_RECORDS_MANAGEMENT_UNIT = "journalenhet";
    public static final String CASE_STATUS = "saksstatus";
    public static final String CASE_LOANED_DATE = "utlaantDato";
    public static final String CASE_LOANED_TO = "utlaantTil";

    // CaseParty
    public static final String CASE_PARTY = "saksPart";
    public static final String CASE_PARTY_ID = "saksPartID";
    public static final String CASE_PARTY_NAME = "sakspartNavn";
    public static final String CASE_PARTY_ROLE = "sakspartRolle";
    public static final String CASE_PARTY_POSTAL_ADDRESS = "postadresse";
    public static final String CASE_PARTY_POST_CODE = "postnummer";
    public static final String CASE_PARTY_POSTAL_TOWN = "poststed";
    public static final String CASE_PARTY_FOREIGN_ADDRESS = "utenlandsadresse";
    public static final String CASE_PARTY_EMAIL_ADDRESS = "utenlandsadresse";
    public static final String CASE_PARTY_TELEPHONE_NUMBER = "";
    public static final String CASE_PARTY_CONTACT_PERSON = "kontaktperson";

    // Record
    public static final String RECORD_ARCHIVED_BY = "arkivertAv";
    public static final String RECORD_ARCHIVED_DATE = "arkivertDato";

    // BasicRecord
    public static final String BASIC_RECORD_ID = "registreringsID";

    // RegistryEntry
    public static final String REGISTRY_ENTRY_DATE = "journaldato";
    public static final String REGISTRY_ENTRY_DOCUMENT_DATE = "dokumentetsDato";
    public static final String REGISTRY_ENTRY_RECEIVED_DATE = "mottattDato";
    public static final String REGISTRY_ENTRY_RECORD_FREEDOM_ASSESSMENT_DATE = "offentlighetsvurdertDato";
    public static final String REGISTRY_ENTRY_SENT_DATE = "sendtDato";
    public static final String REGISTRY_ENTRY_DUE_DATE = "forfallsdato";
    public static final String REGISTRY_ENTRY_NUMBER_OF_ATTACHMENTS = "antallVedlegg";
    public static final String REGISTRY_ENTRY_YEAR = "journalaar";
    public static final String REGISTRY_ENTRY_SEQUENCE_NUMBER = "journalsekvensnummer";
    public static final String REGISTRY_ENTRY_NUMBER = "journalpostnummer";
    public static final String REGISTRY_ENTRY_TYPE = "journalposttype";
    public static final String REGISTRY_ENTRY_STATUS = "journalstatus";

    // CorrespondencePart
    public static final String CORRESPONDENCE_PART  = "korrespondansepart";
    public static final String CORRESPONDENCE_PART_TYPE = "korrespondanseparttype";
    public static final String CORRESPONDENCE_PART_NAME = "korrespondansepartNavn";
    public static final String CORRESPONDENCE_PART_POSTAL_ADDRESS = "postadresse";
    public static final String CORRESPONDENCE_PART_POST_CODE = "postnummer";
    public static final String CORRESPONDENCE_PART_POSTAL_TOWN = "poststed";
    public static final String CORRESPONDENCE_PART_COUNTRY = "land";
    public static final String CORRESPONDENCE_PART_EMAIL_ADDRESS = "epostadresse";
    public static final String CORRESPONDENCE_PART_TELEPHONE_NUMBER = "telefonnummer";
    public static final String CORRESPONDENCE_PART_CASE_HANDLER = "saksbehandler";
    public static final String CORRESPONDENCE_PART_CONTACT_PERSON = "kontaktperson";
    public static final String CORRESPONDENCE_PART_ADMINISTRATIVE_UNIT = "administrativEnhet";

    // DocumentDescription
    public static final String DOCUMENT_DESCRIPTION_ASSOCIATION_DATE = "tilknyttetDato";
    public static final String DOCUMENT_DESCRIPTION_DOCUMENT_TYPE = "dokumenttype";
    public static final String DOCUMENT_DESCRIPTION_STATUS = "dokumentstatus";
    public static final String DOCUMENT_DESCRIPTION_ASSOCIATED_WITH_RECORD_AS = "tilknyttetRegistreringSom";
    public static final String DOCUMENT_DESCRIPTION_DOCUMENT_NUMBER = "dokumentnummer";
    public static final String DOCUMENT_DESCRIPTION_ASSOCIATED_BY = "tilknyttetAv";

    // DocumentObject
    public static final String DOCUMENT_OBJECT_VERSION_NUMBER = "versjonsnummer";
    public static final String DOCUMENT_OBJECT_VARIANT_FORMAT = "variantformat";
    public static final String DOCUMENT_OBJECT_FORMAT = "format";
    public static final String DOCUMENT_OBJECT_FORMAT_DETAILS = "formatDetaljer";
    public static final String DOCUMENT_OBJECT_REFERENCE_DOCUMENT_FILE = "referanseDokumentfil";
    public static final String DOCUMENT_OBJECT_CHECKSUM = "sjekksum";
    public static final String DOCUMENT_OBJECT_CHECKSUM_ALGORITHM = "sjekksumAlgoritme";
    public static final String DOCUMENT_OBJECT_FILE_SIZE = "filstoerrelse";
    // The following may or may not be part of the official standard. Sent an email to find out
    // but are specified in n5v4 tjenestegrensesnitt, but nit in metadata catalogues and therefore lack official
    // identifying numbers
    public static final String DOCUMENT_OBJECT_FILE_NAME = "filnavn";
    public static final String DOCUMENT_OBJECT_MIME_TYPE = "mimeType";
    // Other Noark Objects

    // Precedence
    public static final String PRECEDENCE = "presedens";
    public static final String PRECEDENCE_DATE = "presedensDato";
    public static final String PRECEDENCE_AUTHORITY = "presedensHjemmel";
    public static final String PRECEDENCE_SOURCE_OF_LAW = "rettskildefaktor";
    public static final String PRECEDENCE_APPROVED_DATE = "presedensGodkjentDato";
    public static final String PRECEDENCE_APPROVED_BY = "presedensGodkjentAv";
    public static final String PRECEDENCE_STATUS = "presedensStatus";

    // Disposal
    public static final String DISPOSAL = "kassasjon";
    public static final String DISPOSAL_DECISION = "kassasjonsvedtak";
    public static final String DISPOSAL_AUTHORITY = "kassasjonshjemmel";
    public static final String DISPOSAL_PRESERVATION_TIME = "bevaringstid";
    public static final String DISPOSAL_DATE = "kassasjonsdato";

    // DisposalUndertaken
    public static final String DISPOSAL_UNDERTAKEN = "utfoertKassasjon";
    public static final String DISPOSAL_UNDERTAKEN_BY = "kassertAv";
    public static final String DISPOSAL_UNDERTAKEN_DATE = "kassertDato";

    // Screening
    public static final String SCREENING = "skjerming";
    public static final String SCREENING_ACCESS_RESTRICTION = "tilgangsrestriksjon";
    public static final String SCREENING_AUTHORITY = "skjermingshjemmel";
    public static final String SCREENING_METADATA = "skjermingMetadata";
    public static final String SCREENING_DOCUMENT = "skjermingDokument";
    public static final String SCREENING_EXPIRES_DATE = "skjermingOpphoererDato";
    public static final String SCREENING_DURATION = "skjermingsvarighet";

    // Deletion
    public static final String DELETION = "sletting";
    public static final String DELETION_TYPE = "slettingstype";
    public static final String DELETION_BY = "slettetAv";
    public static final String DELETION_DATE = "slettetDato";

    // Comment
    public static final String COMMENT = "merknad";
    public static final String COMMENT_TEXT = "merknadstekst";
    public static final String COMMENT_TYPE = "merknadstype";
    public static final String COMMENT_DATE = "merknadsdato";
    public static final String COMMENT_REGISTERED_BY = "merknadRegistrertAv";

    // Classified
    public static final String CLASSIFIED = "gradering"; // root node
    public static final String CLASSIFICATION = "gradering"; // property node
    public static final String CLASSIFICATION_DATE = "graderingsdato";
    public static final String CLASSIFICATION_BY = "gradertAv";
    public static final String CLASSIFICATION_DOWNGRADED_DATE = "nedgraderingsdato";
    public static final String CLASSIFICATION_DOWNGRADED_BY = "nedgradertAv";

    // CrossReference
    public static final String CROSS_REFERENCE = "kryssreferanse";
    public static final String CROSS_REFERENCE_RECORD = "referanseTilRegistrering";
    public static final String CROSS_REFERENCE_FILE = "referanseTilMappe";
    public static final String CROSS_REFERENCE_CLASS = "referanseTilKlasse";

    // ElectronicSignature
    public static final String ELECTRONIC_SIGNATURE = "elektronisksignatur";
    public static final String ELECTRONIC_SIGNATURE_SECURITY_LEVEL = "elektronisksignatursikkerhetsnivaa";
    public static final String ELECTRONIC_SIGNATURE_VERIFIED = "elektronisksignaturverifisert";
    public static final String ELECTRONIC_SIGNATURE_VERIFIED_DATE = "verifisertDato";
    public static final String ELECTRONIC_SIGNATURE_VERIFIED_BY = "verifisertAv";

    // Meeting
    public static final String MEETING_FILE_TYPE = "moetesakstype";
    public static final String MEETING_PARTICIPANT_FUNCTION = "moetedeltakerfunksjon";
    public static final String MEETING_REGISTRATION_STATUS = "moteregistreringsstatus";
    public static final String MEETING_REGISTRATION_TYPE = "moeteregistreringstype";

    // FondsCreator
    public static final String FONDS_CREATOR = "arkivskaper";
    public static final String FONDS_CREATOR_ID = "arkivskaperID";
    public static final String FONDS_CREATOR_NAME = "arkivskaperNavn";

    // SignOff
    public static final String SIGN_OFF  = "avskrivning";
    public static final String SIGN_OFF_DATE  = "avskrivning";
    public static final String SIGN_OFF_BY  = "avskrevetAv";
    public static final String SIGN_OFF_METHOD  = "avskrivningsmaate";

    // DocumentFlow
    public static final String DOCUMENT_FLOW = "dokumentflyt";
    public static final String DOCUMENT_FLOW_FLOW_TO = "flytTil";
    public static final String DOCUMENT_FLOW_FLOW_FROM = "flytFra";
    public static final String DOCUMENT_FLOW_FLOW_RECEIVED_DATE = "flytMottattDato";
    public static final String DOCUMENT_FLOW_FLOW_SENT_DATE = "flytSendtDato";
    public static final String DOCUMENT_FLOW_FLOW_STATUS = "flytStatus";
    public static final String DOCUMENT_FLOW_FLOW_COMMENT = "flytMerknad";

    // Conversion
    public static final String CONVERSION = "konvertering";
    public static final String CONVERTED_DATE = "konvertertDato";
    public static final String CONVERTED_BY = "konvertertAv";
    public static final String CONVERTED_FROM_FORMAT = "konvertertFraFormat";
    public static final String CONVERTED_TO_FORMAT = "konvertertTilFormat";
    public static final String CONVERSION_TOOL = "konverteringsverktoey";
    public static final String CONVERSION_COMMENT = "konverteringskommentar";

    // Author
    public static final String AUTHOR = "forfatter";


    public static final String SECONDARY_CLASSIFICATION = "sekundaerklassifikasjon";

    // Constant values defined in the Metadata catalogue

    public static final String STATUS_OPEN = "Opprettet";
    public static final String STATUS_CLOSED = "Avsluttet";

    // M300 dokumentmedium
    public static final String DOCUMENT_MEDIUM_PHYSICAL = "Fysisk arkiv";
    public static final String DOCUMENT_MEDIUM_ELECTRONIC ="Elektronisk arkiv";
    public static final String DOCUMENT_MEDIUM_MIXED = "Blandet fysisk og elektronisk arkiv";

    // M217 tilknyttetRegistreringSom
    public static final String MAIN_DOCUMENT = "Hoveddokument";
    public static final String ATTACHMENT = "Vedlegg";

    // M083 dokumenttype
    public static final String LETTER = "Brev";
    public static final String CIRCULAR = "Rundskriv";
    public static final String INVOICE = "Faktura";
    public static final String CONFIRMATION = "Ordrebekreftelser";

    // M054 dokumentstatus
    public static final String DOCUMENT_STATUS_EDIT = "Dokumentet er under redigering";
    public static final String DOCUMENT_STATUS_FINALISED = "Dokumentet er ferdigstilt\"";

    // M700 variantformat

    public static final String PRODUCTION_VERSION = "Produksjonsformat";
    public static final String ARCHIVE_VERSION = "Arkivformat";
    public static final String SCREENED_VERSION = "Dokument hvor deler av innholdet er skjermet";

    public static final String FORMAT = "format";
    public static final String VARIANT_FORMAT = "variantformat";
    public static final String DOCUMENT_STATUS = "dokumentstatus";
    public static final String DOCUMENT_TYPE = "dokumenttype";

    public static final String CLASSIFICATION_SYSTEM_TYPE = "klassifikasjonstype";
    private N5ResourceMappings() {
        throw new AssertionError();
    }
}

