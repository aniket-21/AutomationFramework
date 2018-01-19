package com.framework.base;

import com.framework.core.Wrappers;
import org.openqa.selenium.Proxy;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * Created by gadrea on 11/6/2015.
 */
public class BaseAppiumAndroidTest extends BaseTest {

    public void beforeClass() throws IOException {
        browser = "ANDROID";
        super.beforeClass();
    }

    protected void setAppiumAndroidDriver(String appPackage, String appActivity, String deviceName, String serverURL) throws MalformedURLException {

        if(driver == null) {
            driver = execDriver.getAppiumAndroidDriver(appPackage, appActivity, deviceName, serverURL);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            Reporter.setDriver(driver);

            //Initialize Common functions
            doAction = new Wrappers(driver,Reporter);
            doAction.waitForAndroidActivity(appActivity, 20);
        }
    }

    protected void setAppiumChromeDriver(String deviceName, String serverURL, Proxy proxy) throws MalformedURLException, UnknownHostException {

        if(driver == null) {
            driver = execDriver.getAndroidChromeDriver(deviceName, serverURL, proxy);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            Reporter.setDriver(driver);

            //Initialize Common functions
            doAction = new Wrappers(driver,Reporter);
        }
    }

    public void afterClass(){
        super.afterClass();
    }
}
