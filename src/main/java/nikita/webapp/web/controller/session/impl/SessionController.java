package nikita.webapp.web.controller.session.impl;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.Api;
import nikita.common.config.N5ResourceMappings;
import nikita.webapp.service.SessionRegistry;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Adapted from:
 * https://moelholm.com/2016/08/22/spring-boot-sessions-actuator-endpoint/
 */
@RestController
@RequestMapping(value = N5ResourceMappings.SESSIONS)
@Api(value = "Sessions", description = "CRUD operations on fonds")
public class SessionController {

    @Autowired
    private SessionRegistry sessionRegistry;


    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SessionStateActuatorEndpointResponse listActiveSessions() {
        return new SessionStateActuatorEndpointResponse("Active HTTP sessions", sessionRegistry.getSessions());
    }


    @JsonPropertyOrder({"info", "numberOfActiveSessions", "sessions"})
    public static class SessionStateActuatorEndpointResponse {

        @JsonProperty
        private String info;

        @JsonProperty
        private int numberOfActiveSessions;

        @JsonProperty
        private List<Map<String, String>> sessions;

        public SessionStateActuatorEndpointResponse(String info, List<HttpSession> httpSessions) {
            this.info = info;
            this.numberOfActiveSessions = httpSessions.size();
            this.sessions = httpSessions.stream()
                    .map(this::getSessionState)
                    .collect(Collectors.toList());
        }

        private Map<String, String> getSessionState(HttpSession httpSession) {
            Map<String, String> sessionState = new LinkedHashMap<>();
            sessionState.put("@session_id", httpSession.getId());
            sessionState.put("@session_creation_time", formatDateTime(httpSession.getCreationTime()));
            sessionState.put("@session_last_accessed_time", formatDateTime(httpSession.getLastAccessedTime()));
            for (Enumeration<String> attributeNames = httpSession.getAttributeNames(); attributeNames.hasMoreElements(); ) {
                String attributeName = attributeNames.nextElement();
                Object attributeValue = httpSession.getAttribute(attributeName);
                sessionState.put(attributeName, attributeValue instanceof String || attributeValue instanceof Number ?
                        attributeValue.toString() : ReflectionToStringBuilder.toString(attributeValue));
            }
            return sessionState;
        }

        private String formatDateTime(long epoch) {
            Instant instant = Instant.ofEpochMilli(epoch);
            ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
            return zonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }
    }
}
