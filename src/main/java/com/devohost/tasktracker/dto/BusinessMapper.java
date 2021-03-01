package com.devohost.tasktracker.dto;

import com.devohost.tasktracker.entities.*;
import com.devohost.tasktracker.entities.Module;
import com.devohost.tasktracker.entities.enums.State;
import com.devohost.tasktracker.entities.enums.TaskPriority;
import com.devohost.tasktracker.repositories.RoleRepository;
import org.hibernate.mapping.Set;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BusinessMapper {
    @Resource
    private RoleRepository roleRepository;

    //Task entity/DTO mappers

    public Task toTask(TaskDTO dto) {
        return Task.builder()
                .id(dto.getId())
                .deadline(dto.getDeadline())
                .points(dto.getPoints())
                .priority(TaskPriority.valueOf(dto.getPriority()))
                .state(State.valueOf(dto.getState()))
                .taskContent(dto.getTaskContent())
                .build();
    }

    public TaskDTO toDTO(Task task) {
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

    public Module toModule(ModuleDTO dto) {
        return Module.builder()
                .id(dto.getId())
                .name(dto.getName())
                .moduleTasks(dto.getModuleTasks().stream()
                        .map(this::toTask)
                        .collect(Collectors.toList()))
                .state(State.valueOf(dto.getState()))
                .build();
    }

    public ModuleDTO toDTO(Module module) {
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

    public ProjectPhase toProjectPhase(ProjectPhaseDTO dto) {
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

    public ProjectPhaseDTO toDTO(ProjectPhase projectPhase) {
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

    //Project entity/DTO mappers

    public Project toProject(ProjectDTO dto) {
        return Project.builder()
                .id(dto.getId())
                .state(dto.getState())
                .name(dto.getName())
                .startDate(dto.getStartDate())
                .deadline(dto.getDeadline())
                .closeDate(dto.getCloseDate())
                .projectPhases(dto.getProjectPhases().stream()
                        .map(this::toProjectPhase)
                        .collect(Collectors.toList()))
                .participants(dto.getParticipants()
                        .entrySet()
                        .stream()
                        .map((entry) -> Map.entry(toUser(entry.getKey()), entry.getValue()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
                .build();
    }

    public ProjectDTO toDTO(Project project) {
        return ProjectDTO.builder()
                .id(project.getId())
                .state(project.getState())
                .name(project.getName())
                .startDate(project.getStartDate())
                .deadline(project.getDeadline())
                .closeDate(project.getCloseDate())
                .projectPhases(project.getProjectPhases().stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList()))
                .participants(project.getParticipants().entrySet().stream()
                        .map((entry) -> Map.entry(toDTO(entry.getKey()), entry.getValue()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
                .build();
    }

    //User entity/DTO mappers

    public User toUser(UserDTO dto) {
        return User.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .registrationDate(dto.getRegistrationDate())
                .tracker(toTracker(dto.getTracker()))
                .role(toRole(dto.getRole()))
                .build();
    }

    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .registrationDate(user.getRegistrationDate())
                .tracker(toDTO(user.getTracker()))
                .role(user.getRole().getName())
                .build();
    }

    //Tracker entity/DTO mappers

    public Tracker toTracker(TrackerDTO dto) {
        return Tracker.builder()
                .id(dto.getId())
                .userProjects(dto.getUserProjects().stream()
                        .map(this::toProject)
                        .collect(Collectors.toSet()))
                .userTasks(dto.getUserTasks().stream()
                        .map(this::toTask)
                        .collect(Collectors.toList()))
                .build();
    }

    public TrackerDTO toDTO(Tracker tracker) {
        return TrackerDTO.builder()
                .id(tracker.getId())
                .userProjects(tracker.getUserProjects().stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList()))
                .userTasks(tracker.getUserTasks().stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public Role toRole(String role) {

        return roleRepository.findByName(role);
    }

    public RoleDTO toDTO(Role role) {
        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

}
