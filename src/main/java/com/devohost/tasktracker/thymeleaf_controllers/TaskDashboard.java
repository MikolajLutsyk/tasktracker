package com.devohost.tasktracker.thymeleaf_controllers;

import com.devohost.tasktracker.dto.TaskDTO;
import com.devohost.tasktracker.entities.enums.State;
import com.devohost.tasktracker.entities.enums.TaskPriority;
import com.devohost.tasktracker.forms.TaskFormCommand;
import com.devohost.tasktracker.service.interfaces.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class TaskDashboard {

    @Resource
    TaskService taskService;

    @GetMapping(value = "/tasks")
    public ModelAndView showTasks() {
        ModelAndView model = new ModelAndView("tasks");
        model.addObject("allTasks", taskService.getAllTasks());
        return model;
    }

    @PostMapping(value = "/task/create")
    public ModelAndView addTask(@ModelAttribute("taskForm") @Valid TaskFormCommand taskFormCommand, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("task-add", bindingResult.getModel());
            model.addObject("states", State.values());
            model.addObject("priorities", TaskPriority.values());
            return model;
        }
        taskService.addTask(
                TaskDTO.builder()
                        .taskContent(taskFormCommand.getTaskContent())
                        .points(taskFormCommand.getPoints())
                        .deadline(LocalDate.parse(taskFormCommand.getDeadline(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .build());

        ModelAndView model = new ModelAndView("tasks");
        model.addObject("taskForm", new TaskFormCommand());
        model.addObject("allTasks", taskService.getAllTasks());

        return model;
    }

    @GetMapping(value = "/task/delete/{id}")
    public String deleteTask(@PathVariable(name = "id") String id) {
        taskService.deleteTask(Integer.parseInt(id));
        return "redirect:/tasks";
    }

    @GetMapping(value = "/task/add")
    public ModelAndView addPageRedirect(){
        ModelAndView model = new ModelAndView("task-add");
        model.addObject("taskForm", new TaskFormCommand());
        return model;
    }

    @GetMapping(value = "/task/edit/{id}")
    public ModelAndView editTask(@PathVariable(name = "id") String id) {
        ModelAndView model = new ModelAndView("task-edit");
        TaskDTO dto = taskService.getTaskById(Integer.parseInt(id));

        TaskFormCommand taskFormCommand = TaskFormCommand.builder()
                .id(dto.getId())
                .deadline(dto.getDeadline().toString())
                .points(dto.getPoints())
                .taskContent(dto.getTaskContent())
                .state(dto.getState())
                .priority(dto.getPriority())
                .build();
        model.addObject("someTask", taskFormCommand);
        model.addObject("states", State.values());
        model.addObject("priorities", TaskPriority.values());
        return model;
    }

    @PostMapping(value = "/api/task/update")
    public ModelAndView updateTask(@ModelAttribute("someTask") @Valid TaskFormCommand taskFormCommand, BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("task-edit", bindingResult.getModel());
            model.addObject("states", State.values());
            model.addObject("priorities", TaskPriority.values());
            return model;
        }

        taskService.saveTask(TaskDTO.builder()
                .id(taskFormCommand.getId())
                .taskContent(taskFormCommand.getTaskContent())
                .points(taskFormCommand.getPoints())
                .deadline(LocalDate.parse(taskFormCommand.getDeadline(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .state(taskFormCommand.getState())
                .priority(taskFormCommand.getPriority())
                .build());

        ModelAndView model = new ModelAndView("tasks");
        model.addObject("taskForm", new TaskFormCommand());
        model.addObject("allTasks", taskService.getAllTasks());

        return model;
    }

    //TODO create uer sign up method
}