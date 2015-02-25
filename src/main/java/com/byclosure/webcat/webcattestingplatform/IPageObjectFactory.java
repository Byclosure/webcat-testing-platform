package com.byclosure.webcat.webcattestingplatform;

import com.byclosure.webcat.webcattestingplatform.pageobjects.IPageObject;

/**
 *
 */
public interface IPageObjectFactory<P extends IPageObject> {
    P build(NavigationService navigationService);
}
