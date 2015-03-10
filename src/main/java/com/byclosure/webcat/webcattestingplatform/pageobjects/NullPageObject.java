package com.byclosure.webcat.webcattestingplatform.pageobjects;

import com.byclosure.webcat.context.Context;
import com.byclosure.webcat.context.IContext;
import com.byclosure.webcat.webcattestingplatform.NavigationService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.UnreachableBrowserException;


public class NullPageObject extends PageObject {
    private static Logger logger =  Logger.getLogger(NullPageObject.class);

    public NullPageObject(WebDriver driver, NavigationService navigationService) {
        super(driver, navigationService);
    }

    @Override
    protected void takeScreenshotImpl(String methodName) {
        try {
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            final IContext context = Context.getInstance();
            context.addScreenshot(screenshot);
        }
        catch(UnreachableBrowserException ex) {
            logger.log(Level.WARN, "Could not reach browser to take screenshot");
        }
        catch(WebDriverException webDriverEx) {
            if(webDriverEx.getMessage().contains("Session not found")) {
                logger.log(Level.WARN, "Could not reach browser to take screenshot");
            } else {
                throw webDriverEx;
            }
        }
    }
}
