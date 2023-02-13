package com.odoyevsky.service.command.handlers;

import com.odoyevsky.service.emojis.Emojis;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@NoArgsConstructor
public class HelpCommand implements MessageHandler {
    private static final String HELP_TEXT =
                    "/joke - случайная шутка любого жанра\n" +
                    Emojis.LIKE + "/" + Emojis.DISLIKE + " - оценить шутку\n" +
                    Emojis.HEART + " - добавить в избранное\n\n" +
                    "/categories - список доступных жанров\n" +
                    "отправь название жанра, чтобы получить соответствующую шутку\n\n" +
                    "/favourites - избранные анекдоты\n" +
                    Emojis.BROKEN_HEART + " - убрать из избранного\n\n" +
                    "/help - справочная информация\n";

    @Override
    public BotApiMethod<?> handle(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText(HELP_TEXT);
        return sendMessage;
    }
}
