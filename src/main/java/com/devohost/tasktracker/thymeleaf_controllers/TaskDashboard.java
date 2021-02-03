package com.devohost.tasktracker.thymeleaf_controllers;

import com.devohost.tasktracker.dto.TaskDTO;
import com.devohost.tasktracker.forms.TaskFormCommand;
import com.devohost.tasktracker.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class TaskDashboard {

    @Resource
    TaskService taskService;

    @GetMapping(value = "/tasks")
    public ModelAndView showTasks() {
        ModelAndView model = new ModelAndView("tasks");
        model.addObject("taskForm", new TaskFormCommand());
        model.addObject("allTasks", taskService.getAllTasks());
        return model;
    }

    @PostMapping(value = "/task/create")
    public String addTask(@ModelAttribute("taskForm") TaskFormCommand taskFormCommand){
        taskService.addTask(
                TaskDTO.builder()
                        .taskContent(taskFormCommand.getTaskContent())
                        .points(taskFormCommand.getPoints())
                        .deadline(LocalDate.parse(taskFormCommand.getDeadline(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .build());
        return"redirect:/tasks";
    }

    @GetMapping(value = "/task/delete/{id}")
    public String deleteTask(@PathVariable(name = "id") String id){
        taskService.deleteTask(Integer.parseInt(id));
        return"redirect:/tasks";
    }
}