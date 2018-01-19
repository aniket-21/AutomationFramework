package com.app.pageobjects.android.xe.currency;

import com.framework.core.Reporting;
import com.framework.core.Wrappers;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

/**
 * Created by gadrea on 9/6/2015.
 */
public class AddCurrencyActivity {

    private Reporting Reporter;
    private io.appium.java_client.AppiumDriver driver;
    private Wrappers doAction;

    //UIObjects
    private String btnSearch = "id:=com.xe.currency:id/search_button";
    private String edtSearchCurrencies = "id:=com.xe.currency:id/search_src_text";
    private String txtCurrency = "id:=com.xe.currency:id/codeAndName";
    private String imgBack = "accessibility_id:=Navigate up";

    //Constructor
    public AddCurrencyActivity(WebDriver GDriver, Reporting GReporter)
    {
        Reporter = GReporter;
        driver = (AppiumDriver) GDriver;
        doAction = new Wrappers(driver, Reporter);
    }

    public AddCurrencyActivity searchAndAddCurrency(String currencyCode) throws InterruptedException {
        doAction.click(btnSearch);
        doAction.enterText(edtSearchCurrencies, currencyCode);
        driver.hideKeyboard();
        List<WebElement> txtCurrencies = doAction.getElements(txtCurrency);
        Assert.assertEquals(txtCurrencies.size(), 1);
        doAction.click(txtCurrencies.get(0));

        Assert.assertTrue(doAction.isElementDisplayed("uiautomator:=new UiSelector().text(\"" + currencyCode.toUpperCase() + " added successfully\")"));
        return this;
    }

    public XeCurrencyActivity backToMainActivity(){
        doAction.click(imgBack);
        doAction.waitForAndroidActivity(".activity.XECurrency", 20);
        return new XeCurrencyActivity(driver, Reporter);
    }
}
