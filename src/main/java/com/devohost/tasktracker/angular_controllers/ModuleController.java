package com.devohost.tasktracker.angular_controllers;

import com.devohost.tasktracker.dto.ModuleDTO;
import com.devohost.tasktracker.dto.TaskDTO;
import com.devohost.tasktracker.exceptions.ModuleException;
import com.devohost.tasktracker.forms.ModuleFormCommand;
import com.devohost.tasktracker.forms.TaskFormCommand;
import com.devohost.tasktracker.service.interfaces.ModuleService;
import com.devohost.tasktracker.service.interfaces.TaskService;
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
@RequestMapping("/module")
public class ModuleController {
    @Resource
    private ModuleService moduleService;
    @Resource
    private TaskService taskService;

    @PostMapping("/add")
    public ModuleDTO addModule(@RequestBody @Valid ModuleFormCommand moduleFormCommand) {
        System.out.println(moduleFormCommand.getName() + " - name");
        return moduleService.addModule(ModuleDTO.builder()
                .name(moduleFormCommand.getName())
                .moduleTasks(new ArrayList<>())
                .build());
    }

    @GetMapping("/{id}")
    public ModuleDTO getModuleById(@PathVariable(name = "id") String id) {
        return moduleService.getModuleById(Integer.parseInt(id));
    }

    @GetMapping("/getall")
    public List<ModuleDTO> getAllModules() {
        return moduleService.getAllModules();
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteModule(@PathVariable(name = "id") String id) {
        return moduleService.deleteModule(Integer.parseInt(id));
    }

    @PutMapping("/update")
    public void updateModule(@RequestBody @Valid ModuleDTO moduleDTO) {
        moduleService.saveModule(moduleDTO);
    }

    @PostMapping("/{id}/add-task")
    public ModuleDTO addTaskToModule(@PathVariable(name = "id") String moduleId, @RequestBody TaskFormCommand task) {
        String dateFormat = "dd.MM.yyyy";
        ModuleDTO module = moduleService.getModuleById(Integer.parseInt(moduleId));

        TaskDTO taskDTO = taskService.addTask(
                TaskDTO.builder()
                        .taskContent(task.getTaskContent())
                        .points(task.getPoints())
                        .deadline(LocalDate.parse(task.getDeadline(), DateTimeFormatter.ofPattern(dateFormat)))
                        .build());

        module.addTask(taskDTO);
        moduleService.saveModule(module);
        return module;
    }

    @DeleteMapping("/{id}/deletetask/{taskid}")
    public ModuleDTO deleteTaskFromModule(@PathVariable(name = "id") String id, @PathVariable(name = "taskid") String taskId){
        ModuleDTO module = moduleService.getModuleById(Integer.parseInt(id));
        TaskDTO taskDTO = taskService.getTaskById(Integer.parseInt(taskId));
        module.getModuleTasks().remove(taskDTO);
        moduleService.saveModule(module);
        taskService.deleteTask(taskDTO.getId());
        return module;
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
