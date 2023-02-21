package com.odoyevsky.dto;

import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class CategoryDTO {
    String name;
    Set<JokeDTO> jokes;
}