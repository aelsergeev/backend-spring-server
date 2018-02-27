package ru.server.spring.models.api;

import lombok.Data;
import ru.server.spring.models.HelpdeskQueue;

@Data
public class HelpdeskQueueCount {

    private HelpdeskQueue queue;
    private Integer count;

    public HelpdeskQueueCount(HelpdeskQueue queue, Integer count) {
        this.queue = queue;
        this.count = count;
    }

}
