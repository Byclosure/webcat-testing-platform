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
        final WebElement elementById = findElementById(field);
        final WebElement elementByName = findElementByName(field);
        final WebElement elementByText = findInputByText(field);
        final WebElement elementByCss = findElementByCss(field);

        if(elementById != null) {
            elementById.clear();
            elementById.sendKeys(value);
        } else if(elementByName != null) {
            elementByName.clear();
            elementByName.sendKeys(value);
        } else if(elementByText != null) {
            elementByText.clear();
            elementByText.sendKeys(value);
        } else if(elementByCss != null){
            elementByCss.clear();
            elementByCss.sendKeys(value);
        } else {
            throw new NoSuchElementException("\"" + field + "\" not found.");
        }
    }
    
    public boolean formFieldContains(String field, String parent, String value) {
        final String parentSelector; 
        if(null == parent || "".equals(parent)) {
            parentSelector = "form";
        } else {
            parentSelector = parent;
        }
        
        final WebElement parentElement = driver.findElement(By.cssSelector(parentSelector));
        final WebElement element = parentElement.findElement(By.cssSelector(field));
        final String elementValue = element.getText();
        
        return elementValue.contains(value);
    }

    public void takeScreenshot() {
        if(driver instanceof TakesScreenshot) {
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Context.getInstance().addScreenshot(screenshot);
        }
    }

    private WebElement findLinkElement(String locator) {
        final WebElement elementByText = findLinkByText(locator);
        final WebElement elementById = findElementById(locator);
        final WebElement elementByTitle = findLinkByTitle(locator);
        final WebElement elementByImageAlt = findLinkByImageAlt(locator);
        final WebElement elementByCss = findElementByCss(locator);

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
        final WebElement elementById = findElementById(locator);
        final WebElement elementByTitle = findButtonByTitle(locator);
        final WebElement elementByValue = findButtonByValue(locator);
        final WebElement elementByCss = findElementByCss(locator);

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

    private WebElement findElementByCss(String selector) {
        if(selector.contains(" ")) { //we'll assume that if the selector has spaces it's not a css selector
            return null;
        } else {
            final List<WebElement> elements = driver.findElements(By.cssSelector(selector));
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

    private WebElement findInputByText(String locator) {
        final List<WebElement> labels = driver.findElements(By.cssSelector("label"));
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
    
    private WebElement findElementByName(String name){
        final List<WebElement> elementsByName =  driver.findElements(By.name(name));
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
    
    private WebElement findElementById(String id) {
        final List<WebElement> elements = driver.findElements(By.id(id));
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
