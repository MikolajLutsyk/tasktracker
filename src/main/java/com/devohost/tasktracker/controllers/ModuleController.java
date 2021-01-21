package com.devohost.tasktracker.controllers;

import com.devohost.tasktracker.dto.ModuleDTO;
import com.devohost.tasktracker.forms.ModuleFormCommand;
import com.devohost.tasktracker.service.ModuleService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/module")
public class ModuleController {
    @Resource
    private ModuleService moduleService;

    @PostMapping("/add")
    public ModuleDTO addModule(@RequestBody @Valid ModuleFormCommand moduleFormCommand){
        System.out.println(moduleFormCommand.getName() + " - name");
        return moduleService.addModule(ModuleDTO.builder()
                .name(moduleFormCommand.getName())
                .build());
    }

    @GetMapping("/{id}")
    public ModuleDTO getModuleById(@PathVariable(name = "id") String id){
        return moduleService.getModuleById(Integer.parseInt(id));
    }

    @GetMapping("/getall")
    public List<ModuleDTO> getAllModules(){
        return moduleService.getAllModules();
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteModule(@PathVariable(name = "id") String id){
        return moduleService.deleteModule(Integer.parseInt(id));
    }

    @PutMapping("/update")
    public void updateModule(@RequestBody @Valid ModuleDTO moduleDTO){
        moduleService.saveModule(moduleDTO);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
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
