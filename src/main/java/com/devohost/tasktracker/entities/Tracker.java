package com.devohost.tasktracker.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Tracker")
@Data
@AllArgsConstructor
public class Tracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToMany
    private List<Project> userProjects;
    @OneToMany
    private List<Task> userTasks;


    public void addProject(Project project){
        userProjects.add(project);
    }
    public void addUserTask(Task task){
        userTasks.add(task);
    }


    public Tracker(){
        userProjects = new ArrayList<>();
        userTasks = new ArrayList<>();
    }
}
