package com.odoyevsky.controller;

import com.odoyevsky.dto.CategoryJokes;
import com.odoyevsky.service.JokeService;
import com.odoyevsky.service.transport.KafkaProducerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class JokeController {
    private JokeService jokeService;
    private KafkaProducerService producer;


    @GetMapping("/kafka/jokes")
    public ResponseEntity<String> getJokes(){
        jokeService.scrapeAndSend();
        return ResponseEntity.ok("отправил все в кафку");
    }

    @PostMapping("/kafka/jokes")
    public ResponseEntity<String> sendJoke(@RequestBody CategoryJokes categoryJokes){
        producer.send(categoryJokes);
        return ResponseEntity.ok("отправил в кафку");
    }
}