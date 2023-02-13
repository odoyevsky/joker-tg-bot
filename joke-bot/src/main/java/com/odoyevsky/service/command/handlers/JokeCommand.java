package com.odoyevsky.service.command.handlers;

import com.odoyevsky.model.repository.JokeRepository;
import com.odoyevsky.service.emojis.Emojis;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class JokeCommand implements MessageHandler {
    private JokeRepository jokeRepository;

    @Override
    public SendMessage handle(Update update) {
        SendMessage sendMessage = new SendMessage();
        String jokeText = jokeRepository.getRandomJoke().get().getText();

        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText(jokeText);
        sendMessage.setReplyMarkup(createMarkupInLine());

        return sendMessage;
    }

    private InlineKeyboardMarkup createMarkupInLine() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = createInlineKeyboard();
        markup.setKeyboard(rowsInline);
        return markup;
    }

    private List<List<InlineKeyboardButton>> createInlineKeyboard() {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(createButtonRow());

        return rowsInline;
    }

    private List<InlineKeyboardButton> createButtonRow() {
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(Emojis.LIKE);
        button.setCallbackData("like_joke");
        rowInline.add(button);

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        rowInline.add(button1);
        button1.setText(Emojis.DISLIKE);
        button1.setCallbackData("dislike_joke");

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        rowInline.add(button2);
        button2.setText(Emojis.HEART);
        button2.setCallbackData("favourite_joke");

        return rowInline;
    }
}
