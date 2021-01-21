package com.devohost.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Max;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTO {
    private int id;
    @Max(value = 100, message = "points must be 0-100")
    @PositiveOrZero
    private int points;
    private LocalDate deadline;
    private String state;
    private String priority;
    private String taskContent;
}
