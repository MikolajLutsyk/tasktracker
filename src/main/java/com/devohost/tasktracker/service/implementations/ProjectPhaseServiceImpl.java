package com.devohost.tasktracker.service.implementations;

import com.devohost.tasktracker.dto.BusinessMapper;
import com.devohost.tasktracker.dto.ProjectPhaseDTO;
import com.devohost.tasktracker.entities.ProjectPhase;
import com.devohost.tasktracker.entities.enums.State;
import com.devohost.tasktracker.exceptions.ModuleException;
import com.devohost.tasktracker.exceptions.ProjectPhaseException;
import com.devohost.tasktracker.repositories.ProjectPhaseRepository;
import com.devohost.tasktracker.service.interfaces.ProjectPhaseService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectPhaseServiceImpl implements ProjectPhaseService {

    @Resource
    private ProjectPhaseRepository projectPhaseRepository;
    @Resource
    private BusinessMapper mapper;


    @Override
    public ProjectPhaseDTO addProjectPhase(ProjectPhaseDTO dto) {
        if (dto == null){
            throw new ModuleException("income object is null");
        }
        dto.setState(State.NEW);
        dto.setStartDate(LocalDate.now());
        ProjectPhase projectPhase = projectPhaseRepository.save(mapper.toProjectPhase(dto));
        return mapper.toDTO(projectPhase);
    }

    @Override
    public ProjectPhaseDTO getProjectPhaseById(int id) throws ProjectPhaseException {
        return mapper.toDTO(projectPhaseRepository.findById(id).orElseThrow(() -> new ProjectPhaseException("ProjectPhase not found")));
    }

    @Override
    public List<ProjectPhaseDTO> getAllProjectPhases() {
        return projectPhaseRepository.findAll().stream()
                .map(x -> mapper.toDTO(x))
                .collect(Collectors.toList());
    }

    @Override
    public void saveProjectPhase(ProjectPhaseDTO dto) {
        if (!dto.getState().equals(projectPhaseRepository.findById(dto.getId()).get().getState())){
            dto.stateHistoryPut(LocalDate.now(), dto.getState());
        }
        projectPhaseRepository.save(mapper.toProjectPhase(dto));
    }

    @Override
    public boolean deleteProjectPhase(int id) {
        projectPhaseRepository.delete(projectPhaseRepository.findById(id).orElseThrow(() -> new ProjectPhaseException(("ProjectPhaseNotDeleted"))));
        return true;
    }
}