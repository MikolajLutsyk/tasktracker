package com.devohost.tasktracker.dto;


import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor

@Builder
public class TrackerDTO {
    private int id;
    private List<ProjectDTO> userProjects;
    private List<TaskDTO> userTasks;

    public TrackerDTO() {
        userProjects = new ArrayList<>();
        userTasks = new ArrayList<>();
    }

    public void addProject(ProjectDTO project) {
        userProjects.add(project);
    }

    public void addUserTask(TaskDTO task) {
        userTasks.add(task);
    }
}
