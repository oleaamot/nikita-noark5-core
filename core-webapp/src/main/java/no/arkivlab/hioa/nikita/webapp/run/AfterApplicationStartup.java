package no.arkivlab.hioa.nikita.webapp.run;

import nikita.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static java.lang.System.out;
import static nikita.config.Constants.SLASH;

/**
 * Create som basic data if application is in demo mode
 */
@Component
public class AfterApplicationStartup {

    private static final Logger logger = LoggerFactory.getLogger(AfterApplicationStartup.class);
    private final RequestMappingHandlerMapping handlerMapping;

    public AfterApplicationStartup(@Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    /**
     * afterApplicationStarts, go through list of endpoints and make a list of endpoints and
     * the HTTP methods they support.
     *
     */
    public void afterApplicationStarts() {

        for(Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMapping.getHandlerMethods().entrySet()) {

            RequestMappingInfo requestMappingInfo = entry.getKey();
            // Assuming there is always a non-null value
            String servletPaths = requestMappingInfo.getPatternsCondition().toString();
            // Typically the servletPaths looks like [ /api/arkivstruktur/arkiv ]
            // If it contains two more it looks like
            //      [ /api/arkivstruktur/ny-arkivskaper || /api/arkivstruktur/arkiv/ny-arkivskaper ]
            // So simplest way to process is split on space and ignore the "||"
            //  ignore is done in false == servletPaths.contains("|") below
            // servletPath starts with "[" and ends with "]". Removing them if they are there
            if (true == servletPaths.startsWith("[")) {
                servletPaths = servletPaths.substring(1);
            }
            if (true == servletPaths.endsWith("]")) {
                servletPaths = servletPaths.substring(0, servletPaths.length() - 1);
            }

            String[] servletPathList = servletPaths.split("\\s+");

            for (String servletPath : servletPathList) {

                if (servletPath != null && false == servletPath.contains("|")) {

                    // Adding a trailing slash as the incoming request may or may not have it
                    // This is done to be consist on a lookup
                    if (false == servletPath.endsWith("/")) {
                        servletPath += SLASH;
                    }

                    Set<RequestMethod> httpMethodRequests = requestMappingInfo.getMethodsCondition().getMethods();
                    if (null != httpMethodRequests && null != servletPath) {
                        // RequestMethod and HTTPMethod are different types, have to convert them here
                        Set<HttpMethod> httpMethods = new TreeSet<>();
                        for (RequestMethod requestMethod : httpMethodRequests) {
                            if (requestMethod.equals(requestMethod.GET)) {
                                httpMethods.add(HttpMethod.GET);
                            } else if (requestMethod.equals(requestMethod.DELETE)) {
                                httpMethods.add(HttpMethod.DELETE);
                            } else if (requestMethod.equals(requestMethod.OPTIONS)) {
                                httpMethods.add(HttpMethod.OPTIONS);
                            } else if (requestMethod.equals(requestMethod.HEAD)) {
                                httpMethods.add(HttpMethod.HEAD);
                            } else if (requestMethod.equals(requestMethod.PATCH)) {
                                httpMethods.add(HttpMethod.PATCH);
                            } else if (requestMethod.equals(requestMethod.POST)) {
                                httpMethods.add(HttpMethod.POST);
                            } else if (requestMethod.equals(requestMethod.PUT)) {
                                httpMethods.add(HttpMethod.PUT);
                            } else if (requestMethod.equals(requestMethod.TRACE)) {
                                httpMethods.add(HttpMethod.TRACE);
                            }
                        }
                        out.println("Adding " + servletPath + " methods " + httpMethods);
                        CommonUtils.WebUtils.addRequestToMethodMap(servletPath, httpMethods);
                    } else {
                        logger.warn("Missing HTTP Methods for the following servletPath [" + servletPath + "]");
                    }
                }
            }
        }
    }

}
