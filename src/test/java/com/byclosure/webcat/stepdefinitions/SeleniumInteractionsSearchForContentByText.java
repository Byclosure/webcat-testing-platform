package com.byclosure.webcat.stepdefinitions;

import com.byclosure.webcat.stepdefinitions.helpers.SeleniumInteractions;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SeleniumInteractionsSearchForContentByText {
    private static final String MAIN_PAGE = "search_body.html";
    private static PhantomJSDriverService service;

    private WebDriver driver;
    private SeleniumInteractions seleniumInteractions;

    @BeforeClass
    public static void setupEnv() throws IOException {
        String phantomJSPath = System.getProperty("phantomjs.binary");
        File phantomJSExec = new File(phantomJSPath);
        service = new PhantomJSDriverService.Builder().usingPhantomJSExecutable(phantomJSExec).build();
        service.start();
    }

    @AfterClass
    public static void tearDownEnv() {
        service.stop();
    }

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);

        driver = new RemoteWebDriver(service.getUrl(), DesiredCapabilities.phantomjs());
        seleniumInteractions = new SeleniumInteractions(driver);
        
        final URL mainPageURL = getClass().getResource(MAIN_PAGE);
        driver.get(mainPageURL.toString());
    }

    @Test
    public void itShouldFindTextThatExistsInTheBody() {
        Assert.assertTrue(seleniumInteractions.searchForContent("viverra vel mauris"));
    }

    @Test
    public void itShouldNotFindContentThatExistsInTheTitle() {
        Assert.assertFalse(seleniumInteractions.searchForContent("with body"));
    }

    @Test
    public void itShouldNotFindHTMLTags() {
        Assert.assertFalse(seleniumInteractions.searchForContent("<head>"));
    }
}
