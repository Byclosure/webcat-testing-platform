package com.byclosure.webcattestingplatform.pageobjects;

import com.byclosure.webcattestingplatform.NavigationService;
import com.byclosure.webcattestingplatform.exceptions.InvalidPageObjectException;
import org.openqa.selenium.WebDriver;

public abstract class PageObject implements IPageObject {
    protected final WebDriver driver;
    private final NavigationService navigationService;

    public PageObject(WebDriver driver, NavigationService navigationService) {
        this.driver = driver;
        this.navigationService = navigationService;
    }

    public NavigationService getNavigationService() {
        return navigationService;
    }

    public <P extends IPageObject> P cast() {
        try {
            return (P) this;
        } catch (ClassCastException ex) {
            throw new InvalidPageObjectException(this, ex);
        }
    }

    public <P extends IPageObject> P getPageObject(String name) {
        return navigationService.getPage(name);
    }
}