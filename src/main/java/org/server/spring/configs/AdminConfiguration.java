package org.server.spring.configs;

import org.server.spring.configs.properties.AdminProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
@EnableConfigurationProperties(AdminProperties.class)
public class AdminConfiguration {

    private final AdminProperties adminProperties;
    private static final Logger logger = Logger.getLogger(AdminProperties.class.getName());


    @Autowired
    public AdminConfiguration(AdminProperties adminProperties) {
        this.adminProperties = adminProperties;

        logger.info(adminProperties.toString());
    }

    public AdminProperties getAdminProperties() {
        return adminProperties;
    }

}
