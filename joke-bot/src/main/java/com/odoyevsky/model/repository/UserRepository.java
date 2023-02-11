package com.odoyevsky.model.repository;

import com.odoyevsky.model.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByChatId(Long chatId);
}
