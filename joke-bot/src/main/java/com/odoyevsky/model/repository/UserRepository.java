package com.odoyevsky.model.repository;

import com.odoyevsky.model.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
