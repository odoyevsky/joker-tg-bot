package com.odoyevsky.service;

import com.odoyevsky.service.scraper.JokeScraper;
import com.odoyevsky.service.transport.TransportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JokeService {
    private JokeScraper jokeScraper;
    private TransportService transportService;

    public void scrapeAndSend() {
        jokeScraper.getCategories()
                .forEach(category -> transportService.send(jokeScraper.getJokes(category)));
    }
}