package com.devohost.tasktracker.repositories;


import com.devohost.tasktracker.dto.UserDTO;
import com.devohost.tasktracker.entities.User;
import com.devohost.tasktracker.entities.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByRole(UserRole role);
    User findByEmail(String email);
}