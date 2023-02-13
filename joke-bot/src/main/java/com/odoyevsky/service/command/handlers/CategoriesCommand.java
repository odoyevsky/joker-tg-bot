package com.odoyevsky.service.command.handlers;

import com.odoyevsky.model.entity.Category;
import com.odoyevsky.model.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashSet;
import java.util.Set;

@Component
@AllArgsConstructor
public class CategoriesCommand implements MessageHandler {
    private CategoryRepository categoryRepository;

    @Override
    public SendMessage handle(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText(getCategories().toString());

        return sendMessage;
    }

    private Set<Category> getCategories() {
        Iterable<Category> categoryIterable = categoryRepository.findAll();
        Set<Category> categorySet = new HashSet<>();

        categoryIterable.forEach(categorySet::add);
        return categorySet;
    }
}
