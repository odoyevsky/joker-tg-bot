package com.odoyevsky.service;

import com.odoyevsky.exception.JokeNotFoundException;
import com.odoyevsky.model.entity.Joke;
import com.odoyevsky.model.repository.JokeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JokeService {
    private JokeRepository jokeRepository;

    public Joke getRandomJoke() throws JokeNotFoundException {
        return jokeRepository.getRandomJoke()
                .orElseThrow(() -> new JokeNotFoundException("Not any joke was found in repository"));
    }

    public Joke getCategoryJoke(String category) throws JokeNotFoundException {
        return jokeRepository.getCategoryJoke(category)
                .orElseThrow(() -> new JokeNotFoundException("Joke of this category: " + category + " was not found"));
    }
}
