package nikita.config;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.FONDS;

/**
 * Created by tsodring on 3/22/17.
 */
public final class PATHPatterns {
    public static final String PATTERN_NEW_FONDS_STRUCTURE_ALL = SLASH + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + NEW + DASH + "*";
    public static final String PATTERN_METADATA_PATH = SLASH + HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH + "**";
    public static final String PATTERN_FONDS_STRUCTURE_FONDS = SLASH + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + FONDS + SLASH + "**";
}
