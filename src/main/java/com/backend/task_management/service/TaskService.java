package com.backend.task_management.service;

import com.backend.task_management.dtos.CreateUpdateRequest;
import com.backend.task_management.entities.Tasks;
import com.backend.task_management.enums.TaskStatus;
import com.backend.task_management.exceptions.TaskNotFoundException;
import com.backend.task_management.repository.TasksRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {
    private final TasksRepository tasksRepository;

    public TaskService(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public List<Tasks> getAllTasks() {
        return tasksRepository.findAll();
    }

    public Tasks findTaskById(UUID id) {
        return tasksRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not Found"));
    }

    public Tasks createTask(@Valid CreateUpdateRequest task) {
        Tasks tasks = new Tasks();
        tasks.setStatus(task.getStatus());
        tasks.setTitle(task.getTitle());
        tasks.setDescription(task.getDescription());
        tasks.setDueDate(task.getDueDate());
        return tasksRepository.save(tasks);
    }

    public Tasks updateTask(UUID id, @Valid CreateUpdateRequest task) {
        Tasks tasks = tasksRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not Found"));
        tasks.setTitle(task.getTitle());
        tasks.setDescription(task.getDescription());
        tasks.setDueDate(task.getDueDate());
        tasks.setStatus(task.getStatus());
        return tasksRepository.save(tasks);
    }

    public Tasks completeTask(UUID id) {
        Tasks tasks = tasksRepository.findById(id) .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        tasks.setStatus(TaskStatus.completed);
        return tasksRepository.save(tasks);
    }

    public void deleteTask(UUID id) {
        if (!tasksRepository.existsById(id)) {
            throw new TaskNotFoundException("Task not found");
        }
        tasksRepository.deleteById(id);
    }
}
