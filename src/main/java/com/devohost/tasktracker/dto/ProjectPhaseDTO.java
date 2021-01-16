package com.devohost.tasktracker.dto;

import com.devohost.tasktracker.entities.Module;
import com.devohost.tasktracker.entities.enums.State;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectPhaseDTO {
    private int id;
    private State state;
    private int number;
    private String name;
    private LocalDate startDate;
    private LocalDate deadline;
    private LocalDate closeDate;
    private List<Module> modules;

    public void addModule(Module module){
        modules.add(module);
    }

    public ProjectPhaseDTO(){
        modules = new ArrayList<>();
    }
}
