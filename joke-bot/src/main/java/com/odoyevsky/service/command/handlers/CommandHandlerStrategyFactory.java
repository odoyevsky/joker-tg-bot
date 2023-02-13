package com.odoyevsky.service.command.handlers;

import com.odoyevsky.exception.command_handlers.UnknownCommandException;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommandHandlerStrategyFactory {
    private ApplicationContext context;
    public MessageHandler getHandler(String command){
        return switch (command) {
            case "/start" -> context.getBean(StartCommand.class);
            case "/joke" -> context.getBean(JokeCommand.class);
            case "/categories" -> context.getBean(CategoriesCommand.class);
            case "/help" -> context.getBean(HelpCommand.class);
            default -> throw new UnknownCommandException("Unhandled command: " + command);
        };
    }
}
