package com.devohost.tasktracker.repositories;

import com.devohost.tasktracker.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskRepository extends JpaRepository<Task, Integer> {
}
