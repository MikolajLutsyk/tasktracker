package com.devohost.tasktracker.forms;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectFormCommand {
    @NotNull(message = "Deadline can not be null")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "wrong date format (dd.mm.yyyy)")
    private String deadline;
    @NotNull(message = "name can't be null")
    @NotBlank(message = "name can't be blank")
    private String name;
}
