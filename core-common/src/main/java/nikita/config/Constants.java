package nikita.config;

import java.text.SimpleDateFormat;

import static nikita.config.N5ResourceMappings.*;

/**
 * Application constants.
 */
public final class Constants {

    // Spring profile for development and production
    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_DEMO = "demo";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    // Spring profile used to disable swagger
    public static final String SPRING_PROFILE_NO_SWAGGER = "no-swagger";
    public static final String SYSTEM_ACCOUNT = "system";


    // Application demo/dev/test user and password
    public static final String USERNAME_ADMIN = "admin";
    public static final String PASSWORD_ADMIN = "pass";


    // Names of APIs that the core supports
    public static final String IMPORT_API_PATH = "import-api";
    public static final String HATEOAS_API_PATH = "hateoas-api";
    public static final String GUI_PATH = "gui";

    public static final String SLASH = "/";
    public static final String LEFT_PARENTHESIS = "{";
    public static final String RIGHT_PARENTHESIS = "}";

    // Create for new arkivstruktur objects
    public static final String NEW_FONDS = "ny-arkiv";
    public static final String NEW_FONDS_CREATOR = "ny-arkivskaper";
    public static final String NEW_SERIES = "ny-arkivdel";
    public static final String NEW_CLASSIFICATION_SYSTEM = "ny-klassifikasjonssystem";
    public static final String NEW_CLASS = "ny-klasse";
    public static final String NEW_FILE = "ny-mappe";
    public static final String NEW_RECORD = "ny-registrering";
    public static final String NEW_BASIC_RECORD = "ny-basisregistrering";
    public static final String NEW_DOCUMENT_DESCRIPTION = "ny-dokumentbeskrivelse";
    public static final String NEW_DOCUMENT_OBJECT = "ny-dokumentobjekt";

    // Other arkivstruktur commands
    public static final String FILE_END = "avslutt-mappe";
    public static final String FILE_EXPAND_TO_CASE_FILE = "utvid-til-saksmappe";
    public static final String FILE_EXPAND_TO_MEETING_FILE = "utvid-til-moetemappe";
    public static final String NEW_COMMENT = "utvid-til-moetemappe";
    public static final String NEW_SUB_FILE = "ny-undermappe";
    public static final String SUB_FILE = "undermappe";
    public static final String NEW_CROSS_REFERENCE = "ny-kryssreferanse";
    public static final String NEW_REFERENCE_CLASS = "ny-klassereferanse";
    public static final String REFERENCE_NEW_SERIES = "ny-arkivdelReferanse";
    public static final String NEW_CLASSIFIED = "ny-gradering";
    public static final String NEW_SCREENING = "ny-skjerming";
    public static final String NEW_DISPOSAL = "ny-kassasjon";
    public static final String NEW_DISPOSAL_UNDERTAKEN = "ny-utfoertkassasjon";
    public static final String NEW_DELETION = "ny-sletting";
    public static final String NEW_STORAGE_LOCATION = "ny-oppbevaringsted";
    public static final String NEW_AUTHOR = "ny-forfatter";

    // Create for new sakarkiv objects
    public static final String NEW_CASE_FILE = "ny-saksmappe";
    public static final String NEW_REGISTRY_ENTRY = "ny-journalpost";
    public static final String NEW_PRECEDENCE = "ny-presedens";
    public static final String NEW_CASE_PARTY = "ny-sakspart";
    public static final String NEW_SECONDARY_CLASSIFICATION = "ny-sekundaerklassifikasjon";

    // Create for new administrasjon objects

    // // Create for new metadata objects
    public static final String NEW_DOCUMENT_MEDIUM = "ny-dokumentmedium";

    // Some user identifiers used for testing
    public static final String TEST_USER_CASE_HANDLER_1 = "test user case handler 1";
    public static final String TEST_USER_CASE_HANDLER_2 = "test user case handler 2";
    public static final String TEST_USER_ADMIN = "test user admin";
    public static final String TEST_USER_RECORD_KEEPER = "test user record keeper";

