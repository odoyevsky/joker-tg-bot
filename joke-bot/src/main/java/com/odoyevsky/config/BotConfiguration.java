package com.odoyevsky.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "bot")
public class BotConfiguration {
    private String name;
    private String token;
    private List<BotCommand> commands = new ArrayList<>();
}