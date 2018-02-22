package ru.server.spring.configs.security.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import ru.server.spring.configs.security.CustomUserDetails;
import ru.server.spring.services.UserService;

//@Configuration
//@EnableWebSecurity
public class GlobalSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    private final UserService userService;

    @Autowired
    public GlobalSecurityConfiguration(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> new CustomUserDetails(userService.getUserByUsername(username)));
    }

}