    // Some strings used during testing
    public static final String TEST_TITLE = "test title";
    public static final String TEST_DESCRIPTION = "test description";
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

    // namespace definition
    public static final String NOARK_CONFORMANCE_REL = "http://rel.kxml.no/noark5/v4/api/";
    public static final String NIKITA_CONFORMANCE_REL = "http://nikita.arkivlab.no/noark5/v4/";

    public static final String NOARK_FONDS_STRUCTURE_PATH = "arkivstruktur";
    public static final String NOARK_METADATA_PATH = "metadata";
    public static final String NOARK_ADMINISTRATION_PATH = "administration";
    public static final String NOARK_CASE_HANDLING_PATH = "sakarkiv";

    public static final String NIKITA_CONTENT_TYPE = "http://nikita.arkivlab.no/noark5/v4/";
    public static final String NOARK5_V4_CONTENT_TYPE = "application/vnd.noark5-v4+json";

    public static final String INFO_CANNOT_CREATE_OBJECT = "Cannot create ";
    public static final String INFO_CANNOT_FIND_OBJECT = "Cannot find object of type ";
    public static final String INFO_CANNOT_ASSOCIATE_WITH_CLOSED_OBJECT = "Cannot associate with a closed object";
    public static final String INFO_INVALID_STRUCTURE = "Invalid Noark structure";

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
    public static final String REL_FONDS_STRUCTURE_FONDS_STATUS = REL_METADATA + SLASH + FONDS_STATUS + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_FONDS = REL_FONDS_STRUCTURE + NEW_FONDS + SLASH;

    // Common FondsHateoas/SeriesHateoas REL links
    public static final String REL_METADATA_DOCUMENT_MEDIUM = REL_METADATA + DOCUMENT_MEDIUM + SLASH;

    // Common FondsHateoas/SeriesHateoas REL links
    public static final String REL_FONDS_STRUCTURE_NEW_SERIES = REL_FONDS_STRUCTURE + NEW_SERIES + SLASH;
    public static final String REL_FONDS_STRUCTURE_SERIES = REL_FONDS_STRUCTURE + SERIES + SLASH;

    // Common SeriesHateoas/FileHateoas/RegistrationHateoas REL links
    public static final String REL_FONDS_STRUCTURE_NEW_REGISTRATION = REL_FONDS_STRUCTURE + NEW_RECORD + SLASH;
    public static final String REL_FONDS_STRUCTURE_REGISTRATION = REL_FONDS_STRUCTURE + REGISTRATION + SLASH;

    // Common SeriesHateoas/FileHateoas REL links
    public static final String REL_FONDS_STRUCTURE_NEW_FILE = REL_FONDS_STRUCTURE + NEW_FILE + SLASH;
    public static final String REL_FONDS_STRUCTURE_FILE = REL_FONDS_STRUCTURE + FILE + SLASH;

    // Common SeriesHateoas/ClassificationHateoas REL links
    public static final String REL_FONDS_STRUCTURE_NEW_CLASSIFICATION_SYSTEM = REL_FONDS_STRUCTURE + NEW_CLASSIFICATION_SYSTEM + SLASH;
    public static final String REL_FONDS_STRUCTURE_CLASSIFICATION_SYSTEM = REL_FONDS_STRUCTURE + CLASSIFICATION_SYSTEM + SLASH;

    // SeriesHateoas REL links
    public static final String REL_METADATA_SERIES_STATUS = REL_METADATA + SERIES_STATUS + SLASH;

