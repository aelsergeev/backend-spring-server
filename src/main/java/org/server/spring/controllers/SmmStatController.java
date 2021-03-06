package org.server.spring.controllers;


import org.server.spring.models.SmmStat;
import org.server.spring.models.api.Statistic;
import org.server.spring.services.SmmStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/smmstat")
public class SmmStatController {

    private final SmmStatService smmStatService;

    @Autowired
    public SmmStatController(SmmStatService smmStatService) {
        this.smmStatService = smmStatService;
    }

    @GetMapping("/list")
    public List<SmmStat> listSmmStat() {
        return smmStatService.list();
    }

    @PutMapping("/update")
    public SmmStat updateSmmStat(@RequestBody SmmStat smmStat) {
        return smmStatService.update(smmStat);
    }

    @GetMapping("/stat")
    public List<Statistic> statSmmStat(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date firstDay,
                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date secondDay) {
        return smmStatService.stat(firstDay, secondDay);
    }

}
