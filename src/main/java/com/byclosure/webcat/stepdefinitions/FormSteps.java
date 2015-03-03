package com.byclosure.webcat.stepdefinitions;

import com.byclosure.webcat.stepdefinitions.helpers.SeleniumInteractions;
import com.google.inject.Inject;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;
import org.openqa.selenium.WebDriver;

public class FormSteps {
    private final SeleniumInteractions seleniumInteractions;
    
    @Inject
    public FormSteps(WebDriver driver) {
        this.seleniumInteractions = new SeleniumInteractions(driver);
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
