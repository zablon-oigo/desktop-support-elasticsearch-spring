package com.example.task.consumer;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.kafka.annotation.KafkaListener; 
import org.springframework.stereotype.Service;

import com.example.task.domain.dto.TaskDto;

@Service
public class TaskConsumer {

    private final ElasticsearchOperations elasticsearchOperations;

    public TaskConsumer(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @KafkaListener(topics = "tasks", groupId = "task-consumer")
    public void consume(TaskDto taskDto) {
        if (taskDto == null) {
            return;
        }
        
        elasticsearchOperations.save(taskDto, IndexCoordinates.of("tasks"));
        System.out.println("Saved to Elasticsearch: " + taskDto);
    }
}