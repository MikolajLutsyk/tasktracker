package com.devohost.tasktracker.service.interfaces;

import com.devohost.tasktracker.dto.ProjectDTO;
import com.devohost.tasktracker.exceptions.ProjectException;
import java.util.List;

public interface ProjectService {
    ProjectDTO addProject(ProjectDTO dto);
    ProjectDTO getProjectById(int id) throws ProjectException;
    List<ProjectDTO> getAllProjects();
    void saveProject(ProjectDTO dto);
    boolean deleteProject(int id);
}
