package no.arkivlab.hioa.nikita.webapp.listener;

import no.arkivlab.hioa.nikita.webapp.service.SessionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Taken from:
 * https://moelholm.com/2016/08/22/spring-boot-sessions-actuator-endpoint/
 */
@Component
public class SessionListener implements HttpSessionListener {

    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        sessionRegistry.addSession(se.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        sessionRegistry.removeSession(se.getSession());
    }
}

