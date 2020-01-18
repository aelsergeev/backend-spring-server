package org.server.spring.configs.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "schedulers")
public class SchedulersProperties {

    private SchedulersSettings statCategory = new SchedulersSettings();
    private SchedulersSettings clearModStat = new SchedulersSettings();
    private SchedulersSettings taskLog = new SchedulersSettings();
    private SchedulersSettings adminUserId = new SchedulersSettings();
    private SchedulersSettings hdGroupsStat = new SchedulersSettings();

    @Data
    public static class SchedulersSettings {

        private Boolean enable;
        private String cron;

        SchedulersSettings() {
            this.enable = false;
            this.cron = "1 * * * * *";
        }

    }

}
