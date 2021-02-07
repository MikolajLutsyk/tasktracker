package com.devohost.tasktracker.service;

import com.devohost.tasktracker.dto.ProjectPhaseDTO;
import com.devohost.tasktracker.exceptions.ProjectPhaseException;
import com.devohost.tasktracker.service.interfaces.ProjectPhaseService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectPhaseServiceImplTest {

    @Resource
    private ProjectPhaseService service;

    @Test
    @Order(1)
    public void createProjectPhaseTest(){
        ProjectPhaseDTO dto = ProjectPhaseDTO.builder()
                .number(1)
                .name("new name")
                .deadline(LocalDate.now())
                .modules(new ArrayList<>())
                .build();
        ProjectPhaseDTO returnedDTO = service.addProjectPhase(dto);
        assertEquals(1, returnedDTO.getId());
    }

    @Test
    @Order(2)
    public void getProjectPhaseByIdTest(){
        assertEquals("new name", service.getProjectPhaseById(1).getName());
        assertEquals(1, service.getProjectPhaseById(1).getNumber());

    }

    @Test
    @Order(3)
    public void getAllProjectPhases(){
        ProjectPhaseDTO dto2 = service.addProjectPhase(ProjectPhaseDTO.builder()
                .number(2)
                .name("new name 2")
                .deadline(LocalDate.now())
                .modules(new ArrayList<>())
                .build());
        ProjectPhaseDTO dto3 = service.addProjectPhase(ProjectPhaseDTO.builder()
                .number(3)
                .name("new name 3")
                .deadline(LocalDate.now())
                .modules(new ArrayList<>())
                .build());
        ProjectPhaseDTO dto4 = service.addProjectPhase(ProjectPhaseDTO.builder()
                .number(4)
                .name("new name 4")
                .deadline(LocalDate.now())
                .modules(new ArrayList<>())
                .build());
        List<ProjectPhaseDTO> phaseDTOS = service.getAllProjectPhases();

        assertEquals("new name 2", phaseDTOS.get(1).getName());
        assertEquals("new name 3", phaseDTOS.get(2).getName());
        assertEquals("new name 4", phaseDTOS.get(3).getName());
    }

    @Test
    @Order(4)
    public void updateProjectPhaseTest(){
        ProjectPhaseDTO dto = service.getProjectPhaseById(1);
        dto.setNumber(10);
        service.saveProjectPhase(dto);
        assertEquals(10, service.getProjectPhaseById(1).getNumber());
    }

    @Test
    @Order(5)
    public void deleteProjectPhaseTest(){
        assertEquals(true, service.deleteProjectPhase(1));

        Exception exception = assertThrows(ProjectPhaseException.class, () -> {
            service.getProjectPhaseById(1);
        });
    }
}