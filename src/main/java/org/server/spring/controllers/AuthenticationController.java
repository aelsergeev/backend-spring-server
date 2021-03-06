package org.server.spring.controllers;

import org.server.spring.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final SessionService sessionService;

    @Autowired
    public AuthenticationController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/principal")
    public Principal getPrincipal(Principal principal) {
        return principal;
    }

    @GetMapping("/sessions")
    public List<Object> getPrincipals() {
        return sessionService.sessionRegistry.getAllPrincipals();
    }

}
