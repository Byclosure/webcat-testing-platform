package com.byclosure.webcattestingplatform.criterias;

import org.openqa.selenium.WebDriver;

public abstract class Criteria implements ICriteria {
    protected WebDriver driver;

    public Criteria(WebDriver driver) {
        this.driver = driver;
    }

    public abstract boolean isContentLoaded();

    public boolean hasTitle(String title) {
        return title.equals(driver.getTitle());
    }

    public boolean match() {
        return isContentLoaded();
    }
}
