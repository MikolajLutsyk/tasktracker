package com.devohost.tasktracker.service.implementations;

import com.devohost.tasktracker.dto.ProjectDTO;
import com.devohost.tasktracker.dto.UserDTO;
import com.devohost.tasktracker.entities.enums.UserRole;
import com.devohost.tasktracker.exceptions.ProjectException;
import com.devohost.tasktracker.service.interfaces.ProjectService;
import com.devohost.tasktracker.service.interfaces.TrackerService;
import com.devohost.tasktracker.service.interfaces.UserService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectServiceImplTest {
    @Resource
    private ProjectService service;
    @Resource
    private UserService userService;
    @Resource
    private TrackerService trackerService;

    @Test
    @Order(1)
    public void projectCreationTest(){
        ProjectDTO dto = ProjectDTO.builder()
                .name("name")
                .deadline(LocalDate.now())
                .build();
        ProjectDTO returnedDTO = service.addProject(dto);
        assertEquals(1, returnedDTO.getId());
        assertEquals("name", returnedDTO.getName());
    }

    @Test
    @Order(2)
    public void getProjectByIdTest(){
        assertEquals("name", service.getProjectById(1).getName());
    }

    @Test
    @Order(2)
    public void getAllProjectsTest(){
        List<ProjectDTO> dtoList = service.getAllProjects();
        assertEquals("name", dtoList.get(0).getName());
    }

    @Test
    @Order(3)
    public void updateProjectTest(){
        ProjectDTO dto = service.getProjectById(1);
        UserDTO userDTO = userService.addUser(UserDTO.builder()
                .email("email")
                .password("password")
                .firstname("firstname")
                .lastname("lastname")
                .build());
        dto.addUser(userService.addUser(userDTO), UserRole.USER);
        service.saveProject(dto);
        assertEquals(UserRole.USER, service.getProjectById(1).getParticipants().get(userDTO));
    }

    @Test
    public void deleteProjectTest(){
        assertEquals(true, service.deleteProject(1));

        Exception exception = assertThrows(ProjectException.class, () -> {
            service.getProjectById(1);
        });
    }
}