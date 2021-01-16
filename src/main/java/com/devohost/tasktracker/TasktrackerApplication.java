package com.devohost.tasktracker;

import com.devohost.tasktracker.entities.*;
import com.devohost.tasktracker.entities.Module;
import com.devohost.tasktracker.entities.enums.UserRole;
import com.devohost.tasktracker.repositories.*;
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


//    @Bean
//    CommandLineRunner init(UserRepository userRepository, TrackerRepository trackerRepository, ProjectRepository projectRepository,
//                           ProjectPhaseRepository phaseRepository, ModuleRepository moduleRepository, TaskRepository taskRepository){
//        return args -> {
//            User user = new User("adminemail2", "password");
//            user.setRole(UserRole.USER);
//            user.setRegistrationDate(LocalDate.now());
//            userRepository.save(user);
//            Tracker tracker = new Tracker();
//            user.setTracker(tracker);
//            trackerRepository.save(tracker);
//            userRepository.save(user);
//            Project project = new Project();
//            tracker.addProject(project);
//            projectRepository.save(project);
//            trackerRepository.save(tracker);
//            ProjectPhase projectPhase = new ProjectPhase();
//            project.addPhase(projectPhase);
//            project.addUser(user, UserRole.ADMIN);
//            phaseRepository.save(projectPhase);
//            projectRepository.save(project);
//            Module module = new Module();
//            projectPhase.addModule(module);
//            moduleRepository.save(module);
//            phaseRepository.save(projectPhase);
//            Task task = new Task();
//            module.addTask(task);
//            taskRepository.save(task);
//            moduleRepository.save(module);
//        };
//    }
}