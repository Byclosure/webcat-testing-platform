package com.byclosure.webcattestingplatform;

import com.byclosure.webcattestingplatform.exceptions.InvalidWebDriverInstanceException;
import junit.framework.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.mockito.Mockito.when;

public class DownloaderTestCase {

    @Mock private RemoteWebDriver driver;

    public DownloaderTestCase() {
        MockitoAnnotations.initMocks(this);

        mockCalls();
    }

    private void mockCalls() {
        when(driver.executeScript(Matchers.startsWith("(function()"))).thenReturn(null);
        when(driver.executeScript(Matchers.startsWith("return window."))).thenReturn("data:application/octet-stream;base64,ThisIsAMockOfABase64String");
    }

    @Test(expected = InvalidWebDriverInstanceException.class)
    public void itShould_ThrowException_OnInstantiateWithoutJavascriptExecutionSupport() {
        WebDriver webDriver = Mockito.mock(WebDriver.class);

        new Downloader(webDriver);
    }

    @Test(expected = RuntimeException.class)
    public void itShould_ThrowException_WhenTryToDownloadFromInvalidURL() {
        Downloader downloader = new Downloader(driver);

        downloader.downloadFile("htp:someinvalidURL");
    }

    @Test
    public void itShould_NotReturnContentType_InTheBeginOfFile() {
        Downloader downloader = new Downloader(driver);
        Base64String fileBase64 = downloader.downloadFile("http://someurl.com/");

        Assert.assertFalse(fileBase64.toString().startsWith("data:application/octet-stream;base64"));
    }


}
