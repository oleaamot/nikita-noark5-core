package nikita.config;


import static nikita.config.Constants.DASH;
import static nikita.config.Constants.NEW;

public final class N5ResourceMappings {

    // Commonly used entities for REST request mappings
    public static final String FONDS = "arkiv";
    public static final String SUB_FONDS = "underarkiv";
    public static final String SERIES = "arkivdel";
    public static final String REFERENCE_SERIES = "referanseArkivdel";
    public static final String REFERENCE_ADMINISTRATIVE_UNIT = "referanseAdministratitivEnhet";
    public static final String REFERENCE_CASE_HANDLER = "referanseSaksbehandler";
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
    public static final String STORAGE_LOCATION = "oppbevaringssted";
    // Not part of interface standard
    public static final String STORAGE_LOCATIONS = "oppbevaringssteder";

    public static final String KEYWORD = "noekkelord";

    public static final String CODE = "kode";

    public static final String FILE_TYPE = "mappetype";
    public static final String FLOW_STATUS = "flytstatus";
    public static final String EVENT_TYPE = "hendelsetype";
    // Might be confusion if it's land or lankode
    public static final String COUNTRY = "land";
    public static final String COUNTRY_CODE = "landkode";
    public static final String CLASSIFIED_CODE = "graderingskode";
    public static final String CLASSIFICATION_TYPE = "klassifikasjonstype";
    public static final String ASSOCIATED_WITH_RECORD_AS = "tilknyttetregistreringsom";
    public static final String ACCESS_RESTRICTION = "tilgangsrestriksjon";
    public static final String ACCESS_CATEGORY = "tilgangskategori";
    public static final String POSTAL_NUMBER = "postnummer";
    public static final String POSTAL_TOWN = "poststed";
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
    public static final String NEW_DOCUMENT_MEDIUM = NEW + DASH + DOCUMENT_MEDIUM;

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

    public static final String CASE_RESPONSIBLE = "saksansvarlig";
    public static final String CASE_RECORDS_MANAGEMENT_UNIT = "journalenhet";
    public static final String CASE_STATUS = "saksstatus";
    public static final String CASE_LOANED_DATE = "utlaantDato";
    public static final String CASE_LOANED_TO = "utlaantTil";

    // CaseParty
    public static final String CASE_PARTY = "sakspart";
    public static final String CASE_PARTY_ID = "saksPartID";
    public static final String CASE_PARTY_NAME = "sakspartNavn";
    public static final String CASE_PARTY_ROLE = "sakspartrolle";

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

    public static final String CORRESPONDENCE_PART_PERSON = "korrespondansepartperson";
    public static final String CORRESPONDENCE_PART_INTERNAL = "korrespondansepartintern";
    public static final String CORRESPONDENCE_PART_UNIT = "korrespondansepartenhet";
    public static final String CORRESPONDENCE_PART_TYPE = "korrespondanseparttype";
    public static final String CORRESPONDENCE_PART_NAME = "navn";
    public static final String POST_CODE = "postnummer";

    public static final String SOCIAL_SECURITY_NUMBER = "foedselsnummer";
    public static final String D_NUMBER = "dnummer";
    public static final String CASE_HANDLER = "saksbehandler";
    // This is probably CORRESPONDENCE_PART_NAME. Waiting for clarification
    public static final String NAME = "navn";
    public static final String ADDRESS_LINE_1 = "adresselinje1";
    public static final String ADDRESS_LINE_2 = "adresselinje2";
    public static final String ADDRESS_LINE_3 = "adresselinje3";
    public static final String CONTACT_INFORMATION = "kontaktinformasjon";
    public static final String EMAIL_ADDRESS = "epostadresse";
    public static final String TELEPHONE_NUMBER = "telefonnummer";
    public static final String MOBILE_TELEPHONE_NUMBER = "mobiltelefon";
    public static final String FOREIGN_ADDRESS = "utenlandsadresse";
    public static final String CONTACT_PERSON = "kontaktperson";
    public static final String ORGANISATION_NUMBER = "organisasjonsnummer";
    public static final String POSTAL_ADDRESS = "postadresse";
    public static final String RESIDING_ADDRESS = "bostedsadresse";
    public static final String BUSINESS_ADDRESS = "forretningsadresse";

    // DocumentDescription
    public static final String DOCUMENT_DESCRIPTION_ASSOCIATION_DATE = "tilknyttetDato";
    public static final String DOCUMENT_DESCRIPTION_ASSOCIATION_BY = "tilknyttetAv";
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
    public static final String PRECEDENCE_STATUS = "presedensstatus";

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
    public static final String DOCUMENT_STATUS_FINALISED = "Dokumentet er ferdigstilt";

    // M700 variantformat

    public static final String PRODUCTION_VERSION = "Produksjonsformat";
    public static final String ARCHIVE_VERSION = "Arkivformat";
    public static final String SCREENED_VERSION = "Dokument hvor deler av innholdet er skjermet";

    public static final String FORMAT = "format";
    public static final String VARIANT_FORMAT = "variantformat";
    public static final String DOCUMENT_STATUS = "dokumentstatus";
    public static final String DOCUMENT_TYPE = "dokumenttype";

    public static final String CLASSIFICATION_SYSTEM_TYPE = "klassifikasjonstype";

    public static final String USER = "bruker";
    public static final String USER_NAME = "brukerNavn";
    public static final String NEW_USER = NEW + DASH + USER;

    public static final String RIGHT = "rettighet";
    public static final String NEW_RIGHT = NEW + DASH + RIGHT;

