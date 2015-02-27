package com.byclosure.webcat.stepdefinitions;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;

public class SeleniumInteractionsClickLink {

    private static PhantomJSDriverService service;
    private WebDriver driver;

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
        driver = new RemoteWebDriver(service.getUrl(), DesiredCapabilities.phantomjs());
        driver.get("http://localhost:63342/webcat-testing-platform/com/byclosure/webcat/stepdefinitions/links.html");
    }

    @Test
    public void itShouldFindLinkByText() {
        //TODO
    }

    @Test
    public void itShouldFindLinkByIdAttribute() {
        //TODO
    }

    @Test
    public void itShouldFindLinkByTitleAttribute() {
        //TODO
    }

    @Test
    public void itShouldFindLinkByImageAltAttribute() {
        //TODO
    }

}
