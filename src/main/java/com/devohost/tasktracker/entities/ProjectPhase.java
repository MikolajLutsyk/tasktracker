package com.devohost.tasktracker.entities;

import com.devohost.tasktracker.entities.enums.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity(name = "ProjectPhase")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectPhase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private State state;
    private String name;
    private int number;
    private LocalDate startDate;
    private LocalDate deadline;
    private LocalDate closeDate;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Module> modules;
    @ElementCollection
    @CollectionTable(name = "state_history")
    private Map<LocalDateTime, State > stateHistory;

    public void addModule(Module module){
        modules.add(module);
    }
}
