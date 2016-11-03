package nikita.config;


public final class N5ResourceMappings {

    // Commonly used entities for REST request mappings
    public static final String FONDS = "arkiv";
    public static final String FONDS_CREATOR = "arkivskaper";
    public static final String SERIES = "arkivdel";
    public static final String CLASSIFICATION_SYSTEM = "klassifikasjon";
    public static final String CLASS = "klasse";
    public static final String FILE = "mappe";
    public static final String REGISTRATION = "registrering";
    public static final String BASIC_REGISTRATION = "basisregistrering";
    public static final String REGISTRY_ENTRY = "journalpost";
    public static final String DOCUMENT_DESCRIPTION = "dokumentbeskrivelse";
    public static final String DOCUMENT_OBJECT = "dokumentobjekt";

    public static final String FONDS_STATUS = "arkivstatus";
    public static final String STORAGE_LOCATION = "oppbevaringsted";
    public static final String CLASSIFIED = "gradering";
    public static final String SCREENED = "skjerming";
    public static final String DISPOSAL = "kassasjon";
    public static final String DISPOSAL_UNDERTAKEN = "ufoertKassasjon";

    public static final String SESSIONS = "sessions";


    // Commonly used metadata elements
    public static final String DOCUMENT_MEDIUM = "dokumentmedium";
    public static final String TESTS = "tests";
    public static final String SERIES_SUCCESSOR = "arvtager";
    public static final String SERIES_PRECURSOR = "forloeper";


    // Constant values defined in the Metadata catalogue

    public static final String STATUS_OPEN = "Opprettet";
    public static final String STATUS_CLOSED = "Avsluttet";

    // M300 dokumentmedium
    public static final String DOCUMENT_MEDIUM_PHYSICAL = "Fysisk arkiv";
    public static final String DOCUMENT_MEDIUM_ELECTRONIC ="Elektronisk arkiv";
    public static final String DOCUMENT_MEDIUM_MIXED = "Blandet fysisk og elektronisk arkiv";


    // For soft deletes
    public static final Boolean NOT_DELETED = false;
    public static final Boolean DELETED = true;

    // discoverability

    public static final class Singural {
        public static final String TEST = "test";
    }
    
    private N5ResourceMappings() {
        throw new AssertionError();
    }

    // API

}

