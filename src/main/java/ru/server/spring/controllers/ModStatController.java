package ru.server.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.server.spring.models.ModStat;
import ru.server.spring.models.api.Statistic;
import ru.server.spring.services.ModStatService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/modstat")
public class ModStatController {

    private final ModStatService modStatService;

    @Autowired
    public ModStatController(ModStatService modStatService) {
        this.modStatService = modStatService;
    }

    @GetMapping("/list")
    public List<ModStat> listModStat() {
        return modStatService.list();
    }

    @PutMapping("/update")
    public ModStat updateModStat(@RequestBody ModStat modStat) {
        return modStatService.update(modStat);
    }

    @GetMapping("/stat")
    public List<Statistic> statModStat(@RequestParam String category,
                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date firstDay,
                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date secondDay) {
        return modStatService.stat(category, firstDay, secondDay);
    }

}
