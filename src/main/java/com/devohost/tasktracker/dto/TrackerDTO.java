package com.devohost.tasktracker.dto;


import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrackerDTO {
    private int id;
    private List<ProjectDTO> userProjects;
    private List<TaskDTO> userTasks;

    public void addProject(ProjectDTO project) {
        userProjects.add(project);
    }

    public void addUserTask(TaskDTO task) {
        userTasks.add(task);
    }
}
