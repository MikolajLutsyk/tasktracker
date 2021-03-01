package com.devohost.tasktracker.service.implementations;

import com.devohost.tasktracker.dto.TaskDTO;
import com.devohost.tasktracker.dto.TrackerDTO;
import com.devohost.tasktracker.exceptions.TrackerException;
import com.devohost.tasktracker.service.interfaces.TaskService;
import com.devohost.tasktracker.service.interfaces.TrackerService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TrackerServiceImplTest {
    @Resource
    private TrackerService service;
    @Resource
    private TaskService taskService;

    @Test
    @Order(1)
    public void trackerCreationTest(){
        TrackerDTO tracker = new TrackerDTO();
        TrackerDTO returnedDTO = service.addTracker(tracker);
        assertEquals(1, returnedDTO.getId());
    }

    @Test
    @Order(2)
    public void getTrackerByIdTest(){
        assertEquals(1, service.getTrackerById(1).getId());
    }

    @Test
    @Order(3)
    public void getAllTrackers(){
        List<TrackerDTO> trackerList = service.getAllTrackers();
        assertEquals(1, trackerList.get(0).getId());
    }

    @Test
    @Order(4)
    public void updateServiceTest(){
        TrackerDTO tracker = service.getTrackerById(1);
        tracker.addUserTask(taskService.addTask(TaskDTO.builder()
                .taskContent("content")
                .build()));
        service.saveTracker(tracker);
        assertEquals("content", service.getTrackerById(1).getUserTasks().get(0).getTaskContent());
    }

    @Test
    @Order(5)
    public void deleteTrackerTest(){
        assertEquals(true, service.deleteTracker(1));

        Exception exception = assertThrows(TrackerException.class, () -> {
            service.getTrackerById(1);
        });
    }
}