package com.devohost.tasktracker.entities;

import com.devohost.tasktracker.entities.enums.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "modules")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Enumerated(EnumType.STRING)
    private State state;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Task> moduleTasks = new ArrayList<>();

    public void addTask(Task task){
        moduleTasks.add(task);
    }
}