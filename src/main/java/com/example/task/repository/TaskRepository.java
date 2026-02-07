package com.example.task.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.task.domain.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

}