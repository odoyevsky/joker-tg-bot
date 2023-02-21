package com.odoyevsky.scraper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Joke {
    private final String text;
    private final Category category;
}
