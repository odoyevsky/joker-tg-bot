package com.odoyevsky.joke_scraper.scraper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class CategoryElement {
    private final String NAME;
    private final String URL;
}
