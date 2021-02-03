package com.devohost.tasktracker.thymeleaf_controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class ProjectPhaseDashboard {
    @GetMapping(value = "/project-phases")
    public ModelAndView showTasks() {
        ModelAndView model = new ModelAndView("project-phases");
        return model;
    }
}
