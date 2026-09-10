package com.backend.task_management.dtos;

import com.backend.task_management.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class CreateUpdateRequest {
    @NotBlank(message = "Title is Required")
    @Size(max = 100,message = "Title is too long")
    private String title;
    @NotBlank(message = "Description is required")
    @Size(max = 500 , message = "Description is too long")
    private String description;
    @NotNull(message = "Status is Required")
    private TaskStatus status;
    @NotNull(message = "Due Date is Required")
    private LocalDate dueDate;

    public CreateUpdateRequest(String title, String description, TaskStatus status, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
    }

    public CreateUpdateRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
