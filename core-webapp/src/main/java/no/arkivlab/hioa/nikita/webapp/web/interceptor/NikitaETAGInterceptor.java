package no.arkivlab.hioa.nikita.webapp.web.interceptor;

import nikita.util.exceptions.NikitaMalformedHeaderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpHeaders.ETAG;

/**
 * Created by tsodring on 3/25/17.
 * <p>
 * NikitaETAGInterceptor adds a
 */
public class NikitaETAGInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(NikitaETAGInterceptor.class);

    /**
     * @param request
     * @param response
     * @param handler  Make sure an incoming PUT request contains a ETAG. Any PUT request without an ETAG is rejected with
     *                 a MalformedHeaderException
     * @return
     * @throws RuntimeException
     */
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
            throws RuntimeException {

        // All PUT requests must have an eTag. Otherwise reject it
        if (RequestMethod.PUT.name().equals(request.getMethod())) {
            if (request.getHeader(ETAG) == null) {
                throw new NikitaMalformedHeaderException("eTag is missing on following request: " +
                        "[" + request.getRequestURI() + "], Request method [" + request.getMethod() + "]" +
                        ". You cannot update an entity in the core unless you first ensure you have retrieved an" +
                        " entity with an eTag set. Using an entity from a list does not provide an eTag.");
            } else { // Make sure the ETAG is numeric and greater than or equal to 0
                String etagWithQuotes = request.getHeader(ETAG);
                String etagWithoutQuotes = "";
                // check that quotes are at beginning and end or throw and there actually is some content
                // < 3 characters means it cannot contain content between ""
                if (!etagWithQuotes.startsWith("\"") && !etagWithQuotes.endsWith("\"") &&
                        etagWithQuotes.length() < 3) {
                    throw new NikitaMalformedHeaderException("eTag is malformed in following request: " +
                            "[" + request.getRequestURI() + "]. ETag must be quoted and contain content. The ETag " +
                            "you submitted is [" + request.getHeader(ETAG) + "]. Note if you e.g are running curl " +
                            "from the commandline, yo might need to escape the quotes");
                }
                etagWithoutQuotes = etagWithQuotes.substring(1, etagWithQuotes.length() - 1);
                try {
                    long etagVal = Long.parseLong(etagWithoutQuotes);
                    if (etagVal < 0) {
                        throw new NikitaMalformedHeaderException("eTag value is less than 0 for request: " +
                                "[" + request.getRequestURI() + "]. Value is [" + etagVal + "]. This is illegal" +
                                "as ETAG values show version of an entity in the database and start at 0");
                    }
                } catch (NumberFormatException nfe) {
                    throw new NikitaMalformedHeaderException("eTag value is not numeric. Nikita  uses numeric ETAG " +
                            "values >= 0. The value in the request [" + request.getRequestURI() + "] is [" +
                            request.getHeader(ETAG) + "]");
                }
            }
        }
        // Important! You must return true if all is OK or further filer processing stops
        return true;
    }
}
