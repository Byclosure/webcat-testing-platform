package com.byclosure.webcattestingplatform;

import com.byclosure.webcattestingplatform.criterias.ICriteria;
import com.byclosure.webcattestingplatform.exceptions.PageObjectNotFound;
import com.byclosure.webcattestingplatform.pageobjects.IPageObject;

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

    public <P extends IPageObject> P getCurrentPage(){
        int secs = Configs.WAIT_IN_SECS;

        List<CriteriaMapper> allPageObjects = new ArrayList<CriteriaMapper>();
        allPageObjects.addAll(pageObjectMapper);
        allPageObjects.addAll(errorPageObjects);

        while(secs>0) {
            for(CriteriaMapper pageObjectMap : allPageObjects) {
                if(pageObjectMap.getCriteria().match()) {
                    final NavigationService navigationService = pageObjectMap.getNavigationService();
                    final IPageObjectFactory factory = pageObjectMap.getPageObjectFactory();

                    return factory.build(navigationService).cast();
                }
            }
            try {
                logger.fine("Going to sleep");
                Thread.sleep(Configs.SLEEP_IN_MILLIS);
            } catch (InterruptedException e) {
                throw new PageObjectNotFound(e);
            }
            secs--;
        }

        return null;
    }

    public List<CriteriaMapper> getPageObjectCriterias() {
        return this.pageObjectMapper;
    }
}
