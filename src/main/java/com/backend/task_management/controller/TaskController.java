package com.backend.task_management.controller;

import com.backend.task_management.dtos.CreateUpdateRequest;
import com.backend.task_management.entities.Tasks;
import com.backend.task_management.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    @GetMapping
    public ResponseEntity<List<Tasks>> findAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Tasks> findTaskById(@PathVariable UUID id) {
        return ResponseEntity.ok(taskService.findTaskById(id));
    }
    @PostMapping
    public ResponseEntity<Tasks> createTask(@Valid @RequestBody CreateUpdateRequest task) {
        return new ResponseEntity<>(taskService.createTask(task),HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    ResponseEntity<Tasks> updateTask(@PathVariable UUID id, @Valid @RequestBody CreateUpdateRequest task) {
        return ResponseEntity.ok(taskService.updateTask(id,task));
    }
    @PatchMapping("/{id}/complete")
    ResponseEntity<Tasks> completeTask(@PathVariable UUID id) {
        return ResponseEntity.ok(taskService.completeTask(id));
    }
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
