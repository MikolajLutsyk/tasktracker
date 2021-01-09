package com.devohost.tasktracker.dto;

import com.devohost.tasktracker.entities.enums.State;
import com.devohost.tasktracker.entities.enums.TaskPriority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTO {

    private int id;
    private int points;
    private LocalDate deadline;
    private State state;
    private TaskPriority priority;
    private String taskContent;
}
