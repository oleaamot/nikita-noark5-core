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
            }
        }
        // Important! You must return true if all is OK or further filer processing stops
        return true;
    }
}
