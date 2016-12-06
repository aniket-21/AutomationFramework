package com.ui.pageobjects.android.xe.currency;

import com.framework.core.Reporting;
import com.framework.core.Wrappers;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;

/**
 * Created by gadrea on 9/6/2015.
 */
public class AddCurrencyActivity {

    private Reporting Reporter;
    private io.appium.java_client.AppiumDriver driver;
    private HashMap<String, String> Dictionary;
    private HashMap<String, String> Environment;
    private Wrappers doAction;

    //UIObjects
    private String edtSearchCurrencies = "classname:=android.widget.EditText";
    private String txtCurrency = "id:=com.xe.currency:id/codeAndName";
    private String imgBack = "accessibility_id:=Navigate up";

    //Constructor
    public AddCurrencyActivity(WebDriver GDriver, HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment, Reporting GReporter)
    {
        Reporter = GReporter;
        driver = (AppiumDriver) GDriver;
        Dictionary = GDictionary;
        Environment = GEnvironment;
        doAction = new Wrappers(driver, Reporter);
    }

    public AddCurrencyActivity searchAndAddCurrency(String currencyCode) throws InterruptedException {
        doAction.enterText(edtSearchCurrencies, currencyCode);
        driver.hideKeyboard();
        List<WebElement> txtCurrencies = doAction.getElements(txtCurrency);
        Assert.assertEquals(txtCurrencies.size(), 1);
        doAction.click(txtCurrencies.get(0));

        Assert.assertTrue(doAction.isWebElementDisplayed("uiautomator:=new UiSelector().text(\"" + currencyCode.toUpperCase() + " added successfully\")"));
        return this;
    }

    public XeCurrencyActivity backToMainActivity(){
        doAction.click(imgBack);
        return new XeCurrencyActivity(driver,Dictionary,Environment,Reporter);
    }
}
