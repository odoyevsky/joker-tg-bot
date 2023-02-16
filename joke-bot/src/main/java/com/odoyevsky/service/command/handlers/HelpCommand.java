package com.odoyevsky.service.command.handlers;

import com.odoyevsky.service.emojis.Emojis;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.odoyevsky.service.utility.TgBotsApiPreparer.*;

@Component
@NoArgsConstructor
public class HelpCommand implements MessageHandler {
    private static final String HELP_TEXT = """
            /joke - случайная шутка
            
            /categories - список тем для шуток
            отправь название темы, чтобы получить соответствующую шутку
            
            Ты можешь оценивать шутки для составления их рейтинга:
            %s/%s
            
            Чтобы не потерять понравившуюся шутку, добавь ее в избранное %s
            
            Если она тебе больше не нравится, и ты хочешь ее убрать из списка %s
            
            /favourites - избранные шутки
            """.formatted(Emojis.LIKE, Emojis.DISLIKE, Emojis.HEART, Emojis.BROKEN_HEART);

    @Override
    public BotApiMethod<?> handle(Update update) {
        return prepareTextMessage(HELP_TEXT, update.getMessage().getChatId());
    }
}
