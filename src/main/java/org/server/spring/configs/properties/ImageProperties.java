package org.server.spring.configs.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "image.location")
public class ImageProperties {

    private String employee;

}
