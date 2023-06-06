package com.odoyevsky.service.transport;

import com.odoyevsky.model.CategoryJokes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducerService implements TransportService {
    private KafkaTemplate<String, CategoryJokes> kafkaTemplate;
    private String topicName;

    public KafkaProducerService(KafkaTemplate<String, CategoryJokes> kafkaTemplate, @Value("${joke-topic}") String topicName){
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    @Override
    public void send(CategoryJokes categoryJokes){
        try {
            kafkaTemplate.send(topicName, categoryJokes);
            log.info("Отправлено в кафку: " + categoryJokes);
        }
        catch (KafkaException e){
            log.info("Не удалось отправить: " + e.getMessage());
        }
    }
}