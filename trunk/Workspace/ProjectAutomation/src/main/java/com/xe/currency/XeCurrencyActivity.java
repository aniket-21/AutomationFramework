package com.xe.currency;

import com.automation.framework.core.Reporting;
import com.automation.framework.core.Wrappers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;

/**
 * Created by gadrea on 9/6/2015.
 */
public class XeCurrencyActivity {

    private Reporting Reporter;
    private io.appium.java_client.AppiumDriver driver;
    private HashMap<String, String> Dictionary;
    private HashMap<String, String> Environment;
    private Wrappers doAction;

    //UIObjects
    private String txtAddNewCurrencies = "uiautomator:=new UiSelector().text(\"Add More Currencies\")";
    private String imgCurrencyCalculator = "id:=com.xe.currency:id/calculatorIcon";
    private String imgEqualsButton = "id:=com.xe.currency:id/equal";

    //Constructor
    public XeCurrencyActivity(WebDriver GDriver,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
    {
        Reporter = GReporter;
        driver = (AppiumDriver) GDriver;
        Dictionary = GDictionary;
        Environment = GEnvironment;
        doAction = new Wrappers(driver, Reporter);
    }

    public XeCurrencyActivity scrollToAddNewCurrency(){
        driver.scrollToExact("Add More Currencies");
        return this;
    }

    public AddCurrencyActivity clickAddNewCurrency() {
        doAction.click(txtAddNewCurrencies);
        return new AddCurrencyActivity(driver,Dictionary,Environment,Reporter);
    }

    public void navigateToXeChart(String curCode){
        doAction.click("xpath:=//android.widget.ScrollView/android.widget.RelativeLayout/android.widget.TextView[text()='" + curCode + "']");
        //return new AddCurrencyActivity(driver,Dictionary,Environment,Reporter);
    }

    public XeCurrencyActivity removeCurrency(String curCode) throws InterruptedException {
        driver.scrollToExact(curCode);
        Thread.sleep(2000);
        WebElement dragElement = doAction.getElement("uiautomator:=new UiSelector().text(\"" + curCode + "\")");
        WebElement dropElement = doAction.getElement("id:=com.xe.currency:id/menu_share_item");
        TouchAction dragNDrop = new TouchAction(driver).longPress(dragElement).moveTo(dropElement).release();
        dragNDrop.perform();

        return this;
    }

    public XeCurrencyActivity selectCurrency(String curCode) throws InterruptedException {
        driver.scrollTo(curCode);
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



}
