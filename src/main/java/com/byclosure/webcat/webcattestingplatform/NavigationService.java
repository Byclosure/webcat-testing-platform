package com.byclosure.webcat.webcattestingplatform;

import com.byclosure.webcat.webcattestingplatform.exceptions.InvalidPageObjectException;
import com.byclosure.webcat.webcattestingplatform.exceptions.PageObjectNotFoundException;
import com.byclosure.webcat.webcattestingplatform.pageobjects.IPageObject;
import com.byclosure.webcat.webcattestingplatform.criterias.ICriteria;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import java.util.*;

public class NavigationService {
    /**
     * The time you wait for a page to load
     */
    public static final int WAIT_IN_SECS = 60;
    /**
     * The Explicit wait time (i.e. the time you wait for a certain condition to be satisfied)
     */
    public static final int EXPLICIT_WAIT_IN_SECS = 30;

    static int WAIT;
    static int EXPLICITWAIT;

    private final Map<String, PageObjectResolver> pageObjectResolverMapper;
    private final Map<String, PageObjectResolver> errorPageObjects;

    private final List<CriteriaMapper> errorPageObjectCriterias;

    private final WebDriver driver;

    public NavigationService(List<CriteriaMapper> errorPageObjectCriterias,
                             Map<String, PageObjectResolver> errorPageObjects, WebDriver driver) {
        this(errorPageObjectCriterias, errorPageObjects, driver, WAIT_IN_SECS, EXPLICIT_WAIT_IN_SECS);
    }

    public NavigationService(List<CriteriaMapper> errorPageObjectCriterias,
                             Map<String, PageObjectResolver> errorPageObjects, WebDriver driver,
                             int waitInSecs, int explicitWaitInSecs) {
        WAIT = waitInSecs;
        EXPLICITWAIT = explicitWaitInSecs;

        this.errorPageObjectCriterias = errorPageObjectCriterias;
        this.errorPageObjects = errorPageObjects;
        this.pageObjectResolverMapper = new HashMap<String, PageObjectResolver>();
        this.driver = driver;
    }

    public NavigationService(WebDriver driver) {
        this(new ArrayList<CriteriaMapper>(), new HashMap<String, PageObjectResolver>(), driver);
    }

    public NavigationService register(String pageObjectName, ICriteria criteria, IPageObjectFactory pageFactory) {
        final PageObjectResolver pageFactoryService;
        if (pageObjectResolverMapper.containsKey(pageObjectName)) {
            pageFactoryService = pageObjectResolverMapper.get(pageObjectName);
        } else {
            pageFactoryService = new PageObjectResolver(errorPageObjectCriterias);
            pageObjectResolverMapper.put(pageObjectName, pageFactoryService);
        }

        NavigationService navigationService = new NavigationService(errorPageObjectCriterias, errorPageObjects, driver);

        return pageFactoryService.register(criteria, pageFactory, navigationService);
    }

    public void registerErrorPage(String pageObjectName, ICriteria criteria, IPageObjectFactory pageFactory) {
        NavigationService navigationService = new NavigationService(errorPageObjectCriterias, errorPageObjects, driver);
        errorPageObjectCriterias.add(new CriteriaMapper(criteria,
                navigationService, pageFactory));

        PageObjectResolver pageObjectResolver = new PageObjectResolver(errorPageObjectCriterias);
        errorPageObjects.put(pageObjectName, pageObjectResolver);
    }

    public <P extends IPageObject> P getPage(String name) {
        final P pageObject;
        try {
            if (pageObjectResolverMapper.containsKey(name)) {
                pageObject = pageObjectResolverMapper.get(name).getCurrentPage(driver);
            } else if (errorPageObjects.containsKey(name)) {
                pageObject = errorPageObjects.get(name).getCurrentPage(driver);
            } else {
                throw new InvalidPageObjectException("Page Object " + name + " is not registered.");
            }
        } catch(TimeoutException e) {
            throw new PageObjectNotFoundException("Could not find Page Object " + name);
        }

        return pageObject;
    }

    public Map<String, PageObjectResolver> getAllPageObjectResolverMappers() {
        Map<String, PageObjectResolver> pageObjects = new HashMap<String, PageObjectResolver>();
        pageObjects.putAll(this.errorPageObjects);
        pageObjects.putAll(this.pageObjectResolverMapper);
        return pageObjects;
    }

}
