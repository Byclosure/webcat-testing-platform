package com.byclosure.webcat.stepdefinitions.helpers;

import com.byclosure.webcat.context.IContext;
import org.openqa.selenium.*;
import org.openqa.selenium.internal.selenesedriver.TakeScreenshot;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeleniumInteractions {

    private final WebDriver driver;
    private final IContext context;

    public SeleniumInteractions(WebDriver driver, IContext context) {
        this.driver = driver;
        this.context = context;
    }

    public void clickLink(String locator) {
        WebElement linkElement = findLinkElement(locator);
        linkElement.click();
    }

    public boolean searchForContent(String text) {
        final WebElement body = driver.findElement(By.xpath("//*"));
        return body.isDisplayed() && body.getText().contains(text);
    }

    public boolean searchForContent(String regex, String flags) {
        final WebElement body = driver.findElement(By.xpath("//*"));
        final String bodyContent = body.getText();
        Matcher matcher = buildMatcherForContent(regex, flags, bodyContent);

        return body.isDisplayed() && matcher.matches();
    }

    public void takeScreenshot() {
        if(driver instanceof TakeScreenshot) {
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            context.addScreenshot(screenshot);
        }
    }

    private WebElement findLinkElement(String locator) {
        List<WebElement> elementsByText;
        List<WebElement> elementsById;
        List<WebElement> elementsByTitle;
        List<WebElement> elementsByImageAlt;

        if((elementsByText = driver.findElements(By.linkText(locator))).size() > 0) {
            return elementsByText.get(0);
        } else if((elementsById = driver.findElements(By.id(locator))).size() > 0) {
            return  elementsById.get(0);
        } else if((elementsByTitle = driver.findElements(By.cssSelector("a[title='" + locator + "']"))).size() > 0) {
            return elementsByTitle.get(0);
        } else if((elementsByImageAlt = driver.findElements(By.cssSelector("a > img[alt='" + locator + "']"))).size() > 0) {
            return elementsByImageAlt.get(0);
        } else {
            throw new NoSuchElementException("\""+ locator +"\" not found");
        }
    }

    private Matcher buildMatcherForContent(String regexp, String flagsString, String content) {
        int flags = 0;
        if(flagsString.contains("i")) {
            flags |= Pattern.CASE_INSENSITIVE;
        }
        if(flagsString.contains("m")) {
            flags |= Pattern.MULTILINE;
        }
        if(flagsString.contains("x")) {
            flags |= Pattern.COMMENTS;
        }

        Pattern pattern = Pattern.compile(regexp, flags);
        Matcher matcher = pattern.matcher(content);

        return matcher;
    }


}
