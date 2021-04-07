package com.devohost.tasktracker.thymeleaf_controllers;

import com.devohost.tasktracker.dto.ModuleDTO;
import com.devohost.tasktracker.dto.ProjectDTO;
import com.devohost.tasktracker.dto.ProjectPhaseDTO;
import com.devohost.tasktracker.dto.UserDTO;
import com.devohost.tasktracker.forms.ModuleFormCommand;
import com.devohost.tasktracker.forms.ProjectPhaseFormCommand;
import com.devohost.tasktracker.service.interfaces.ModuleService;
import com.devohost.tasktracker.service.interfaces.ProjectPhaseService;
import com.devohost.tasktracker.service.interfaces.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class ModulesDashboard {
    @Resource
    private ModuleService moduleService;
    @Resource
    private ProjectPhaseService projectPhaseService;
    @Resource
    private UserService userService;


    @GetMapping("/modules/{projectId}/{phaseId}")
    public ModelAndView openPage(@PathVariable(value = "phaseId") String phaseId, @PathVariable(value = "projectId") String projectId){
        UserDTO userDTO = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<ProjectDTO> projectDTOS = userDTO.getTracker().getUserProjects();
        for (ProjectDTO project : projectDTOS){
            if (project.getId() == Integer.parseInt(projectId)){
                for (ProjectPhaseDTO phase : project.getProjectPhases()){
                    if (phase.getId() == Integer.parseInt(phaseId)){
                        ModelAndView model = new ModelAndView("modules");
                        model.addObject("phaseModules", projectPhaseService.getProjectPhaseById(Integer.parseInt(phaseId)).getModules());
                        model.addObject("projectId", projectId);
                        return model;
                    }
                }
            }
        }
        ModelAndView model = new ModelAndView("projects");
        model.addObject("userInfo", userDTO);
        model.addObject("allProjects", userDTO.getTracker().getUserProjects());
        return model;
    }

    @GetMapping("/module/add/{projectId}/{phaseId}")
    public ModelAndView openAddPage(@PathVariable(value = "phaseId") String phaseId,
                                    @PathVariable(value = "projectId") String projectId){
        ModelAndView model = new ModelAndView("module-add");
        model.addObject("moduleForm", new ModuleFormCommand());
        model.addObject("projectId", projectId);
        model.addObject("phaseId", phaseId);
        return model;
    }

    @PostMapping("/module/add/{projectId}/{phaseId}")
    public ModelAndView addModule(@PathVariable(value = "phaseId") String phaseId,
                                    @PathVariable(value = "projectId") String projectId,
                                    @ModelAttribute("moduleForm") ModuleFormCommand moduleFormCommand,
                                    BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            ModelAndView model = new ModelAndView("module", bindingResult.getModel());
            return model;
        }
        ModuleDTO someModule = moduleService.addModule(ModuleDTO.builder()
                .name(moduleFormCommand.getName())
                .build());
        ProjectPhaseDTO projectPhaseDTO = projectPhaseService.getProjectPhaseById(Integer.parseInt(phaseId));
        projectPhaseDTO.addModule(someModule);
        projectPhaseService.saveProjectPhase(projectPhaseDTO);

        ModelAndView model = new ModelAndView("modules");
        model.addObject("moduleForm", new ProjectPhaseFormCommand());
        model.addObject("projectId", projectId);
        model.addObject("phaseId", phaseId);
        model.addObject("allModules", projectPhaseService.getProjectPhaseById(Integer.parseInt(phaseId)).getModules());

        return model;
    }
}
