package com.devohost.tasktracker.dto;

import com.devohost.tasktracker.entities.enums.State;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectPhaseDTO {
    private int id;
    private State state;
    @Positive
    @NotNull
    private int number;
    @NotNull(message = "Name can not be null")
    @NotBlank(message = "Name can not be blank")
    private String name;
    private LocalDate startDate;
    @NotNull(message = "Deadline can not be null")
    private LocalDate deadline;
    private LocalDate closeDate;
    private List<ModuleDTO> modules = new ArrayList<>();

    private Map<LocalDate, State> stateHistory;

    public void stateHistoryPut(LocalDate date, State state){
        stateHistory.put(date, state);
    }

    public void addModule(ModuleDTO module){
        modules.add(module);
    }
}