    public static final String ADMINISTRATIVE_UNIT = "administrativEnhet";
    public static final String NEW_ADMINISTRATIVE_UNIT = NEW + DASH + "administrativEnhet";

    public static final String ADMINISTRATIVE_UNIT_STATUS = "administrativEnhetsstatus";
    public static final String ADMINISTRATIVE_UNIT_NAME = "administrativEnhetNavn";
    public static final String ADMINISTRATIVE_UNIT_PARENT_REFERENCE = "referanseOverordnetEnhet";

    public static final String SHORT_NAME = "kortnavn";


    // English version of above, sorted alphabetically. The following are the
    // table / column names within the Noark domain model
    // TODO: Do the actual translation job,
    public static final String ACCESS_CATEGORY_ENG = "access_category";
    public static final String ACCESS_RESTRICTION_ENG = "access_restriction";
    public static final String ADDRESS_LINE_1_ENG = "adresselinje1";
    public static final String ADDRESS_LINE_2_ENG = "adresselinje2";
    public static final String ADDRESS_LINE_3_ENG = "adresselinje3";
    public static final String ADMINISTRATIVE_UNIT_ENG = "administrativEnhet";
    public static final String ADMINISTRATIVE_UNIT_NAME_ENG = "administrativEnhetNavn";
    public static final String ADMINISTRATIVE_UNIT_PARENT_REFERENCE_ENG = "referanseOverordnetEnhet";
    public static final String ADMINISTRATIVE_UNIT_STATUS_ENG = "administrativEnhetsstatus";
    public static final String ASSOCIATED_WITH_RECORD_AS_ENG = "tilknyttetregistreringsom";
    public static final String AUTHOR_ENG = "forfatter";
    public static final String BASIC_RECORD_ENG = "basisregistrering";
    public static final String BASIC_RECORD_ID_ENG = "registreringsID";
    public static final String BUSINESS_ADDRESS_ENG = "forretningsadresse";
    public static final String CASE_DATE_ENG = "saksdato";
    public static final String CASE_FILE_ENG = "saksmappe";
    public static final String CASE_HANDLER_ENG = "saksbehandler";
    public static final String CASE_LOANED_DATE_ENG = "utlaantDato";
    public static final String CASE_LOANED_TO_ENG = "utlaantTil";
    public static final String CASE_PARTY_ENG = "sakspart";
    public static final String CASE_PARTY_ID_ENG = "saksPartID";
    public static final String CASE_PARTY_NAME_ENG = "sakspartNavn";
    public static final String CASE_PARTY_ROLE_ENG = "sakspartrolle";
    public static final String CASE_RECORDS_MANAGEMENT_UNIT_ENG = "journalenhet";
    public static final String CASE_RESPONSIBLE_ENG = "saksansvarlig";
    public static final String CASE_SEQUENCE_NUMBER_ENG = "sakssekvensnummer";
    public static final String CASE_STATUS_ENG = "saksstatus";
    public static final String CASE_YEAR_ENG = "saksaar";
    public static final String CLASS_ENG = "klasse";
    public static final String CLASS_ID_ENG = "klasseID";
    public static final String CLASSIFICATION_BY_ENG = "gradertAv";
    public static final String CLASSIFICATION_DATE_ENG = "graderingsdato";
    public static final String CLASSIFICATION_DOWNGRADED_BY_ENG = "nedgradertAv";
    public static final String CLASSIFICATION_DOWNGRADED_DATE_ENG = "nedgraderingsdato";
    public static final String CLASSIFICATION_ENG = "gradering"; // property node
    public static final String CLASSIFICATION_SYSTEM_ENG = "klassifikasjonssystem";
    public static final String CLASSIFICATION_SYSTEM_TYPE_ENG = "klassifikasjonstype";
    public static final String CLASSIFICATION_TYPE_ENG = "klassifikasjonstype";
    public static final String CLASSIFIED_CODE_ENG = "graderingskode";
    public static final String CLASSIFIED_ENG = "gradering"; // root node
    public static final String CODE_ENG = "kode";
    public static final String COMMENT_DATE_ENG = "merknadsdato";
    public static final String COMMENT_ENG = "merknad";
    public static final String COMMENT_REGISTERED_BY_ENG = "merknadRegistrertAv";
    public static final String COMMENT_TEXT_ENG = "merknadstekst";
    public static final String COMMENT_TYPE_ENG = "merknadstype";
    public static final String CONTACT_INFORMATION_ENG = "kontaktinformasjon";
    public static final String CONTACT_PERSON_ENG = "kontaktperson";
    public static final String CONVERSION_COMMENT_ENG = "konverteringskommentar";
    public static final String CONVERSION_ENG = "konvertering";
    public static final String CONVERSION_TOOL_ENG = "konverteringsverktoey";
    public static final String CONVERTED_BY_ENG = "konvertertAv";
    public static final String CONVERTED_DATE_ENG = "konvertertDato";
    public static final String CONVERTED_FROM_FORMAT_ENG = "konvertertFraFormat";
    public static final String CONVERTED_TO_FORMAT_ENG = "konvertertTilFormat";
    public static final String CORRESPONDENCE_PART_ENG = "korrespondansepart";
    public static final String CORRESPONDENCE_PART_INTERNAL_ENG = "korrespondansepartintern";
    public static final String CORRESPONDENCE_PART_NAME_ENG = "navn";
    public static final String CORRESPONDENCE_PART_PERSON_ENG = "korrespondansepartperson";
    public static final String CORRESPONDENCE_PART_TYPE_ENG = "korrespondanseparttype";
    public static final String CORRESPONDENCE_PART_UNIT_ENG = "korrespondansepartenhet";
    public static final String COUNTRY_CODE_ENG = "landkode";
    public static final String COUNTRY_ENG = "land";
    public static final String CREATED_BY_ENG = "opprettetAv";
    public static final String CREATED_DATE_ENG = "opprettetDato";
    public static final String CROSS_REFERENCE_CLASS_ENG = "referanseTilKlasse";
    public static final String CROSS_REFERENCE_ENG = "kryssreferanse";
    public static final String CROSS_REFERENCE_FILE_ENG = "referanseTilMappe";
    public static final String CROSS_REFERENCE_RECORD_ENG = "referanseTilRegistrering";
    public static final String DELETION_BY_ENG = "slettetAv";
    public static final String DELETION_DATE_ENG = "slettetDato";
    public static final String DELETION_ENG = "sletting";
    public static final String DELETION_TYPE_ENG = "slettingstype";
    public static final String DESCRIPTION_ENG = "beskrivelse";
    public static final String DISPOSAL_AUTHORITY_ENG = "kassasjonshjemmel";
    public static final String DISPOSAL_DATE_ENG = "kassasjonsdato";
    public static final String DISPOSAL_DECISION_ENG = "kassasjonsvedtak";
    public static final String DISPOSAL_ENG = "kassasjon";
    public static final String DISPOSAL_PRESERVATION_TIME_ENG = "bevaringstid";
    public static final String DISPOSAL_UNDERTAKEN_BY_ENG = "kassertAv";
    public static final String DISPOSAL_UNDERTAKEN_DATE_ENG = "kassertDato";
    public static final String DISPOSAL_UNDERTAKEN_ENG = "utfoertKassasjon";
    public static final String D_NUMBER_ENG = "dnummer";
    public static final String DOCUMENT_DESCRIPTION_ASSOCIATED_BY_ENG = "tilknyttetAv";
    public static final String DOCUMENT_DESCRIPTION_ASSOCIATED_WITH_RECORD_AS_ENG = "tilknyttetRegistreringSom";
    public static final String DOCUMENT_DESCRIPTION_ASSOCIATION_BY_ENG = "tilknyttetAv";
    public static final String DOCUMENT_DESCRIPTION_ASSOCIATION_DATE_ENG = "tilknyttetDato";
    public static final String DOCUMENT_DESCRIPTION_DOCUMENT_NUMBER_ENG = "dokumentnummer";
    public static final String DOCUMENT_DESCRIPTION_DOCUMENT_TYPE_ENG = "dokumenttype";
    public static final String DOCUMENT_DESCRIPTION_ENG = "dokumentbeskrivelse";
    public static final String DOCUMENT_DESCRIPTION_STATUS_ENG = "dokumentstatus";
    public static final String DOCUMENT_FLOW_ENG = "dokumentflyt";
    public static final String DOCUMENT_FLOW_FLOW_COMMENT_ENG = "flytMerknad";
    public static final String DOCUMENT_FLOW_FLOW_FROM_ENG = "flytFra";
    public static final String DOCUMENT_FLOW_FLOW_RECEIVED_DATE_ENG = "flytMottattDato";
    public static final String DOCUMENT_FLOW_FLOW_SENT_DATE_ENG = "flytSendtDato";
    public static final String DOCUMENT_FLOW_FLOW_STATUS_ENG = "flytStatus";
    public static final String DOCUMENT_FLOW_FLOW_TO_ENG = "flytTil";
    public static final String DOCUMENT_MEDIUM_ENG = "dokumentmedium";
    public static final String DOCUMENT_OBJECT_CHECKSUM_ALGORITHM_ENG = "sjekksumAlgoritme";
    public static final String DOCUMENT_OBJECT_CHECKSUM_ENG = "sjekksum";
    public static final String DOCUMENT_OBJECT_ENG = "dokumentobjekt";
    public static final String DOCUMENT_OBJECT_FILE_NAME_ENG = "filnavn";
    public static final String DOCUMENT_OBJECT_FILE_SIZE_ENG = "filstoerrelse";
    public static final String DOCUMENT_OBJECT_FORMAT_DETAILS_ENG = "formatDetaljer";
    public static final String DOCUMENT_OBJECT_FORMAT_ENG = "format";
    public static final String DOCUMENT_OBJECT_MIME_TYPE_ENG = "mimeType";
    public static final String DOCUMENT_OBJECT_REFERENCE_DOCUMENT_FILE_ENG = "referanseDokumentfil";
    public static final String DOCUMENT_OBJECT_VARIANT_FORMAT_ENG = "variantformat";
    public static final String DOCUMENT_OBJECT_VERSION_NUMBER_ENG = "versjonsnummer";
    public static final String DOCUMENT_STATUS_ENG = "dokumentstatus";
    public static final String DOCUMENT_TYPE_ENG = "dokumenttype";
    public static final String ELECTRONIC_SIGNATURE_ENG = "elektronisksignatur";
    public static final String ELECTRONIC_SIGNATURE_SECURITY_LEVEL_ENG = "elektronisksignatursikkerhetsnivaa";
    public static final String ELECTRONIC_SIGNATURE_VERIFIED_BY_ENG = "verifisertAv";
    public static final String ELECTRONIC_SIGNATURE_VERIFIED_DATE_ENG = "verifisertDato";
    public static final String ELECTRONIC_SIGNATURE_VERIFIED_ENG = "elektronisksignaturverifisert";
    public static final String EMAIL_ADDRESS_ENG = "epostadresse";
    public static final String EVENT_TYPE_ENG = "hendelsetype";
    public static final String FILE_ENG = "file";
    public static final String FILE_ID_ENG = "fileId";
    public static final String FILE_PUBLIC_TITLE_ENG = "offentligTittel";
    public static final String FILE_TYPE_ENG = "mappetype";
    public static final String FINALISED_BY_ENG = "avsluttetAv";
    public static final String FINALISED_DATE_ENG = "avsluttetDato";
    public static final String FLOW_STATUS_ENG = "flytstatus";
    public static final String FONDS_CREATOR_ENG = "arkivskaper";
    public static final String FONDS_CREATOR_ID_ENG = "fondsCreatorId";
    public static final String FONDS_CREATOR_NAME_ENG = "fondsCreatorName";
    public static final String FONDS_ENG = "fonds";
    public static final String FONDS_STATUS_ENG = "arkivstatus";
    public static final String FOREIGN_ADDRESS_ENG = "utenlandsadresse";
    public static final String FORMAT_ENG = "format";
    public static final String KEYWORD_ENG = "noekkelord";
    public static final String MEETING_FILE_ENG = "moetemappe";
    public static final String MEETING_FILE_TYPE_ENG = "moetesakstype";
    public static final String MEETING_PARTICIPANT_ENG = "moetedeltager";
    public static final String MEETING_PARTICIPANT_FUNCTION_ENG = "moetedeltakerfunksjon";
    public static final String MEETING_RECORD_ENG = "moeteregistrering";
    public static final String MEETING_REGISTRATION_STATUS_ENG = "moteregistreringsstatus";
    public static final String MEETING_REGISTRATION_TYPE_ENG = "moeteregistreringstype";
    public static final String MOBILE_TELEPHONE_NUMBER_ENG = "mobiltelefon";
    public static final String NAME_ENG = "navn";
    public static final String ORGANISATION_NUMBER_ENG = "organisasjonsnummer";
    public static final String PARENT_CLASS_ENG = "overordnetklasse";
    public static final String POSTAL_ADDRESS_ENG = "postadresse";
    public static final String POSTAL_NUMBER_ENG = "postnummer";
    public static final String POSTAL_TOWN_ENG = "poststed";
    public static final String POST_CODE_ENG = "postnummer";
    public static final String PRECEDENCE_APPROVED_BY_ENG = "presedensGodkjentAv";
    public static final String PRECEDENCE_APPROVED_DATE_ENG = "presedensGodkjentDato";
    public static final String PRECEDENCE_AUTHORITY_ENG = "presedensHjemmel";
    public static final String PRECEDENCE_DATE_ENG = "presedensDato";
    public static final String PRECEDENCE_ENG = "presedens";
    public static final String PRECEDENCE_SOURCE_OF_LAW_ENG = "rettskildefaktor";
    public static final String PRECEDENCE_STATUS_ENG = "presedensstatus";
    public static final String PRODUCTION_VERSION_ENG = "Produksjonsformat";
    public static final String RECORD_ARCHIVED_BY_ENG = "arkivertAv";
    public static final String RECORD_ARCHIVED_DATE_ENG = "arkivertDato";
    public static final String REFERENCE_ADMINISTRATIVE_UNIT_ENG = "referanseAdministratitivEnhet";
    public static final String REFERENCE_CASE_HANDLER_ENG = "referanseSaksbehandler";
    public static final String REFERENCE_SERIES_ENG = "referanseArkivdel";
    public static final String REGISTRATION_ENG = "registrering";
    public static final String REGISTRY_ENTRY_DATE_ENG = "journaldato";
    public static final String REGISTRY_ENTRY_DOCUMENT_DATE_ENG = "dokumentetsDato";
    public static final String REGISTRY_ENTRY_DUE_DATE_ENG = "forfallsdato";
    public static final String REGISTRY_ENTRY_ENG = "journalpost";
    public static final String REGISTRY_ENTRY_NUMBER_ENG = "journalpostnummer";
    public static final String REGISTRY_ENTRY_NUMBER_OF_ATTACHMENTS_ENG = "antallVedlegg";
    public static final String REGISTRY_ENTRY_RECEIVED_DATE_ENG = "mottattDato";
    public static final String REGISTRY_ENTRY_RECORD_FREEDOM_ASSESSMENT_DATE_ENG = "offentlighetsvurdertDato";
    public static final String REGISTRY_ENTRY_SENT_DATE_ENG = "sendtDato";
    public static final String REGISTRY_ENTRY_SEQUENCE_NUMBER_ENG = "journalsekvensnummer";
    public static final String REGISTRY_ENTRY_STATUS_ENG = "journalstatus";
    public static final String REGISTRY_ENTRY_TYPE_ENG = "journalposttype";
    public static final String REGISTRY_ENTRY_YEAR_ENG = "journalaar";
    public static final String RESIDING_ADDRESS_ENG = "bostedsadresse";
    public static final String RIGHT_ENG = "rettighet";
    public static final String SCREENING_ACCESS_RESTRICTION_ENG = "tilgangsrestriksjon";
    public static final String SCREENING_AUTHORITY_ENG = "skjermingshjemmel";
    public static final String SCREENING_DOCUMENT_ENG = "skjermingDokument";
    public static final String SCREENING_DURATION_ENG = "skjermingsvarighet";
    public static final String SCREENING_ENG = "skjerming";
    public static final String SCREENING_EXPIRES_DATE_ENG = "skjermingOpphoererDato";
    public static final String SCREENING_METADATA_ENG = "skjermingMetadata";
    public static final String SECONDARY_CLASSIFICATION_ENG = "sekundaerklassifikasjon";
    public static final String SECONDARY_CLASSIFICATION_SYSTEM_ENG = "sekundaerklassifikasjonssystem";
    public static final String SERIES_END_DATE_ENG = "arkivperiodeSluttDato";
    public static final String SERIES_ENG = "arkivdel";
    public static final String SERIES_PRECURSOR_ENG = "forloeper";
    public static final String SERIES_START_DATE_ENG = "arkivperiodeStartDato";
    public static final String SERIES_STATUS_ENG = "arkivdelstatus";
    public static final String SERIES_SUCCESSOR_ENG = "arvtager";
    public static final String SHORT_NAME_ENG = "kortnavn";
    public static final String SIGN_OFF_BY_ENG = "avskrevetAv";
    public static final String SIGN_OFF_DATE_ENG = "avskrivning";
    public static final String SIGN_OFF_ENG = "avskrivning";
    public static final String STORAGE_LOCATION_ENG = "storage_location";
    public static final String SIGN_OFF_METHOD_ENG = "avskrivningsmaate";
    public static final String SOCIAL_SECURITY_NUMBER_ENG = "social_security_number";
    public static final String SUB_CLASS_ENG = "underklasse";
    public static final String SUB_FONDS_ENG = "underarkiv";
    public static final String SYSTEM_ID_ENG = "systemID";
    public static final String TELEPHONE_NUMBER_ENG = "telefonnummer";
    public static final String TITLE_ENG = "title";
    public static final String USER_ENG = "bruker";
    public static final String USER_NAME_ENG = "brukerNavn";
    public static final String VARIANT_FORMAT_ENG = "variantformat";

