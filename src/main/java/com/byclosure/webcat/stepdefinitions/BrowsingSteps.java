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
    }
    
    @Then("^I should be redirected to (.+)$")
    public void shouldBeRedirectedTo(String page) {
        shouldBeOnPage(page);
    }
    
    @Then("^(?:|I )should see \"([^\"]*)\"$")
    public void shouldSee(String text) {
        final WebElement body = driver.findElement(By.xpath("//*"));
        Assert.assertTrue(body.isDisplayed() && body.getText().contains(text));
    }

    @Then("^(?:|I )should not see \"([^\"]*)\"$")
    public void shouldNotSee(String text) {
        final WebElement body = driver.findElement(By.xpath("//*"));
        Assert.assertFalse(body.isDisplayed() && body.getText().contains(text));
    }

    @Then("^(?:|I )should see \\/([^\\/]*)\\/([imx])?$")
    public void shouldSeeRegexp(String regexp, String flags) {
        final WebElement body = driver.findElement(By.xpath("//*"));
        final String bodyContent = body.getText();
        Matcher matcher = buildMatcherForContent(regexp, flags, bodyContent);
        
        Assert.assertTrue(body.isDisplayed() && matcher.matches());
    }
    
    @Then("^(?:|I )should not see \\/([^\\/]*)\\/([imxo])?$")
    public void shouldNotSeeRegexp(String regexp, String flags) {
        final WebElement body = driver.findElement(By.xpath("//*"));
        final String bodyContent = body.getText();
        Matcher matcher = buildMatcherForContent(regexp, flags, bodyContent);
        
        Assert.assertFalse(body.isDisplayed() && matcher.matches());
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

    @Then("^(?:|I )should be on (.+)$")
    public void shouldBeOnPage(String url) {
        final String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(url, currentUrl);
    }
    
    @Then("^I should see (\\d+) elements? kind of (.+)$")
    public void shouldSeeElementsKindOf(int count, String locator) {
//    actual_count = all(selector_for(locator)).count
//            count = count.to_i
//
//    if actual_count.respond_to?(:should)
//    actual_count.should eq(count)
//    else
//    assert_equal count, actual_count
//    end
        
    }
    
    @Then("^I should not see elements? kind of (.+)$")
    public void shouldNotSeeElementsKindOf(String locator) {
//            if defined?(RSpec)
//    page.should_not have_css(selector_for(locator))
//            else
//            assert page.has_no_css?(selector_for(locator))
//    end
    }
    
    private static void takeScreenshot(WebDriver driver) {
        if(driver instanceof TakeScreenshot) {
            final Context context = Context.getInstance();
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            context.addScreenshot(screenshot);
        }
    }
}
