package com.devohost.tasktracker.repositories;

import com.devohost.tasktracker.entities.Tracker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackerRepository extends JpaRepository<Tracker, Integer> {
}
