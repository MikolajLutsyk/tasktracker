package com.devohost.tasktracker.service.implementations;

import com.devohost.tasktracker.dto.BusinessMapper;
import com.devohost.tasktracker.dto.ProjectDTO;
import com.devohost.tasktracker.dto.TrackerDTO;
import com.devohost.tasktracker.entities.Tracker;
import com.devohost.tasktracker.exceptions.TrackerException;
import com.devohost.tasktracker.repositories.TrackerRepository;
import com.devohost.tasktracker.service.interfaces.TrackerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class TrackerServiceImpl implements TrackerService {

    @Resource
    private TrackerRepository repository;
    @Resource
    private BusinessMapper mapper;

    @Override
    public TrackerDTO addTracker(TrackerDTO dto) {
        if (dto == null){
            throw new TrackerException("income tracker object is null");
        }
        dto.setUserTasks(new ArrayList<>());
        dto.setUserProjects(new ArrayList<>());
        Tracker tracker = repository.save(mapper.toTracker(dto));
        return mapper.toDTO(tracker);
    }

    @Override
    public TrackerDTO getTrackerById(int id) throws TrackerException {
        return mapper.toDTO(repository.findById(id).orElseThrow(() -> new TrackerException("Tracker not found")));
    }

    @Override
    public List<TrackerDTO> getAllTrackers() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void saveTracker(TrackerDTO dto) {
        repository.save(mapper.toTracker(dto));
    }

    @Override
    public boolean deleteTracker(int id) {
        repository.delete(repository.findById(id).orElseThrow(() -> new TrackerException("Can't find tracker to delete")));
        return true;
    }
}
