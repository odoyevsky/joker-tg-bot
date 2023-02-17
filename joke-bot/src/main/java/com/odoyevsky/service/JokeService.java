package com.odoyevsky.service;

import com.odoyevsky.exception.JokeNotFoundException;
import com.odoyevsky.model.entity.Joke;
import com.odoyevsky.model.repository.JokeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
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

    public Joke getJokeByText(String text) throws JokeNotFoundException{
        return jokeRepository.getJokeByText(text)
                .orElseThrow(() -> new JokeNotFoundException("Joke was not found. Text: \n" + text));
    }
}
