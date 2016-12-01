package nikita.config;

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
    // Name of the base of the api
    public static final String API_PATH = "api";
    public static final String HATEOAS_API_PATH = "hateoas-api";
    public static final String GUI_PATH = "gui";

    public static final String SLASH = "/";
    // Some user identifiers used for testing
    public static final String TEST_USER_CASE_HANDLER_1 = "test user case handler 1";
    public static final String TEST_USER_CASE_HANDLER_2 = "test user case handler 2";
    public static final String TEST_USER_ADMIN = "test user admin";
    public static final String TEST_USER_RECORD_KEEPER = "test user record keeper";

    // Some strings used during testing
    public static final String TEST_TITLE = "test title";
    public static final String TEST_DESCRIPTION = "test description";

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
    public static final String NOARK_CASE_HANDLING_PATH = "sakarkiv";

    public static final String NIKITA_CONTENT_TYPE = "http://nikita.arkivlab.no/noark5/v4/";
    public static final String NOARK5_V4_CONTENT_TYPE = "application/vnd.noark5-v4+json";

    private Constants() {
    }
}
