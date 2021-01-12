package com.devohost.tasktracker.service;

import com.devohost.tasktracker.dto.ModuleDTO;
import com.devohost.tasktracker.entities.Module;
import com.devohost.tasktracker.entities.enums.State;
import com.devohost.tasktracker.exceptions.ModuleException;
import com.devohost.tasktracker.repositories.ModuleRepository;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleSersiceImpl implements ModuleService{

    @Resource
    ModuleRepository moduleRepository;

    @Override
    public ModuleDTO addModule(ModuleDTO dto) {
        if(dto == null){
            throw new ModuleException("income module object is null");
        }
        dto.setState(State.NEW);
        Module module = moduleRepository.save(toModule(dto));
        return toDTO(module);
    }

    @Override
    public ModuleDTO getModuleById(int id) throws ModuleException {
        return toDTO(moduleRepository.getOne(id));
    }

    @Override
    public List<ModuleDTO> getAllModules() {
        return moduleRepository.findAll().stream()
                .map(x -> toDTO(x))
                .collect(Collectors.toList());
    }

    @Override
    public void saveModule(ModuleDTO dto) {
        moduleRepository.save(toModule(dto));
    }

    @Override
    public boolean deleteModule(int id) {
        if(moduleRepository.getOne(id) != null){
            moduleRepository.delete(moduleRepository.getOne(id));
            return true;
        }
        return false;
    }

    public static Module toModule(ModuleDTO dto){
        return Module.builder()
                .id(dto.getId())
                .moduleTasks(dto.getModuleTasks())
                .state(dto.getState())
                .build();
    }

    public static ModuleDTO toDTO(Module module){
        return ModuleDTO.builder()
                .id(module.getId())
                .moduleTasks(module.getModuleTasks())
                .state(module.getState())
                .build();
    }


}
