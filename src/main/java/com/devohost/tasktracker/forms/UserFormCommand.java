package com.devohost.tasktracker.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFormCommand {
    @NotBlank(message = "Task content can not be empty")
    private String email;
    @NotBlank(message = "Task content can not be empty")
    private String password;
    private String firstname;
    private String lastname;
}
