package com.devohost.tasktracker.entities;

import com.devohost.tasktracker.entities.enums.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "modules")
@Data
@AllArgsConstructor
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private State state;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Task> moduleTasks;

    public void addTask(Task task){
        moduleTasks.add(task);
    }

    public Module(){
        moduleTasks = new ArrayList<>();
    }
}