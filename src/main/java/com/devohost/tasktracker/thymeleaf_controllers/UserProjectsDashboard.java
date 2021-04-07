package com.devohost.tasktracker.thymeleaf_controllers;


import com.devohost.tasktracker.dto.ProjectDTO;
import com.devohost.tasktracker.dto.TrackerDTO;
import com.devohost.tasktracker.dto.UserDTO;
import com.devohost.tasktracker.forms.ProjectFormCommand;
import com.devohost.tasktracker.service.interfaces.ProjectService;
import com.devohost.tasktracker.service.interfaces.TrackerService;
import com.devohost.tasktracker.service.interfaces.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class UserProjectsDashboard {
    @Resource
    private ProjectService projectService;
    @Resource
    private UserService userService;
    @Resource
    private TrackerService trackerService;

    @GetMapping("/projects")
    public ModelAndView openPage() {

        UserDTO userDTO = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());


        ModelAndView model = new ModelAndView("projects");
        model.addObject("userInfo", userDTO);
        model.addObject("allProjects", userDTO.getTracker().getUserProjects());
        return model;
    }

    @GetMapping("/project/add")
    public ModelAndView openAddPage() {
        ModelAndView model = new ModelAndView("project-add");
        model.addObject("projectForm", new ProjectFormCommand());
        return model;
    }


    @PostMapping("/project/add")
    public ModelAndView addProject(@ModelAttribute("projectForm") @Valid ProjectFormCommand projectFormCommand, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("project-add", bindingResult.getModel());
            return model;
        }
        ProjectDTO someProject = projectService.addProject(
                ProjectDTO.builder()
                        .name(projectFormCommand.getName())
                        .deadline(LocalDate.parse(projectFormCommand.getDeadline(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .build());

        UserDTO userDTO = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        TrackerDTO trackerDTO = userDTO.getTracker();
        trackerDTO.addProject(someProject);
        trackerService.saveTracker(trackerDTO);

        ModelAndView model = new ModelAndView("projects");
        model.addObject("projectForm", new ProjectFormCommand());
        model.addObject("allProjects", trackerDTO.getUserProjects());
        model.addObject("userInfo", userDTO);

        return model;
    }

    @GetMapping("/project/delete/{id}")
    public String deleteProject(@PathVariable(value = "id") String id){
        UserDTO userDTO = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        TrackerDTO trackerDTO = userDTO.getTracker();

        trackerDTO.getUserProjects().remove(projectService.getProjectById(Integer.parseInt(id)));
        trackerService.saveTracker(trackerDTO);
        projectService.deleteProject(Integer.parseInt(id));

        return "redirect:/projects";
    }
}