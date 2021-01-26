package com.devohost.tasktracker.dto;

import com.devohost.tasktracker.entities.Task;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModuleDTO {
    private int id;
    private String state;
    @NotBlank(message = "name must not be blank")
    @NotNull(message = "name must not be null")
    private String name;
 
    private List<TaskDTO> moduleTasks = new ArrayList<>();

    public void addTask(TaskDTO task) {
        System.out.println(moduleTasks);
        moduleTasks.add(task);
    }
}