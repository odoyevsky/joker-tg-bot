package com.odoyevsky.strategy;

import com.odoyevsky.config.JokeCategoriesConfig;
import com.odoyevsky.service.CategoryService;
import com.odoyevsky.utility.TgApiUtility;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.*;

@Component
@AllArgsConstructor
public class CategoriesCommandStrategy implements CommandStrategy {
    private CategoryService categoryService;
    private TgApiUtility tgApiUtility;
    private JokeCategoriesConfig categoriesConfig;

    @Override
    public List<BotApiMethod<?>> handle(Update update) {
        SendMessage sendMessage = tgApiUtility.createSendMessage(getCategoriesText(), update.getMessage().getChatId());
        sendMessage.setReplyMarkup(replyKeyboardMarkup());

        return List.of(sendMessage);
    }

    private String getCategoriesText() {
        StringBuilder message = new StringBuilder("\uD83E\uDD78\uD83E\uDD78\uD83E\uDD78 \n");
        categoriesConfig.getCategoryGroups().forEach(group -> {
            message.append(group.toLowerCase() + ":" + "\n");
            categoriesConfig.getCategoryGroup(group).forEach(category -> {
                message
                        .append("• ")
                        .append(category)
                        .append("\n");
            });
            message.append("\n");
        });

        return message.toString();
    }

    private ReplyKeyboardMarkup replyKeyboardMarkup() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(false);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setKeyboard(rowsWithCategoryGroups());

        return replyKeyboardMarkup;
    }

    private List<KeyboardRow> rowsWithCategoryGroups() {
        List<KeyboardRow> rows = new ArrayList<>();

        JokeCategoriesConfig.batches(categoriesConfig.getCategoryGroups(), 2)
                .forEach(group -> {
                            KeyboardRow row = new KeyboardRow();
                            group.forEach(row::add);
                            rows.add(row);
                        }
                );
        return rows;
    }
}
