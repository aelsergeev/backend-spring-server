package ru.server.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.server.spring.models.InternLog;
import ru.server.spring.services.InternLogService;

import java.util.List;


@RestController
@RequestMapping("/intern")
public class InternController {

    private final InternLogService internLogService;

    @Autowired
    public InternController(InternLogService internLogService) {
        this.internLogService = internLogService;
    }

    @GetMapping("/list")
    public List<InternLog> listIntern() {
        return internLogService.list();
    }

    @PutMapping("/update")
    public InternLog updateIntern(@RequestBody InternLog internLog) {
        return internLogService.update(internLog);
    }

}
