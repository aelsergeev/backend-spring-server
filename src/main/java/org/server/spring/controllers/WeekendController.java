package org.server.spring.controllers;

import org.server.spring.dao.WeekendDao;
import org.server.spring.models.Weekend;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/weekend")
public class WeekendController {

    private final WeekendDao weekendDao;

    public WeekendController(WeekendDao weekendDao) {
        this.weekendDao = weekendDao;
    }

    @GetMapping("/list")
    public List<Weekend> listWeekend() {
        return weekendDao.findAll();
    }

}
