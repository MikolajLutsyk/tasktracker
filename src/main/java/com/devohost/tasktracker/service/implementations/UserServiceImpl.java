package com.devohost.tasktracker.service.implementations;

import com.devohost.tasktracker.dto.BusinessMapper;
import com.devohost.tasktracker.dto.UserDTO;
import com.devohost.tasktracker.entities.Tracker;
import com.devohost.tasktracker.entities.User;
import com.devohost.tasktracker.exceptions.TrackerException;
import com.devohost.tasktracker.exceptions.UserException;
import com.devohost.tasktracker.repositories.TrackerRepository;
import com.devohost.tasktracker.repositories.UserRepository;
import com.devohost.tasktracker.service.interfaces.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository repository;
    @Resource
    private TrackerRepository trackerRepository;
    @Resource
    private BusinessMapper mapper;
    @Resource
    private BCryptPasswordEncoder encoder;

    @Override
    public UserDTO addUser(UserDTO dto) {
        if (dto == null){
            throw new UserException("User is null");
        }
        dto.setRegistrationDate(LocalDate.now());
        dto.setTracker(mapper.toDTO(trackerRepository.save(new Tracker())));
        dto.setPassword(encoder.encode(dto.getPassword()));
        dto.setRole("USER");
        User user = repository.save(mapper.toUser(dto));
        return mapper.toDTO(user);
    }

    @Override
    public UserDTO getUserById(int id) throws UserException {
        return mapper.toDTO(repository.findById(id).orElseThrow(() -> new UserException("User not found")));
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return mapper.toDTO(repository.findByEmail(email));
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
