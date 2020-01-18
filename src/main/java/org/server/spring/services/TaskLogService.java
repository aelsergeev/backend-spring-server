package org.server.spring.services;

import org.server.spring.dao.TaskLogDao;
import org.server.spring.models.TaskLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TaskLogService {
    List<TaskLog> list();
    TaskLog update(TaskLog taskLog);
    void delete(TaskLog taskLog);
}

@Service
class TaskLogServiceImpl implements TaskLogService {

    private final TaskLogDao taskLogDao;

    @Autowired
    public TaskLogServiceImpl(TaskLogDao taskLogDao) {
        this.taskLogDao = taskLogDao;
    }

    public List<TaskLog> list() {
        return taskLogDao.list();
    }

    public TaskLog update(TaskLog taskLog) {
        return taskLogDao.update(taskLog);
    }

    public void delete(TaskLog taskLog) { taskLogDao.delete(taskLog); }

}
