package org.server.spring.configs.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "websocket")
public class WebSocketProperties {

    /**
     * URL to connect to WebSocket
     */
    private String endpoint;

    /**
     * Set start destination prefix for WebSocket requests
     */
    private String destinationPrefixes;

    /**
     * Enable server.cors.origins properties to WebSocket
     * Default: *
     */
    private Boolean cors;

    /**
     * Subscription URL for error
     */
    public static final String error = "/queue/error";

    public static class Notification {

        /**
         * Subscription URL for new Notification
         */
        public static final String newSubscription = "/queue/notification.new";

        /**
         * Subscription URL for update Notification
         */
        public static final String updateSubscription = "/queue/notification.update";

    }

    public static class User {

        /**
         * Subscription URL for update User
         */
        public static final String updateSubscription = "/queue/user.update";

    }

}
