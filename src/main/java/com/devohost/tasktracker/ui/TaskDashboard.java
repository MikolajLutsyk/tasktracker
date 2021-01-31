package com.devohost.tasktracker.ui;

import com.devohost.tasktracker.forms.TaskFormCommand;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TaskDashboard {
    @GetMapping(value = "/tasks")
    public ModelAndView showTasks() {
        ModelAndView model = new ModelAndView("tasks");
        model.addObject("taskForm", new TaskFormCommand());

        return model;
    }
}
