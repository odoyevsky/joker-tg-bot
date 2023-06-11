package com.odoyevsky.utility;

import com.odoyevsky.emojis.Emojis;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.List;

@Component
@NoArgsConstructor
public class TgApiUtility {
    public SendMessage createSendMessage(String text, Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);

        return sendMessage;
    }

    public AnswerCallbackQuery createAnswerCallBackQuery(String text, String id) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setText(text);
        answerCallbackQuery.setCallbackQueryId(id);
        return answerCallbackQuery;
    }

    public InlineKeyboardMarkup createInlineKeyboardMarkup(List<InlineKeyboardButton>... rows) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(Arrays.stream(rows).toList());
        return markup;
    }

    public List<InlineKeyboardButton> createRowLine(InlineKeyboardButton... buttons) {
        return Arrays.stream(buttons).toList();
    }

    public InlineKeyboardButton createInLineButton(String text, String callBackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callBackData);
        return button;
    }

    public InlineKeyboardMarkup createInlineKeyboardMarkupHeart() {
        return createInlineKeyboardMarkup(
                createRowLine(
                        createJokeRowWithHeart().toArray(new InlineKeyboardButton[0]))
        );
    }

    public InlineKeyboardMarkup createInlineKeyboardMarkupBrokenHeart() {
        return createInlineKeyboardMarkup(
                createRowLine(
                        createJokeRowWithBrokenHeart().toArray(new InlineKeyboardButton[0]))
        );
    }

    private List<InlineKeyboardButton> createJokeRowWithBrokenHeart() {
        return List.of(
                //   createInLineButton(Emojis.LIKE, "/like_joke"),
                // createInLineButton(Emojis.DISLIKE, "/dislike_joke"),
                createInLineButton(Emojis.BROKEN_HEART, "/remove_favourite_joke")
        );
    }

    private List<InlineKeyboardButton> createJokeRowWithHeart() {
        return List.of(
               // createInLineButton(Emojis.LIKE, "/like_joke"),
               // createInLineButton(Emojis.DISLIKE, "/dislike_joke"),
                createInLineButton(Emojis.HEART, "/add_favourite_joke")
        );
    }
}
