package com.odoyevsky.model.repository;

import com.odoyevsky.model.entity.Joke;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface JokeRepository extends CrudRepository<Joke, Long> {
    @Query(
            value = "SELECT j.text FROM JOKES j ORDER BY RANDOM() LIMIT 1",
            nativeQuery = true
    )
    Optional<String> getRandomJoke();
}
