package com.odoyevsky.joke_scraper.scraper;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.odoyevsky.joke_scraper.model.Category;
import com.odoyevsky.joke_scraper.model.Joke;

import java.util.List;

public interface JokeScraper {
    HtmlPage getPage(String url);
    List<Category> getCategoryList();
    List<Joke> getJokeList();
}
