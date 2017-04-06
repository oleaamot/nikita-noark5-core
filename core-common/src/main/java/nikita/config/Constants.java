package nikita.config;

import java.text.SimpleDateFormat;

import static nikita.config.N5ResourceMappings.*;

/**
 * Application constants.
 */
public final class Constants {

    public static final String NEW = "ny";
    public static final String DASH = "-";
    public static final String SUB = "under";

    // Spring profile for development and production
    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_DEMO = "demo";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    // Spring profile used to disable swagger
    public static final String SPRING_PROFILE_NO_SWAGGER = "no-swagger";
    public static final String SYSTEM_ACCOUNT = "system";

    public static final String USERNAME_ADMIN = "admin";
    public static final String PASSWORD_ADMIN = "password";

    // Definition of Authorities / Roles
    // Equivalent of the Arkivansvarlig role
    public static final String ROLE_RECORDS_MANAGER = "RECORDS_MANAGER";
    // Equivalent of the Arkivar role
    public static final String ROLE_RECORDS_KEEPER = "RECORDS_KEEPER";
    // Equivalent of the Saksbehandler role
    public static final String ROLE_CASE_HANDLER = "CASE_HANDLER";
    // Equivalent of the Leder role
    public static final String ROLE_LEADER = "LEADER";
    // Not part of standard, basically a guest that has limited access
    public static final String ROLE_GUEST = "GUEST";

    // Names of APIs that the core supports
    public static final String IMPORT_API_PATH = "import-api";
    public static final String HATEOAS_API_PATH = "hateoas-api";
    public static final String GUI_PATH = "gui";

    public static final String SLASH = "/";
    public static final String LEFT_PARENTHESIS = "{";
    public static final String RIGHT_PARENTHESIS = "}";
    public static final String REF_ID = "$ref?$id=";

    // Create for new arkivstruktur objects
    public static final String NEW_FONDS = NEW + DASH + FONDS;
    public static final String NEW_FONDS_CREATOR = NEW + DASH + FONDS_CREATOR;
    public static final String NEW_SERIES = NEW + DASH + SERIES;
    public static final String NEW_CLASSIFICATION_SYSTEM = NEW + DASH + CLASSIFICATION_SYSTEM;
    public static final String NEW_SECONDARY_CLASSIFICATION_SYSTEM = NEW + DASH + CLASSIFICATION_SYSTEM;
    public static final String NEW_CLASS = NEW + DASH + CLASS;
    public static final String NEW_SUB_CLASS = NEW + DASH + SUB_CLASS;
    public static final String NEW_FILE = NEW + DASH + FILE;
    public static final String NEW_RECORD = NEW + DASH + REGISTRATION;
    public static final String NEW_BASIC_RECORD = NEW + DASH + BASIC_RECORD;
    public static final String NEW_DOCUMENT_DESCRIPTION = NEW + DASH + DOCUMENT_DESCRIPTION;
    public static final String NEW_DOCUMENT_OBJECT = NEW + DASH + DOCUMENT_OBJECT;
    public static final String NEW_REFERENCE_SERIES = NEW + DASH + REFERENCE_SERIES;


    // Other arkivstruktur commands
    public static final String FILE_END = "avslutt-mappe";
    public static final String FILE_EXPAND_TO_CASE_FILE = "utvid-til-" + CASE_FILE;
    public static final String FILE_EXPAND_TO_MEETING_FILE = "utvid-til-" + MEETING_FILE;
    public static final String NEW_COMMENT = NEW + DASH + COMMENT;
    public static final String NEW_SUB_FILE = NEW + DASH + "undermappe";
    public static final String SUB_FILE = SUB + FILE ;
    public static final String NEW_SUB_FONDS = NEW + DASH + SUB + FONDS;
    public static final String NEW_CROSS_REFERENCE = NEW + DASH + CROSS_REFERENCE;
    public static final String NEW_REFERENCE_CLASS = NEW + DASH + "klassereferanse";
    public static final String REFERENCE_NEW_SERIES = NEW + DASH + "referanseArkivdel";
    public static final String NEW_CLASSIFIED = NEW + DASH + CLASSIFIED;
    public static final String NEW_SCREENING = NEW + DASH + SCREENING;
    public static final String NEW_DISPOSAL = NEW + DASH + DISPOSAL;
    public static final String NEW_DISPOSAL_UNDERTAKEN = NEW + DASH + DISPOSAL_UNDERTAKEN;
    public static final String NEW_DELETION = NEW + DASH + "sletting";
    public static final String NEW_STORAGE_LOCATION = NEW + DASH + STORAGE_LOCATION;
    public static final String NEW_STORAGE_LOCATIONS = NEW + DASH + STORAGE_LOCATIONS;
    public static final String NEW_AUTHOR = NEW + DASH + AUTHOR;
    public static final String NEW_ELECTRONIC_SIGNATURE = NEW + DASH + ELECTRONIC_SIGNATURE;
    public static final String NEW_CONVERSION = NEW + DASH + CONVERSION;
    public static final String NEW_KEYWORD = NEW + DASH + KEYWORD;
    public static final String NEW_SIGN_OFF = NEW + DASH + SIGN_OFF;
    public static final String NEW_DOCUMENT_FLOW = NEW + DASH + DOCUMENT_FLOW;
    public static final String NEW_CORRESPONDENCE_PART = NEW + DASH + CORRESPONDENCE_PART;
    public static final String NEW_SERIES_SUCCESSOR = NEW + DASH + SERIES_SUCCESSOR;
    public static final String NEW_SERIES_PRECURSOR = NEW + DASH + SERIES_PRECURSOR;

