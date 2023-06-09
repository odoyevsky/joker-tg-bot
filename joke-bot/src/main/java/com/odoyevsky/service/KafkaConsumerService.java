package com.odoyevsky.service;

import com.odoyevsky.dto.CategoryJokes;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class KafkaConsumerService {
    private JokeService jokeService;

    @KafkaListener(topics = "jokes")
    public void listen(CategoryJokes jokes){
        jokeService.save(jokes);
    }
}
