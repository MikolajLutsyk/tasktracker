package com.devohost.tasktracker.angular_controllers;

import com.devohost.tasktracker.dto.ModuleDTO;
import com.devohost.tasktracker.dto.ProjectPhaseDTO;
import com.devohost.tasktracker.exceptions.ModuleException;
import com.devohost.tasktracker.forms.ModuleFormCommand;
import com.devohost.tasktracker.forms.ProjectPhaseFormCommand;
import com.devohost.tasktracker.service.interfaces.ModuleService;
import com.devohost.tasktracker.service.interfaces.ProjectPhaseService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/projectphase")
public class ProjectPhaseController {

    @Resource
    private ProjectPhaseService projectPhaseService;
    @Resource
    private ModuleService moduleService;
    String dateFormat = "dd.MM.yyyy";

    @PostMapping("/add")
    public ProjectPhaseDTO addProjectPhaseDTO(@RequestBody @Valid ProjectPhaseFormCommand projectPhaseFormCommand){
        return projectPhaseService.addProjectPhase(ProjectPhaseDTO.builder()
                .name(projectPhaseFormCommand.getName())
                .deadline(LocalDate.parse(projectPhaseFormCommand.getDeadline(), DateTimeFormatter.ofPattern(dateFormat)))
                .number(projectPhaseFormCommand.getNumber())
                .modules(new ArrayList<>())
                .build());
    }

    @GetMapping("{id}")
    public ProjectPhaseDTO getProjectPhaseById(@PathVariable(name = "id") String id){
        return projectPhaseService.getProjectPhaseById(Integer.parseInt(id));
    }

    @GetMapping("getall")
    public List<ProjectPhaseDTO> getAllProjectPhases(){
        return projectPhaseService.getAllProjectPhases();
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteProjectPhase(@PathVariable(name = "id") String id){
        projectPhaseService.deleteProjectPhase(Integer.parseInt(id));
        return true;
    }

    @PutMapping("/update")
    public void updateProjectPhase(@RequestBody @Valid ProjectPhaseDTO dto){
        projectPhaseService.saveProjectPhase(dto);
    }

    @PostMapping("/{id}/addmodule")
    public ProjectPhaseDTO addModuleToProjectPhase(@PathVariable(name = "id") String id,@RequestBody @Valid ModuleFormCommand moduleFormCommand){
        ProjectPhaseDTO projectPhaseDTO = projectPhaseService.getProjectPhaseById(Integer.parseInt(id));
        ModuleDTO moduleDTO = moduleService.addModule(ModuleDTO.builder()
                .name(moduleFormCommand.getName())
                .moduleTasks(new ArrayList<>())
                .build());
        projectPhaseDTO.addModule(moduleDTO);
        projectPhaseService.saveProjectPhase(projectPhaseDTO);

        return projectPhaseDTO;
    }

    @DeleteMapping("/{id}/delete-module/{moduleid}")
    public ProjectPhaseDTO deleteModuleFromProjectPhase(@PathVariable(name = "id") String id, @PathVariable(name = "moduleid") String moduleid){
        ProjectPhaseDTO projectPhaseDTO = projectPhaseService.getProjectPhaseById(Integer.parseInt(id));
        ModuleDTO moduleDTO = moduleService.getModuleById(Integer.parseInt(moduleid));
        projectPhaseDTO.getModules().remove(moduleDTO);
        projectPhaseService.saveProjectPhase(projectPhaseDTO);
        moduleService.deleteModule(moduleDTO.getId());
        return projectPhaseDTO;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler( value = {MethodArgumentNotValidException.class, ModuleException.class})
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
