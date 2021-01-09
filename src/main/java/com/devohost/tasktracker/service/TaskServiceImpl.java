package com.devohost.tasktracker.service;

import com.devohost.tasktracker.dto.TaskDTO;
import com.devohost.tasktracker.entities.Task;
import com.devohost.tasktracker.entities.enums.State;
import com.devohost.tasktracker.entities.enums.TaskPriority;
import com.devohost.tasktracker.exceptions.TaskException;
import com.devohost.tasktracker.repositories.TaskRepository;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{

    @Resource
    private TaskRepository taskRepository;

    @Override
    public TaskDTO addTask(TaskDTO dto) {
        if (dto == null){
            throw new TaskException("income object is null");
        }
        dto.setState(State.NEW);
        dto.setPoints(0);
        dto.setPriority(TaskPriority.NOTURGENT_NOTIMPORTANT);
        Task task =  taskRepository.save(toTask(dto));
        return toDTO(task);
    }

    @Override
    public TaskDTO getTaskById(int id) throws TaskException {
        return null;
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return null;
    }

    @Override
    public void saveTask(TaskDTO dto) {

    }

    @Override
    public boolean deleteTask(int id) {
        return false;
    }

    public static Task toTask(TaskDTO dto){
        return Task.builder()
                .id(dto.getId())
                .deadline(dto.getDeadline())
                .points(dto.getPoints())
                .priority(dto.getPriority())
                .state(dto.getState())
                .taskContent(dto.getTaskContent())
                .build();
    }

    public static TaskDTO toDTO(Task task){
        return TaskDTO.builder()
                .id(task.getId())
                .deadline(task.getDeadline())
                .points(task.getPoints())
                .priority(task.getPriority())
                .state(task.getState())
                .taskContent(task.getTaskContent())
                .build();
    }

}