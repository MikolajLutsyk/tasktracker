package com.devohost.tasktracker.angular_controllers;

import com.devohost.tasktracker.dto.TaskDTO;
import com.devohost.tasktracker.forms.TaskFormCommand;
import com.devohost.tasktracker.service.interfaces.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Resource
    private TaskService taskService;
    private String dateFormat = "dd.MM.yyyy";

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TaskDTO addTask (@RequestBody @Valid TaskFormCommand taskFormCommand) {
        return taskService.addTask(
                TaskDTO.builder()
                        .taskContent(taskFormCommand.getTaskContent())
                        .points(taskFormCommand.getPoints())
                        .deadline(LocalDate.parse(taskFormCommand.getDeadline(), DateTimeFormatter.ofPattern(dateFormat)))
                        .build());
    }

    @GetMapping("/{id}")
    public TaskDTO getTask(@PathVariable(name="id") String id){
        return  taskService.getTaskById(Integer.parseInt(id));
    }

    @GetMapping("/getall")
    public List<TaskDTO> getAllTasks(){
        return taskService.getAllTasks();
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteTask(@PathVariable(name = "id") String id){
        return taskService.deleteTask(Integer.parseInt(id));
    }

    @PutMapping("/update")
    public void updateTask(@RequestBody @Valid TaskDTO dto){
            taskService.saveTask(dto);
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
// localhost:8080/task/add