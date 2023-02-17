package com.odoyevsky.handler;

import com.odoyevsky.factory.HandlingStrategyFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class RequestHandler {
    private final HandlingStrategyFactory commandHandlerStrategyFactory;

    public List<BotApiMethod<?>> handle(Update update) {
        List<BotApiMethod<?>> responseMessages = null;

        if (update.hasMessage() && update.getMessage().hasText()) {
            responseMessages = handleMessage(update);
        }
        else if ((update.hasCallbackQuery())){
            responseMessages = handleQuery(update);
        }

        return responseMessages;
    }

    private List<BotApiMethod<?>> handleMessage(Update update){
        String command = update.getMessage().getText();
        return commandHandlerStrategyFactory.getHandler(command).handle(update);
    }

    private List<BotApiMethod<?>> handleQuery(Update update){
        String command = update.getCallbackQuery().getData();
        return commandHandlerStrategyFactory.getHandler(command).handle(update);
    }
}