package com.devohost.tasktracker.pages_config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PageConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/tasks").setViewName("tasks");
        registry.addViewController("/task-edit").setViewName("task-edit");
        registry.addViewController("/task-add").setViewName("task-add");
        registry.addViewController("/sign-up").setViewName("sign-up");
        registry.addViewController("/sign-in").setViewName("sign-in");
        registry.addViewController("/projects").setViewName("projects");
        registry.addViewController("/project-add").setViewName("project-add");
        registry.addViewController("/phases").setViewName("phases");
        registry.addViewController("/phase-add").setViewName("phase-add");
        registry.addViewController("/modules").setViewName("modules");
        registry.addViewController("/module-add").setViewName("module-add");
    }
}