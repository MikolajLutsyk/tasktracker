package com.devohost.tasktracker.service.interfaces;

import com.devohost.tasktracker.dto.UserDTO;
import com.devohost.tasktracker.exceptions.UserException;

import java.util.List;

public interface UserService {
    UserDTO addUser(UserDTO dto);
    UserDTO getUserById(int id) throws UserException;
    UserDTO getUserByEmail(String email);
    List<UserDTO> getAllUsers();
    void saveUser(UserDTO dto);
    boolean deleteUser(int id);

}
