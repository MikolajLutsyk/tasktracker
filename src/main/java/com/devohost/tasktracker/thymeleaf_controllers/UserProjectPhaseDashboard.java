package com.devohost.tasktracker.thymeleaf_controllers;

import com.devohost.tasktracker.dto.ProjectDTO;
import com.devohost.tasktracker.dto.ProjectPhaseDTO;
import com.devohost.tasktracker.dto.UserDTO;
import com.devohost.tasktracker.forms.ProjectPhaseFormCommand;
import com.devohost.tasktracker.service.interfaces.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class UserProjectPhaseDashboard {
    @Resource
    private ProjectPhaseService projectPhaseService;
    @Resource
    private ProjectService projectService;
    @Resource
    private UserService userService;

    @GetMapping(value = "/phases/{id}")
    public ModelAndView openPage(@PathVariable(name = "id") String id) {
        UserDTO userDTO = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<ProjectDTO> projectDTOS = userDTO.getTracker().getUserProjects();
        for (ProjectDTO project: projectDTOS){
            if(project.getId() == Integer.parseInt(id)){
                ModelAndView model = new ModelAndView("phases");
                model.addObject("allPhases", projectService.getProjectById(Integer.parseInt(id)).getProjectPhases());
                model.addObject("projectId", id);
                return model;
            }
        }
        ModelAndView model = new ModelAndView("projects");
        model.addObject("userInfo", userDTO);
        model.addObject("allProjects", userDTO.getTracker().getUserProjects());
        return model;
    }

    @GetMapping("/phase/add/{id}")
    public ModelAndView openAddPhasePage(@PathVariable(name = "id") String id) {
        ModelAndView model = new ModelAndView("phase-add");
        model.addObject("phaseForm", new ProjectPhaseFormCommand());
        model.addObject("projectId", id);
        return model;
    }

    @PostMapping("/phase/add/{id}")
    public ModelAndView addPhase(@PathVariable(name = "id") String id,
                                 @ModelAttribute("phaseForm") @Valid ProjectPhaseFormCommand projectPhaseFormCommand,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("phase-add", bindingResult.getModel());
            return model;
        }
        ProjectPhaseDTO someProjectPhase = projectPhaseService.addProjectPhase(
                ProjectPhaseDTO.builder()
                        .name(projectPhaseFormCommand.getName())
                        .number(projectPhaseFormCommand.getNumber())
                        .deadline(LocalDate.parse(projectPhaseFormCommand.getDeadline(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .build());

        ProjectDTO projectDTO = projectService.getProjectById(Integer.parseInt(id));
        projectDTO.addPhase(someProjectPhase);
        projectService.saveProject(projectDTO);

        ModelAndView model = new ModelAndView("phases");
        model.addObject("phaseForm", new ProjectPhaseFormCommand());
        model.addObject("projectId", id);
        model.addObject("allPhases", projectService.getProjectById(Integer.parseInt(id)).getProjectPhases());

        return model;
    }

    @GetMapping("/phase/delete/{projectId}/{phaseId}")
    public String deleteProject(@PathVariable(value = "projectId") String projectId, @PathVariable(value = "phaseId") String phaseId){
        UserDTO userDTO = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        ProjectDTO projectDTO = projectService.getProjectById(Integer.parseInt(projectId));

        projectDTO.getProjectPhases().remove(projectPhaseService.getProjectPhaseById(Integer.parseInt(phaseId)));
        projectService.saveProject(projectDTO);
        projectPhaseService.deleteProjectPhase(Integer.parseInt(phaseId));

        return "redirect:/phases/" + projectId;
    }
}