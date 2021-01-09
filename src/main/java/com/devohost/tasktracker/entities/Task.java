package com.devohost.tasktracker.entities;

import com.devohost.tasktracker.entities.enums.State;
import com.devohost.tasktracker.entities.enums.TaskPriority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int points;
    @Column(nullable = false)
    private String taskContent;
    private LocalDate deadline;
    @Enumerated(EnumType.STRING)
    private State state;
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;
}