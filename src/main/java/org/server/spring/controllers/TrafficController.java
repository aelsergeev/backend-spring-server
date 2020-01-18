package org.server.spring.controllers;

import org.server.spring.models.TrafficTemplate;
import org.server.spring.services.TrafficService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/traffic")
public class TrafficController {

    private final TrafficService trafficService;

    @Autowired
    public TrafficController(TrafficService trafficService) {
        this.trafficService = trafficService;
    }

    @GetMapping("/template/list")
    public List<TrafficTemplate> listPermission() {
        return trafficService.listTemplates();
    }

    @PutMapping("/template/update")
    public TrafficTemplate updatePermission(@RequestBody TrafficTemplate trafficTemplate) {
        return trafficService.updateTemplates(trafficTemplate);
    }

    @DeleteMapping("/template/delete")
    public void deletePermission(@RequestBody TrafficTemplate trafficTemplate) {
        trafficService.deleteTemplates(trafficTemplate);
    }

}
