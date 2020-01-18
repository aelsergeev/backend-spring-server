package org.server.spring.configs.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "server.cors")
public class ServerCorsProperties {

    private List<String> allowedOrigins = new ArrayList<>();
    private List<String> allowedMethods = new ArrayList<>();
    private List<String> allowedHeaders = new ArrayList<>();

}
