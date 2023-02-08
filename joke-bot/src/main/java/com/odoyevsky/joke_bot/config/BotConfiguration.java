package com.odoyevsky.joke_bot.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@PropertySource("classpath:application.yml")
@NoArgsConstructor
public class BotConfiguration {
    @Value("${bot.name}")
    private String name;

    @Value("${bot.token}")
    private String token;
}
