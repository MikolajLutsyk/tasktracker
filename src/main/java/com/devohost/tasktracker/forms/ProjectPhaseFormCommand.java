package com.devohost.tasktracker.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectPhaseFormCommand {
    @NotBlank(message = "Name can not be empty")
    private String name;
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "wrong date format (dd.mm.yyyy)")
    private String deadline;
    @PositiveOrZero
    private int number;
}
