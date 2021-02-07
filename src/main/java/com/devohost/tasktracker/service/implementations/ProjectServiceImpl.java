package com.devohost.tasktracker.service.implementations;

import com.devohost.tasktracker.dto.BusinessMapper;
import com.devohost.tasktracker.dto.ProjectDTO;
import com.devohost.tasktracker.entities.Project;
import com.devohost.tasktracker.entities.enums.State;
import com.devohost.tasktracker.exceptions.ProjectException;
import com.devohost.tasktracker.repositories.ProjectRepository;
import com.devohost.tasktracker.service.interfaces.ProjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Resource
    private ProjectRepository projectRepository;
    @Resource
    private BusinessMapper mapper;

    @Override
    public ProjectDTO addProject(ProjectDTO dto) {
        if (dto == null) {
            throw new ProjectException("income object can't be null");
        }
        dto.setState(State.NEW);
        dto.setStartDate(LocalDate.now());
        Project project = projectRepository.save(mapper.toProject(dto));
        return mapper.toDTO(project);
    }

    @Override
    public ProjectDTO getProjectById(int id) throws ProjectException {
        return mapper.toDTO(projectRepository.findById(id).orElseThrow(() -> new ProjectException("No such project")));
    }

    @Override
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void saveProject(ProjectDTO dto) {
        projectRepository.save(mapper.toProject(dto));
    }

    @Override
    public boolean deleteProject(int id) {
        projectRepository.delete(projectRepository.findById(id).orElseThrow(() -> new ProjectException("can't find object to delete")));
        return true;
    }
}
