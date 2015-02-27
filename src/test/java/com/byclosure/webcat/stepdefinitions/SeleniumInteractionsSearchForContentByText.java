package com.byclosure.webcat.stepdefinitions;

import com.byclosure.webcat.context.IContext;
import com.byclosure.webcat.stepdefinitions.helpers.SeleniumInteractions;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;

public class SeleniumInteractionsSearchForContentByText {
    private static final String PAGES_URL_PATH = "http://localhost:63342/webcat-testing-platform/com/byclosure/webcat/stepdefinitions/";
    private static final String MAIN_PAGE = "search_body.html";
    private static PhantomJSDriverService service;

    private WebDriver driver;
    private SeleniumInteractions seleniumInteractions;
    @Mock
    private IContext context;

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

        seleniumInteractions = new SeleniumInteractions(driver, context);
        driver.get(PAGES_URL_PATH + MAIN_PAGE);
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
