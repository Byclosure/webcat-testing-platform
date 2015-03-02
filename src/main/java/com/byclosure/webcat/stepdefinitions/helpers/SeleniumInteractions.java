package com.byclosure.webcat.stepdefinitions.helpers;

import com.byclosure.webcat.context.IContext;
import org.openqa.selenium.*;
import org.openqa.selenium.internal.selenesedriver.TakeScreenshot;

import java.util.List;
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

        if(elementById != null) {
            elementById.sendKeys(value);
        } else if(elementByName != null) {
            elementByName.sendKeys(value);
        } else if(elementByText != null) {
            elementByText.sendKeys(value);
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
        if(driver instanceof TakeScreenshot) {
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            context.addScreenshot(screenshot);
        }
    }

    private WebElement findLinkElement(String locator) {
        final WebElement elementByText = findLinkByText(locator);
        final WebElement elementById = findElementById(locator);
        final WebElement elementByTitle = findLinkByTitle(locator);
        final WebElement elementByImageAlt = findLinkByImageAlt(locator);

        if(elementByText != null) {
            return elementByText;
        } else if(elementById != null) {
            return  elementById;
        } else if(elementByTitle != null) {
            return elementByTitle;
        } else if(elementByImageAlt != null) {
            return elementByImageAlt;
        } else {
            throw new NoSuchElementException("\""+ locator +"\" not found");
        }
    }
    
    private WebElement findButtonElement(String locator) {
        final WebElement elementByText = findButtonByText(locator);
        final WebElement elementById = findElementById(locator);
        final WebElement elementByTitle = findButtonByTitle(locator);
        final WebElement elementByValue = findButtonByValue(locator);

        if(elementByText != null) {
            return elementByText;
        } else if(elementById != null) {
            return  elementById;
        } else if(elementByTitle != null) {
            return elementByTitle;
        } else if(elementByValue != null) {
            return elementByValue;
        } else {
            throw new NoSuchElementException("\""+ locator +"\" not found");
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
        if(null != buttonText && !"".equals(buttonText)) {
            final List<WebElement> inputs = driver.findElements(By.cssSelector("input[type='button']"));
            final List<WebElement> submitButtons = driver.findElements(By.cssSelector("input[type='submit']"));
            final List<WebElement> buttons = driver.findElements(By.tagName("button"));
            inputs.addAll(submitButtons);
            inputs.addAll(buttons);

            for(WebElement input : inputs) {
                if(buttonText.equals(input.getText())){
                    return input;
                }
            }
        }
        
        return null;
    }
}