    public static final String DOCUMENT_FILE = "fil";

    // Create for new sakarkiv objects
    public static final String NEW_CASE_FILE = NEW + DASH + CASE_FILE;
    public static final String NEW_REGISTRY_ENTRY = NEW + DASH + REGISTRY_ENTRY;
    public static final String NEW_PRECEDENCE = NEW + DASH + PRECEDENCE;
    public static final String NEW_CASE_PARTY = NEW + DASH + CASE_PARTY;
    public static final String NEW_CASE_STATUS = NEW + DASH + CASE_STATUS;
    public static final String NEW_SECONDARY_CLASSIFICATION = NEW + DASH + "sekundaerklassifikasjon";

    // Create for new administrasjon objects
    public static final String NEW_DOCUMENT_MEDIUM = NEW + DASH + DOCUMENT_MEDIUM;
    public static final String NEW_FONDS_STATUS = NEW + DASH + FONDS_STATUS;


    public static final String SERIES_ASSOCIATE_AS_SUCCESSOR = "referanseArvtager";
    public static final String SERIES_ASSOCIATE_AS_PRECURSOR = "referanseForloeper";

    // Some user identifiers used for testing
    public static final String TEST_USER_CASE_HANDLER_1 = "test user case handler 1";
    public static final String TEST_USER_CASE_HANDLER_2 = "test user case handler 2";
    public static final String TEST_USER_ADMIN = "test user admin";
    public static final String TEST_USER_RECORD_KEEPER = "test user record keeper";

    // Some strings used during testing
    public static final String TEST_TITLE = "test title";
    public static final String TEST_DESCRIPTION = "test description";
    public static final String TEST_ADMINISTRATIVE_UNIT = "test administrative unit";

    public static final String NOARK_DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    public static final String NOARK_TIME_FORMAT_PATTERN = "HH:mm:ss";
    public static final String NOARK_DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(NOARK_DATE_FORMAT_PATTERN);
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(NOARK_TIME_FORMAT_PATTERN);
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(NOARK_DATE_TIME_FORMAT_PATTERN);

    // OData variable names
    public static final String TOP = "top";
    public static final String SKIP = "skip";
    public static final String DOLLAR = "$";

    // Strings relating to conformity to Noark 5v4 standard
    public static final String NOARK_CONFORMANCE_LEVEL_0 = "Nivå 0 – Basiskrav";
    public static final String NOARK_CONFORMANCE_LEVEL_1= "Nivå 1 – Arkivstruktur - obligatoriske krav";
    public static final String NOARK_CONFORMANCE_LEVEL_1_1 = "Nivå 1.1 – Arkivstruktur - valgfrie krav";
    public static final String NOARK_CONFORMANCE_LEVEL_2 = "Nivå 2a – Sakarkiv – obligatoriske krav";
    public static final String NOARK_CONFORMANCE_LEVEL_2_1_a = "Nivå 2.1a – Sakarkiv - valgfrie krav";

    public static final String REFERENCE_FILE = "referanseFil";

    // namespace definition
    public static final String NOARK_CONFORMANCE_REL = "http://rel.kxml.no/noark5/v4/api/";
    public static final String NIKITA_CONFORMANCE_REL = "http://nikita.arkivlab.no/noark5/v4/";

