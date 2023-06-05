package com.odoyevsky.service;

import com.odoyevsky.model.CategoryJokes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducerService {
    private KafkaTemplate<String, CategoryJokes> kafkaTemplate;
    private String topicName;

    public KafkaProducerService(KafkaTemplate<String, CategoryJokes> kafkaTemplate, @Value("${joke-topic}") String topicName){
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    public void sendMessage(CategoryJokes categoryJokes){
        kafkaTemplate.send(topicName, categoryJokes);
        log.info("Отправлено в кафку: " + categoryJokes);
    }
}