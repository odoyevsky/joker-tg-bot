package com.odoyevsky.service.command.handlers;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface  MessageHandler {
    BotApiMethod<?> handle(Update update);
}
