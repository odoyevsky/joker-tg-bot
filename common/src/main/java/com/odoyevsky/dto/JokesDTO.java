package com.odoyevsky.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
public class JokesDTO {
    private String categoryName;
    private Set<String> jokes;
}