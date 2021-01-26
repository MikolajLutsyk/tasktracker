package com.devohost.tasktracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
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
    @Pattern(regexp = "\\d{2}\\.\\d{2}\\.\\d{4}", message = "wrong date format (dd.mm.yyyy)")
    private LocalDate deadline;
    private String state;
    private String priority;
    private String taskContent;
}
