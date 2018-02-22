package ru.server.spring.configs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import ru.server.spring.configs.properties.ServerCorsProperties;
import ru.server.spring.configs.properties.WebSocketProperties;

import java.util.Arrays;
import java.util.logging.Logger;

@Configuration
@EnableWebSocketMessageBroker
@EnableConfigurationProperties(WebSocketProperties.class)
public class WebSocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer {

    private String[] origins = {"*"};
    private final WebSocketProperties webSocketProperties;
    private static final Logger logger = Logger.getLogger(WebSocketConfiguration.class.getName());

    public WebSocketConfiguration(WebSocketProperties webSocketProperties, ServerCorsProperties serverCorsProperties) {
        this.webSocketProperties = webSocketProperties;

        if (webSocketProperties.getCors())
            this.origins = serverCorsProperties.getAllowedOrigins().toArray(new String[0]);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/queue", "/topic");
        config.setApplicationDestinationPrefixes(webSocketProperties.getDestinationPrefixes());
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        logger.info("Allowed-Origins: " + Arrays.toString(origins));

        registry.addEndpoint(webSocketProperties.getEndpoint()).setAllowedOrigins(origins);
        registry.addEndpoint(webSocketProperties.getEndpoint()).setAllowedOrigins(origins).withSockJS();
    }

}