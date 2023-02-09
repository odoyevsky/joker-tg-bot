package com.odoyevsky.model.repository;

import com.odoyevsky.model.entity.Joke;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface JokeRepository extends CrudRepository<Joke, Long> {
    @Query(
            "SELECT text FROM jokes " +
            "ORDER BY RANDOM() " +
            "LIMIT 1;"
    )
    public Optional<Joke> getRandomJoke();
}
