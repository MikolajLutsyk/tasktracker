package com.devohost.tasktracker.service;

import com.devohost.tasktracker.dto.TaskDTO;
import com.devohost.tasktracker.exceptions.TaskException;
import java.util.List;

public interface TaskService {
    TaskDTO addTask(TaskDTO dto);
    TaskDTO getTaskById(int id) throws TaskException;
    List<TaskDTO> getAllTasks();
    void saveTask(TaskDTO dto);
    boolean deleteTask(int id);
}