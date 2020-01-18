package org.server.spring.configs.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "server.ip")
public class ServerIpProperties {

    private List<String> allowedIp = new ArrayList<>();
    private List<String> blockedIp = new ArrayList<>();

}
