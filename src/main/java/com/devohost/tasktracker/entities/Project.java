package com.devohost.tasktracker.entities;

import com.devohost.tasktracker.entities.enums.State;
import com.devohost.tasktracker.entities.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity(name = "project")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate startDate;
    private LocalDate deadline;
    private LocalDate closeDate;
    private State state;
    private String name;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "participants")
    private Map<User, UserRole> participants = new HashMap<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProjectPhase> projectPhases = new ArrayList<>();

    public void addUser(User user, UserRole role) {
        participants.put(user, role);
    }

    public void addPhase(ProjectPhase phase) {
        projectPhases.add(phase);
    }

}