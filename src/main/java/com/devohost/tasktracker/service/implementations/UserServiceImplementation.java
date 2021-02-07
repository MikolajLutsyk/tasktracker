package com.devohost.tasktracker.service.implementations;

import com.devohost.tasktracker.dto.BusinessMapper;
import com.devohost.tasktracker.dto.UserDTO;
import com.devohost.tasktracker.entities.User;
import com.devohost.tasktracker.exceptions.TrackerException;
import com.devohost.tasktracker.exceptions.UserException;
import com.devohost.tasktracker.repositories.UserRepository;
import com.devohost.tasktracker.service.interfaces.UserService;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImplementation implements UserService {

    @Resource
    private UserRepository repository;
    @Resource
    private BusinessMapper mapper;

    @Override
    public UserDTO addUser(UserDTO dto) {
        if (dto == null){
            throw new UserException("User is null");
        }
        dto.setRegistrationDate(LocalDate.now());
        User user = repository.save(mapper.toUser(dto));
        return mapper.toDTO(user);
    }

    @Override
    public UserDTO getUserById(int id) throws UserException {
        return mapper.toDTO(repository.findById(id).orElseThrow(() -> new UserException("User not found")));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void saveUser(UserDTO dto) {
        repository.save(mapper.toUser(dto));
    }

    @Override
    public boolean deleteUser(int id) {
        repository.delete(repository.findById(id).orElseThrow(() -> new TrackerException("User not found")));
        return true;
    }
}
