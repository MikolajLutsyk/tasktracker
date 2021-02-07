package com.devohost.tasktracker.service.interfaces;

import com.devohost.tasktracker.dto.ProjectPhaseDTO;
import com.devohost.tasktracker.exceptions.ProjectPhaseException;

import java.util.List;

public interface ProjectPhaseService {
    ProjectPhaseDTO addProjectPhase(ProjectPhaseDTO dto);
    ProjectPhaseDTO getProjectPhaseById(int id) throws ProjectPhaseException;
    List<ProjectPhaseDTO> getAllProjectPhases();
    void saveProjectPhase(ProjectPhaseDTO dto);
    boolean deleteProjectPhase(int id);
}
