package com.odoyevsky.service.command.handlers;

import com.odoyevsky.model.entity.User;
import com.odoyevsky.model.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.odoyevsky.service.utility.TgBotsApiPreparer.prepareTextMessage;

@Component
@AllArgsConstructor
public class StartCommand implements MessageHandler {
    private UserRepository userRepository;

    private final String START_MESSAGE = """
            Привет! 👋
                                                                       
            Я бот-шутник и я знаю тысячи шуток, могу шутить как на конкретную тему, так и случайным образом 😋
                                                                       
            Ты можешь сохранять понравившиеся шутки, чтобы не потерять 🥰
                                                                       
            Пока я умею совсем немного 🥺
            Но со временем я буду уметь делать куда больше! 😎
            
            Используй меню слева для выбора команд.
            Чтобы узнать больше, используй /help.
            """;

    @Override
    public SendMessage handle(Update update) {
        registerUser(update);
        return prepareTextMessage(START_MESSAGE, update.getMessage().getChatId());
    }

    private void registerUser(Update update) {
        Long chatId = update.getMessage().getChatId();
        String username = update.getMessage().getChat().getUserName();

        if (userRepository.findByChatId(chatId).isEmpty()) {
            User user = new User();
            user.setChatId(chatId);
            user.setUserName(username);
            userRepository.save(user);
        }
    }
}
