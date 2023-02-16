package com.odoyevsky.service.utility;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class TgBotsApiPreparer {
    private TgBotsApiPreparer(){}

    public static SendMessage prepareTextMessage(String text, Long chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);

        return sendMessage;
    }
}
