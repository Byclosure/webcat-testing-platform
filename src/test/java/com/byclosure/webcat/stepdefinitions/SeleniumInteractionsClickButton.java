package com.byclosure.webcat.stepdefinitions;

import com.byclosure.webcat.context.IContext;
import com.byclosure.webcat.stepdefinitions.helpers.SeleniumInteractions;
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
import java.net.URL;

public class SeleniumInteractionsClickButton {
    private static final String MAIN_PAGE = "buttons.html";
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
        final URL mainPageURL = getClass().getResource(MAIN_PAGE);
        driver.get(mainPageURL.toString());
    }
    
    @Test
    public void itShouldFindButtonElementByText() {
        seleniumInteractions.clickButton("Button text");
    }
    
    @Test
    public void itShouldFindButtonElementByValue() {
        seleniumInteractions.clickButton("button with value");
    }
    
    @Test
    public void itShouldFindButtonElementByTitle() {
        seleniumInteractions.clickButton("button title");
    }
    
    @Test
    public void itShouldFindButtonElementById() {
        seleniumInteractions.clickButton("button_id");
    }
}