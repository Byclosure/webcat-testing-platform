package com.byclosure.webcat.stepdefinitions;

import com.byclosure.webcat.stepdefinitions.helpers.SeleniumInteractions;
import org.junit.*;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SeleniumInteractionsClickLink {
    private static final String MAIN_PAGE = "links.html";
    private static final String ID_LINK_PAGE = "id_link.html";
    private static final String IMAGE_ALT_LINK_PAGE = "image_alt_link.html";
    private static final String TEXT_LINK_PAGE = "text_link.html";
    private static final String TITLE_LINK_PAGE = "title_link.html";
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
    public void itShouldFindLinkByText() {
        seleniumInteractions.clickLink("text link");
        
        Assert.assertTrue(driver.getCurrentUrl().endsWith(TEXT_LINK_PAGE));
    }

    @Test
    public void itShouldFindLinkByIdAttribute() {
        seleniumInteractions.clickLink("link_id");

        Assert.assertTrue(driver.getCurrentUrl().endsWith(ID_LINK_PAGE));
    }

    @Test
    public void itShouldFindLinkByTitleAttribute() {
        seleniumInteractions.clickLink("title link");

        Assert.assertTrue(driver.getCurrentUrl().endsWith(TITLE_LINK_PAGE));
    }

    @Test
    public void itShouldFindLinkByImageAltAttribute() {
        seleniumInteractions.clickLink("image alt text");

        Assert.assertTrue(driver.getCurrentUrl().endsWith(IMAGE_ALT_LINK_PAGE));
    }

}
