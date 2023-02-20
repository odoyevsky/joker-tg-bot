package com.odoyevsky.strategy;

import com.odoyevsky.exception.JokeNotFoundException;
import com.odoyevsky.exception.UserNotFoundException;
import com.odoyevsky.service.UserService;
import com.odoyevsky.emojis.Emojis;
import com.odoyevsky.utility.TgApiUtility;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class AddFavouriteCommand implements HandlingStrategy {
    private UserService userService;
    private TgApiUtility tgApiUtility;

    @Override
    public List<BotApiMethod<?>> handle(Update update) {
        List<BotApiMethod<?>> messages = new ArrayList<>();
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String messageText = update.getCallbackQuery().getMessage().getText();
        long chatId = callbackQuery.getMessage().getChatId();

        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callbackQuery.getId());

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessageText.setReplyMarkup(tgApiUtility.createInlineKeyboardMarkupBrokenHeart());
        editMessageText.setText(callbackQuery.getMessage().getText());

        try {
            userService.addFavouriteJoke(chatId, messageText);
            answerCallbackQuery.setText("Шутка добавлена в избранное " + Emojis.HEART);
            messages.add(answerCallbackQuery);
            messages.add(editMessageText);
        } catch (UserNotFoundException | JokeNotFoundException e) {
            answerCallbackQuery.setText("Ошибка. Что-то не получается" + Emojis.CRYING);
        }

        return messages;
    }
}

