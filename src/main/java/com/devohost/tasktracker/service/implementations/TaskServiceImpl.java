package com.devohost.tasktracker.service.implementations;

import com.devohost.tasktracker.dto.BusinessMapper;
import com.devohost.tasktracker.dto.TaskDTO;
import com.devohost.tasktracker.entities.Task;
import com.devohost.tasktracker.entities.enums.State;
import com.devohost.tasktracker.entities.enums.TaskPriority;
import com.devohost.tasktracker.exceptions.TaskException;
import com.devohost.tasktracker.repositories.TaskRepository;
import com.devohost.tasktracker.service.interfaces.TaskService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Resource
    private TaskRepository taskRepository;
    @Resource
    private BusinessMapper mapper;

    @Override
    public TaskDTO addTask(TaskDTO dto) {
        if (dto == null){
            throw new TaskException("income task object is null");
        }
        dto.setState(State.NEW.toString());
        dto.setPriority(TaskPriority.NOTURGENT_NOTIMPORTANT.toString());
        Task task =  taskRepository.save(mapper.toTask(dto));
        return mapper.toDTO(task);
    }

    @Override
    public TaskDTO getTaskById(int id) throws TaskException {
        return mapper.toDTO( taskRepository.findById(id).orElseThrow(()->new TaskException("Problem with task searching")));
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(x -> mapper.toDTO(x))
                .collect(Collectors.toList());
    }

    @Override
    public void saveTask(TaskDTO dto) {
        taskRepository.save(mapper.toTask(dto));
    }

    @Override
    public boolean deleteTask(int id) {
        Task task =  taskRepository.findById(id).orElseThrow(()->new TaskException("Problem with task searching"));
        taskRepository.delete(task);
        return true;
    }


}