package org.server.spring.controllers;

import org.server.spring.models.Position;
import org.server.spring.services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/position")
public class PositionController {

    private final PositionService positionService;

    @Autowired
    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping("/list")
    public List<Position> listPosition() {
        return positionService.list();
    }

    @PutMapping("/update")
    public Position updatePosition(@RequestBody Position position) {
        return positionService.update(position);
    }

    @DeleteMapping("/delete")
    public void deletePosition(@RequestBody Position position) {
        positionService.delete(position);
    }

}
