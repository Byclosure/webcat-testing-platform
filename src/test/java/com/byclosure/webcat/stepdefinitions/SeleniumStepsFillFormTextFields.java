package com.byclosure.webcat.stepdefinitions;

import com.byclosure.webcat.stepdefinitions.helpers.SeleniumInteractions;
import org.junit.*;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SeleniumStepsFillFormTextFields {
    private static final String MAIN_PAGE = "form.html";
    private static PhantomJSDriverService service;
    
    private static final String TEXT = "asdf";
    private static final String INPUT_ID = "input_text_1";
    private static final String INPUT_NAME = "text_input_1_name";
    private static final String INPUT_LABEL_TEXT = "Input 1:";
    private static final String INPUT_CLASS = "text-input";
    
    
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
    public void itShouldFindTextInputById() {
        seleniumInteractions.fillFormTextField(INPUT_ID, TEXT);
        WebElement element = driver.findElement(By.id(INPUT_ID));
        Assert.assertEquals(TEXT, element.getAttribute("value"));
    }
    
    @Test
    public void itShouldFindTextInputByName() {
        seleniumInteractions.fillFormTextField(INPUT_NAME, TEXT);
        WebElement element = driver.findElement(By.id(INPUT_ID));
        Assert.assertEquals(TEXT, element.getAttribute("value"));
    }
    
    @Test
    public void itShouldFindTextInputByLabelText() {
        seleniumInteractions.fillFormTextField(INPUT_LABEL_TEXT, TEXT);
        WebElement element = driver.findElement(By.id(INPUT_ID));
        Assert.assertEquals(TEXT, element.getAttribute("value"));
    }
    
    @Test
    public void itShouldFindTextInputByCSSSelector() {
        seleniumInteractions.fillFormTextField("." + INPUT_CLASS, TEXT);
        WebElement element = driver.findElement(By.id(INPUT_ID));
        Assert.assertEquals(TEXT, element.getAttribute("value"));
    }
}
