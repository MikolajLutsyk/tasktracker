package com.devohost.tasktracker.service.interfaces;

import com.devohost.tasktracker.dto.TrackerDTO;
import com.devohost.tasktracker.exceptions.TrackerException;

import java.util.List;

public interface TrackerService {
    TrackerDTO addTracker(TrackerDTO dto);
    TrackerDTO getTrackerById(int id) throws TrackerException;
    List<TrackerDTO> getAllTrackers();
    void saveTracker(TrackerDTO dto);
    boolean deleteTracker(int id);
}
