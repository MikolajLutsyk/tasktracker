package com.devohost.tasktracker.dto;

import com.devohost.tasktracker.entities.Task;
import com.devohost.tasktracker.entities.enums.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ModuleDTO {
    private int id;
    private State state;
    private List<Task> moduleTasks;

    public void addTask(Task task){
        moduleTasks.add(task);
    }
    public ModuleDTO(){
        moduleTasks = new ArrayList<>();
    }
}