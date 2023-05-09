package com.odoyevsky.controller;

import com.odoyevsky.dto.CategoriesDTO;
import com.odoyevsky.dto.JokesDTO;
import com.odoyevsky.service.JokeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class JokeController {
    private JokeService jokeService;

    @GetMapping("/jokes")
    public List<JokesDTO> getAllJokes(){
        return jokeService.getAllJokes();
    }

    @GetMapping("/categories")
    public CategoriesDTO getCategories(){
        return jokeService.getCategories();
    }

    @GetMapping("/jokes/{category}")
    public JokesDTO getCategoryJokes(@PathVariable("category") String category){
        return jokeService.getCategoryJokes(category);
    }
}
