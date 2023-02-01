package com.odoyevsky.joke_scraper.scraper;

public class PageNotFoundException extends RuntimeException{
    public PageNotFoundException(String message){
        super(message);
    }
}
