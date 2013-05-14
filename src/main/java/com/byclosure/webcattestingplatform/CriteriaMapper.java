package com.byclosure.webcattestingplatform;

import com.byclosure.webcattestingplatform.criterias.ICriteria;
import com.byclosure.webcattestingplatform.pageobjects.IPageObject;

/**
 *
 */
public class CriteriaMapper {

    private final ICriteria criteria;
    private final NavigationService navigationService;
    private final IPageObjectFactory<IPageObject> pageObjectFactory;

    public CriteriaMapper(ICriteria criteria,
                          NavigationService navigationService,
                          IPageObjectFactory<IPageObject> pageObjectFactory) {
        this.criteria = criteria;
        this.navigationService = navigationService;
        this.pageObjectFactory = pageObjectFactory;
    }

    public ICriteria getCriteria() {
        return this.criteria;
    }

    public NavigationService getNavigationService() {
        return navigationService;
    }

    public IPageObjectFactory<?> getPageObjectFactory() {
        return this.pageObjectFactory;
    }
}
