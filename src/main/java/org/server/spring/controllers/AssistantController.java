package org.server.spring.controllers;

import org.server.spring.exceptions.AssistantException;
import org.server.spring.models.assistant.AssistantGraphVertex;
import org.server.spring.models.assistant.AssistantGraphs;
import org.server.spring.services.AssistantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/assistant")
public class AssistantController {

    private final AssistantService assistantService;

    public AssistantController(AssistantService assistantService) {
        this.assistantService = assistantService;
    }

    @GetMapping("/list")
    public List<AssistantGraphVertex> listGraphInfo(@RequestParam(required = false) UUID graph,
                                                    @RequestParam(required = false) Set<UUID> children) {
        if (children != null && graph != null) throw new AssistantException("Используйте только один из параметров!");
        else {
            if (graph != null) return assistantService.graphInfoListByGraphUuid(graph);
            if (children != null) return assistantService.graphInfoListByUuid(children);

            return assistantService.list();
        }
    }

    @GetMapping("/{uuid}")
    public AssistantGraphVertex graphInfoByUuid(@PathVariable UUID uuid) {
        return assistantService.graphInfoByUuid(uuid);
    }

    @PutMapping("/update")
    public AssistantGraphVertex updateGraphInfo(@RequestBody AssistantGraphVertex assistantGraphVertex) {
        return assistantService.update(assistantGraphVertex);
    }

    @DeleteMapping("/delete")
    public void deleteGraphInfo(@RequestBody AssistantGraphVertex assistantGraphVertex) {
        assistantService.delete(assistantGraphVertex);
    }


    @GetMapping("/graphs/list")
    public List<AssistantGraphs> listGraphs() {
        return assistantService.graphsList();
    }

    @GetMapping("/graphs/head/{uuid}")
    public AssistantGraphVertex graphInfoHeadByGraphUuid(@PathVariable UUID uuid) {
        AssistantGraphVertex assistantGraphVertex = assistantService.graphInfoHeadByGraphUuid(uuid);
        if (assistantGraphVertex == null) throw new AssistantException("В графе нету главной вершины или их больше одной.");
        else return assistantGraphVertex;
    }

    @PutMapping("/graphs/update")
    public AssistantGraphs updateGraph(@RequestBody AssistantGraphs assistantGraphs) {
        return assistantService.update(assistantGraphs);
    }

    @DeleteMapping("/graphs/delete")
    public void deleteGraphs(@RequestBody AssistantGraphs assistantGraphs) {
        assistantService.delete(assistantGraphs);
    }

}
