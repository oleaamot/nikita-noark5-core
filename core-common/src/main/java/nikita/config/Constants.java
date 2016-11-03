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


    // Some user identifiers used for testing
    public static final String TEST_USER_CASE_HANDLER_1 = "test user case handler 1";
    public static final String TEST_USER_CASE_HANDLER_2 = "test user case handler 2";
    public static final String TEST_USER_ADMIN = "test user admin";
    public static final String TEST_USER_RECORD_KEEPER = "test user record keeper";

    // Some strings used during testing
    public static final String TEST_TITLE = "test title";
    public static final String TEST_DESCRIPTION = "test description";

    private Constants() {
    }
}
