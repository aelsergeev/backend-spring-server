package ru.server.spring.models.api;

import lombok.Data;
import ru.server.spring.models.HelpdeskGroup;

@Data
public class HelpdeskGroupCount {

    private HelpdeskGroup hd;
    private Integer count;

    public HelpdeskGroupCount(HelpdeskGroup hd, Integer count) {
        this.hd = hd;
        this.count = count;
    }

}
