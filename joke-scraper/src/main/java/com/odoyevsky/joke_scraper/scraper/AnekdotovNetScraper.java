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
        String url = BASE_URL + category.getURL();
        List<Joke> jokes = new ArrayList<>();
        HtmlPage page;
        String currentUrlPage;
        int pageNumber = 0;

        while(true){
            try{
                currentUrlPage = url + "index-page-" + pageNumber +".html";
                page = getPage(currentUrlPage);
                jokes.addAll(getJokesPage(page, category));
                pageNumber++;
            }
            catch (PageNotFoundException e){
                break;
            }
        }

        return jokes;
    }

    @Override
    public List<Category> getCategories(){
        List<Category> categories = new ArrayList<>();
        getCategoryElements().forEach(htmlElement ->
                categories.add(extractCategoryData(htmlElement)));

        return categories;
    }

    private List<Joke> getJokesPage(HtmlPage page, Category category){
        List<Joke> jokesTextFromPage = new ArrayList<>();
        page = getPage(BASE_URL);
        page.getBody().getElementsByAttribute("div", "class", "anekdot")
                .forEach(jokeElement -> jokesTextFromPage.add(new Joke(jokeElement.getTextContent(), category)));

        return jokesTextFromPage;
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

    private HtmlPage getPage(String url) {
        HtmlPage page;
        try {
            page = webClient.getPage(url);
            return page;
        }
        catch (Exception e){
            throw new PageNotFoundException("Page not found");
        }
    }
}
