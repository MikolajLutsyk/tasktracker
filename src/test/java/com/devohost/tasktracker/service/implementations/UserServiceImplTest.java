package com.devohost.tasktracker.service.implementations;

import com.devohost.tasktracker.dto.BusinessMapper;
import com.devohost.tasktracker.dto.TrackerDTO;
import com.devohost.tasktracker.dto.UserDTO;
import com.devohost.tasktracker.exceptions.UserException;
import com.devohost.tasktracker.service.interfaces.TrackerService;
import com.devohost.tasktracker.service.interfaces.UserService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceImplTest {
    @Resource
    private UserService service;
    @Resource
    private TrackerService trackerService;
    @Resource
    private BusinessMapper mapper;


    @Test
    @Order(1)
    public void userCreationTest(){
        UserDTO userDTO = UserDTO.builder()
                .email("email")
                .password("password")
                .build();
        UserDTO returnedDTO = service.addUser(userDTO);
        assertEquals(1, returnedDTO.getId());
    }

    @Test
    @Order(2)
    public void getUserByIdTest(){
        assertEquals(1, service.getUserById(1).getId());
    }

    @Test
    @Order(3)
    public void getAllUsersTest(){
        List<UserDTO> userList = service.getAllUsers();
        assertEquals(1, userList.get(0).getId());
    }

    @Test
    @Order(4)
    public void updateUserTest(){
        UserDTO dto = service.getUserById(1);
        dto.setEmail("new email");
        service.saveUser(dto);
        assertEquals("new email", service.getUserById(1).getEmail());
    }

    @Test
    @Order(5)
    public void deleteUserTest(){
        assertEquals(true, service.deleteUser(1));

        Exception exception = assertThrows(UserException.class, () -> {
            service.getUserById(1);
        });
    }
}