    // CaseFileHateoas REL links
    public static final String REL_CASE_HANDLING_NEW_CLASS = REL_FONDS_STRUCTURE + NEW_CLASS + SLASH;
    public static final String REL_CASE_HANDLING_CLASS = REL_FONDS_STRUCTURE + CLASS + SLASH;
    public static final String REL_CASE_HANDLING_PRECEDENCE = REL_CASE_HANDLING + PRECEDENCE + SLASH;
    public static final String REL_CASE_HANDLING_NEW_PRECEDENCE = REL_CASE_HANDLING + NEW_PRECEDENCE + SLASH;
    public static final String REL_CASE_HANDLING_CASE_PARTY = REL_CASE_HANDLING + CASE_PARTY + SLASH;
    public static final String REL_CASE_HANDLING_NEW_CASE_PARTY = REL_CASE_HANDLING + NEW_CASE_PARTY + SLASH;
    public static final String REL_CASE_HANDLING_SECONDARY_CLASSIFICATION = REL_CASE_HANDLING + SECONDARY_CLASSIFICATION + SLASH;
    public static final String REL_METADATA_CASE_STATUS = REL_METADATA + CASE_STATUS + SLASH;

    // FileHateoas REL links
    public static final String REL_FONDS_STRUCTURE_EXPAND_TO_CASE_FILE = REL_FONDS_STRUCTURE + FILE_EXPAND_TO_CASE_FILE + SLASH;
    public static final String REL_FONDS_STRUCTURE_EXPAND_TO_MEETING_FILE = REL_FONDS_STRUCTURE + FILE_EXPAND_TO_MEETING_FILE + SLASH;
    public static final String REL_FONDS_STRUCTURE_END_FILE = REL_FONDS_STRUCTURE + FILE_END + SLASH;
    public static final String REL_FONDS_STRUCTURE_BASIC_RECORD_ = REL_FONDS_STRUCTURE + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_BASIC_RECORD = REL_FONDS_STRUCTURE + SLASH;

    // Comment
    public static final String REL_FONDS_STRUCTURE_COMMENT = REL_FONDS_STRUCTURE + COMMENT + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_COMMENT = REL_FONDS_STRUCTURE + NEW_COMMENT + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_SUB_FILE = REL_FONDS_STRUCTURE + NEW_SUB_FILE + SLASH;
    public static final String REL_FONDS_STRUCTURE_SUB_FILE = REL_FONDS_STRUCTURE + SUB_FILE + SLASH;

    public static final String REL_FONDS_STRUCTURE_NEW_CROSS_REFERENCE = REL_FONDS_STRUCTURE + NEW_CROSS_REFERENCE + SLASH;
    public static final String REL_FONDS_STRUCTURE_CROSS_REFERENCE = REL_FONDS_STRUCTURE + CROSS_REFERENCE + SLASH;

    public static final String REL_FONDS_STRUCTURE_REFERENCE_CLASS = REL_FONDS_STRUCTURE + REFERENCE_CLASS + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_REFERENCE_CLASS = REL_FONDS_STRUCTURE + NEW_REFERENCE_CLASS + SLASH;

    public static final String REL_FONDS_STRUCTURE_REFERENCE_SERIES = REL_FONDS_STRUCTURE + SERIES_REFERENCE + SLASH;
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
  
/*
    public static final String REL_FONDS_STRUCTURE_ = REL_FONDS_STRUCTURE +  + SLASH;
    public static final String REL_FONDS_STRUCTURE_ = REL_FONDS_STRUCTURE +  + SLASH;
    public static final String REL_FONDS_STRUCTURE_ = REL_FONDS_STRUCTURE +  + SLASH;
    public static final String REL_FONDS_STRUCTURE_ = REL_FONDS_STRUCTURE +  + SLASH;
    public static final String REL_FONDS_STRUCTURE_ = REL_FONDS_STRUCTURE +  + SLASH;
    public static final String REL_FONDS_STRUCTURE_ = REL_FONDS_STRUCTURE +  + SLASH;
    public static final String REL_FONDS_STRUCTURE_ = REL_FONDS_STRUCTURE +  + SLASH;
    public static final String REL_FONDS_STRUCTURE_ = REL_FONDS_STRUCTURE +  + SLASH;
*/

    // This is removed each time functionality is implemented
    private Constants() {
    }
}
