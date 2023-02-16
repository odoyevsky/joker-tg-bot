package com.odoyevsky.service.command.handlers;

import com.odoyevsky.model.entity.Category;
import com.odoyevsky.model.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.*;

import static com.odoyevsky.service.utility.TgBotsApiPreparer.prepareTextMessage;

@Component
@AllArgsConstructor
public class CategoriesCommand implements MessageHandler {
    private CategoryRepository categoryRepository;

    @Override
    public SendMessage handle(Update update) {
        return prepareTextMessage(getCategoriesText(), update.getMessage().getChatId());
    }

    private Set<Category> getCategories() {
        Iterable<Category> categoryIterable = categoryRepository.findAll();
        Set<Category> categorySet = new HashSet<>();
        categoryIterable.forEach(categorySet::add);

        return categorySet;
    }

    private String getCategoriesText() {
        StringBuilder message = new StringBuilder("Темы шуток \uD83E\uDD78 \n");
        getCategories().forEach(category -> message
                .append("• ")
                .append(category.getName())
                .append("\n"));

        return message.toString();
    }
}