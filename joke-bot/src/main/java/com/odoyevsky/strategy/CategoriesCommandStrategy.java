package com.odoyevsky.strategy;

import com.odoyevsky.service.CategoryService;
import com.odoyevsky.utility.TgApiUtility;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@AllArgsConstructor
public class CategoriesCommandStrategy implements HandlingStrategy {
    private CategoryService categoryService;
    private TgApiUtility tgApiUtility;

    @Override
    public List<BotApiMethod<?>> handle(Update update) {
        return List.of(tgApiUtility.createSendMessage(getCategoriesText(), update.getMessage().getChatId()));
    }

    private String getCategoriesText() {
        StringBuilder message = new StringBuilder("Темы шуток \uD83E\uDD78 \n");

        categoryService.getCategories().forEach(category -> message
                .append("• ")
                .append(category.getName())
                .append("\n"));

        return message.toString();
    }
}