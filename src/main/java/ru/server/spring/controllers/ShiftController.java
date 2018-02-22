package ru.server.spring.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.server.spring.dao.ShiftDao;
import ru.server.spring.models.Shift;

import java.util.List;

@RestController
@RequestMapping("/shift")
public class ShiftController {

    private final ShiftDao shiftDao;

    public ShiftController(ShiftDao shiftDao) {
        this.shiftDao = shiftDao;
    }

    @GetMapping("/list")
    public List<Shift> listShift() {
        return shiftDao.findAll();
    }

}
