package com.devohost.tasktracker.dto;

import com.devohost.tasktracker.entities.enums.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModuleDTO {
    private int id;
    private State state;
    private List<TaskDTO> moduleTasks = new ArrayList<>();
}