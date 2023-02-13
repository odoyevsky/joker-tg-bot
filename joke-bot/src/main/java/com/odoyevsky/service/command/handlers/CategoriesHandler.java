package com.odoyevsky.service.command.handlers;

import com.odoyevsky.model.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class CategoriesHandler implements MessageHandler {
    private CategoryRepository categoryRepository;

    @Override
    public SendMessage handle(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText("sdadasda");
        return sendMessage;
    }
}
