package com.devohost.tasktracker.service;

import com.devohost.tasktracker.dto.BusinessMapper;
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
    @Resource
    BusinessMapper mapper;

    @Override
    public ModuleDTO addModule(ModuleDTO dto) {
        if(dto == null){
            throw new ModuleException("income module object is null");
        }
        dto.setState(State.NEW);
        Module module = moduleRepository.save(mapper.toModule(dto));
        return mapper.toDTO(module);
    }

    @Override
    public ModuleDTO getModuleById(int id) throws ModuleException {
        return mapper.toDTO(moduleRepository.getOne(id));
    }

    @Override
    public List<ModuleDTO> getAllModules() {
        return moduleRepository.findAll().stream()
                .map(x -> mapper.toDTO(x))
                .collect(Collectors.toList());
    }

    @Override
    public void saveModule(ModuleDTO dto) {
        moduleRepository.save(mapper.toModule(dto));
    }

    @Override
    public boolean deleteModule(int id) {
            moduleRepository.delete(moduleRepository.findById(id).orElseThrow(() -> new ModuleException("Module not deleted")));
            return true;
    }
}
