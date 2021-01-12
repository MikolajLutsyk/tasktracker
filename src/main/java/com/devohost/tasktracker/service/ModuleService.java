package com.devohost.tasktracker.service;

import com.devohost.tasktracker.dto.ModuleDTO;
import com.devohost.tasktracker.exceptions.ModuleException;
import java.util.List;

public interface ModuleService {
    ModuleDTO addModule(ModuleDTO dto);
    ModuleDTO getModuleById(int id) throws ModuleException;
    List<ModuleDTO> getAllModules();
    void saveModule(ModuleDTO dto);
    boolean deleteModule(int id);
}
