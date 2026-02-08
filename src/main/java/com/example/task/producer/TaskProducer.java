package com.example.task.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.task.domain.dto.TaskDto;

@Service
public class TaskProducer {

    private final KafkaTemplate<String, TaskDto> kafkaTemplate;

    public TaskProducer(KafkaTemplate<String, TaskDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(TaskDto taskDto) {
        kafkaTemplate.send("tasks", taskDto.id().toString(), taskDto);
    }

    public void sendDelete(String taskId) {
        kafkaTemplate.send("tasks", taskId, null);
    }
}