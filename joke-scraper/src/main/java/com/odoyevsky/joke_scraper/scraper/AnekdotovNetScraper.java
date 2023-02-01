package com.odoyevsky.joke_scraper.scraper;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.odoyevsky.joke_scraper.model.Category;
import com.odoyevsky.joke_scraper.model.Joke;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AnekdotovNetScraper implements JokeScraper {
    private static final String BASE_URL = "http://anekdotov.net/";
    private static final String MENU_FULL_XPATH = "/html/body/center/div/div[2]/center/p[2]/table/tbody/tr[2]/td/table";
    private static final List<String> BAN_CATEGORY_LIST = List.of(
            "рассакажи анекдот", "архив по дням",
            "лучшие в этот день",
            "анекдоты дня", "сегодня",
            "лучшие архив", "лучшие в этот день",
            "лучшие за месяц", "лучшие за неделю");

    private final WebClient webClient;

    public AnekdotovNetScraper(){
        webClient = new WebClient();
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
    }

    @Override
    public HtmlPage getPage(String url) {
        HtmlPage page = null;
        try {
            page = webClient.getPage(url);
            return page;
        }
        catch (Exception e){
            throw new RuntimeException("Cannot get the html page");
        }
    }

    @Override
    public List<Category> getCategoryList() {
        return null;
    }

    private List<String> getJokeList(String url){
        List<String> jokeList = new ArrayList<>();
        HtmlPage page = getPage(BASE_URL + url);
        List<HtmlElement> jokeElementList = page.getBody().getElementsByAttribute("div", "class", "anekdot");

        jokeElementList.forEach(htmlElement -> jokeList.add(htmlElement.getTextContent()));
        return jokeList;
    }
    private List<CategoryElement> getCategoryElementList(){
        List<CategoryElement> categoryElementList = new ArrayList<>();

        getCategoryHtmlElementList().forEach(htmlElement ->
                categoryElementList.add(getCategoryData(htmlElement)));

        return categoryElementList;
    }

    private CategoryElement getCategoryData(HtmlElement categoryElement){
        String name = categoryElement.getAttribute("title");
        String url = categoryElement.getAttribute("href");

        return new CategoryElement(name, url);
    }

    private List<HtmlElement> getCategoryHtmlElementList() {
        HtmlElement menuElement = getMenuElement();
        List<HtmlElement> categoryElementList = menuElement.getElementsByAttribute("a", "class", "menuanekdot");

        return categoryElementList.stream()
                .filter(categoryElement -> !BAN_CATEGORY_LIST.contains(categoryElement.getFirstChild().getNodeValue()))
                .collect(Collectors.toList());
    }

    private HtmlElement getMenuElement(){
        HtmlPage page = getPage(BASE_URL);
        return page.getFirstByXPath(MENU_FULL_XPATH);
    }

    @Override
    public List<Joke> getJokeList() {
        return null;
    }

    public static void main(String[] args) {
        AnekdotovNetScraper scraper = new AnekdotovNetScraper();
        System.out.println(scraper.getCategoryElementList());
        System.out.println(scraper.getJokeList("anekdot/blonde/"));
    }
}
