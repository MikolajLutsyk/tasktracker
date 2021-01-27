package com.devohost.tasktracker.dto;

import com.devohost.tasktracker.entities.Module;
import com.devohost.tasktracker.entities.ProjectPhase;
import com.devohost.tasktracker.entities.Task;
import com.devohost.tasktracker.entities.enums.State;
import com.devohost.tasktracker.entities.enums.TaskPriority;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
public class BusinessMapper {

    //Task entity/DTO mappers

    public Task toTask(TaskDTO dto){
        return Task.builder()
                .id(dto.getId())
                .deadline(dto.getDeadline())
                .points(dto.getPoints())
                .priority(TaskPriority.valueOf(dto.getPriority()))
                .state(State.valueOf(dto.getState()))
                .taskContent(dto.getTaskContent())
                .build();
    }

    public TaskDTO toDTO(Task task){
        return TaskDTO.builder()
                .id(task.getId())
                .deadline(task.getDeadline())
                .points(task.getPoints())
                .priority(task.getPriority().toString())
                .state(task.getState().toString())
                .taskContent(task.getTaskContent())
                .build();
    }

    //Module entity/DTO mappers

    public Module toModule(ModuleDTO dto){
        return Module.builder()
                .id(dto.getId())
                .name(dto.getName())
                .moduleTasks(dto.getModuleTasks().stream()
                        .map(this::toTask)
                        .collect(Collectors.toList()))
                .state(State.valueOf(dto.getState()))
                .build();
    }

    public ModuleDTO toDTO(Module module){
        return ModuleDTO.builder()
                .id(module.getId())
                .name(module.getName())
                .state(module.getState().toString())
                .moduleTasks(module.getModuleTasks().stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    //ProjectPhase entity/DTO mappers

    public ProjectPhase toProjectPhase(ProjectPhaseDTO dto){
        return ProjectPhase.builder()
                .id(dto.getId())
                .state(dto.getState())
                .name(dto.getName())
                .number(dto.getNumber())
                .startDate(dto.getStartDate())
                .deadline(dto.getDeadline())
                .closeDate(dto.getCloseDate())
                .modules(dto.getModules().stream()
                        .map(this::toModule)
                        .collect(Collectors.toList()))
                .stateHistory(dto.getStateHistory())
                .build();
    }

    public ProjectPhaseDTO toDTO(ProjectPhase projectPhase){
        return ProjectPhaseDTO.builder()
                .id(projectPhase.getId())
                .state(projectPhase.getState())
                .name(projectPhase.getName())
                .number(projectPhase.getNumber())
                .startDate(projectPhase.getStartDate())
                .deadline(projectPhase.getDeadline())
                .closeDate(projectPhase.getCloseDate())
                .modules(projectPhase.getModules().stream()
                            .map(this::toDTO)
                            .collect(Collectors.toList()))
                .stateHistory(projectPhase.getStateHistory())
                .build();
    }
}
