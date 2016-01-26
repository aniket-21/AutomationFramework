package com.automation.framework.base;

import com.automation.framework.core.Wrappers;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Proxy;
import org.testng.Assert;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * Created by gadrea on 11/6/2015.
 */
public class BaseAppiumAndroidTest extends BaseTest {
    AppiumDriverLocalService service;

    public void beforeClass() throws IOException {
        browser = "ANDROID";
        super.beforeClass();

    /*    //start appium server
        service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder());
        service.start();
        Assert.assertTrue(service.isRunning(), "Appium server is not running"); */
    }

    public void afterClass(){
        super.afterClass();

    /*    //Stop Service
        if(service.isRunning())
            service.stop(); */
    }

    public void setAppiumAndroidDriver(String appPackage, String appActivity, String deviceName, String serverURL) throws MalformedURLException {
        if(driver == null){
            driver = asapDriver.getAppiumAndroidDriver(appPackage,appActivity,deviceName,serverURL);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            Reporter.setDriver(driver);

            //Initialize Common functions
            doAction = new Wrappers(driver,Reporter);
        }
    }

    public void setAppiumChromeDriver(String deviceName, String serverURL, Proxy proxy) throws MalformedURLException, UnknownHostException {
        if(driver==null){
            driver = asapDriver.getAndroidChromeDriver(deviceName,serverURL,proxy);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            Reporter.setDriver(driver);

            //Initialize Common functions
            doAction = new Wrappers(driver,Reporter);
        }
    }
}
