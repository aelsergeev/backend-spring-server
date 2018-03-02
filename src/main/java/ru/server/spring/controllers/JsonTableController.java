package ru.server.spring.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.server.spring.dao.JsonTableDao;
import ru.server.spring.models.JsonTable;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/{uuid}")
    public JsonTable getJsonById(@PathVariable UUID uuid) {
        return jsonTableDao.findOne(uuid);
    }

    @GetMapping("/search")
    public List<JsonTable> getJsonByName(String name) {
        return jsonTableDao.findAllByName(name);
    }

}
