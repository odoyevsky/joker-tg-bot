package com.odoyevsky.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
public class CategoriesDTO {
    private Set<String> name;
}