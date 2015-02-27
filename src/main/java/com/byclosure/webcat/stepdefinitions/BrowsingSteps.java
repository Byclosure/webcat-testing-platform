package com.byclosure.webcat.stepdefinitions;

import com.byclosure.webcat.context.IContext;
import com.byclosure.webcat.stepdefinitions.helpers.SeleniumInteractions;
import com.google.inject.Inject;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.validator.routines.UrlValidator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class BrowsingSteps {
    
    private final WebDriver driver;
    private final SeleniumInteractions seleniumInteractions;

    @Inject
    public BrowsingSteps(WebDriver driver, IContext context){
        this.driver = driver;
        this.seleniumInteractions = new SeleniumInteractions(driver, context);
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

        seleniumInteractions.takeScreenshot();
    }
    
    @When("^(?:|I )follow \"([^\"]*)\"$")
    public void followLink(String link) throws UnsupportedEncodingException {
        seleniumInteractions.clickLink(link);

        seleniumInteractions.takeScreenshot();
    }
    
    @Then("^I should be redirected to (.+)$")
    public void shouldBeRedirectedTo(String page) {
        assertOnPage(page);
    }
    
    @Then("^(?:|I )should see \"([^\"]*)\"$")
    public void shouldSee(String text) {
        boolean contentExists = seleniumInteractions.searchForContent(text);

        seleniumInteractions.takeScreenshot();
        Assert.assertTrue(contentExists);
    }

    @Then("^(?:|I )should not see \"([^\"]*)\"$")
    public void shouldNotSee(String text) {
        boolean contentExists = seleniumInteractions.searchForContent(text);

        seleniumInteractions.takeScreenshot();
        Assert.assertFalse(contentExists);
    }

    @Then("^(?:|I )should see \\/([^\\/]*)\\/([imx])?$")
    public void shouldSeeRegexp(String regexp, String flags) {
        boolean contentExists = seleniumInteractions.searchForContent(regexp, flags);
        seleniumInteractions.takeScreenshot();
        
        Assert.assertTrue(contentExists);
    }
    
    @Then("^(?:|I )should not see \\/([^\\/]*)\\/([imxo])?$")
    public void shouldNotSeeRegexp(String regexp, String flags) {
        boolean contentExists = seleniumInteractions.searchForContent(regexp, flags);
        seleniumInteractions.takeScreenshot();
        
        Assert.assertFalse(contentExists);
    }

    @Then("^(?:|I )should be on (.+)$")
    public void shouldBeOnPage(String url) {
        assertOnPage(url);
    }

    @Then("^I should see (\\d+) elements? kind of (.+)$")
    public void shouldSeeElementsKindOf(int count, String locator) {
        List<WebElement> elements = driver.findElements(By.cssSelector(locator));

        seleniumInteractions.takeScreenshot();
        Assert.assertEquals(count, elements.size());
    }
    
    @Then("^I should not see elements? kind of (.+)$")
    public void shouldNotSeeElementsKindOf(String locator) {
        List<WebElement> elements = driver.findElements(By.cssSelector(locator));

        seleniumInteractions.takeScreenshot();
        Assert.assertEquals(0, elements.size());
    }

    private void assertOnPage(String url) {
        final String currentUrl = driver.getCurrentUrl();

        seleniumInteractions.takeScreenshot();
        Assert.assertEquals(url, currentUrl);
    }
}
