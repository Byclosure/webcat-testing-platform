package com.byclosure.webcat.stepdefinitions;

import com.byclosure.webcat.context.IContext;
import com.byclosure.webcat.stepdefinitions.helpers.SeleniumInteractions;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;

public class SeleniumInteractionsClickLink {

    private static final String PAGES_URL_PATH = "http://localhost:63342/webcat-testing-platform/com/byclosure/webcat/stepdefinitions/";
    private static final String MAIN_PAGE = "links.html";
    private static final String ID_LINK_PAGE = "id_link.html";
    private static final String IMAGE_ALT_LINK_PAGE = "image_alt_link.html";
    private static final String TEXT_LINK_PAGE = "text_link.html";
    private static final String TITLE_LINK_PAGE = "title_link.html";
    private static PhantomJSDriverService service;

    private WebDriver driver;
    private SeleniumInteractions seleniumInteractions;
    @Mock private IContext context;

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
    public void itShouldFindLinkByText() {
        seleniumInteractions.clickLink("text link");

        Assert.assertEquals(PAGES_URL_PATH + TEXT_LINK_PAGE, driver.getCurrentUrl());
    }

    @Test
    public void itShouldFindLinkByIdAttribute() {
        seleniumInteractions.clickLink("link_id");

        Assert.assertEquals(PAGES_URL_PATH + ID_LINK_PAGE, driver.getCurrentUrl());
    }

    @Test
    public void itShouldFindLinkByTitleAttribute() {
        seleniumInteractions.clickLink("title link");

        Assert.assertEquals(PAGES_URL_PATH + TITLE_LINK_PAGE, driver.getCurrentUrl());
    }

    @Test
    public void itShouldFindLinkByImageAltAttribute() {
        seleniumInteractions.clickLink("image alt text");

        Assert.assertEquals(PAGES_URL_PATH + IMAGE_ALT_LINK_PAGE, driver.getCurrentUrl());
    }

}
