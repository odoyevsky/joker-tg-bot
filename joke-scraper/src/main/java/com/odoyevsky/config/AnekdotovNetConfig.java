package com.odoyevsky.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "anekdotovNet")
public record AnekdotovNetConfig(String url, String menuXpath, List<String> invalidCategories) {
}