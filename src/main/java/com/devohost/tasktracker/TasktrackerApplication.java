package com.devohost.tasktracker;

import com.devohost.tasktracker.dto.ProjectDTO;
import com.devohost.tasktracker.dto.TaskDTO;
import com.devohost.tasktracker.dto.TrackerDTO;
import com.devohost.tasktracker.dto.UserDTO;
import com.devohost.tasktracker.entities.*;
import com.devohost.tasktracker.repositories.*;
import com.devohost.tasktracker.service.interfaces.ProjectService;
import com.devohost.tasktracker.service.interfaces.TaskService;
import com.devohost.tasktracker.service.interfaces.TrackerService;
import com.devohost.tasktracker.service.interfaces.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.time.LocalDate;

@SpringBootApplication
public class TasktrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TasktrackerApplication.class, args);
    }


    @Bean
    CommandLineRunner init(UserService userService, RoleRepository roleRepository, TrackerRepository trackerRepository, ProjectRepository projectRepository,
                           ProjectPhaseRepository phaseRepository, ModuleRepository moduleRepository, TaskRepository taskRepository, TaskService taskService, ProjectService projectService, TrackerService trackerService){
        return args -> {

            taskService.addTask(TaskDTO.builder().deadline(LocalDate.now().plusDays(2)).points(10).taskContent("task 1").build());
            taskService.addTask(TaskDTO.builder().deadline(LocalDate.now().plusDays(2)).points(10).taskContent("task 2").build());
            taskService.addTask(TaskDTO.builder().deadline(LocalDate.now().plusDays(2)).points(10).taskContent("task 3").build());
            taskService.addTask(TaskDTO.builder().deadline(LocalDate.now().plusDays(2)).points(10).taskContent("task 4").build());

            Role role = new Role(0, "ADMIN");
            Role role2 = new Role(0, "USER");
            Role role3 = new Role(0, "MANAGER");
            roleRepository.save(role);
            roleRepository.save(role2);
            roleRepository.save(role3);

            UserDTO userDTO = UserDTO.builder().firstname("Mykola").lastname("Lutsyk").email("email@gmail.com").password("1").role(role2.getName()).build();
            userService.addUser(userDTO);

            ProjectDTO someProject = projectService.addProject(
                    ProjectDTO.builder()
                            .name("project")
                            .deadline(LocalDate.now())
                            .build());

            TrackerDTO trackerDTO = userDTO.getTracker();
            trackerDTO.addProject(someProject);
            trackerService.saveTracker(trackerDTO);
        };
    }
}