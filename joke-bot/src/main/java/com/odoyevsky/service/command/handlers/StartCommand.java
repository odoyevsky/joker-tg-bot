package com.odoyevsky.service.command.handlers;

import com.odoyevsky.model.entity.User;
import com.odoyevsky.model.repository.UserRepository;
import com.odoyevsky.service.emojis.Emojis;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class StartCommand implements MessageHandler {
    private UserRepository userRepository;

    private final String START_MESSAGE =
            "/joke - случайная шутка любого жанра\n" +
                    Emojis.LIKE + "/" + Emojis.DISLIKE + " - оценить шутку\n" +
                    Emojis.HEART + " - добавить в избранное\n\n" +
                    "/categories - список доступных жанров\n" +
                    "отправь название жанра, чтобы получить соответствующую шутку\n\n" +
                    "/favourites - избранные анекдоты\n" +
                    Emojis.BROKEN_HEART + " - убрать из избранного\n\n" +
                    "/help - справочная информация\n";

    @Override
    public SendMessage handle(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(START_MESSAGE);
        sendMessage.setChatId(update.getMessage().getChatId());

        registerUser(update);

        return sendMessage;
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
