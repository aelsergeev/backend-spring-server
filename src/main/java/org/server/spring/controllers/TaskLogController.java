package org.server.spring.controllers;

import org.server.spring.models.TaskLog;
import org.server.spring.services.TaskLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasklog")
public class TaskLogController {

    private final TaskLogService taskLogService;

    @Autowired
    public TaskLogController(TaskLogService taskLogService) {
        this.taskLogService = taskLogService;
    }

    @GetMapping("/list")
    public List<TaskLog> listTaskLog() {
        return taskLogService.list();
    }

    @PutMapping("/update")
    public TaskLog updateTaskLog(@RequestBody TaskLog taskLog) {
        return taskLogService.update(taskLog);
    }

    @DeleteMapping("/delete")
    public void deleteTaskLog(@RequestBody TaskLog taskLog) {
        taskLogService.delete(taskLog);
    }

}
