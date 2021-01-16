package com.devohost.tasktracker.dto;

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
    private String state;
    private String priority;
    private String taskContent;
}
