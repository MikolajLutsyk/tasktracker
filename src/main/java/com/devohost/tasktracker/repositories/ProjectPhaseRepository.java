package com.devohost.tasktracker.repositories;

import com.devohost.tasktracker.entities.ProjectPhase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectPhaseRepository extends JpaRepository<ProjectPhase, Integer> {
}
