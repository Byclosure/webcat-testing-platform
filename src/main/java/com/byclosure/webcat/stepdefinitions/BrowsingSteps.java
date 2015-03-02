package com.byclosure.webcat.stepdefinitions;

import com.byclosure.webcat.context.IContext;
import com.byclosure.webcat.stepdefinitions.helpers.SeleniumInteractions;
import com.google.inject.Inject;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.validator.routines.UrlValidator;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.io.UnsupportedEncodingException;

public class BrowsingSteps {
    
    private final WebDriver driver;
    private final SeleniumInteractions seleniumInteractions;

    @Inject
    public BrowsingSteps(WebDriver driver, IContext context){
        this.driver = driver;
        this.seleniumInteractions = new SeleniumInteractions(driver, context);
    }
    
    @Given("^(?:|I )am on (.+)$")
    @When("^(?:|I )go to (.+)$")
    public void iAmOn(String url) {
        this.gotToPage(url);
    }

    @When("^(?:|I )follow \"([^\"]*)\"$")
    public void followLink(String link) throws UnsupportedEncodingException {
        seleniumInteractions.clickLink(link);

        seleniumInteractions.takeScreenshot();
    }

    @Then("^(?:|I )should see \"([^\"]*)\"$")
    public void shouldSee(String text) {
        boolean contentExists = seleniumInteractions.searchForContent(text);

        seleniumInteractions.takeScreenshot();
        Assert.assertTrue(contentExists);
    }

    @Then("^(?:|I )should be on (.+)$")
    public void shouldBeOnPage(String url) {
        assertOnPage(url);
    }

    private void assertOnPage(String url) {
        final String currentUrl = driver.getCurrentUrl();

        seleniumInteractions.takeScreenshot();
        Assert.assertEquals(url, currentUrl);
    }

    private void gotToPage(String url) {
        UrlValidator urlValidator = new UrlValidator();
        urlValidator.isValid(url);
        driver.get(url);

        seleniumInteractions.takeScreenshot();
    }
}
