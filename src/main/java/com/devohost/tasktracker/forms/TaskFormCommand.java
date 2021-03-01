package com.devohost.tasktracker.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

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
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "wrong date format (yyyy.mm.dd)")
    private String deadline;
    private int id;
    private String state;
    private String priority;
}
