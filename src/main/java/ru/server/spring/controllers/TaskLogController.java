package ru.server.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.server.spring.models.TaskLog;
import ru.server.spring.services.TaskLogService;

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
