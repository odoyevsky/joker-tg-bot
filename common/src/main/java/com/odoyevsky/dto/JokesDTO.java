package com.odoyevsky.dto;

import lombok.Data;

import java.util.Set;

@Data
public class JokesDTO {
    private String categoryName;
    private Set<String> jokes;
}