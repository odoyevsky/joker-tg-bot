package com.odoyevsky.config;

import com.odoyevsky.LongPollingJokeBot;
import lombok.AllArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@AllArgsConstructor
public class BotInitializer {
    private LongPollingJokeBot jokeBot;

    @EventListener
    public void init(ContextRefreshedEvent ctxRefreshedEvent){
        try{
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(jokeBot);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
}
