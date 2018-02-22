package ru.server.spring.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.server.spring.configs.properties.ImageProperties;

@Configuration
@EnableConfigurationProperties(ImageProperties.class)
public class ImageConfiguration {

    private final ImageProperties imageProperties;

    @Autowired
    public ImageConfiguration(ImageProperties imageProperties) {
        this.imageProperties = imageProperties;
    }

    public ImageProperties getImageProperties() {
        return imageProperties;
    }

}
