package com.byclosure.webcattestingplatform.pageobjects;

/**
 *
 */
public interface IPageObject {

    <P extends IPageObject> P cast();
}
