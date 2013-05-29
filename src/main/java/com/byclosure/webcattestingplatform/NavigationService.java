package com.byclosure.webcattestingplatform;

import com.byclosure.webcattestingplatform.criterias.ICriteria;
import com.byclosure.webcattestingplatform.exceptions.InvalidPageObjectException;
import com.byclosure.webcattestingplatform.exceptions.PageObjectNotFoundException;
import com.byclosure.webcattestingplatform.pageobjects.IPageObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NavigationService {

    private final Map<String, PageObjectResolver> pageObjectResolverMapper;
    private final Map<String, PageObjectResolver> errorPageObjects;

    private final List<CriteriaMapper> errorPageObjectCriterias;


    public NavigationService(List<CriteriaMapper> errorPageObjectCriterias,
                             Map<String, PageObjectResolver> errorPageObjects) {
        this.errorPageObjectCriterias = errorPageObjectCriterias;
        this.errorPageObjects = errorPageObjects;
        this.pageObjectResolverMapper = new HashMap<String, PageObjectResolver>();
    }

    public NavigationService() {
        this(new ArrayList<CriteriaMapper>(), new HashMap<String, PageObjectResolver>());
    }

    public NavigationService register(String pageObjectName, ICriteria criteria, IPageObjectFactory pageFactory) {
        final PageObjectResolver pageFactoryService;
        if (pageObjectResolverMapper.containsKey(pageObjectName)) {
            pageFactoryService = pageObjectResolverMapper.get(pageObjectName);
        } else {
            pageFactoryService = new PageObjectResolver(errorPageObjectCriterias);
            pageObjectResolverMapper.put(pageObjectName, pageFactoryService);
        }

        NavigationService navigationService = new NavigationService(errorPageObjectCriterias, errorPageObjects);

        return pageFactoryService.register(criteria, pageFactory, navigationService);
    }

    public void registerErrorPage(String pageObjectName, ICriteria criteria, IPageObjectFactory pageFactory) {
        NavigationService navigationService = new NavigationService(errorPageObjectCriterias, errorPageObjects);
        errorPageObjectCriterias.add(new CriteriaMapper(criteria,
                navigationService, pageFactory));

        PageObjectResolver pageObjectResolver = new PageObjectResolver(errorPageObjectCriterias);
        errorPageObjects.put(pageObjectName, pageObjectResolver);
    }

    public <P extends IPageObject> P getPage(String name) {
        final P pageObject;
        if (pageObjectResolverMapper.containsKey(name)) {
            pageObject = pageObjectResolverMapper.get(name).getCurrentPage();
        } else if (errorPageObjects.containsKey(name)) {
            pageObject = errorPageObjects.get(name).getCurrentPage();
        } else {
            throw new InvalidPageObjectException("Page Object " + name + " is not registered.");
        }

        if (pageObject == null) throw new PageObjectNotFoundException("Could not find Page Object " + name);
        return pageObject;
    }

    public Map<String, PageObjectResolver> getAllPageObjectResolverMappers() {
        Map<String, PageObjectResolver> pageObjects = new HashMap<String, PageObjectResolver>();
        pageObjects.putAll(this.errorPageObjects);
        pageObjects.putAll(this.pageObjectResolverMapper);
        return pageObjects;
    }

}
