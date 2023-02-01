package com.odoyevsky.joke_scraper;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.odoyevsky.joke_scraper.scraper.AnekdotovNetScraper;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        AnekdotovNetScraper anekdotovNetScraper = new AnekdotovNetScraper();
        HtmlPage page = anekdotovNetScraper.getPage("https://anekdotov.net/");
        System.out.println("======================");
        System.out.println(page.getTitleText());

        HtmlElement menuElement = page.getFirstByXPath("/html/body/center/div/div[2]/center/p[2]/table/tbody/tr[2]/td/table");
        List<HtmlElement> menuElementList = menuElement.getElementsByAttribute("a","class", "menuanekdot");
        menuElementList.forEach(x -> System.out.println(x.getTextContent()));
    }
}