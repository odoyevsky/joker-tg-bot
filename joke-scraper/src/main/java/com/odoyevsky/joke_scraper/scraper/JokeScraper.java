package com.odoyevsky.joke_scraper.scraper;

import java.util.List;

public interface JokeScraper {
    List<Category> getCategories();
    List<Joke> getJokes(Category category);
}
