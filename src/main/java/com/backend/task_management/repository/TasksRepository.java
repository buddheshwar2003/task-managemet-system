package com.backend.task_management.repository;

import com.backend.task_management.entities.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TasksRepository extends JpaRepository<Tasks, UUID> {
}
