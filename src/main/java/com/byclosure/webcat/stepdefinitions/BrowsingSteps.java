package com.byclosure.webcat.stepdefinitions;

import com.byclosure.webcat.context.Context;
import com.google.inject.Inject;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.validator.routines.UrlValidator;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.internal.selenesedriver.TakeScreenshot;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BrowsingSteps {
    
    private final WebDriver driver;
    
    @Inject
    public BrowsingSteps(WebDriver driver){
        this.driver = driver;
    }
    
    @Given("^(?:|I )am on (.+)$")
    public void iAmOn(String url) {
        this.gotToPage(url);
    }
    
    @When("^(?:|I )go to (.+)$")
    public void iGoTo(String url) {
        this.gotToPage(url);
    }
    
    private void gotToPage(String url) {
        UrlValidator urlValidator = new UrlValidator();
        urlValidator.isValid(url);
        driver.get(url);

        takeScreenshot(driver);
    }
    
    @When("^(?:|I )follow \"([^\"]*)\"$")
    public void followLink(String link) throws UnsupportedEncodingException {
        final String encodedLink = URLEncoder.encode(link, Charset.defaultCharset().name());
        WebElement linkElement = driver.findElement(By.cssSelector("a[href='" + encodedLink + "']"));
        linkElement.click();

        takeScreenshot(driver);
    }
    
    @Then("^I should be redirected to (.+)$")
    public void shouldBeRedirectedTo(String page) {
        assertOnPage(page);
    }
    
    @Then("^(?:|I )should see \"([^\"]*)\"$")
    public void shouldSee(String text) {
        final WebElement body = driver.findElement(By.xpath("//*"));

        takeScreenshot(driver);
        Assert.assertTrue(body.isDisplayed() && body.getText().contains(text));
    }

    @Then("^(?:|I )should not see \"([^\"]*)\"$")
    public void shouldNotSee(String text) {
        final WebElement body = driver.findElement(By.xpath("//*"));

        takeScreenshot(driver);
        Assert.assertFalse(body.isDisplayed() && body.getText().contains(text));
    }

    @Then("^(?:|I )should see \\/([^\\/]*)\\/([imx])?$")
    public void shouldSeeRegexp(String regexp, String flags) {
        final WebElement body = driver.findElement(By.xpath("//*"));
        final String bodyContent = body.getText();
        Matcher matcher = buildMatcherForContent(regexp, flags, bodyContent);

        takeScreenshot(driver);
        
        Assert.assertTrue(body.isDisplayed() && matcher.matches());
    }
    
    @Then("^(?:|I )should not see \\/([^\\/]*)\\/([imxo])?$")
    public void shouldNotSeeRegexp(String regexp, String flags) {
        final WebElement body = driver.findElement(By.xpath("//*"));
        final String bodyContent = body.getText();
        Matcher matcher = buildMatcherForContent(regexp, flags, bodyContent);

        takeScreenshot(driver);
        
        Assert.assertFalse(body.isDisplayed() && matcher.matches());
    }

    @Then("^(?:|I )should be on (.+)$")
    public void shouldBeOnPage(String url) {
        assertOnPage(url);
    }

    @Then("^I should see (\\d+) elements? kind of (.+)$")
    public void shouldSeeElementsKindOf(int count, String locator) {
        List<WebElement> elements = driver.findElements(By.cssSelector(locator));

        takeScreenshot(driver);
        Assert.assertEquals(count, elements.size());
    }
    
    @Then("^I should not see elements? kind of (.+)$")
    public void shouldNotSeeElementsKindOf(String locator) {
        List<WebElement> elements = driver.findElements(By.cssSelector(locator));

        takeScreenshot(driver);
        Assert.assertEquals(0, elements.size());
    }
    
    private static void takeScreenshot(WebDriver driver) {
        if(driver instanceof TakeScreenshot) {
            final Context context = Context.getInstance();
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            context.addScreenshot(screenshot);
        }
    }

    private Matcher buildMatcherForContent(String regexp, String flags, String content) {
        int flag = 0;
        if(flags.contains("i")) {
            flag |= Pattern.CASE_INSENSITIVE;
        }
        if(flags.contains("m")) {
            flag |= Pattern.MULTILINE;
        }
        if(flags.contains("x")) {
            flag |= Pattern.COMMENTS;
        }

        Pattern pattern = Pattern.compile(regexp, flag);
        Matcher matcher = pattern.matcher(content);

        return matcher;
    }

    private void assertOnPage(String url) {
        final String currentUrl = driver.getCurrentUrl();

        takeScreenshot(driver);
        Assert.assertEquals(url, currentUrl);
    }
}