    public static final String NOARK_FONDS_STRUCTURE_PATH = "arkivstruktur";
    public static final String NOARK_METADATA_PATH = "metadata";
    public static final String NOARK_ADMINISTRATION_PATH = "administration";
    public static final String NOARK_CASE_HANDLING_PATH = "sakarkiv";
    public static final String NOARK_LOGGING_PATH = "loggingogpsoring";

    public static final String LOGIN_REL_PATH = "login";
    public static final String LOGIN_PATH = "auth";
    // Logging using JWT / RFC7519
    public static final String LOGIN_JWT = "rfc7519";

    public static final String NOARK5_V4_CONTENT_TYPE_JSON = "application/vnd.noark5-v4+json";
    public static final String NOARK5_V4_CONTENT_TYPE_JSON_XML = "application/vnd.noark5-v4+xml";

    public static final String INFO_CANNOT_CREATE_OBJECT = "Cannot create ";
    public static final String INFO_CANNOT_FIND_OBJECT = "Cannot find object of type ";
    public static final String INFO_CANNOT_ASSOCIATE_WITH_CLOSED_OBJECT = "Cannot associate with a closed object";
    public static final String INFO_INVALID_STRUCTURE = "Invalid Noark structure";

    public static final String TEMPLATE_FONDS_STATUS_CODE = "Opprettet";
    public static final String TEMPLATE_FONDS_STATUS_DESCRIPTION = "Arkivet er opprettet og i aktiv bruk";
    public static final String TEMPLATE_DOCUMENT_MEDIUM_CODE = "Elektronisk arkiv";
    public static final String TEMPLATE_DOCUMENT_MEDIUM_DESCRIPTION = "Bare elektroniske dokumenter";


    public static final int UUIDLength = 32;

    public static final String REFERENCE_CLASS = "referanseKlasse";

    // Messages used for API description
    public static final String API_MESSAGE_OBJECT_ALREADY_PERSISTED = "object already persisted.";
    public static final String API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED = "object successfully created.";
    public static final String API_MESSAGE_UNAUTHENTICATED_USER  = "Unauthenticated. User has not provided necessary credentials to carry out the request.";
    public static final String API_MESSAGE_UNAUTHORISED_FOR_USER  = "Unauthorised. User does not have necessary rights to carry out the request.";
    public static final String API_MESSAGE_PARENT_DOES_NOT_EXIST = "Non-existent parent object. The parent specified does not exist";
    public static final String API_MESSAGE_CONFLICT = "Conflict. The resource is being used by someone else";
    public static final String API_MESSAGE_INTERNAL_SERVER_ERROR = "Internal server error";
    public static final String API_MESSAGE_NOT_IMPLEMENTED = "Not implemented yet";
    public static final String API_MESSAGE_MALFORMED_PAYLOAD = "Incoming data is malformed";


    public static final String REL_METADATA = "http://rel.kxml.no/noark5/v4/api/metadata/";
    public static final String REL_ADMINISTRATION = "http://rel.kxml.no/noark5/v4/api/administrasjon/";
    public static final String REL_FONDS_STRUCTURE = "http://rel.kxml.no/noark5/v4/api/arkivstruktur/";
    public static final String REL_CASE_HANDLING = "http://rel.kxml.no/noark5/v4/api/sakarkiv/";

    // FondsHateoas REL links

    public static final String REL_METADATA_FONDS_STATUS = REL_METADATA + FONDS_STATUS + SLASH;
    public static final String REL_FONDS_STRUCTURE_FONDS_CREATOR = REL_FONDS_STRUCTURE + FONDS_CREATOR + SLASH;
    public static final String REL_FONDS_STRUCTURE_FONDS = REL_FONDS_STRUCTURE + FONDS + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_FONDS_CREATOR = REL_FONDS_STRUCTURE + NEW_FONDS_CREATOR + SLASH;
    public static final String REL_FONDS_STRUCTURE_SUB_FONDS = REL_FONDS_STRUCTURE + SUB_FONDS + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_SUB_FONDS = REL_FONDS_STRUCTURE + NEW_SUB_FONDS + SLASH;
    public static final String REL_FONDS_STRUCTURE_FONDS_STATUS = REL_METADATA + SLASH + FONDS_STATUS + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_FONDS = REL_FONDS_STRUCTURE + NEW_FONDS + SLASH;

