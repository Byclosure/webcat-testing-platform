package com.byclosure.webcattestingplatform;

import com.byclosure.webcattestingplatform.criterias.ICriteria;
import com.byclosure.webcattestingplatform.pageobjects.IPageObject;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class PageObjectResolver {

    private static Logger logger = Logger.getLogger(PageObjectResolver.class.getName());

    private final List<CriteriaMapper> pageObjectMapper;
    private final List<CriteriaMapper> errorPageObjects;

    public PageObjectResolver(List<CriteriaMapper> errorPageObjects) {
        this.errorPageObjects = errorPageObjects;
        this.pageObjectMapper =  new ArrayList<CriteriaMapper>();
    }

    public PageObjectResolver() {
        this(new ArrayList<CriteriaMapper>());
    }

    public NavigationService register(ICriteria criteria, IPageObjectFactory<IPageObject> pageFactory, NavigationService navigationService) {
        this.pageObjectMapper.add(new CriteriaMapper(criteria, navigationService, pageFactory));
        return navigationService;
    }

    public <P extends IPageObject> P getCurrentPage(WebDriver driver){

        final List<CriteriaMapper> allPageObjects = new ArrayList<CriteriaMapper>();
        allPageObjects.addAll(pageObjectMapper);
        allPageObjects.addAll(errorPageObjects);

        WebDriverWait wait = new WebDriverWait(driver, NavigationService.WAIT);
        return wait.until(new ExpectedCondition<P>() {
            public P apply(org.openqa.selenium.WebDriver webDriver) {
                    for(CriteriaMapper pageObjectMap : allPageObjects) {
                        if(pageObjectMap.getCriteria().match()) {
                            final NavigationService navigationService = pageObjectMap.getNavigationService();
                            final IPageObjectFactory factory = pageObjectMap.getPageObjectFactory();

                            return factory.build(navigationService).cast();
                        }
                    }
                throw new NoSuchElementException("No matching criterias");
            }
        });
    }

    public List<CriteriaMapper> getPageObjectCriterias() {
        return this.pageObjectMapper;
    }
}
