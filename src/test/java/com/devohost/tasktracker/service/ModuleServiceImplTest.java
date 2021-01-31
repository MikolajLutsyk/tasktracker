package com.devohost.tasktracker.service;

import com.devohost.tasktracker.dto.ModuleDTO;
import com.devohost.tasktracker.entities.enums.State;
import com.devohost.tasktracker.exceptions.ModuleException;
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
class ModuleServiceImplTest {
    @Resource
    private ModuleService moduleService;

    @Test
    @Order(1)
    public void moduleCreationTest(){
        ModuleDTO dto = new ModuleDTO();
        ModuleDTO returnedDto = moduleService.addModule(dto);
        assertEquals(1, returnedDto.getId());
    }

    @Test
    @Order(2)
    public void getModuleByIdTest(){
        ModuleDTO moduleDTO = moduleService.getModuleById(1);
        assertEquals(1, moduleDTO.getId());
    }

    @Test
    @Order(3)
    public void updateModuleTest(){
        ModuleDTO moduleDTO = moduleService.getModuleById(1);
        moduleDTO.setName("RandomName");
        moduleService.saveModule(moduleDTO);
        ModuleDTO updatedDTO = moduleService.getModuleById(1);
        assertEquals("RandomName", updatedDTO.getName());
    }

    @Test
    @Order(4)
    public void getAllModulesTest(){
        ModuleDTO dto = moduleService.addModule(ModuleDTO.builder()
                .state(State.NEW.toString())
                .name("SomeName1")
                .build());
        ModuleDTO dto2 = moduleService.addModule(ModuleDTO.builder()
                .state(State.IN_PROGRESS.toString())
                .name("SomeName2")
                .build());
        ModuleDTO dto3 = moduleService.addModule(ModuleDTO.builder()
                .state(State.POSTPONED.toString())
                .name("SomeName3")
                .build());
        List<ModuleDTO> dtoList = moduleService.getAllModules();

        assertEquals("RandomName", dtoList.get(0).getName());
        assertEquals("SomeName1", dtoList.get(1).getName());
    }

    @Test
    @Order(5)
    public void deleteModuleTest(){
        assertEquals(true, moduleService.deleteModule(1));

        Exception exception = assertThrows(ModuleException.class, () -> {
           moduleService.getModuleById(1);
        });
    }
}