package com.odoyevsky.model.repository;

import com.odoyevsky.model.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}