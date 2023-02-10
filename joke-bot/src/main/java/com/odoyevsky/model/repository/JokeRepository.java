package com.odoyevsky.model.repository;

import com.odoyevsky.model.entity.Joke;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface JokeRepository extends CrudRepository<Joke, Long> {
    @Query(nativeQuery = true,
            value =
                    "SELECT j.*, max_id FROM jokes j " +
                    "JOIN (SELECT RANDOM() * (SELECT MAX(joke_id) FROM JOKES) as max_id) as m " +
                    "ON j.joke_id >= m.max_id " +
                    "ORDER BY j.joke_id ASC " +
                    "LIMIT 1"
    )
    Optional<Joke> getRandomJoke();
}
