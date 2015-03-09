package com.byclosure.webcat.webcattestingplatform.pageobjects;

public interface IPageObject {

    <P extends IPageObject> P cast();
    void takeScreenshot(String methodName);
}
