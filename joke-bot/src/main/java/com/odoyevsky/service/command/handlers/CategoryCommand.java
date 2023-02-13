package com.odoyevsky.service.command.handlers;

import com.odoyevsky.model.entity.Category;
import com.odoyevsky.model.entity.Joke;
import com.odoyevsky.model.repository.CategoryRepository;
import com.odoyevsky.model.repository.JokeRepository;
import com.odoyevsky.service.emojis.Emojis;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashSet;
import java.util.Set;

@Component
@AllArgsConstructor
public class CategoryCommand implements MessageHandler {
    private CategoryRepository categoryRepository;
    private JokeRepository jokeRepository;

    @Override
    public BotApiMethod<?> handle(Update update) {
        String categoryName = update.getMessage().getText();
        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(update.getMessage().getChatId());

        if (getCategories().stream().anyMatch(category -> category.getName().equals(categoryName))) {
            sendMessage.setText(getCategoryJokeText(categoryName));
        } else {
            sendMessage.setText("Я не знаю таких тем... " + Emojis.CRYING);
        }

        return sendMessage;
    }

    private String getCategoryJokeText(String category) {
        return jokeRepository.getCategoryJoke(category)
                .map(Joke::getText)
                .orElse("Я не знаю шуток на эту тему " + Emojis.CRYING);
    }

    private Set<Category> getCategories() {
        Iterable<Category> categoryIterable = categoryRepository.findAll();
        Set<Category> categorySet = new HashSet<>();

        categoryIterable.forEach(categorySet::add);

        return categorySet;
    }
}
