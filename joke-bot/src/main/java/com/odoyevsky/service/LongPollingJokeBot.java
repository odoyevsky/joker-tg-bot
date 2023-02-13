package com.odoyevsky.service;

import com.odoyevsky.config.BotConfiguration;
import com.odoyevsky.service.command.handlers.RequestHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class LongPollingJokeBot extends TelegramLongPollingBot {
    private BotConfiguration botConfiguration;
    private RequestHandler requestHandler;

    public LongPollingJokeBot(BotConfiguration botConfiguration, RequestHandler requestHandler) {
        this.botConfiguration = botConfiguration;
        this.requestHandler = requestHandler;
        addCommands(getCommands());
    }

    @Override
    public void onUpdateReceived(Update update) {
        BotApiMethod<?> response = null;
        response = requestHandler.handle(update);
        executeMessage(response);
    }

    private void executeMessage(BotApiMethod<?> message) {
        try {
                execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void addCommands(List<BotCommand> botCommands) {
        try {
            this.execute(new SetMyCommands(botCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private List<BotCommand> getCommands() {
        List<BotCommand> commands = new ArrayList<>();
        commands.add(new BotCommand("/joke", "случайный анекдот"));
        commands.add(new BotCommand("/categories", "список жанров"));
        commands.add(new BotCommand("/favourites", "избранные анекдоты"));
        commands.add(new BotCommand("/help", "справочная информация"));
        return commands;
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
