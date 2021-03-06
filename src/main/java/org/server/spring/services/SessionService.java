package org.server.spring.services;

import org.server.spring.models.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SessionService {

    public final SessionRegistry sessionRegistry;

    public SessionService(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    public void expireUserSessions(String username) {
        List<Object> userSessions = sessionRegistry.getAllPrincipals();
        for (Object principal : userSessions) {
            if (principal instanceof User) {
                UserDetails userDetails = (UserDetails) principal;
                if (userDetails.getUsername().equals(username)) {
                    for (SessionInformation sessionInformation : sessionRegistry.getAllSessions(userDetails, true)) {
                        sessionInformation.expireNow();
                        killExpiredSessionForSure(sessionInformation.getSessionId());
                    }
                }
            }
        }
    }

    // TODO rewrite request
    private void killExpiredSessionForSure(String id) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Cookie", "JSESSIONID=" + id);

            HttpEntity requestEntity = new HttpEntity(null, httpHeaders);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.exchange("http://localhost:8080", HttpMethod.GET, requestEntity, String.class);
        } catch (Exception ignored) {}
    }

}
