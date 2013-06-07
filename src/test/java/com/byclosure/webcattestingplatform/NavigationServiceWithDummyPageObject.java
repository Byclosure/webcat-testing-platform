package com.byclosure.webcattestingplatform;

import com.byclosure.webcattestingplatform.criterias.Criteria;
import com.byclosure.webcattestingplatform.exceptions.PageObjectNotFoundException;
import com.byclosure.webcattestingplatform.pageobjects.PageObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebDriver;

public class NavigationServiceWithDummyPageObject {

    @Mock private WebDriver driver;
    private NavigationService navigationService;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);

        navigationService = new NavigationService(driver);

        navigationService.register(TestPageObject.class.getName(), new TestPageCriteria(driver), new IPageObjectFactory<TestPageObject>() {
            public TestPageObject build(NavigationService navigationService) {
                return new TestPageObject(driver, navigationService);
            }
        });
    }

    @Test(expected = PageObjectNotFoundException.class)
    public void itShould_ThrowPageNotFoundException_WhenCannotGetPage() {
        navigationService.getPage(TestPageObject.class.getName());
    }

    private class TestPageObject extends PageObject {

        public TestPageObject(WebDriver driver, NavigationService navigationService) {
            super(driver, navigationService);
        }
    }

    private class TestPageCriteria extends Criteria {

        public TestPageCriteria(WebDriver driver) {
            super(driver);
        }

        @Override
        public boolean isContentLoaded() {
            return false;
        }
    }
}
