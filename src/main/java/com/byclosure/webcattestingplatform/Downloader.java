package com.byclosure.webcattestingplatform;

import com.byclosure.webcattestingplatform.exceptions.InvalidWebDriverInstanceException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Date;

/**
 * This class implements a downloader to use when there is a need to download a file from the server.
 * It uses WebDriver to communicate with the current page.
 * The WebDriver instance passed in the constructor must implement the <pre>JavascriptExecutor</pre> interface.
 */
public class Downloader {

    final private WebDriver driver;
    final private static int WAITING_TIME = 10;

    public Downloader(WebDriver driver) {
        if(!(driver instanceof JavascriptExecutor)) {
            throw new InvalidWebDriverInstanceException("The given instance of the WebDriver " +
                    "does not implement the JavascriptExecutor interface");
        }

        this.driver = driver;
    }

    /**
     * Downloads the file at a given URL.
     * WARN: this method injects a javascript closure in the page code and creates a random variable in the <pre>window</pre>
     * variable, where it temporarily stores the file content.
     * @param url the URL of the file
     * @return
     */
    public Base64String downloadFile(String url) {
        //TODO protect against an invalid URL

        final String windowResponseVar = "rsp_variable" + new Date().getTime();

        ((JavascriptExecutor) driver).executeScript(getDownloadScript(windowResponseVar, url));

        WebDriverWait wait = new WebDriverWait(driver, WAITING_TIME);
        String file = wait.until(new ExpectedCondition<String>() {
            public String apply(WebDriver driver) {
                String obj = (String) ((JavascriptExecutor) driver).executeScript("return window." + windowResponseVar);
                if(obj == null) {
                    throw new NoSuchElementException("Object Not Found");
                }
                return obj;
            }
        });

        return new Base64String(file.replace("data:application/octet-stream;base64,", ""));
    }

    private String getDownloadScript(String windowResponseVar, String url) {
        return "(function() {\n" +
                "var downloadFile = function(url) {\n" +
                "    var request = new XMLHttpRequest();\n" +
                "    request.open('GET', url, true);\n"+
                "    request.responseType = 'blob';\n"+
                "    request.onload = function(e) {\n"+
                "        if(this.status == 200) {\n"+
                "            var reader = new FileReader();\n"+
                "            reader.onload = function(e) {\n"+
                "                window." + windowResponseVar + " = event.target.result;\n"+
                "            }\n"+
                "            reader.readAsDataURL(this.response);\n"+
                "        }\n"+
                "    }\n"+
                "    request.send();\n"+
                "}\n"+
                "downloadFile('" + url + "');\n"+
                "})()";
    }

}
