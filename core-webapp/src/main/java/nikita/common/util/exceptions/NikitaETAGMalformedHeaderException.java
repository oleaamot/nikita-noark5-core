package nikita.common.util.exceptions;

/**
 * Created by tsodring on 12/8/16.
 * <p>
 * Has it's own exception as ETAG issues occur outside of a controller. This is a special case exception
 */
public class NikitaETAGMalformedHeaderException extends NikitaMalformedHeaderException {

    public NikitaETAGMalformedHeaderException() {
        super();
    }

    public NikitaETAGMalformedHeaderException(String message) {
        super(message);
    }
}

