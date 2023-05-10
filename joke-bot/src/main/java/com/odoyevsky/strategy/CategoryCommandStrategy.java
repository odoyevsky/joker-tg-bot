package com.odoyevsky.strategy;

import com.odoyevsky.exception.JokeNotFoundException;
import com.odoyevsky.emojis.Emojis;
import com.odoyevsky.exception.UserNotFoundException;
import com.odoyevsky.service.JokeService;
import com.odoyevsky.service.UserService;
import com.odoyevsky.utility.TgApiUtility;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class CategoryCommandStrategy implements CommandStrategy {
    private JokeService jokeService;
    private TgApiUtility tgApiUtility;
    private UserService userService;

    @Override
    public List<BotApiMethod<?>> handle(Update update) {
        List<BotApiMethod<?>> messages = new ArrayList<>();
        long chatId = update.getMessage().getChatId();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        try {
            String messageText = jokeService.getCategoryJoke(update.getMessage().getText()).getText();
            sendMessage.setText(messageText);

            if (userService.isFavouriteJoke(chatId, messageText)) {
                sendMessage.setReplyMarkup(tgApiUtility.createInlineKeyboardMarkupBrokenHeart());
            } else sendMessage.setReplyMarkup(tgApiUtility.createInlineKeyboardMarkupHeart());

        } catch (JokeNotFoundException e) {
            log.info(e.getMessage());
            sendMessage.setText("Я не знаю шуток на эту тему " + Emojis.CRYING);
        } catch (UserNotFoundException e){
            log.info(e.getMessage());
            sendMessage.setText("Что-то пошло не так" + Emojis.CRYING);
        }

        messages.add(sendMessage);
        return messages;
    }
}
