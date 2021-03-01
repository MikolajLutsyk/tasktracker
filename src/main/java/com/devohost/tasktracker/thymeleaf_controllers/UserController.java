package com.devohost.tasktracker.thymeleaf_controllers;


import com.devohost.tasktracker.dto.UserDTO;
import com.devohost.tasktracker.forms.UserFormCommand;
import com.devohost.tasktracker.service.interfaces.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/sign-in")
    public ModelAndView openPage() {
        ModelAndView modelAndView = new ModelAndView("sign-in");
        return modelAndView;
    }

    @GetMapping("/sign-up")
    public ModelAndView pageOpen() {
        ModelAndView model = new ModelAndView("sign-up");
        model.addObject("userForm", new UserFormCommand());
        return model;
    }


    @PostMapping("/sign-up")
    public String registerUser(@ModelAttribute("userForm") @Valid UserFormCommand userFormCommand, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("sign-up", bindingResult.getModel());
        }

        userService.addUser(UserDTO.builder()
                .email(userFormCommand.getEmail())
                .password(userFormCommand.getPassword())
                .firstname(userFormCommand.getFirstname())
                .lastname(userFormCommand.getLastname())
                .build());

        return "redirect:/sign-in";
    }

}