    // English version of above, sorted alphabetically. The following are the
    // english object names as used within the Noark domain model
    // TODO: Do the actual translation job,
    public static final String ACCESS_CATEGORY_ENG_OBJECT = "tilgangskategori";
    public static final String ACCESS_RESTRICTION_ENG_OBJECT = "tilgangsrestriksjon";
    public static final String ADDRESS_LINE_1_ENG_OBJECT = "adresselinje1";
    public static final String ADDRESS_LINE_2_ENG_OBJECT = "adresselinje2";
    public static final String ADDRESS_LINE_3_ENG_OBJECT = "adresselinje3";
    public static final String ADMINISTRATIVE_UNIT_ENG_OBJECT = "administrativEnhet";
    public static final String ADMINISTRATIVE_UNIT_NAME_ENG_OBJECT = "administrativEnhetNavn";
    public static final String ADMINISTRATIVE_UNIT_PARENT_REFERENCE_ENG_OBJECT = "referanseOverordnetEnhet";
    public static final String ADMINISTRATIVE_UNIT_STATUS_ENG_OBJECT = "administrativEnhetsstatus";
    public static final String ASSOCIATED_WITH_RECORD_AS_ENG_OBJECT = "tilknyttetregistreringsom";
    public static final String AUTHOR_ENG_OBJECT = "forfatter";
    public static final String BASIC_RECORD_ENG_OBJECT = "basisregistrering";
    public static final String BASIC_RECORD_ID_ENG_OBJECT = "registreringsID";
    public static final String BUSINESS_ADDRESS_ENG_OBJECT = "forretningsadresse";
    public static final String CASE_DATE_ENG_OBJECT = "saksdato";
    public static final String CASE_FILE_ENG_OBJECT = "saksmappe";
    public static final String CASE_HANDLER_ENG_OBJECT = "saksbehandler";
    public static final String CASE_LOANED_DATE_ENG_OBJECT = "utlaantDato";
    public static final String CASE_LOANED_TO_ENG_OBJECT = "utlaantTil";
    public static final String CASE_PARTY_ENG_OBJECT = "sakspart";
    public static final String CASE_PARTY_ID_ENG_OBJECT = "saksPartID";
    public static final String CASE_PARTY_NAME_ENG_OBJECT = "sakspartNavn";
    public static final String CASE_PARTY_ROLE_ENG_OBJECT = "sakspartrolle";
    public static final String CASE_RECORDS_MANAGEMENT_UNIT_ENG_OBJECT = "journalenhet";
    public static final String CASE_RESPONSIBLE_ENG_OBJECT = "saksansvarlig";
    public static final String CASE_SEQUENCE_NUMBER_ENG_OBJECT = "sakssekvensnummer";
    public static final String CASE_STATUS_ENG_OBJECT = "saksstatus";
    public static final String CASE_YEAR_ENG_OBJECT = "saksaar";
    public static final String CLASS_ENG_OBJECT = "klasse";
    public static final String CLASS_ID_ENG_OBJECT = "klasseID";
    public static final String CLASSIFICATION_BY_ENG_OBJECT = "gradertAv";
    public static final String CLASSIFICATION_DATE_ENG_OBJECT = "graderingsdato";
    public static final String CLASSIFICATION_DOWNGRADED_BY_ENG_OBJECT = "nedgradertAv";
    public static final String CLASSIFICATION_DOWNGRADED_DATE_ENG_OBJECT = "nedgraderingsdato";
    public static final String CLASSIFICATION_ENG_OBJECT = "gradering"; // property node
    public static final String CLASSIFICATION_SYSTEM_ENG_OBJECT = "klassifikasjonssystem";
    public static final String CLASSIFICATION_SYSTEM_TYPE_ENG_OBJECT = "klassifikasjonstype";
    public static final String CLASSIFICATION_TYPE_ENG_OBJECT = "klassifikasjonstype";
    public static final String CLASSIFIED_CODE_ENG_OBJECT = "graderingskode";
    public static final String CLASSIFIED_ENG_OBJECT = "gradering"; // root node
    public static final String CODE_ENG_OBJECT = "kode";
    public static final String COMMENT_DATE_ENG_OBJECT = "merknadsdato";
    public static final String COMMENT_ENG_OBJECT = "merknad";
    public static final String COMMENT_REGISTERED_BY_ENG_OBJECT = "merknadRegistrertAv";
    public static final String COMMENT_TEXT_ENG_OBJECT = "merknadstekst";
    public static final String COMMENT_TYPE_ENG_OBJECT = "merknadstype";
    public static final String CONTACT_INFORMATION_ENG_OBJECT = "kontaktinformasjon";
    public static final String CONTACT_PERSON_ENG_OBJECT = "kontaktperson";
    public static final String CONVERSION_COMMENT_ENG_OBJECT = "konverteringskommentar";
    public static final String CONVERSION_ENG_OBJECT = "konvertering";
    public static final String CONVERSION_TOOL_ENG_OBJECT = "konverteringsverktoey";
    public static final String CONVERTED_BY_ENG_OBJECT = "konvertertAv";
    public static final String CONVERTED_DATE_ENG_OBJECT = "konvertertDato";
    public static final String CONVERTED_FROM_FORMAT_ENG_OBJECT = "konvertertFraFormat";
    public static final String CONVERTED_TO_FORMAT_ENG_OBJECT = "konvertertTilFormat";
    public static final String CORRESPONDENCE_PART_ENG_OBJECT = "korrespondansepart";
    public static final String CORRESPONDENCE_PART_INTERNAL_ENG_OBJECT = "korrespondansepartintern";
    public static final String CORRESPONDENCE_PART_NAME_ENG_OBJECT = "navn";
    public static final String CORRESPONDENCE_PART_PERSON_ENG_OBJECT = "korrespondansepartperson";
    public static final String CORRESPONDENCE_PART_TYPE_ENG_OBJECT = "korrespondanseparttype";
    public static final String CORRESPONDENCE_PART_UNIT_ENG_OBJECT = "korrespondansepartenhet";
    public static final String COUNTRY_CODE_ENG_OBJECT = "landkode";
    public static final String COUNTRY_ENG_OBJECT = "land";
    public static final String CREATED_BY_ENG_OBJECT = "opprettetAv";
    public static final String CREATED_DATE_ENG_OBJECT = "opprettetDato";
    public static final String CROSS_REFERENCE_CLASS_ENG_OBJECT = "referanseTilKlasse";
    public static final String CROSS_REFERENCE_ENG_OBJECT = "kryssreferanse";
    public static final String CROSS_REFERENCE_FILE_ENG_OBJECT = "referanseTilMappe";
    public static final String CROSS_REFERENCE_RECORD_ENG_OBJECT = "referanseTilRegistrering";
    public static final String DELETION_BY_ENG_OBJECT = "slettetAv";
    public static final String DELETION_DATE_ENG_OBJECT = "slettetDato";
    public static final String DELETION_ENG_OBJECT = "sletting";
    public static final String DELETION_TYPE_ENG_OBJECT = "slettingstype";
    public static final String DESCRIPTION_ENG_OBJECT = "beskrivelse";
    public static final String DISPOSAL_AUTHORITY_ENG_OBJECT = "kassasjonshjemmel";
    public static final String DISPOSAL_DATE_ENG_OBJECT = "kassasjonsdato";
    public static final String DISPOSAL_DECISION_ENG_OBJECT = "kassasjonsvedtak";
    public static final String DISPOSAL_ENG_OBJECT = "kassasjon";
    public static final String DISPOSAL_PRESERVATION_TIME_ENG_OBJECT = "bevaringstid";
    public static final String DISPOSAL_UNDERTAKEN_BY_ENG_OBJECT = "kassertAv";
    public static final String DISPOSAL_UNDERTAKEN_DATE_ENG_OBJECT = "kassertDato";
    public static final String DISPOSAL_UNDERTAKEN_ENG_OBJECT = "utfoertKassasjon";
    public static final String D_NUMBER_ENG_OBJECT = "dnummer";
    public static final String DOCUMENT_DESCRIPTION_ASSOCIATED_BY_ENG_OBJECT = "tilknyttetAv";
    public static final String DOCUMENT_DESCRIPTION_ASSOCIATED_WITH_RECORD_AS_ENG_OBJECT = "tilknyttetRegistreringSom";
    public static final String DOCUMENT_DESCRIPTION_ASSOCIATION_BY_ENG_OBJECT = "tilknyttetAv";
    public static final String DOCUMENT_DESCRIPTION_ASSOCIATION_DATE_ENG_OBJECT = "tilknyttetDato";
    public static final String DOCUMENT_DESCRIPTION_DOCUMENT_NUMBER_ENG_OBJECT = "dokumentnummer";
    public static final String DOCUMENT_DESCRIPTION_DOCUMENT_TYPE_ENG_OBJECT = "dokumenttype";
    public static final String DOCUMENT_DESCRIPTION_ENG_OBJECT = "dokumentbeskrivelse";
    public static final String DOCUMENT_DESCRIPTION_STATUS_ENG_OBJECT = "dokumentstatus";
    public static final String DOCUMENT_FLOW_ENG_OBJECT = "dokumentflyt";
    public static final String DOCUMENT_FLOW_FLOW_COMMENT_ENG_OBJECT = "flytMerknad";
    public static final String DOCUMENT_FLOW_FLOW_FROM_ENG_OBJECT = "flytFra";
    public static final String DOCUMENT_FLOW_FLOW_RECEIVED_DATE_ENG_OBJECT = "flytMottattDato";
    public static final String DOCUMENT_FLOW_FLOW_SENT_DATE_ENG_OBJECT = "flytSendtDato";
    public static final String DOCUMENT_FLOW_FLOW_STATUS_ENG_OBJECT = "flytStatus";
    public static final String DOCUMENT_FLOW_FLOW_TO_ENG_OBJECT = "flytTil";
    public static final String DOCUMENT_MEDIUM_ENG_OBJECT = "dokumentmedium";
    public static final String DOCUMENT_OBJECT_CHECKSUM_ALGORITHM_ENG_OBJECT = "sjekksumAlgoritme";
    public static final String DOCUMENT_OBJECT_CHECKSUM_ENG_OBJECT = "sjekksum";
    public static final String DOCUMENT_OBJECT_ENG_OBJECT = "dokumentobjekt";
    public static final String DOCUMENT_OBJECT_FILE_NAME_ENG_OBJECT = "filnavn";
    public static final String DOCUMENT_OBJECT_FILE_SIZE_ENG_OBJECT = "filstoerrelse";
    public static final String DOCUMENT_OBJECT_FORMAT_DETAILS_ENG_OBJECT = "formatDetaljer";
    public static final String DOCUMENT_OBJECT_FORMAT_ENG_OBJECT = "format";
    public static final String DOCUMENT_OBJECT_MIME_TYPE_ENG_OBJECT = "mimeType";
    public static final String DOCUMENT_OBJECT_REFERENCE_DOCUMENT_FILE_ENG_OBJECT = "referanseDokumentfil";
    public static final String DOCUMENT_OBJECT_VARIANT_FORMAT_ENG_OBJECT = "variantformat";
    public static final String DOCUMENT_OBJECT_VERSION_NUMBER_ENG_OBJECT = "versjonsnummer";
    public static final String DOCUMENT_STATUS_ENG_OBJECT = "dokumentstatus";
    public static final String DOCUMENT_TYPE_ENG_OBJECT = "dokumenttype";
    public static final String ELECTRONIC_SIGNATURE_ENG_OBJECT = "elektronisksignatur";
    public static final String ELECTRONIC_SIGNATURE_SECURITY_LEVEL_ENG_OBJECT = "elektronisksignatursikkerhetsnivaa";
    public static final String ELECTRONIC_SIGNATURE_VERIFIED_BY_ENG_OBJECT = "verifisertAv";
    public static final String ELECTRONIC_SIGNATURE_VERIFIED_DATE_ENG_OBJECT = "verifisertDato";
    public static final String ELECTRONIC_SIGNATURE_VERIFIED_ENG_OBJECT = "elektronisksignaturverifisert";
    public static final String EMAIL_ADDRESS_ENG_OBJECT = "epostadresse";
    public static final String EVENT_TYPE_ENG_OBJECT = "hendelsetype";
    public static final String FILE_ENG_OBJECT = "file";
    public static final String FILE_ID_ENG_OBJECT = "fileId";
    public static final String FILE_PUBLIC_TITLE_ENG_OBJECT = "offentligTittel";
    public static final String FILE_TYPE_ENG_OBJECT = "mappetype";
    public static final String FINALISED_BY_ENG_OBJECT = "avsluttetAv";
    public static final String FINALISED_DATE_ENG_OBJECT = "avsluttetDato";
    public static final String FLOW_STATUS_ENG_OBJECT = "flytstatus";
    public static final String FONDS_CREATOR_ENG_OBJECT = "arkivskaper";
    public static final String FONDS_CREATOR_ID_ENG_OBJECT = "fondsCreatorId";
    public static final String FONDS_CREATOR_NAME_ENG_OBJECT = "fondsCreatorName";
    public static final String FONDS_ENG_OBJECT = "Fonds";
    public static final String FONDS_STATUS_ENG_OBJECT = "arkivstatus";
    public static final String FOREIGN_ADDRESS_ENG_OBJECT = "utenlandsadresse";
    public static final String FORMAT_ENG_OBJECT = "format";
    public static final String KEYWORD_ENG_OBJECT = "noekkelord";
    public static final String MEETING_FILE_ENG_OBJECT = "moetemappe";
    public static final String MEETING_FILE_TYPE_ENG_OBJECT = "moetesakstype";
    public static final String MEETING_PARTICIPANT_ENG_OBJECT = "moetedeltager";
    public static final String MEETING_PARTICIPANT_FUNCTION_ENG_OBJECT = "moetedeltakerfunksjon";
    public static final String MEETING_RECORD_ENG_OBJECT = "moeteregistrering";
    public static final String MEETING_REGISTRATION_STATUS_ENG_OBJECT = "moteregistreringsstatus";
    public static final String MEETING_REGISTRATION_TYPE_ENG_OBJECT = "moeteregistreringstype";
    public static final String MOBILE_TELEPHONE_NUMBER_ENG_OBJECT = "mobiltelefon";
    public static final String NAME_ENG_OBJECT = "navn";
    public static final String ORGANISATION_NUMBER_ENG_OBJECT = "organisasjonsnummer";
    public static final String PARENT_CLASS_ENG_OBJECT = "overordnetklasse";
    public static final String POSTAL_ADDRESS_ENG_OBJECT = "postadresse";
    public static final String POSTAL_NUMBER_ENG_OBJECT = "postnummer";
    public static final String POSTAL_TOWN_ENG_OBJECT = "poststed";
    public static final String POST_CODE_ENG_OBJECT = "postnummer";
    public static final String PRECEDENCE_APPROVED_BY_ENG_OBJECT = "presedensGodkjentAv";
    public static final String PRECEDENCE_APPROVED_DATE_ENG_OBJECT = "presedensGodkjentDato";
    public static final String PRECEDENCE_AUTHORITY_ENG_OBJECT = "presedensHjemmel";
    public static final String PRECEDENCE_DATE_ENG_OBJECT = "presedensDato";
    public static final String PRECEDENCE_ENG_OBJECT = "presedens";
    public static final String PRECEDENCE_SOURCE_OF_LAW_ENG_OBJECT = "rettskildefaktor";
    public static final String PRECEDENCE_STATUS_ENG_OBJECT = "presedensstatus";
    public static final String PRODUCTION_VERSION_ENG_OBJECT = "Produksjonsformat";
    public static final String RECORD_ARCHIVED_BY_ENG_OBJECT = "arkivertAv";
    public static final String RECORD_ARCHIVED_DATE_ENG_OBJECT = "arkivertDato";
    public static final String REFERENCE_ADMINISTRATIVE_UNIT_ENG_OBJECT = "referanseAdministratitivEnhet";
    public static final String REFERENCE_CASE_HANDLER_ENG_OBJECT = "referanseSaksbehandler";
    public static final String REFERENCE_SERIES_ENG_OBJECT = "referanseArkivdel";
    public static final String REGISTRATION_ENG_OBJECT = "registrering";
    public static final String REGISTRY_ENTRY_DATE_ENG_OBJECT = "journaldato";
    public static final String REGISTRY_ENTRY_DOCUMENT_DATE_ENG_OBJECT = "dokumentetsDato";
    public static final String REGISTRY_ENTRY_DUE_DATE_ENG_OBJECT = "forfallsdato";
    public static final String REGISTRY_ENTRY_ENG_OBJECT = "journalpost";
    public static final String REGISTRY_ENTRY_NUMBER_ENG_OBJECT = "journalpostnummer";
    public static final String REGISTRY_ENTRY_NUMBER_OF_ATTACHMENTS_ENG_OBJECT = "antallVedlegg";
    public static final String REGISTRY_ENTRY_RECEIVED_DATE_ENG_OBJECT = "mottattDato";
    public static final String REGISTRY_ENTRY_RECORD_FREEDOM_ASSESSMENT_DATE_ENG_OBJECT = "offentlighetsvurdertDato";
    public static final String REGISTRY_ENTRY_SENT_DATE_ENG_OBJECT = "sendtDato";
    public static final String REGISTRY_ENTRY_SEQUENCE_NUMBER_ENG_OBJECT = "journalsekvensnummer";
    public static final String REGISTRY_ENTRY_STATUS_ENG_OBJECT = "journalstatus";
    public static final String REGISTRY_ENTRY_TYPE_ENG_OBJECT = "journalposttype";
    public static final String REGISTRY_ENTRY_YEAR_ENG_OBJECT = "journalaar";
    public static final String RESIDING_ADDRESS_ENG_OBJECT = "bostedsadresse";
    public static final String RIGHT_ENG_OBJECT = "rettighet";
    public static final String SCREENING_ACCESS_RESTRICTION_ENG_OBJECT = "tilgangsrestriksjon";
    public static final String SCREENING_AUTHORITY_ENG_OBJECT = "skjermingshjemmel";
    public static final String SCREENING_DOCUMENT_ENG_OBJECT = "skjermingDokument";
    public static final String SCREENING_DURATION_ENG_OBJECT = "skjermingsvarighet";
    public static final String SCREENING_ENG_OBJECT = "skjerming";
    public static final String SCREENING_EXPIRES_DATE_ENG_OBJECT = "skjermingOpphoererDato";
    public static final String SCREENING_METADATA_ENG_OBJECT = "skjermingMetadata";
    public static final String SECONDARY_CLASSIFICATION_ENG_OBJECT = "sekundaerklassifikasjon";
    public static final String SECONDARY_CLASSIFICATION_SYSTEM_ENG_OBJECT = "sekundaerklassifikasjonssystem";
    public static final String SERIES_END_DATE_ENG_OBJECT = "arkivperiodeSluttDato";
    public static final String SERIES_ENG_OBJECT = "arkivdel";
    public static final String SERIES_PRECURSOR_ENG_OBJECT = "forloeper";
    public static final String SERIES_START_DATE_ENG_OBJECT = "arkivperiodeStartDato";
    public static final String SERIES_STATUS_ENG_OBJECT = "arkivdelstatus";
    public static final String SERIES_SUCCESSOR_ENG_OBJECT = "arvtager";
    public static final String SHORT_NAME_ENG_OBJECT = "kortnavn";
    public static final String SIGN_OFF_BY_ENG_OBJECT = "avskrevetAv";
    public static final String SIGN_OFF_DATE_ENG_OBJECT = "avskrivning";
    public static final String SOCIAL_SECURITY_NUMBER_ENG_OBJECT = "socialSecurityNumber";
    public static final String SIGN_OFF_ENG_OBJECT = "avskrivning";
    public static final String STORAGE_LOCATION_ENG_OBJECT = "storageLocation";
    public static final String SIGN_OFF_METHOD_ENG_OBJECT = "avskrivningsmaate";
    public static final String SUB_CLASS_ENG_OBJECT = "underklasse";
    public static final String SUB_FONDS_ENG_OBJECT = "underarkiv";
    public static final String SYSTEM_ID_ENG_OBJECT = "systemID";
    public static final String TELEPHONE_NUMBER_ENG_OBJECT = "telefonnummer";
    public static final String TITLE_ENG_OBJECT = "title";
    public static final String USER_ENG_OBJECT = "bruker";
    public static final String USER_NAME_ENG_OBJECT = "brukerNavn";
    public static final String VARIANT_FORMAT_ENG_OBJECT = "variantformat";

    private N5ResourceMappings() {
        throw new AssertionError();
    }
}

