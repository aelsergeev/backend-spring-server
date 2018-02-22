package ru.server.spring.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.server.spring.dao.WeekendDao;
import ru.server.spring.models.Weekend;

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
