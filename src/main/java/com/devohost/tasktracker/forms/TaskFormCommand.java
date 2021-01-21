package com.devohost.tasktracker.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskFormCommand {
    @NotBlank(message = "Task content can not be empty")
    private String taskContent;
    //@Size(max = 100, min = 0,)
    @Max(value =  100,  message = "points must be 0-100")
    @PositiveOrZero
    private int points;
    @Pattern(regexp = "\\d{2}\\.\\d{2}\\.\\d{4}", message = "wrong date format (dd.mm.yyyy)")
    private String deadline;
}
