package com.byclosure.webcattestingplatform;

import com.byclosure.webcattestingplatform.exceptions.InvalidWebDriverInstanceException;
import com.byclosure.webcattestingplatform.exceptions.URLMalformedException;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.mockito.Mockito.when;

public class TestDownloaderInitialization {

    @Mock private RemoteWebDriver driver;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockCalls();
    }

    private void mockCalls() {
        when(driver.executeScript(Matchers.startsWith("(function()"))).thenReturn(null);

    }

    @Test(expected = InvalidWebDriverInstanceException.class)
    public void itShould_ThrowException_OnInstantiateWithoutJavascriptExecutionSupport() {
        WebDriver webDriver = Mockito.mock(WebDriver.class);

        new Downloader(webDriver);
    }

    @Test(expected = URLMalformedException.class)
    public void itShould_ThrowException_WhenTryToDownloadFromInvalidURL() {
        Downloader downloader = new Downloader(driver);

        downloader.downloadFile("htp:someinvalidURL");
    }
}
