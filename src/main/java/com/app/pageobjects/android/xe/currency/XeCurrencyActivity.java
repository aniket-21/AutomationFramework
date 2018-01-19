package com.app.pageobjects.android.xe.currency;

import com.framework.core.Reporting;
import com.framework.core.Wrappers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by gadrea on 9/6/2015.
 */
public class XeCurrencyActivity {

    private Reporting Reporter;
    private io.appium.java_client.AppiumDriver driver;
    private Wrappers doAction;

    //UIObjects
    private String txtAddNewCurrencies = "id:=com.xe.currency:id/add_cur_row_label";
    private String imgCurrencyCalculator = "id:=com.xe.currency:id/calculatorIcon";
    private String imgEqualsButton = "id:=com.xe.currency:id/equal";
    private String btnNo = "uiautomator:=new UiSelector().text(\"NO\")";

    //Constructor
    public XeCurrencyActivity(WebDriver GDriver, Reporting GReporter)
    {
        Reporter = GReporter;
        driver = (AppiumDriver) GDriver;
        doAction = new Wrappers(driver, Reporter);
    }

    public XeCurrencyActivity scrollToAddNewCurrency(){
        //scrollToExact method is deprecated. Need to replace it with swipe
        //driver.scrollToExact("Add More Currencies");

        return this;
    }

    public AddCurrencyActivity clickAddNewCurrency() {
        doAction.click(txtAddNewCurrencies);
        doAction.waitForAndroidActivity(".activity.AddCurrency", 20);
        return new AddCurrencyActivity(driver, Reporter);
    }

    public void navigateToXeChart(String curCode){
        doAction.click("xpath:=//android.widget.ScrollView/android.widget.RelativeLayout/android.widget.TextView[text()='" + curCode + "']");
        //return new AddCurrencyActivity(driver,dictionary,environment,Reporter);
    }

    public XeCurrencyActivity removeCurrency(String curCode) throws InterruptedException {
        //scrollToExact method is deprecated. Need to replace it with swipe
        //driver.scrollToExact(curCode);
        Thread.sleep(2000);
        WebElement dragElement = doAction.getElement("uiautomator:=new UiSelector().text(\"" + curCode + "\")");
        WebElement dropElement = doAction.getElement("id:=com.xe.currency:id/menu_share_item");
        TouchAction dragNDrop = new TouchAction(driver).longPress(dragElement).moveTo(dropElement).release();
        dragNDrop.perform();

        return this;
    }

    public XeCurrencyActivity selectCurrency(String curCode) throws InterruptedException {
        //scrollToExact method is deprecated. Need to replace it with swipe
        //driver.scrollTo(curCode);
        Thread.sleep(2000);
        doAction.click("uiautomator:=new UiSelector().textContains(\"" + curCode + "\")");
        return this;
    }

    public XeCurrencyActivity openCurrencyCalculator(){
        doAction.click(imgCurrencyCalculator);
        return this;
    }

    public XeCurrencyActivity tapCurrencyCalcButton(String buttonText){
        doAction.click("uiautomator:=new UiSelector().text(\"" + buttonText + "\")");
        return this;
    }

    public XeCurrencyActivity tapEqualsButton(){
        doAction.click(imgEqualsButton);
        return this;
    }

    public XeCurrencyActivity denyTour() {
        doAction.click(btnNo);
        return this;
    }



}
