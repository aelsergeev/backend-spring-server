package org.server.spring.configs.security;

import org.server.spring.configs.properties.ServerCorsProperties;
import org.server.spring.configs.properties.ServerIpProperties;
import org.server.spring.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties({ServerCorsProperties.class, ServerIpProperties.class})
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDao userDao;
    private final ServerCorsProperties serverCorsProperties;

    private static final Logger logger = Logger.getLogger(WebSecurityConfiguration.class.getName());


    @Autowired
    public WebSecurityConfiguration(UserDao userDao, ServerCorsProperties serverCorsProperties) {
        this.userDao = userDao;
        this.serverCorsProperties = serverCorsProperties;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
            .antMatchers("/webjars/**", "/swagger-resources/**", "/v2/api-docs").permitAll()
            .antMatchers("/**").authenticated()
            .antMatchers("/swagger-ui.html", "/auth/sessions").hasRole("ADMIN");

        http.formLogin()
                .successHandler(new AjaxAuthenticationSuccessHandler(new SavedRequestAwareAuthenticationSuccessHandler()))
                .failureHandler(new AjaxAuthenticationFailureHandler());

        http.sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .sessionRegistry(sessionRegistry());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> new CustomUserDetails(userDao.getUserByUsername(username)));
    }

    @Bean
    SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        List<String> origins = serverCorsProperties.getAllowedOrigins();
        List<String> methods = serverCorsProperties.getAllowedMethods();
        List<String> headers = serverCorsProperties.getAllowedHeaders();

        logger.info("Allowed-Origins: " + origins.toString());
        logger.info("Allowed-Methods: " + methods.toString());
        logger.info("Allowed-Headers: " + headers.toString());

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(origins);
        configuration.setAllowedMethods(methods);
        configuration.setExposedHeaders(headers);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}

