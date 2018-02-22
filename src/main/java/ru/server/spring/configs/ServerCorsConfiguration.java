package ru.server.spring.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ru.server.spring.configs.properties.ServerCorsProperties;

import java.util.Arrays;
import java.util.logging.Logger;

@Configuration
@EnableConfigurationProperties(ServerCorsProperties.class)
public class ServerCorsConfiguration extends WebMvcConfigurerAdapter {

    private final ServerCorsProperties serverCorsProperties;
    private static final Logger logger = Logger.getLogger(ServerCorsProperties.class.getName());

    @Autowired
    public ServerCorsConfiguration(ServerCorsProperties serverCorsProperties) {
        this.serverCorsProperties = serverCorsProperties;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] origins = serverCorsProperties.getAllowedOrigins().toArray(new String[0]);
        String[] methods = serverCorsProperties.getAllowedMethods().toArray(new String[0]);
        String[] headers = serverCorsProperties.getAllowedHeaders().toArray(new String[0]);

        logger.info("Allowed-Origins: " + Arrays.toString(origins));
        logger.info("Allowed-Methods: " + Arrays.toString(methods));
        logger.info("Allowed-Headers: " + Arrays.toString(headers));

        registry.addMapping("*").allowedOrigins(origins).allowedMethods(methods).allowedHeaders(headers);
    }

}
