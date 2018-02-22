package ru.server.spring.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.server.spring.configs.properties.AdminProperties;

@Configuration
@EnableConfigurationProperties(AdminProperties.class)
public class AdminConfiguration {

    private final AdminProperties adminProperties;

    @Autowired
    public AdminConfiguration(AdminProperties adminProperties) {
        this.adminProperties = adminProperties;
    }

    public AdminProperties getAdminProperties() {
        return adminProperties;
    }

}
