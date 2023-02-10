package com.odoyevsky.service.command.handlers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class RequestHandler {
    private final CommandHandlerStrategyFactory commandHandlerStrategyFactory;

    public BotApiMethod<?> handle(Update update) {
        BotApiMethod<?> responseMessage = null;

        if (update.hasMessage() && update.getMessage().hasText()) {
            responseMessage = handleMessage(update);
        }
        else if ((update.hasCallbackQuery())){
            responseMessage = handleQuery(update);
        }

        return responseMessage;
    }

    private BotApiMethod<?> handleMessage(Update update){
        String command = update.getMessage().getText();
        return commandHandlerStrategyFactory.getHandler(command).handle(update);
    }

    private BotApiMethod<?> handleQuery(Update update){
        return null;
    }
}