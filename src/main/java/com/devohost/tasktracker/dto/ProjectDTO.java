package com.devohost.tasktracker.dto;

import com.devohost.tasktracker.entities.enums.State;
import com.devohost.tasktracker.entities.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate startDate;
    @NotNull(message = "Deadline can not be null")
    private LocalDate deadline;
    private LocalDate closeDate;
    private State state;
    @NotNull(message = "name can't be null")
    @NotBlank(message = "name can't be blank")
    private String name;
    private Map<UserDTO, UserRole> participants = new HashMap<>();
    private List<ProjectPhaseDTO> projectPhases = new ArrayList<>();

    public void addUser(UserDTO user, UserRole role) {
        participants.put(user, role);
    }

    public void addPhase(ProjectPhaseDTO phase) {
        projectPhases.add(phase);
    }
}
