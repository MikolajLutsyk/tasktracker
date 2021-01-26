package com.devohost.tasktracker.dto;

import com.devohost.tasktracker.entities.Module;
import com.devohost.tasktracker.entities.enums.State;
import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectPhaseDTO {
    private int id;
    private State state;
    private int number;
    @NotNull(message = "Name can not be null")
    @NotBlank(message = "Name can not be blank")
    private String name;
    private LocalDate startDate;
    @NotNull(message = "Deadline can not be null")
    @NotBlank(message = "Deadline can not be blank")
    @Pattern(regexp = "\\d{2}\\.\\d{2}\\.\\d{4}", message = "wrong date format (dd.mm.yyyy)")
    private LocalDate deadline;
    private LocalDate closeDate;
    private List<Module> modules;

    private Map<LocalDateTime, State > stateHistory;

    public void addModule(Module module){
        modules.add(module);
    }
}