    // Common FondsHateoas/SeriesHateoas REL links
    public static final String REL_FONDS_STRUCTURE_NEW_SERIES = REL_FONDS_STRUCTURE + NEW_SERIES + SLASH;
    public static final String REL_FONDS_STRUCTURE_SERIES = REL_FONDS_STRUCTURE + SERIES + SLASH;

    // Common SeriesHateoas/FileHateoas/RegistrationHateoas REL links
    public static final String REL_FONDS_STRUCTURE_NEW_REGISTRATION = REL_FONDS_STRUCTURE + NEW_RECORD + SLASH;
    public static final String REL_FONDS_STRUCTURE_REGISTRATION = REL_FONDS_STRUCTURE + REGISTRATION + SLASH;

    // Common SeriesHateoas/FileHateoas REL links
    public static final String REL_FONDS_STRUCTURE_NEW_FILE = REL_FONDS_STRUCTURE + NEW_FILE + SLASH;
    public static final String REL_FONDS_STRUCTURE_FILE = REL_FONDS_STRUCTURE + FILE + SLASH;

    public static final String REL_FONDS_STRUCTURE_NEW_CASE_FILE = REL_CASE_HANDLING + NEW_CASE_FILE + SLASH;
    public static final String REL_FONDS_STRUCTURE_CASE_FILE = REL_CASE_HANDLING + CASE_FILE + SLASH;


    // Common SeriesHateoas/ClassificationHateoas REL links
    public static final String REL_FONDS_STRUCTURE_NEW_CLASSIFICATION_SYSTEM = REL_FONDS_STRUCTURE + NEW_CLASSIFICATION_SYSTEM + SLASH;
    public static final String REL_FONDS_STRUCTURE_CLASSIFICATION_SYSTEM = REL_FONDS_STRUCTURE + CLASSIFICATION_SYSTEM + SLASH;

    // SeriesHateoas REL links
    public static final String REL_METADATA_SERIES_STATUS = REL_METADATA + SERIES_STATUS + SLASH;

    // Series precursor / successor
    public static final String REL_FONDS_STRUCTURE_SUCCESSOR = REL_FONDS_STRUCTURE + SERIES_SUCCESSOR + SLASH;
    public static final String REL_FONDS_STRUCTURE_PRECURSOR = REL_FONDS_STRUCTURE + SERIES_PRECURSOR + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_SUCCESSOR = REL_FONDS_STRUCTURE + NEW_SERIES_SUCCESSOR + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_PRECURSOR = REL_FONDS_STRUCTURE + NEW_SERIES_PRECURSOR + SLASH;

    // CaseFileHateoas REL links
    public static final String REL_CASE_HANDLING_NEW_CLASS = REL_FONDS_STRUCTURE + NEW_CLASS + SLASH;
    public static final String REL_CASE_HANDLING_CLASS = REL_FONDS_STRUCTURE + CLASS + SLASH;
    public static final String REL_CASE_HANDLING_PRECEDENCE = REL_CASE_HANDLING + PRECEDENCE + SLASH;
    public static final String REL_CASE_HANDLING_NEW_PRECEDENCE = REL_CASE_HANDLING + NEW_PRECEDENCE + SLASH;
    public static final String REL_CASE_HANDLING_CASE_PARTY = REL_CASE_HANDLING + CASE_PARTY + SLASH;
    public static final String REL_CASE_HANDLING_NEW_CASE_PARTY = REL_CASE_HANDLING + NEW_CASE_PARTY + SLASH;
    public static final String REL_CASE_HANDLING_SECONDARY_CLASSIFICATION = REL_CASE_HANDLING + SECONDARY_CLASSIFICATION + SLASH;
    public static final String REL_CASE_HANDLING_NEW_SECONDARY_CLASSIFICATION = REL_CASE_HANDLING + NEW_SECONDARY_CLASSIFICATION + SLASH;
    public static final String REL_METADATA_CASE_STATUS = REL_METADATA + CASE_STATUS + SLASH;

