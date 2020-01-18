package org.server.spring.configs;

import org.server.spring.configs.properties.ImageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
@EnableConfigurationProperties(ImageProperties.class)
public class ImageConfiguration {

    private final ImageProperties imageProperties;
    private static final Logger logger = Logger.getLogger(ImageProperties.class.getName());


    @Autowired
    public ImageConfiguration(ImageProperties imageProperties) {
        this.imageProperties = imageProperties;

        logger.info(imageProperties.toString());
    }

    public ImageProperties getImageProperties() {
        return imageProperties;
    }

}
