package com.odoyevsky.service;

import com.odoyevsky.dto.CategoryJokes;
import com.odoyevsky.model.entity.Category;
import com.odoyevsky.model.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class CategoryService {
    private CategoryRepository categoryRepository;

    public Category save(String categoryName){
        return categoryRepository.findByName(categoryName).orElseGet(() -> {
            Category category = new Category(categoryName);
            categoryRepository.save(category);
            log.info("Категория добавлена в БД: " + category.getName());
            return category;
        });
    }

    public Set<Category> getCategories(){
        Iterable<Category> categoryIterable = categoryRepository.findAll();

        Set<Category> categorySet = new HashSet<>();
        categoryIterable.forEach(categorySet::add);

        return categorySet;
    }
}
