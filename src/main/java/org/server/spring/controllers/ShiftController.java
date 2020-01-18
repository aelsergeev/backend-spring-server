package org.server.spring.controllers;

import org.server.spring.dao.ShiftDao;
import org.server.spring.models.Shift;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
