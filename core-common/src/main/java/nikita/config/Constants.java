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

    public static final String NEW_FONDS = "ny-arkiv";
    public static final String NEW_FONDS_CREATOR = "ny-arkivskaper";
    public static final String NEW_SERIES = "ny-arkivdel";
    public static final String NEW_CLASSIFICATION_SYSTEM = "ny-klassifikasjonssystem";
    public static final String NEW_CLASS = "ny-klasse";
    public static final String NEW_FILE = "ny-mappe";
    public static final String NEW_CASE_FILE = "ny-saksmappe";
    public static final String NEW_RECORD = "ny-registrering";
    public static final String NEW_BASIC_RECORD = "ny-basisregistrering";
    public static final String NEW_REGISTRY_ENTRY = "ny-journalpost";


    public static final String NEW_DOCUMENT_DESCRIPTION = "ny-dokumentbeskrivelse";
    public static final String NEW_DOCUMENT_OBJECT = "ny-dokumentobjekt";


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

    
    // Messages used for API description
    public static final String API_MESSAGE_OBJECT_ALREADY_PERSISTED = "object already persisted.";
    public static final String API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED = "object successfully created.";
    public static final String API_MESSAGE_UNAUTHENTICATED_USER  = "Unauthenticated. User has not provided necessary credentials to carry out the request.";
    public static final String API_MESSAGE_UNAUTHORISED_FOR_USER  = "Unauthorised. User does not have necessary rights to carry out the request.";
    public static final String API_MESSAGE_PARENT_DOES_NOT_EXIST = "Non-existent parent object. The parent specified does not exist";
    public static final String API_MESSAGE_CONFLICT = "Conflict. The resource is being used by someone else";
    public static final String API_MESSAGE_INTERNAL_SERVER_ERROR = "Internal server error";
    public static final String API_MESSAGE_NOT_IMPLEMTNED = "Not implemented yet";
    public static final String API_MESSAGE_MALFORMED_PAYLOAD = "Incoming data is malformed";


    public static final String REL_METADATA = "http://rel.kxml.no/noark5/v4/api/metadata/";
    public static final String REL_FONDS_STRUCTURE = "http://rel.kxml.no/noark5/v4/api/arkivstruktur/";
    public static final String REL_METADATA_DOCUMENT_MEDIUM = REL_METADATA + DOCUMENT_MEDIUM + SLASH;
    public static final String REL_METADATA_FONDS_STATUS = REL_METADATA + FONDS_STATUS + SLASH;
    public static final String REL_FONDS_STRUCTURE_FONDS_CREATOR = REL_FONDS_STRUCTURE + FONDS_CREATOR + SLASH;
    public static final String REL_FONDS_STRUCTURE_SERIES = REL_FONDS_STRUCTURE + SERIES + SLASH;
    public static final String REL_FONDS_STRUCTURE_FONDS = REL_FONDS_STRUCTURE + FONDS + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_FONDS_CREATOR = REL_FONDS_STRUCTURE + NEW_FONDS_CREATOR + SLASH;
    public static final String REL_FONDS_STRUCTURE_SUB_FONDS = REL_FONDS_STRUCTURE + SUB_FONDS + SLASH;
    public static final String REL_FONDS_STRUCTURE_FONDS_STATUS = REL_METADATA + SLASH + FONDS_STATUS + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_SERIES = REL_FONDS_STRUCTURE + NEW_SERIES + SLASH;
    public static final String REL_FONDS_STRUCTURE_NEW_FONDS = REL_FONDS_STRUCTURE + NEW_FONDS + SLASH;


    // This is removed each time functionality is implemented
    private Constants() {
    }
}
