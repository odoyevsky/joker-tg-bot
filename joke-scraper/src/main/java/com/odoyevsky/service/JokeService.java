package com.odoyevsky.service;

import com.odoyevsky.dto.CategoriesDTO;
import com.odoyevsky.dto.JokesDTO;
import com.odoyevsky.scraper.AnekdotovNetScraper;
import com.odoyevsky.scraper.Category;
import com.odoyevsky.scraper.Joke;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JokeService {
    private AnekdotovNetScraper anekdotovNetScraper;

    public List<JokesDTO> getAllJokes() {
        return anekdotovNetScraper.getCategories().stream()
                .map(category ->
                        new JokesDTO(category.getNAME(), anekdotovNetScraper.getJokes(category.getNAME())
                                .stream()
                                .map(Joke::getText)
                                .collect(Collectors.toSet())))
                .toList();
    }

    public CategoriesDTO getCategories() {
        return new CategoriesDTO(
                anekdotovNetScraper.getCategories().stream()
                        .map(Category::getNAME)
                        .collect(Collectors.toSet()));
    }

    public JokesDTO getCategoryJokes(String category){
        return new JokesDTO(
                category, anekdotovNetScraper.getJokes(category).stream()
                .map(Joke::getText)
                .collect(Collectors.toSet()));
    }
}
