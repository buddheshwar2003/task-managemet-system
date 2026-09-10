package com.backend.task_management.controller;


import com.backend.task_management.dtos.CreateUpdateRequest;
import com.backend.task_management.entities.Tasks;
import com.backend.task_management.enums.TaskStatus;
import com.backend.task_management.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TaskService taskService;

    @Test
    void findAllTasks() throws Exception {

        Tasks task = new Tasks();
        task.setTitle("Test task");
        task.setDescription("Test description");
        task.setStatus(TaskStatus.pending);
        task.setDueDate(LocalDate.now().plusDays(2));

        when(taskService.getAllTasks()).thenReturn(List.of(task));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test task"))
                .andExpect(jsonPath("$[0].description").value("Test description"));

        verify(taskService).getAllTasks();
    }

    @Test
    void findTaskById() throws Exception {

        UUID id = UUID.randomUUID();

        Tasks task = new Tasks();
        task.setTitle("Test task");

        when(taskService.findTaskById(id)).thenReturn(task);

        mockMvc.perform(get("/tasks/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test task"));
        verify(taskService).findTaskById(id);
    }

    @Test
    void createTask() throws Exception {

        CreateUpdateRequest request = new CreateUpdateRequest();
        request.setTitle("New task");
        request.setDescription("New description");
        request.setDueDate(LocalDate.now().plusDays(2));
        request.setStatus(TaskStatus.pending);

        Tasks task = new Tasks();
        task.setTitle("New task");
        when(taskService.createTask(any(CreateUpdateRequest.class)))
                .thenReturn(task);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New task"));
        verify(taskService).createTask(any(CreateUpdateRequest.class));
    }

    @Test
    void updateTask() throws Exception {

        UUID id = UUID.randomUUID();

        CreateUpdateRequest request = new CreateUpdateRequest();
        request.setTitle("Updated task");
        request.setDescription("Updated description");
        request.setStatus(TaskStatus.pending);
        request.setDueDate(LocalDate.now().plusDays(2));

        Tasks task = new Tasks();
        task.setTitle("Updated task");

        when(taskService.updateTask(
                eq(id),
                any(CreateUpdateRequest.class)
        )).thenReturn(task);

        mockMvc.perform(put("/tasks/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated task"));
        verify(taskService).updateTask(
                eq(id),
                any(CreateUpdateRequest.class)
        );
    }

    @Test
    void completeTask() throws Exception {

        UUID id = UUID.randomUUID();

        Tasks task = new Tasks();
        task.setTitle("Completed task");
        task.setDescription("Completed task");
        task.setDueDate(LocalDate.now().plusDays(2));
        task.setStatus(TaskStatus.completed);

        when(taskService.completeTask(id)).thenReturn(task);
        mockMvc.perform(patch("/tasks/{id}/complete", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("completed"));

        verify(taskService).completeTask(id);
    }

    @Test
    void deleteTask() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(taskService).deleteTask(id);
        mockMvc.perform(delete("/tasks/{id}", id))
                .andExpect(status().isNoContent());
        verify(taskService).deleteTask(id);
    }
}