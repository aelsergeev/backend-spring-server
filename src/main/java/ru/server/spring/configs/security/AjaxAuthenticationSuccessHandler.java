package ru.server.spring.configs.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private AuthenticationSuccessHandler defaultHandler;

    AjaxAuthenticationSuccessHandler(AuthenticationSuccessHandler defaultHandler) {
        this.defaultHandler = defaultHandler;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if ("true".equals(request.getHeader("X-Ajax-Request"))) {
            response.getWriter().print("Authentication successful with Ajax");
            response.getWriter().flush();
        } else {
            defaultHandler.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
