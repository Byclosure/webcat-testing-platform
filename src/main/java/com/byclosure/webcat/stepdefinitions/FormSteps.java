package com.byclosure.webcat.stepdefinitions;

import com.byclosure.webcat.context.IContext;
import com.byclosure.webcat.stepdefinitions.helpers.SeleniumInteractions;
import com.google.inject.Inject;
import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class FormSteps {
    private final WebDriver driver;
    private final SeleniumInteractions seleniumInteractions;
    
    @Inject
    public FormSteps(WebDriver driver, IContext context) {
        this.driver = driver;
        this.seleniumInteractions = new SeleniumInteractions(driver, context);
    }

    @When("^(?:|I )fill in \"([^\"]*)\" with \"([^\"]*)\"$")
    public void fillFieldWith(String field, String value) {
        seleniumInteractions.fillFormTextField(field, value);
        
        seleniumInteractions.takeScreenshot();
    }

    @Then("^the \"([^\"]*)\" field(?: within (.*))? should contain \"([^\"]*)\"$")
    public void fieldShouldContain(String field, String parent, String value) {
        final boolean fieldContains = seleniumInteractions.formFieldContains(field, parent, value);

        seleniumInteractions.takeScreenshot();
        
        Assert.assertTrue(fieldContains);
    }
    
    @When("^(?:|I )press \"([^\"]*)\"$")
    public void pressButton(String button) {
        seleniumInteractions.clickButton(button);

        seleniumInteractions.takeScreenshot();
    }
}
