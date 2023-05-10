package com.odoyevsky.strategy;

import com.odoyevsky.emojis.Emojis;
import com.odoyevsky.utility.TgApiUtility;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@AllArgsConstructor
public class UnknownCommandStrategy implements CommandStrategy {
    private TgApiUtility tgApiUtility;
    private static final String message = "Я не знаю такой команды " + Emojis.CRYING;

    @Override
    public List<BotApiMethod<?>> handle(Update update) {
        if (update.hasCallbackQuery()) {
            return List.of(tgApiUtility.createAnswerCallBackQuery(message, update.getCallbackQuery().getId()));
        } else return List.of(tgApiUtility.createSendMessage(message, update.getMessage().getChatId()));
    }
}
