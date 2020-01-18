package org.server.spring.configs.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        if ("true".equals(request.getHeader("X-Ajax-Request"))) {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().print("{ \"message\": \"Bad credentials with Ajax\" }");
        } else {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().print("{ \"message\": \"Bad credentials\" }");
        }
    }

}
