package com.odoyevsky.model.repository;

import com.odoyevsky.model.entity.Joke;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

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

    @Query(nativeQuery = true,
            value =
                    "SELECT j.* from jokes j\n" +
                            "JOIN categories c\n" +
                            "ON c.category_id = j.category_id AND \n" +
                            "\tc.name = :category\n" +
                            "JOIN (SELECT RANDOM() * (SELECT MAX(j.joke_id) FROM jokes j) as max_id) as m\n" +
                            "ON j.joke_id >= m.max_id \n" +
                            "LIMIT 1"
    )
    Optional<Joke> getCategoryJoke(@Param("category") String category);
}