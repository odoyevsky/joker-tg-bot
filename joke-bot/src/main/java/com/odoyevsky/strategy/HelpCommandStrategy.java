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
public class HelpCommandStrategy implements CommandStrategy {
    private TgApiUtility tgApiUtility;
    private static final String HELP_TEXT = """
            /joke - случайная шутка
            
            /categories - список тем для шуток
            отправь название темы, чтобы получить соответствующую шутку
                        
            Чтобы не потерять понравившуюся шутку, добавь ее в избранное %s
            
            Если она тебе больше не нравится, и ты хочешь ее убрать из списка %s
            
            /favourites - избранные шутки
            """.formatted(Emojis.LIKE, Emojis.DISLIKE, Emojis.HEART, Emojis.BROKEN_HEART);

    @Override
    public List<BotApiMethod<?>> handle(Update update) {
        return List.of(tgApiUtility.createSendMessage(HELP_TEXT, update.getMessage().getChatId()));
    }
}
