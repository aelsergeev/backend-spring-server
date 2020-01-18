package org.server.spring.controllers;

import org.server.spring.dao.JsonTableDao;
import org.server.spring.models.JsonTable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/json")
public class JsonTableController {

    private final JsonTableDao jsonTableDao;

    public JsonTableController(JsonTableDao jsonTableDao) {
        this.jsonTableDao = jsonTableDao;
    }

    @GetMapping("/list")
    public List<JsonTable> listJson() {
        return jsonTableDao.findAll();
    }

    @GetMapping("/search")
    public List<JsonTable> getJsonByName(String name) {
        return jsonTableDao.findAllByName(name);
    }

}
