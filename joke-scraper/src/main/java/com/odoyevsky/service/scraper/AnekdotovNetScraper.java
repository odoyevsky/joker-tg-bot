package com.odoyevsky.service.scraper;

import com.odoyevsky.config.AnekdotovNetConfig;
import com.odoyevsky.model.Category;
import com.odoyevsky.dto.CategoryJokes;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.htmlunit.FailingHttpStatusCodeException;
import org.htmlunit.WebClient;
import org.htmlunit.html.DomNode;
import org.htmlunit.html.HtmlElement;
import org.htmlunit.html.HtmlPage;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
@EnableConfigurationProperties(AnekdotovNetConfig.class)
public class AnekdotovNetScraper implements JokeScraper {
    private WebClient webClient;
    private AnekdotovNetConfig config;

    @Override
    public List<Category> getCategories() {
        return getCategoryElements().stream()
                .map(this::extractCategoryData)
                .toList();
    }

    @Override
    public CategoryJokes getJokes(Category category) {
        String url = config.url() + category.url();
        Set<String> jokes = new HashSet<>();
        int pageNumber = 0;
        String currentUrlPage;
        HtmlPage page;

        while (true) {
            currentUrlPage = url + "index-page-" + pageNumber + ".html";
            log.info("Getting jokes from: " + currentUrlPage);

            try {
                page = getPage(currentUrlPage);
                jokes.addAll(getTextOfJokesFromPage(page));
            } catch (FailingHttpStatusCodeException e) {
                log.info(e.getMessage());
                break;
            } catch (RuntimeException e) {
                log.info(e.getMessage());
            }

            pageNumber++;
        }

        return new CategoryJokes(category.name(), jokes);
    }

    private Set<String> getTextOfJokesFromPage(HtmlPage page) {
        return page.getBody().getElementsByAttribute("div", "class", "anekdot")
                .stream()
                .map(DomNode::getTextContent)
                .collect(Collectors.toSet());
    }

    private Category extractCategoryData(HtmlElement categoryElement) {
        String name = categoryElement.getTextContent();
        String url = categoryElement.getAttribute("href");

        return new Category(name, url);
    }

    private List<HtmlElement> getCategoryElements() {
        HtmlElement menuElement = getMenuElement();
        List<HtmlElement> categoryElements = menuElement
                .getElementsByAttribute("a", "class", "menuanekdot");

        return categoryElements.stream()
                .filter(categoryElement -> !config.invalidCategories().contains(categoryElement.getTextContent()))
                .toList();
    }

    private HtmlElement getMenuElement() {
        HtmlPage page = getPage(config.url());
        return page.getFirstByXPath(config.menuXpath());
    }

    private HtmlPage getPage(String url) {
        try {
            return webClient.getPage(url);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}