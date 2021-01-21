package com.devohost.tasktracker.forms;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModuleFormCommand {
    @NotBlank(message = "Module must be given a name")
    @NotNull(message = "Module name is null")
    private String name;
}
