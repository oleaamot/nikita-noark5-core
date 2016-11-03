package no.arkivlab.hioa.nikita.webapp.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;


/**
 * Taken from:
 * https://moelholm.com/2016/08/22/spring-boot-sessions-actuator-endpoint/
 */
@Service
public class SessionRegistry {

    private final Map<String, HttpSession> httpSessionMap = new ConcurrentSkipListMap<>();

    public void addSession(HttpSession httpSession) {
        this.httpSessionMap.put(httpSession.getId(), httpSession);
    }

    public void removeSession(HttpSession httpSession) {
        this.httpSessionMap.remove(httpSession.getId());
    }

    public List<HttpSession> getSessions() {
        return new ArrayList<>(httpSessionMap.values());
    }
}