    // FileHateoas REL links
    public static final String REL_FONDS_STRUCTURE_EXPAND_TO_CASE_FILE = REL_FONDS_STRUCTURE + FILE_EXPAND_TO_CASE_FILE + SLASH;
    public static final String REL_FONDS_STRUCTURE_EXPAND_TO_MEETING_FILE = REL_FONDS_STRUCTURE + FILE_EXPAND_TO_MEETING_FILE + SLASH;
    public static final String REL_FONDS_STRUCTURE_END_FILE = REL_FONDS_STRUCTURE + FILE_END + SLASH;
    public static final String REL_FONDS_STRUCTURE_BASIC_RECORD = REL_FONDS_STRUCTURE + BASIC_RECORD + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_BASIC_RECORD = REL_FONDS_STRUCTURE + NEW_BASIC_RECORD + SLASH;

    // Comment
    public static final String REL_FONDS_STRUCTURE_COMMENT = REL_FONDS_STRUCTURE + COMMENT + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_COMMENT = REL_FONDS_STRUCTURE + NEW_COMMENT + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_SUB_FILE = REL_FONDS_STRUCTURE + NEW_SUB_FILE + SLASH;
    public static final String REL_FONDS_STRUCTURE_SUB_FILE = REL_FONDS_STRUCTURE + SUB_FILE + SLASH;

    // CrossReference
    public static final String REL_FONDS_STRUCTURE_NEW_CROSS_REFERENCE = REL_FONDS_STRUCTURE + NEW_CROSS_REFERENCE + SLASH;
    public static final String REL_FONDS_STRUCTURE_CROSS_REFERENCE = REL_FONDS_STRUCTURE + CROSS_REFERENCE + SLASH;

    // Class
    public static final String REL_FONDS_STRUCTURE_CLASS = REL_FONDS_STRUCTURE + REFERENCE_CLASS + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_CLASS = REL_FONDS_STRUCTURE + NEW_REFERENCE_CLASS + SLASH;

    public static final String REL_FONDS_STRUCTURE_SUB_CLASS = REL_FONDS_STRUCTURE + SUB + CLASS + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_SUB_CLASS = REL_FONDS_STRUCTURE + NEW + DASH + SUB + CLASS + SLASH + SLASH;

    // Series
    public static final String REL_FONDS_STRUCTURE_REFERENCE_SERIES = REL_FONDS_STRUCTURE + REFERENCE_SERIES + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_REFERENCE_SERIES = REL_FONDS_STRUCTURE + REFERENCE_NEW_SERIES + SLASH;

    // Classified
    public static final String REL_FONDS_STRUCTURE_CLASSIFIED = REL_FONDS_STRUCTURE + CLASSIFIED + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_CLASSIFIED = REL_FONDS_STRUCTURE + NEW_CLASSIFIED + SLASH;

    // Screening
    public static final String REL_FONDS_STRUCTURE_SCREENING = REL_FONDS_STRUCTURE + SCREENING + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_SCREENING = REL_FONDS_STRUCTURE + NEW_SCREENING + SLASH;

    // Disposal
    public static final String REL_FONDS_STRUCTURE_DISPOSAL = REL_FONDS_STRUCTURE + DISPOSAL + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_DISPOSAL = REL_FONDS_STRUCTURE + NEW_DISPOSAL + SLASH;

    // DisposalUndertaken
    public static final String REL_FONDS_STRUCTURE_DISPOSAL_UNDERTAKEN = REL_FONDS_STRUCTURE + DISPOSAL_UNDERTAKEN + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_DISPOSAL_UNDERTAKEN = REL_FONDS_STRUCTURE + NEW_DISPOSAL_UNDERTAKEN + SLASH;

    // StorageLocation
    public static final String REL_FONDS_STRUCTURE_STORAGE_LOCATION = REL_FONDS_STRUCTURE + STORAGE_LOCATION + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_STORAGE_LOCATION = REL_FONDS_STRUCTURE + NEW_STORAGE_LOCATION + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_STORAGE_LOCATION_LIST = REL_FONDS_STRUCTURE + NEW_STORAGE_LOCATIONS + SLASH;

    // DocumentObject
    public static final String REL_FONDS_STRUCTURE_DOCUMENT_OBJECT = REL_FONDS_STRUCTURE + DOCUMENT_OBJECT + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_DOCUMENT_OBJECT = REL_FONDS_STRUCTURE + NEW_DOCUMENT_OBJECT + SLASH;

    // Deletion
    public static final String REL_FONDS_STRUCTURE_DELETION = REL_FONDS_STRUCTURE + DELETION + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_DELETION = REL_FONDS_STRUCTURE + NEW_DELETION + SLASH;

