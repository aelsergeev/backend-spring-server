package org.server.spring.models.api;

import lombok.Data;
import org.server.spring.models.HelpdeskQueue;

@Data
public class HelpdeskQueueApi {

    private HelpdeskQueue queue;
    private Integer count;

    public HelpdeskQueueApi(HelpdeskQueue queue, Integer count) {
        this.queue = queue;
        this.count = count;
    }

}
