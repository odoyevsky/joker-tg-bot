package com.odoyevsky.strategy;

import com.odoyevsky.emojis.Emojis;
import com.odoyevsky.exception.UserNotFoundException;
import com.odoyevsky.model.entity.Joke;
import com.odoyevsky.service.UserService;
import com.odoyevsky.utility.TgApiUtility;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class FavouritesCommandStrategy implements HandlingStrategy {
    private UserService userService;
    private TgApiUtility tgApiUtility;

    @Override
    public List<BotApiMethod<?>> handle(Update update) {
        return getFavouriteList(update);
    }

    private List<BotApiMethod<?>> getFavouriteList(Update update) {
        List<BotApiMethod<?>> messages = new ArrayList<>();
        long chatId = update.getMessage().getChatId();

        try {
            List<Joke> jokes = userService.getUsersFavouriteList(chatId);

            jokes.forEach(
                    joke -> {
                        SendMessage sendMessage = tgApiUtility.createSendMessage(joke.getText(), chatId);
                        sendMessage.setReplyMarkup(tgApiUtility.createInlineKeyboardMarkupBrokenHeart());
                        messages.add(sendMessage);
                    }
            );

            if (jokes.size() == 0){
                messages.add(tgApiUtility.createSendMessage("У тебя пока нет шуток в избранном", chatId));
            }
        } catch (UserNotFoundException e){
            messages.add(tgApiUtility.createSendMessage("Что-то не так" + Emojis.CRYING, chatId));
        }

        return messages;
    }
}
