package com.avis.qa.core;


import com.avis.qa.listeners.report.ExtentListener;
import com.avis.qa.listeners.report.ExtentManager;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.xml.XmlTest;

import java.io.IOException;

import static com.avis.qa.core.Configuration.*;


/**
 * Class contains the Pre-requisite setup before running a Test Case
 *
 * @author ikumar
 */
@Listeners({ExtentListener.class})
@Log4j2
public class TestBase {

    private final ThreadLocal<BrowserInstance> appInstance = new ThreadLocal<>();

    @BeforeTest(alwaysRun = true)
    public void startTest(XmlTest xmlTest) {
        Configuration.setTestNGParameters(xmlTest);
        Configuration.setURL();
        ExtentListener.extent = ExtentManager.createInstance();
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethodTestBase() {
        log.info("beforeMethodTestBase() called");
        if (DOCKER.equalsIgnoreCase("true"))
            appInstance.set(new DockerInstance(BROWSER));
        else
            appInstance.set(new BrowserInstance(BROWSER));
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethodTestBase(ITestResult result) throws IOException {
        log.info("afterMethodTestBase() called");
        getDriver().quit();
    }

    private BrowserInstance getBrowserInstance() {
        return appInstance.get();
    }

    public void launchUrl(String url) {
        getBrowserInstance().start(url);
    }

    public void launchUrl() {
        getBrowserInstance().start(URL);
    }

    public WebDriver getDriver() {
        return getBrowserInstance().getDriver();
    }
}
