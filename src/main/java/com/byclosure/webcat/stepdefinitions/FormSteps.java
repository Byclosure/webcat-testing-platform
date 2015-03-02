package com.byclosure.webcat.stepdefinitions;

import com.google.inject.Inject;
import cucumber.api.DataTable;
import cucumber.api.java.en.When;
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

    @Inject
    public FormSteps(WebDriver driver) {
        this.driver = driver;
    }

    @When("^(?:|I )fill in \"([^\"]*)\" with \"([^\"]*)\"$")
    public void fillFieldWith(String field, String value) {
        final List<WebElement> elementsById;
        final List<WebElement> elementsByName;
        final List<WebElement> elementsByText;
        WebElement element = null;
        if((elementsById = driver.findElements(By.id(field))).size() > 0) {
            element = elementsById.get(0);
        } else if((elementsByName = driver.findElements(By.name(field))).size() > 0) {
            element = elementsByName.get(0);
        } else if((elementsByText = getFillableElements(driver, field)).size() > 0) {
            element = elementsByText.get(0);
        }

        if(element != null) {
            element.sendKeys(value);
        } else {
            throw new NoSuchElementException("\"" + field + "\" not found.");
        }
    }

    private List<WebElement> getFillableElements(WebDriver driver, String locator) {
        final List<WebElement> labels = driver.findElements(By.cssSelector("label"));
        WebElement label = null;
        for(WebElement l : labels) {
            if(locator.equals(l.getText())) {
                label = l;
                break;
            }
        }
        if(label == null) {
            return new ArrayList<WebElement>();
        } else {
            String inputId = label.getAttribute("for");
            return driver.findElements(By.id(inputId));
        }
    }

//    Then /^the "([^"]*)" field(?: within (.*))? should contain "([^"]*)"$/ do |field, parent, value|
//    with_scope(parent) do
//    field = find_field(field)
//    field_value = field.value
//    if field_value.respond_to? :should
//    field_value.should =~ /#{value}/
//            else
//    assert_match(/#{value}/, field_value)
//    end
//            end
//    end
//

//
//    When /^(?:|I )press "([^"]*)"$/ do |button|
//    click_button(button)
//    end
//    Then /^the select "([^"]*)" should have following options:$/ do |field, options|
//    options = options.transpose.raw
//    if options.size > 1
//    raise 'table should have only one column in this step!'
//            else
//    options = options.first
//            end
//
//    actual_options = find_field(field).all('option').map { |option| option.text }
//
//    if options.respond_to?(:should)
//    options.should eq(actual_options)
//    else
//    assert_equal options, actual_options
//    end
//            end
//

}
