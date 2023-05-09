package com.odoyevsky.scraper;

import lombok.extern.slf4j.Slf4j;
import org.htmlunit.FailingHttpStatusCodeException;
import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlElement;
import org.htmlunit.html.HtmlPage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AnekdotovNetScraper {
    private final WebClient webClient;
    private static final String BASE_URL = "https://anekdotov.net";
    //Xpath элемента, в котором перечислены категории шуток
    private static final String MENU_FULL_XPATH = "/html/body/center/div/div[2]/center/p[2]/table/tbody/tr[2]/td/table";
    private static final List<String> BAN_CATEGORY_NODE_VALUE_LIST = List.of(
            "рассакажи анекдот", "архив по дням",
            "лучшие в этот день",
            "анекдоты дня", "сегодня",
            "лучшие архив", "лучшие в этот день",
            "лучшие за месяц", "лучшие за неделю",
            "новые", "расскажи анекдот");


    public AnekdotovNetScraper() {
        webClient = new WebClient();
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setDownloadImages(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(true);
        webClient.getOptions().setTimeout(0);
        webClient.getOptions().setRedirectEnabled(false);
        webClient.getOptions().setAppletEnabled(false);
        webClient.getOptions().setActiveXNative(false);
        webClient.setWebConnection(new FacebookConnectionBlocker(webClient));
    }

    public List<Joke> getJokes(String category) {
        return getJokes(getCategories().stream()
                .filter(categoryC -> categoryC.getNAME().equals(category))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("This category doesn't exist"))
        );
    }

    private List<Joke> getJokes(Category category) {
        String url = BASE_URL + category.getURL();
        List<Joke> jokes = new ArrayList<>();
        HtmlPage page;
        String currentUrlPage;
        int pageNumber = 0;

        while (true) {
            currentUrlPage = url + "index-page-" + pageNumber + ".html";
            log.info("Getting jokes from: " + currentUrlPage);

            try {
                page = getPage(currentUrlPage);
                jokes.addAll(getJokesPage(page, category));
            } catch (FailingHttpStatusCodeException e) {
                log.info(e.getMessage());
                break;
            } catch (RuntimeException e){
                log.info(e.getMessage());
            }

            pageNumber++;
        }

        return jokes;
    }

    public List<Category> getCategories() {
        return getCategoryElements().stream()
                .map(this::extractCategoryData)
                .toList();
    }

    private List<Joke> getJokesPage(HtmlPage page, Category category) {
        return page.getBody().getElementsByAttribute("div", "class", "anekdot")
                .stream()
                .map(jokeElement -> new Joke(jokeElement.getTextContent(), category))
                .toList();
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
                .filter(categoryElement -> !BAN_CATEGORY_NODE_VALUE_LIST.contains(categoryElement.getTextContent()))
                .toList();
    }

    private HtmlElement getMenuElement() {
        HtmlPage page = getPage(BASE_URL);
        return page.getFirstByXPath(MENU_FULL_XPATH);
    }

    private HtmlPage getPage(String url) {
        try {
            return webClient.getPage(url);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}