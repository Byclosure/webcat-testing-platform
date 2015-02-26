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

    @When("^(?:|I )fill in the following:$")
    public void fillInTheFollowing(List<List<String>> formData) {
        final String selectTagRegex = "^(.+\\S+)\\s*(?:\\(select\\))$";
        final String checkBoxTagRegex = "^(.+\\S+)\\s*(?:\\(checkbox\\))$";
        final String radioButtonRegex = "^(.+\\S+)\\s*(?:\\(radio\\))$";
        final String fileFieldRegex = "^(.+\\S+)\\s*(?:\\(file\\))$";

        for(List<String> row : formData) {
            final String fieldName = row.get(0);
            final String fieldValue = row.get(1);

            if(Pattern.matches(selectTagRegex, fieldName)) {
                //    step %(I select "#{value}" from "#{$1}")
            } else if(Pattern.matches(checkBoxTagRegex, fieldName)) {
                if("check".equals(fieldValue)) {
                    //    step %(I check "#{$1}")
                } else if("uncheck".equals(fieldValue)) {
                    //    step %(I uncheck "#{$1}")
                } else {
                    throw new IllegalArgumentException("checkbox values: check|uncheck!");
                }
            } else if(Pattern.matches(radioButtonRegex, fieldName)) {
                //    step %{I choose "#{$1}"}
            } else if(Pattern.matches(fileFieldRegex, fieldName)) {
                //    step %{I attach the file "#{value}" to "#{$1}"}
            } else {
                fillFieldWith(fieldName, fieldValue);
            }
        }
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

    public List<WebElement> getFillableElements(WebDriver driver, String locator) {
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

//
//    When /^(?:|I )fill in "([^"]*)" with "([^"]*)"$/ do |field, value|
//    fill_in(field, :with => value)
//    end
//
//    When /^(?:|I )fill in "([^"]*)" with:$/ do |field, value|
//    fill_in(field, :with => value)
//    end
//    When /^(?:|I )select "([^"]*)" from "([^"]*)"$/ do |value, field|
//    select(value, :from => field)
//    end
//    When /^(?:|I )check "([^"]*)"$/ do |field|
//    check(field)
//    end
//
//    When /^(?:|I )uncheck "([^"]*)"$/ do |field|
//    uncheck(field)
//    end
//    When /^(?:|I )choose "([^"]*)"$/ do |field|
//    choose(field)
//    end
//
//    When /^(?:|I )attach the file "([^"]*)" to "([^"]*)"$/ do |file, field|
//    path = File.expand_path(File.join(SUPPORT_DIR,"attachments/#{file}"))
//    raise RuntimeError, "file '#{path}' does not exists" unless File.exists?(path)
//
//    attach_file(field, path)
//    end
//
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
//    Then /^the "([^"]*)" field(?: within (.*))? should not contain "([^"]*)"$/ do |field, parent, value|
//    with_scope(parent) do
//    field = find_field(field)
//    field_value = field.value
//    if field_value.respond_to? :should_not
//    field_value.should_not =~ /#{value}/
//            else
//    assert_no_match(/#{value}/, field_value)
//    end
//            end
//    end
//
//    Then /^the "([^"]*)" checkbox(?: within (.*))? should be checked$/ do |label, parent|
//    with_scope(parent) do
//    field_checked = find_field(label)['checked']
//            if field_checked.respond_to? :should
//    field_checked.should be_true
//    else
//            assert field_checked
//            end
//    end
//            end
//    Then /^the "([^"]*)" checkbox(?: within (.*))? should not be checked$/ do |label, parent|
//    with_scope(parent) do
//    field_checked = find_field(label)['checked']
//            if field_checked.respond_to? :should
//    field_checked.should be_false
//    else
//            assert !field_checked
//            end
//    end
//            end
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
//    When /^(?:I|i) select following values from "([^"]*)":$/ do |field, values|
//    values = values.transpose.raw
//    if values.size > 1
//    raise 'table should have only one column in this step!'
//            else
//    values = values.first
//            end
//    values.each do |value|
//    select(value, :from => field)
//    end
//            end
//    When /^(?:I|i) unselect following values from "([^"]*)":$/ do |field, values|
//    values = values.transpose.raw
//    if values.size > 1
//    raise 'table should have only one column in this step!'
//            else
//    values = values.first
//            end
//
//    values.each do |value|
//    unselect(value, :from => field)
//    end
//            end
//
//    Then /^the following values should be selected in "([^"]*)":$/ do |select_box, values|
//    values = values.transpose.raw
//    if values.size > 1
//    raise 'table should have only one column in this step!'
//            else
//    values = values.first
//            end
//    select_box=find_field(select_box)
//    unless select_box['multiple']
//    raise "this is not multiple select box!"
//            else
//    values.each do |value|
//            if select_box.respond_to?(:should)
//    select_box.value.should include(value)
//    else
//            assert select_box.value.include?(value)
//    end
//            end
//    end
//            end
//    Then /^the following values should not be selected in "([^"]*)":$/ do |select_box, values|
//    values = values.transpose.raw
//    if values.size > 1
//    raise 'table should have only one column in this step!'
//            else
//    values = values.first
//            end
//
//    select_box=find_field(select_box)
//    unless select_box['multiple']
//    raise "this is not multiple select box!"
//            else
//    values.each do |value|
//            if select_box.respond_to?(:should)
//    select_box.value.should_not include(value)
//    else
//            assert !select_box.value.include?(value)
//    end
//            end
//    end
//            end
}
