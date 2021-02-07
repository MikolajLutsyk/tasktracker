package com.devohost.tasktracker.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity(name = "Tracker")
@Data
@AllArgsConstructor
@Builder
public class Tracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Project> userProjects;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Task> userTasks;


    public void addProject(Project project){
        userProjects.add(project);
    }
    public void addUserTask(Task task){
        userTasks.add(task);
    }
}
