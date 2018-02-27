package ru.server.spring.models.api;

import lombok.Data;
import ru.server.spring.models.HelpdeskGroup;

@Data
public class HelpdeskGroupCount {

    private HelpdeskGroup queue;
    private Integer count;

    public HelpdeskGroupCount(HelpdeskGroup queue, Integer count) {
        this.queue = queue;
        this.count = count;
    }

}
