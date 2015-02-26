package com.byclosure.webcat.stepdefinitions.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectorHelpers {

    public static String selectorFor(String locator) {
        if("the page".equals(locator)) {
            return "html > body";
        } else if("table's header".equals(locator)) {
            return "table tbody > tr th";
        } else if(Pattern.matches("^paragraphs?$", locator)) {
            return "p";
        } else if(Pattern.matches("^\"(.+)\"$", locator)) {
            final Pattern p = Pattern.compile("^\"(.+)\"$");
            final Matcher m = p.matcher(locator);
            if(m.find()) {
                return m.group(1);
            }
        }

        String message = String.format("Can't find mapping from \"%s\" to a selector.", locator);
        throw new IllegalArgumentException(message);
    }

    public static WebElement findLinkElement(WebDriver driver, String locator) {
        List<WebElement> elementsByText;
        List<WebElement> elementsById;
        List<WebElement> elementsByTitle;
        List<WebElement> elementsByAlt;

        if((elementsByText = driver.findElements(By.linkText(locator))).size() > 0) {
            return elementsByText.get(0);
        } else if((elementsById = driver.findElements(By.id(locator))).size() > 0) {
            return  elementsById.get(0);
        } else if((elementsByTitle = driver.findElements(By.cssSelector("a[title='" + locator + "']"))).size() > 0) {
            return elementsByTitle.get(0);
        } else if((elementsByAlt = driver.findElements(By.cssSelector("a > img[alt='" + locator + "']"))).size() > 0) {
            return elementsByAlt.get(0);
        } else {
            throw new NoSuchElementException("\""+ locator +"\" not found");
        }
    }
}
