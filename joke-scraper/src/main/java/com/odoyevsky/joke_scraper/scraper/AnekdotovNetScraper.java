package com.odoyevsky.joke_scraper.scraper;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AnekdotovNetScraper implements JokeScraper {
    private final WebClient webClient;
    private static final String BASE_URL = "http://anekdotov.net/";
    private static final String MENU_FULL_XPATH = "/html/body/center/div/div[2]/center/p[2]/table/tbody/tr[2]/td/table";
    private static final List<String> BAN_CATEGORY_NODE_VALUE_LIST = List.of(
            "рассакажи анекдот", "архив по дням",
            "лучшие в этот день",
            "анекдоты дня", "сегодня",
            "лучшие архив", "лучшие в этот день",
            "лучшие за месяц", "лучшие за неделю",
            "новые", "расскажи анекдот");


    public AnekdotovNetScraper(){
        webClient = new WebClient();
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
    }

    @Override
    public List<Joke> getJokes(Category category){
        List<Joke> jokes = new ArrayList<>();
        HtmlPage page = getPage(BASE_URL + category.getURL());
        page.getBody().getElementsByAttribute("div", "class", "anekdot")
                .forEach(jokeElement -> jokes.add(new Joke(jokeElement.getTextContent(), category)));

        return jokes;
    }

    @Override
    public List<Category> getCategories(){
        List<Category> categories = new ArrayList<>();
        getCategoryElements().forEach(htmlElement ->
                categories.add(extractCategoryData(htmlElement)));

        return categories;
    }

    private HtmlPage getPage(String url) {
        HtmlPage page = null;
        try {
            page = webClient.getPage(url);
            return page;
        }
        catch (Exception e){
            throw new RuntimeException("Cannot get the html page");
        }
    }

    private Category extractCategoryData(HtmlElement categoryElement){
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
                .collect(Collectors.toList());
    }

    private HtmlElement getMenuElement() {
        HtmlPage page = getPage(BASE_URL);
        return page.getFirstByXPath(MENU_FULL_XPATH);
    }
}
