package com.tests.ui.android.xe.currency;

import com.ui.pageobjects.android.xe.currency.AddCurrencyActivity;
import com.ui.pageobjects.android.xe.currency.XeCurrencyActivity;
import com.framework.base.BaseAppiumAndroidTest;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

/**
 * Created by gadrea on 9/6/2015.
 */
public class TestsForXEAndroid extends BaseAppiumAndroidTest {

    String appPackage = "com.xe.currency";
    String appActivity = ".activity.XECurrency";

    @BeforeClass
    public void beforeClass() throws IOException {
        String[] strClassNameArray = this.getClass().getName().split("\\.");
        className = strClassNameArray[strClassNameArray.length - 1];
        super.beforeClass();
    }

    @BeforeMethod
    public void beforeMethod(Method method){
        super.beforeMethod(method);
        try{
            setAppiumAndroidDriver(appPackage, appActivity, "ASUS Zenfone 5", "http://0.0.0.0:4723/wd/hub");
        }
        catch(MalformedURLException e){
            System.out.println("Exception " + e);
        }
    }

    @Test
    public void testAddNewCurrency() throws InterruptedException {

        doAction.waitForAndroidActivity(appActivity, 20);
        XeCurrencyActivity objXE = new XeCurrencyActivity(driver, dictionary, environment,Reporter);
        AddCurrencyActivity objAddCurr = objXE.scrollToAddNewCurrency()
                                                .clickAddNewCurrency();

        doAction.waitForAndroidActivity(".activity.AddCurrency", 20);
        objAddCurr.searchAndAddCurrency("ZAR")
                .backToMainActivity();
        doAction.waitForAndroidActivity(appActivity, 20);
    }

    @Test
    public void testRemoveCurrency() throws InterruptedException {
        //Wait for activity
        doAction.waitForAndroidActivity(appActivity, 20);
        XeCurrencyActivity objXE = new XeCurrencyActivity(driver, dictionary, environment,Reporter);
        String currencyToRemove = "ZAR - South African Rand";
        objXE.removeCurrency(currencyToRemove);
    }

    @Test
    public void testSelectCurrency() throws InterruptedException {
        doAction.waitForAndroidActivity(appActivity, 20);
        XeCurrencyActivity objXE = new XeCurrencyActivity(driver, dictionary, environment,Reporter);
        String currency = "AUD";
        objXE.selectCurrency(currency)
            .openCurrencyCalculator()
            .tapCurrencyCalcButton("1.0")
            .tapEqualsButton();
    }

    @AfterMethod
    public void afterMethod(Method method) {
        super.afterMethod(method);
    }

    @AfterClass
    public void afterClass(){
        super.afterClass();
    }
}
