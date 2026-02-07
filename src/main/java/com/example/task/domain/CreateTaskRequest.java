package com.example.task.domain;

import java.time.LocalDate;

import com.example.task.domain.entity.TaskPriority;

public record CreateTaskRequest(
  String title,
  String description,
  LocalDate dueDate,
  TaskPriority priority
) {

}