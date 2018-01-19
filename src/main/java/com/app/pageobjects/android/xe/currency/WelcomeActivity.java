package com.app.pageobjects.android.xe.currency;

import com.framework.core.Reporting;
import com.framework.core.Wrappers;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;

/**
 * Created by i337111 on 11/01/18.
 */
public class WelcomeActivity {

    private Reporting Reporter;
    private io.appium.java_client.AppiumDriver driver;
    private Wrappers doAction;

    //UIObjects
    private String btnNoThanks = "uiautomator:=new UiSelector().textContains(\"No Thanks\")";
    private String lnkGetStarted = "id:=com.xe.currency:id/toursummary_image";

    //Constructor
    public WelcomeActivity(WebDriver GDriver, Reporting GReporter)
    {
        Reporter = GReporter;
        driver = (AppiumDriver) GDriver;
        doAction = new Wrappers(driver, Reporter);
    }

    public WelcomeActivity clickNoThanks() {
        doAction.click(btnNoThanks);
        return this;
    }

    public XeCurrencyActivity getStarted() {
        doAction.click(lnkGetStarted);
        return new XeCurrencyActivity(driver, Reporter);
    }

}
