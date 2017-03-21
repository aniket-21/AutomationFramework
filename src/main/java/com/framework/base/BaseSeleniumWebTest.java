package com.framework.base;

import com.framework.core.Wrappers;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

/**
 * Created by gadrea on 11/6/2015.
 */
public class BaseSeleniumWebTest extends BaseTest {

    public void afterMethod(Method method){
        super.afterMethod(method);

        if(driver != null) {
            try{
                driver.quit();
                driver = null;
            }
            catch (Exception e){}
        }
    }

    protected void setWebDriver() throws MalformedURLException {
        if(driver==null){
            driver = asapDriver.getWebDriver(browser);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            Reporter.setDriver(driver);
            doAction = new Wrappers(driver,Reporter);
        }
    }

    protected void setRemoteWebDriver(String hubURL, DesiredCapabilities dc) throws MalformedURLException {
        if(driver==null){
            driver = asapDriver.getRemoteWebDriver("http://localhost:4444/wd/hub",dc);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            Reporter.setDriver(driver);
            doAction = new Wrappers(driver,Reporter);
        }
    }
}
