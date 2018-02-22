package ru.server.spring.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.server.spring.configs.properties.SchedulersProperties;
import ru.server.spring.configs.properties.ServerCorsProperties;

import java.util.logging.Logger;

@Configuration
@EnableConfigurationProperties(SchedulersProperties.class)
public class SchedulersConfiguration {

    private final SchedulersProperties schedulersProperties;
    private static final Logger logger = Logger.getLogger(ServerCorsProperties.class.getName());

    @Autowired
    public SchedulersConfiguration(SchedulersProperties schedulersProperties) {
        this.schedulersProperties = schedulersProperties;

        logger.info(schedulersProperties.toString());
    }

    public SchedulersProperties getSchedulersProperties() {
        return schedulersProperties;
    }
}