    // Record
    public static final String REL_FONDS_STRUCTURE_RECORD = REL_FONDS_STRUCTURE + REGISTRATION + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_RECORD = REL_FONDS_STRUCTURE + NEW_RECORD + SLASH;


    // Author
    public static final String REL_FONDS_STRUCTURE_AUTHOR = REL_FONDS_STRUCTURE + AUTHOR + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_AUTHOR = REL_FONDS_STRUCTURE + NEW_AUTHOR + SLASH;

    // Conversion
    public static final String REL_FONDS_STRUCTURE_CONVERSION = REL_FONDS_STRUCTURE + CONVERSION + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_CONVERSION = REL_FONDS_STRUCTURE + NEW_CONVERSION + SLASH;

    // ElectronicSignature
    public static final String REL_FONDS_STRUCTURE_ELECTRONIC_SIGNATURE = REL_FONDS_STRUCTURE + ELECTRONIC_SIGNATURE + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_ELECTRONIC_SIGNATURE = REL_FONDS_STRUCTURE + NEW_ELECTRONIC_SIGNATURE + SLASH;

    // DocumentDescription
    public static final String REL_FONDS_STRUCTURE_DOCUMENT_DESCRIPTION = REL_FONDS_STRUCTURE + DOCUMENT_DESCRIPTION + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_DOCUMENT_DESCRIPTION = REL_FONDS_STRUCTURE + NEW_DOCUMENT_DESCRIPTION + SLASH;

    // Keyword
    public static final String REL_FONDS_STRUCTURE_KEYWORD = REL_FONDS_STRUCTURE + KEYWORD + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_KEYWORD = REL_FONDS_STRUCTURE + NEW_KEYWORD + SLASH;

    // Precedence
    public static final String REL_FONDS_STRUCTURE_PRECEDENCE = REL_FONDS_STRUCTURE + PRECEDENCE + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_PRECEDENCE = REL_FONDS_STRUCTURE + NEW_PRECEDENCE + SLASH;

    // Keyword
    public static final String REL_FONDS_STRUCTURE_SIGN_OFF = REL_FONDS_STRUCTURE + SIGN_OFF + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_SIGN_OFF = REL_FONDS_STRUCTURE + NEW_SIGN_OFF + SLASH;

    // DocumentFlow
    public static final String REL_FONDS_STRUCTURE_DOCUMENT_FLOW = REL_FONDS_STRUCTURE + DOCUMENT_FLOW + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_DOCUMENT_FLOW = REL_FONDS_STRUCTURE + NEW_DOCUMENT_FLOW + SLASH;

    // CorrespondencePart
    public static final String REL_FONDS_STRUCTURE_CORRESPONDENCE_PART = REL_FONDS_STRUCTURE + CORRESPONDENCE_PART + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_CORRESPONDENCE_PART = REL_FONDS_STRUCTURE + NEW_CORRESPONDENCE_PART + SLASH;

    // CorrespondencePart
    public static final String REL_FONDS_STRUCTURE_DOCUMENT_FILE = REL_FONDS_STRUCTURE + DOCUMENT_FILE + SLASH;

    // SecondaryClassification
    public static final String REL_FONDS_STRUCTURE_SECONDARY_CLASSIFICATION = REL_FONDS_STRUCTURE + SECONDARY_CLASSIFICATION + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_SECONDARY_CLASSIFICATION = REL_FONDS_STRUCTURE + NEW_SECONDARY_CLASSIFICATION + SLASH;

    // Metadata RELS
    public static final String REL_METADATA_DOCUMENT_MEDIUM = REL_METADATA + DOCUMENT_MEDIUM + SLASH;
    public static final String REL_METADATA_VARIANT_FORMAT = REL_METADATA + VARIANT_FORMAT + SLASH;
    public static final String REL_METADATA_FORMAT = REL_METADATA + FORMAT + SLASH;
    public static final String REL_METADATA_DOCUMENT_STATUS = REL_METADATA + DOCUMENT_STATUS + SLASH;
    public static final String REL_METADATA_DOCUMENT_TYPE = REL_METADATA + FORMAT + SLASH;
    // M086 dokumenttype
    public static final String REL_METADATA_CLASSIFICATION_SYSTEM_TYPE = REL_METADATA + CLASSIFICATION_SYSTEM_TYPE + SLASH;
    //public static final String REL_METADATA_CLASSIFICATION_SYSTEM_TYPE = REL_METADATA + CLASSIFICATION_SYSTEM + SLASH;

    private Constants() {
    }
}
