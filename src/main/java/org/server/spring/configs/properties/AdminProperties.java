package org.server.spring.configs.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "admin")
public class AdminProperties {

    private String domain;
    private String username;
    private String password;
    private String userAgent;

}
