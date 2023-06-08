package com.odoyevsky.service.scraper;

import com.odoyevsky.model.Category;
import com.odoyevsky.dto.CategoryJokes;

import java.util.List;

public interface JokeScraper {
    List<Category> getCategories();
    CategoryJokes getJokes(Category category);
}