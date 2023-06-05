package com.odoyevsky.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;

@ConstructorBinding
@ConfigurationProperties(prefix = "anekdotov-net")
public record AnekdotovNetConfig(String url, String menuXpath, List<String> invalidCategories) {
}