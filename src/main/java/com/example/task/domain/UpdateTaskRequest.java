package com.example.task.domain;

import java.time.LocalDate;

import com.example.task.domain.entity.TaskPriority;
import com.example.task.domain.entity.TaskStatus;

public record UpdateTaskRequest(
  String title,
  String description,
  LocalDate dueDate,
  TaskStatus status,
  TaskPriority priority
) {

}