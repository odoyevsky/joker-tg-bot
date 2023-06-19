package com.odoyevsky.strategy;

import com.odoyevsky.config.JokeCategoriesConfig;
import com.odoyevsky.utility.TgApiUtility;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CategoriesGroupCommandStrategy implements CommandStrategy {
    private TgApiUtility tgApiUtility;
    private JokeCategoriesConfig categoriesConfig;

    @Override
    public List<BotApiMethod<?>> handle(Update update) {
        SendMessage sendMessage = tgApiUtility.createSendMessage(update.getMessage().getText(), update.getMessage().getChatId());
        ReplyKeyboardMarkup replyKeyboardMarkup = replyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(rowsWithCategoryGroup(update.getMessage().getText()));
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return List.of(sendMessage);
    }

    private ReplyKeyboardMarkup replyKeyboardMarkup() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(false);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }

    public List<KeyboardRow> rowsWithCategoryGroup(String group) {
        List<KeyboardRow> rows = new ArrayList<>();

        List<String> categoriesGroup = categoriesConfig.getCategoryGroup(group);

        JokeCategoriesConfig.batches(categoriesGroup, 2).forEach(g -> {
                    KeyboardRow row = new KeyboardRow();
                    g.forEach(row::add);
                    rows.add(row);
                }
        );

        rows.get(rows.size() - 1).add(0, "\uD83D\uDD19");

        return rows;
    }
}
