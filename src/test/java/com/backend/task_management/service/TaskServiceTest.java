package com.backend.task_management.service;

import com.backend.task_management.dtos.CreateUpdateRequest;
import com.backend.task_management.entities.Tasks;
import com.backend.task_management.enums.TaskStatus;
import com.backend.task_management.exceptions.TaskNotFoundException;
import com.backend.task_management.repository.TasksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TasksRepository tasksRepository;

    @InjectMocks
    private TaskService taskService;

    private UUID id;
    private Tasks task;
    private CreateUpdateRequest request;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();

        request = new CreateUpdateRequest();
        request.setTitle("Task title");
        request.setDescription("task description");
        request.setStatus(TaskStatus.pending);
        request.setDueDate(LocalDate.now().plusDays(2));

        task = new Tasks();
        task.setTitle("Task title");
        task.setDescription("task description");
        task.setStatus(TaskStatus.pending);
        task.setDueDate(LocalDate.now().plusDays(2));
    }

    @Test
    void getAllTasks() {
        when(tasksRepository.findAll()).thenReturn(List.of(task));

        List<Tasks> result = taskService.getAllTasks();

        assertEquals(1, result.size());
        assertEquals("Task title", result.getFirst().getTitle());

        verify(tasksRepository).findAll();
    }

    @Test
    void findTaskById() {
        when(tasksRepository.findById(id)).thenReturn(Optional.of(task));

        Tasks result = taskService.findTaskById(id);

        assertEquals(task, result);
        verify(tasksRepository).findById(id);
    }

    @Test
    void findTaskByIdThrowsException() {
        when(tasksRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.findTaskById(id)
        );

        verify(tasksRepository).findById(id);
    }

    @Test
    void createTask() {
        when(tasksRepository.save(any(Tasks.class))).thenReturn(task);

        Tasks result = taskService.createTask(request);

        assertEquals("Task title", result.getTitle());
        assertEquals("task description", result.getDescription());
        assertEquals(TaskStatus.pending, result.getStatus());

        verify(tasksRepository).save(any(Tasks.class));
    }

    @Test
    void updateTask() {
        when(tasksRepository.findById(id)).thenReturn(Optional.of(task));
        when(tasksRepository.save(task)).thenReturn(task);

        request.setTitle("Updated task");

        Tasks result = taskService.updateTask(id, request);

        assertEquals("Updated task", result.getTitle());

        verify(tasksRepository).findById(id);
        verify(tasksRepository).save(task);
    }

    @Test
    void updateTaskThrowsException() {
        when(tasksRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.updateTask(id, request)
        );

        verify(tasksRepository).findById(id);
        verify(tasksRepository, never()).save(any());
    }

    @Test
    void completeTask() {
        when(tasksRepository.findById(id)).thenReturn(Optional.of(task));
        when(tasksRepository.save(task)).thenReturn(task);

        Tasks result = taskService.completeTask(id);

        assertEquals(TaskStatus.completed, result.getStatus());

        verify(tasksRepository).findById(id);
        verify(tasksRepository).save(task);
    }

    @Test
    void completeTaskThrowsException() {
        when(tasksRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.completeTask(id)
        );

        verify(tasksRepository).findById(id);
    }

    @Test
    void deleteTask() {
        when(tasksRepository.existsById(id)).thenReturn(true);

        taskService.deleteTask(id);

        verify(tasksRepository).deleteById(id);
    }

    @Test
    void deleteTaskThrowsException() {
        when(tasksRepository.existsById(id)).thenReturn(false);

        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.deleteTask(id)
        );

        verify(tasksRepository, never()).deleteById(id);
    }
}


