package com.odoyevsky;

import com.odoyevsky.config.BotConfiguration;
import com.odoyevsky.handler.RequestHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class LongPollingJokeBot extends TelegramLongPollingBot {
    private BotConfiguration botConfiguration;
    private RequestHandler requestHandler;

    @PostConstruct
    private void init() {
        addCommands(botConfiguration.getCommands());
    }

    @Override
    public void onUpdateReceived(Update update) {
        executeMessage(requestHandler.handle(update));
    }

    private void executeMessage(List<BotApiMethod<?>> messages) {
        messages.forEach(this::executeMessage);
    }

    private void executeMessage(BotApiMethod<?> message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.info(e.getMessage());
        }
    }

    private void addCommands(List<BotCommand> botCommands) {
        try {
            execute(new SetMyCommands(botCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.info(e.getMessage());
        }
    }

    @Override
    public String getBotToken() {
        return botConfiguration.getToken();
    }

    @Override
    public String getBotUsername() {
        return botConfiguration.getName();
    }
}