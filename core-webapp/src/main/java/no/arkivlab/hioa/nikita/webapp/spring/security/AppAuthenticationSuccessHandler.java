package no.arkivlab.hioa.nikita.webapp.spring.security;

import no.arkivlab.hioa.nikita.webapp.spring.SwaggerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

import static nikita.config.Constants.*;

/**
 * Created by tsodring on 3/22/17.
 * Started with http://www.baeldung.com/spring_redirect_after_login
 * <p>
 * The purpose of this class is to control what happens after successful login.
 * If we want to have redirects, we can implement them here base on baeldungs example
 * <p>
 * Currently a successful login when using this class will return a 200 OK and the JSESSIONID being set.
 */

@Component
public class AppAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException {

        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void handle(HttpServletRequest request,
                          HttpServletResponse response, Authentication authentication)
            throws IOException {

        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            logger.debug(
                    "Response has already been committed. Unable to redirect to "
                            + targetUrl);
            return;
        }

        //redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(Authentication authentication) {
        boolean isRecordsManager = false;
        boolean isRecordsKeeper = false;
        boolean isCaseHandler = false;
        boolean isLeader = false;
        boolean isGuest = false;
        Collection<? extends GrantedAuthority> authorities
                = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().contains(ROLE_RECORDS_MANAGER)) {
                isRecordsManager = true;
                break;
            } else if (grantedAuthority.getAuthority().contains(ROLE_RECORDS_KEEPER)) {
                isRecordsKeeper = true;
                break;
            } else if (grantedAuthority.getAuthority().contains(ROLE_CASE_HANDLER)) {
                isCaseHandler = true;
                break;
            } else if (grantedAuthority.getAuthority().contains(ROLE_LEADER)) {
                isLeader = true;
                break;
            } else if (grantedAuthority.getAuthority().contains(ROLE_GUEST)) {
                isGuest = true;
                break;
            }
        }

        // If we wanted to capture original URL, perhaps it's hidden under request.
        // We can just redirect them back
        if (isRecordsManager) {
            return "/user";
        } else if (isRecordsKeeper) {
            return "/user";
        } else if (isCaseHandler) {
            return "/user";
        } else if (isLeader) {
            return "/user";
        } else if (isGuest) {
            return "/user";
        } else {
            //throw new IllegalStateException();
            return "/user";
        }
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        //session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
}