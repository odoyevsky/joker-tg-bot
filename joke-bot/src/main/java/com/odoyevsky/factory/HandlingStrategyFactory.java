package com.odoyevsky.factory;

import com.odoyevsky.strategy.*;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HandlingStrategyFactory {
    private ApplicationContext context;

    public HandlingStrategy getHandler(String command) {
        return switch (command) {
            case "/start" -> context.getBean(StartCommand.class);
            case "/help" -> context.getBean(HelpCommand.class);
            case "/joke" -> context.getBean(JokeCommand.class);
            case "/favourites" -> context.getBean(FavouritesCommand.class);
            case "/categories" -> context.getBean(CategoriesCommand.class);
            case "/add_favourite_joke" -> context.getBean(AddFavouriteCommand.class);
            case "/remove_favourite_joke" -> context.getBean(RemoveFavouriteCommand.class);
            default -> {
                if (!command.contains("/")) {
                    yield context.getBean(CategoryCommand.class);
                } else yield context.getBean(UnknownCommand.class);
            }
        };

    }
}