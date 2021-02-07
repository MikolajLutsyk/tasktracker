package com.devohost.tasktracker.service;

import com.devohost.tasktracker.dto.ProjectDTO;
import com.devohost.tasktracker.service.interfaces.ProjectService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectServiceImplTest {
    @Resource
    private ProjectService service;

    @Test
    @Order(1)
    public void projectCreationTest(){
        ProjectDTO dto = ProjectDTO.builder()
                .deadline(LocalDate.now())
                .name("test name")
                .projectPhases(new ArrayList<>())
                .participants(new HashMap<>())
                .build();
        ProjectDTO returnedDTO = service.addProject(dto);
        assertEquals(1, returnedDTO.getId());
        assertEquals("test name", returnedDTO.getName());

        assertEquals("test name", service.getProjectById(1).getName());

        List<ProjectDTO> projectDTOS = service.getAllProjects();
        assertEquals("test name", projectDTOS.get(0).getName());
        System.out.println(service.getProjectById(1).getName() + " 1");
    }

    @Test
    @Order(2)
    public void getProjectByIdTest(){
        System.out.println(service.getProjectById(1).getName() + " 2");
        assertEquals("test name", service.getProjectById(1).getName());
    }

    @Test
    @Order(3)
    public void getAllProjectsTest(){
        List<ProjectDTO> projectDTOS = service.getAllProjects();
        assertEquals("test name", projectDTOS.get(0).getName());
    }

    @Test
    @Order(4)
    public void updateProjectTest(){

    }

    @Test
    @Order(5)
    public void deleteProjectTest(){

    }
}