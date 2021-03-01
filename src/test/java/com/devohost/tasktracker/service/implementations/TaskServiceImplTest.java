package com.devohost.tasktracker.service.implementations;

import com.devohost.tasktracker.dto.TaskDTO;
import com.devohost.tasktracker.exceptions.TaskException;
import com.devohost.tasktracker.service.interfaces.TaskService;
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
class TaskServiceImplTest {
    @Resource
    private TaskService service;

    @Test
    @Order(1)
    public void taskCreationTest(){
        TaskDTO dto = TaskDTO.builder()
                .taskContent("Content")
                .points(12)
                .deadline(LocalDate.now())
                .build();
       TaskDTO returnedDto =  service.addTask(dto);
        assertEquals(1, returnedDto.getId());
    }

    @Test
    @Order(2)
    public void getByIdTest(){
        TaskDTO taskDTO = service.getTaskById(1);
        assertEquals(1, taskDTO.getId());
    }



    @Test
    @Order(3)
    public void updateTest(){
        TaskDTO taskDTO = service.getTaskById(1);
        taskDTO.setPoints(1234);
        service.saveTask(taskDTO);
        TaskDTO updatedTaskDTO = service.getTaskById(1);
        assertEquals(1234, updatedTaskDTO.getPoints());
    }

    @Test
    @Order(4)
    public void getAllTest(){
        TaskDTO dto =  service.addTask(TaskDTO.builder()
                .taskContent("Content 2")
                .points(4132)
                .deadline(LocalDate.now())
                .build());
        TaskDTO dto1 =  service.addTask(TaskDTO.builder()
                .taskContent("Content 3")
                .points(5243)
                .deadline(LocalDate.now())
                .build());
        TaskDTO dto2 =  service.addTask(TaskDTO.builder()
                .taskContent("Content 4")
                .points(6543)
                .deadline(LocalDate.now())
                .build());
        List<TaskDTO> dtoList = service.getAllTasks();
        assertEquals("Content 3", dtoList.get(2).getTaskContent());
        assertEquals("Content 4", dtoList.get(3).getTaskContent());
    }

    @Test
    @Order(5)
    public void deleteTaskTest(){

        assertEquals(true, service.deleteTask(4));

        Exception exception = assertThrows(TaskException.class, () -> {
            service.getTaskById(4);
        });
    }
}