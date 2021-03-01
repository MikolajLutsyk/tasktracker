package com.devohost.tasktracker.dto;

import com.devohost.tasktracker.entities.Role;
import lombok.*;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class UserDTO {
    private int id;
    @NotNull(message = "email can not be null")
    @NotBlank(message = "email can not be blank")
    private String email;
    @NotNull(message = "password can not be null")
    @NotBlank(message = "password can not be blank")
    private String password;
    @NotNull(message = "first name can not be null")
    @NotBlank(message = "first name can not be blank")
    private String firstname;
    @NotNull(message = "last name can not be null")
    @NotBlank(message = "last name can not be blank")
    private String lastname;
    private LocalDate registrationDate;
    private TrackerDTO tracker;
    private String role;

    public UserDTO() {
        tracker = new TrackerDTO();
    }
}
