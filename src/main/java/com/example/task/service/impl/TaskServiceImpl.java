package com.example.task.service.impl;

import com.example.task.domain.CreateTaskRequest;
import com.example.task.domain.UpdateTaskRequest;
import com.example.task.domain.entity.Task;
import com.example.task.domain.entity.TaskStatus;
import com.example.task.exception.TaskNotFoundException;
import com.example.task.repository.TaskRepository;
import com.example.task.service.TaskService;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.example.task.producer.TaskProducer;
import com.example.task.domain.dto.TaskDto;

@Service
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;
  private final TaskProducer taskProducer;

  public TaskServiceImpl(TaskRepository taskRepository,
                         TaskProducer taskProducer) {
    this.taskRepository = taskRepository;
    this.taskProducer = taskProducer;
  }

  @Override
  public Task createTask(CreateTaskRequest request) {
    Instant now = Instant.now();

    Task task = new Task(
      null,
      request.title(),
      request.description(),
      request.dueDate(),
      TaskStatus.OPEN,
      request.priority(),
      now,
      now
    );

    Task saved = taskRepository.save(task);

    taskProducer.send(toDto(saved));

    return saved;
  }

  @Override
  public List<Task> listTasks() {
    return taskRepository.findAll(Sort.by(Direction.ASC, "created"));
  }

  @Override
  public Task updateTask(UUID taskId, UpdateTaskRequest request) {
    Task task = taskRepository.findById(taskId)
      .orElseThrow(() -> new TaskNotFoundException(taskId));

    task.setTitle(request.title());
    task.setDescription(request.description());
    task.setDueDate(request.dueDate());
    task.setStatus(request.status());
    task.setPriority(request.priority());
    task.setUpdated(Instant.now());

    Task saved = taskRepository.save(task);

    taskProducer.send(toDto(saved));

    return saved;
  }

  @Override
  public void deleteTask(UUID taskId) {
    taskRepository.deleteById(taskId);
    taskProducer.sendDelete(taskId.toString());
  }

  private TaskDto toDto(Task task) {
    return new TaskDto(
      task.getId(),
      task.getTitle(),
      task.getDescription(),
      task.getDueDate(),
      task.getPriority(),
      task.getStatus()
    );
  }
}