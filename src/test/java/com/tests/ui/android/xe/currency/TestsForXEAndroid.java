package com.tests.ui.android.xe.currency;

import com.app.enums.xe.XeConfigKeys;
import com.app.enums.xe.XeDataKeys;
import com.app.pageobjects.android.xe.currency.AddCurrencyActivity;
import com.app.pageobjects.android.xe.currency.WelcomeActivity;
import com.app.pageobjects.android.xe.currency.XeCurrencyActivity;
import com.framework.base.BaseAppiumAndroidTest;


import com.framework.handlers.DataHandler;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Set;

/**
 * Created by gadrea on 9/6/2015.
 */
public class TestsForXEAndroid extends BaseAppiumAndroidTest {

    private final String configFile = "xeconfig.json";
    private final String dataFile = "/xe/staticdata.json";

    private String appPackage;
    private String appActivity;
    DataHandler dHandler;



    @BeforeClass
    public void beforeClass() throws IOException {
        setClassName(this);
        super.beforeClass();

        dHandler = new DataHandler(configFile, dataFile);
        appPackage = dHandler.getAppConfig(XeConfigKeys.PACKAGE_NAME).getAsString();
        appActivity = dHandler.getAppConfig(XeConfigKeys.LAUNCHER_ACTIVITY).getAsString();

    }

    @BeforeMethod
    public void beforeMethod(Method method) throws MalformedURLException {
        super.beforeMethod(method);
        setAppiumAndroidDriver(appPackage, appActivity, "ASUS Zenfone 5", "http://0.0.0.0:4723/wd/hub");
    }

    @Test
    public void testAddNewCurrency() throws InterruptedException {

        Set<String> handles = ((AndroidDriver<AndroidElement>)driver).getContextHandles();
        for(String handle : handles) {
            System.out.println(handle);
        }

        WelcomeActivity objWelcomeActivity = new WelcomeActivity(driver ,Reporter);
        AddCurrencyActivity objAddCurr = objWelcomeActivity.clickNoThanks()
                .getStarted()
                .denyTour()
                .scrollToAddNewCurrency()
                .clickAddNewCurrency();


        objAddCurr.searchAndAddCurrency(dHandler.getAppData(XeDataKeys.CURRENCY_TO_ADD).getAsString())
                .backToMainActivity();
    }

/*    @Test
    public void testRemoveCurrency() throws InterruptedException {
        //Wait for activity
        doAction.waitForAndroidActivity(appActivity, 20);
        XeCurrencyActivity objXE = new XeCurrencyActivity(driver, Reporter);
        String currencyToRemove = "ZAR - South African Rand";
        objXE.removeCurrency(currencyToRemove);
    }

    @Test
    public void testSelectCurrency() throws InterruptedException {
        doAction.waitForAndroidActivity(appActivity, 20);
        XeCurrencyActivity objXE = new XeCurrencyActivity(driver, Reporter);
        String currency = "AUD";
        objXE.selectCurrency(currency)
            .openCurrencyCalculator()
            .tapCurrencyCalcButton("1.0")
            .tapEqualsButton();
    }

    */

    @AfterMethod
    public void afterMethod(Method method) {
        super.afterMethod(method);
    }

    @AfterClass
    public void afterClass(){
        super.afterClass();
    }
}
