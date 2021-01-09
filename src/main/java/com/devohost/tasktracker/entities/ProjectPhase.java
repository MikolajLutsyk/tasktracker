package com.devohost.tasktracker.entities;

import com.devohost.tasktracker.entities.enums.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "ProjectPhase")
@Data
@AllArgsConstructor
public class ProjectPhase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private State state;
    private int number;
    private String name;
    private LocalDate startDate;
    private LocalDate deadline;
    private LocalDate closeDate;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Module> modules;

    public void addModule(Module module){
        modules.add(module);
    }

    public ProjectPhase(){
        modules = new ArrayList<>();
    }
}
