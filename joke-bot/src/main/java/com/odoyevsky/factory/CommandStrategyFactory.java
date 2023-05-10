package com.odoyevsky.factory;

import com.odoyevsky.strategy.*;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommandStrategyFactory {
    private ApplicationContext context;

    public CommandStrategy getCommandStrategy(String command) {
        return switch (command) {
            case "/start" -> context.getBean(StartCommandStrategy.class);
            case "/help" -> context.getBean(HelpCommandStrategy.class);
            case "/joke" -> context.getBean(JokeCommandStrategy.class);
            case "/favourites" -> context.getBean(FavouritesCommandStrategy.class);
            case "/categories" -> context.getBean(CategoriesCommandStrategy.class);
            case "/add_favourite_joke" -> context.getBean(AddFavouriteCommandStrategy.class);
            case "/remove_favourite_joke" -> context.getBean(RemoveFavouriteCommandStrategy.class);
            default -> {
                if (!command.contains("/")) {
                    yield context.getBean(CategoryCommandStrategy.class);
                } else yield context.getBean(UnknownCommandStrategy.class);
            }
        };
    }
}