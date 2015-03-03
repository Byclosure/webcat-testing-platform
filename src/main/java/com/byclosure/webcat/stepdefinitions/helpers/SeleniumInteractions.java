package com.byclosure.webcat.stepdefinitions.helpers;

import com.byclosure.webcat.context.Context;
import org.openqa.selenium.*;

import java.util.List;

public class SeleniumInteractions {

    private final WebDriver driver;

    public SeleniumInteractions(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Click on a link 
     * @param locator the link locator. It can be the link's:
     *                - text
     *                - id
     *                - title
     *                - inner image alt
     *                - css selector                
     */
    public void clickLink(String locator) {
        WebElement linkElement = findLinkElement(locator);
        linkElement.click();
    }

    public void clickButton(String locator){
        WebElement buttonElement = findButtonElement(locator);
        buttonElement.click();
    }

    public boolean searchForContent(String text) {
        final WebElement body = driver.findElement(By.xpath("//*"));
        return body.isDisplayed() && body.getText().contains(text);
    }
    
    public void fillFormTextField(String field, String value){
        final WebElement inputElement = findInputElement(driver, field);
        inputElement.clear();
        inputElement.sendKeys(value);
    }
    
    public boolean formFieldContains(String field, String parent, String value) {
        final String parentSelector; 
        if(null == parent || "".equals(parent)) {
            parentSelector = "form";
        } else {
            parentSelector = parent;
        }
        
        final WebElement parentElement = driver.findElement(By.cssSelector(parentSelector));
        final WebElement element = findInputElement(parentElement, field);
        final String elementValue = element.getText();
        
        return elementValue.contains(value);
    }

    public void takeScreenshot() {
        if(driver instanceof TakesScreenshot) {
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Context.getInstance().addScreenshot(screenshot);
        }
    }
    
    private WebElement findInputElement(SearchContext parent, String locator) {
        final WebElement elementById = findElementById(parent, locator);
        final WebElement elementByName = findElementByName(parent, locator);
        final WebElement elementByText = findInputByText(parent, locator);
        final WebElement elementByCss = findElementByCss(parent, locator);

        if(elementById != null) {
            return elementById;
        } else if(elementByName != null) {
            return elementByName;
        } else if(elementByText != null) {
            return elementByText;
        } else if(elementByCss != null){
            return elementByCss;
        } else {
            throw new NoSuchElementException("\"" + locator + "\" not found.");
        }
    }
    
    private WebElement findLinkElement(String locator) {
        final WebElement elementByText = findLinkByText(locator);
        final WebElement elementById = findElementById(driver, locator);
        final WebElement elementByTitle = findLinkByTitle(locator);
        final WebElement elementByImageAlt = findLinkByImageAlt(locator);
        final WebElement elementByCss = findElementByCss(driver, locator);

        if(elementByText != null) {
            return elementByText;
        } else if(elementById != null) {
            return  elementById;
        } else if(elementByTitle != null) {
            return elementByTitle;
        } else if(elementByImageAlt != null) {
            return elementByImageAlt;
        } else if(elementByCss != null) {
            return elementByCss;
        } else {
            throw new NoSuchElementException("\""+ locator +"\" not found");
        }
    }
    
    private WebElement findButtonElement(String locator) {
        final WebElement elementByText = findButtonByText(locator);
        final WebElement elementById = findElementById(driver, locator);
        final WebElement elementByTitle = findButtonByTitle(locator);
        final WebElement elementByValue = findButtonByValue(locator);
        final WebElement elementByCss = findElementByCss(driver, locator);

        if(elementByText != null) {
            return elementByText;
        } else if(elementById != null) {
            return  elementById;
        } else if(elementByTitle != null) {
            return elementByTitle;
        } else if(elementByValue != null) {
            return elementByValue;
        } else if(elementByCss != null) {
            return elementByCss;
        } else {
            throw new NoSuchElementException("\""+ locator +"\" not found");
        }
    }

    private WebElement findElementByCss(SearchContext parent, String selector) {
        if(selector.contains(" ")) { //we'll assume that if the selector has spaces it's not a css selector
            return null;
        } else {
            final List<WebElement> elements = parent.findElements(By.cssSelector(selector));
            return elements.size() > 0 ? elements.get(0) : null;
        }
    }
    
    private WebElement findLinkByText(String text) {
        final List<WebElement> elements = driver.findElements(By.linkText(text));
        return elements.size() > 0 ? elements.get(0) : null;
    }
    
    private WebElement findLinkByTitle(String title) {
        final List<WebElement> elementsByTitle = driver.findElements(By.cssSelector("a[title='" + title + "']"));
        return elementsByTitle.size() > 0 ? elementsByTitle.get(0) : null;
    }
    
    private WebElement findLinkByImageAlt(String alt) {
        final List<WebElement> elementsByImageAlt = driver.findElements(By.cssSelector("a > img[alt='" + alt + "']"));
        return elementsByImageAlt.size() > 0 ? elementsByImageAlt.get(0) : null;
    }

    private WebElement findInputByText(SearchContext parent, String locator) {
        final List<WebElement> labels = parent.findElements(By.cssSelector("label"));
        for(WebElement l : labels) {
            if(locator.equals(l.getText())) {
                String inputId = l.getAttribute("for");
                
                if(null == inputId || "".equals(inputId)) {
                    return null;
                } else {
                    final List<WebElement> inputs = driver.findElements(By.id(inputId));
                    return inputs.size() > 0 ? inputs.get(0) : null;
                }
            }
        }
        return null;
    }

    private WebElement findElementByName(SearchContext parent, String name){
        final List<WebElement> elementsByName =  parent.findElements(By.name(name));
        return elementsByName.size() > 0 ? elementsByName.get(0) : null;
    }
    
    private WebElement findButtonByValue(String value) {
        final List<WebElement> elements = driver.findElements(By.cssSelector("input[type='button'][value='" + value + "']"));
        final List<WebElement> submitButtons = driver.findElements(By.cssSelector("input[type='submit'][value='" + value + "']"));
        final List<WebElement> buttons = driver.findElements(By.cssSelector("button[value='" + value + "']"));
        
        elements.addAll(submitButtons);
        elements.addAll(buttons);
        
        return elements.size() > 0 ? elements.get(0) : null;
    }
    
    private WebElement findButtonByTitle(String title) {
        final List<WebElement> elements = driver.findElements(By.cssSelector("input[type='button'][title='" + title + "']"));
        final List<WebElement> submitButtons = driver.findElements(By.cssSelector("input[type='submit'][title='" + title + "']"));
        final List<WebElement> buttons = driver.findElements(By.cssSelector("button[title='" + title + "']"));
        
        elements.addAll(submitButtons);
        elements.addAll(buttons);
        
        return elements.size() > 0 ? elements.get(0) : null;
    }

    private WebElement findElementById(SearchContext parent, String id) {
        final List<WebElement> elements = parent.findElements(By.id(id));
        return elements.size() > 0 ? elements.get(0) : null;
    }

    private WebElement findButtonByText(String buttonText) {
        final List<WebElement> inputs = driver.findElements(By.cssSelector("input[type='button']"));
        final List<WebElement> submitButtons = driver.findElements(By.cssSelector("input[type='submit']"));
        final List<WebElement> buttons = driver.findElements(By.tagName("button"));
        inputs.addAll(submitButtons);
        inputs.addAll(buttons);

        for(WebElement input : inputs) {
            if(buttonText.equalsIgnoreCase(input.getText())){
                return input;
            }
        }
        
        return null;
    }
}
