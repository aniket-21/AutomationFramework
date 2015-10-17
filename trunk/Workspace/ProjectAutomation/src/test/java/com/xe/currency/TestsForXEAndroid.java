package com.xe.currency;

import com.automation.framework.Driver;
import com.automation.framework.Reporting;
import com.automation.framework.Wrappers;
import com.xe.currency.XeCurrencyActivity;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by gadrea on 9/6/2015.
 */
public class TestsForXEAndroid {

    //Variables
    String className;
    String env;
    String appPackage = "com.xe.currency";
    String appActivity = ".activity.XECurrency";

    //Instances
    Driver asapDriver;
    WebDriver driver;
    Wrappers doAction;

    HashMap<String, String> Environment = new HashMap<String, String>();
    HashMap<String, String> Dictionary = new HashMap<String, String>();
    Reporting Reporter;

    @BeforeClass
    public void beforeClass() throws IOException {

        //Set the DataSheet name by getting the class name
        String[] strClassNameArray = this.getClass().getName().split("\\.");
        className = strClassNameArray[strClassNameArray.length - 1];
        Environment.put("CLASSNAME", className);
        Environment.put("BROWSER", "appium");

        System.out.println("Before Class " + className);

        //Initiate asapDriver
        asapDriver = new Driver(Dictionary, Environment);

        //Get Env
        env = System.getProperty("envName");
        Assert.assertNotNull(env, "No Environment Parameter value received");

        //Add env global environments
        Environment.put("ENV_CODE", env);
        Assert.assertTrue(asapDriver.createExecutionFolders(), "Creating Execution Folders");
        Assert.assertTrue(asapDriver.fetchEnvironmentDetailsFromConfigXML(), "Fetching Environment Details");

        //Instantiate reporter
        Reporter = new Reporting(Dictionary, Environment);
        Reporter.createSummaryReport();
        Reporter.createJenkinsReport();
    }

    @BeforeMethod
    public void beforeMethod(Method method) throws MalformedURLException {
        String testName = method.getName();
        System.out.println("Before Method for test " + testName);
        asapDriver.getDataForTest(testName);
        Reporter.createTestLevelReport(testName);

        //Initiate WebDriver
        if(driver == null){
            driver = asapDriver.getAppiumAndroidDriver(appPackage,appActivity,"ASUS Zenfone 5","http://0.0.0.0:4723/wd/hub");
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            Reporter.setDriver(driver);

            //Initialize Common functions
            doAction = new Wrappers(driver,Reporter);
        }
    }

    @Test
    public void testAddNewCurrency() throws InterruptedException {

        //Wait for activity
        doAction.waitForAndroidActivity(appActivity, 20);

        //Scroll to Add New Currency option and click
        XeCurrencyActivity objXE = new XeCurrencyActivity(driver,Dictionary,Environment,Reporter);

        AddCurrencyActivity objAddCurr = objXE.scrollToAddNewCurrency()
                                                .clickAddNewCurrency();

        //wait for activity
        doAction.waitForAndroidActivity(".activity.AddCurrency", 20);

        //Search and Add Activity
        objAddCurr.searchAndAddCurrency("ZAR").backToMainActivity();
        doAction.waitForAndroidActivity(appActivity, 20);
    }

    @Test
    public void testRemoveCurrency() throws InterruptedException {
        //Wait for activity
        doAction.waitForAndroidActivity(appActivity, 20);

        //Scroll to Add New Currency option and click
        XeCurrencyActivity objXE = new XeCurrencyActivity(driver,Dictionary,Environment,Reporter);

        String currencyToRemove = "ZAR - South African Rand";
        objXE.removeCurrency(currencyToRemove);
    }

    @Test
    public void testSelectCurrency() throws InterruptedException {
        //Wait for activity
        doAction.waitForAndroidActivity(appActivity, 20);

        //Scroll to Add New Currency option and click
        XeCurrencyActivity objXE = new XeCurrencyActivity(driver,Dictionary,Environment,Reporter);

        String currency = "AUD";
        objXE.selectCurrency(currency)
            .openCurrencyCalculator()
            .tapCurrencyCalcButton("1.0")
            .tapEqualsButton();
    }

    @AfterMethod
    public void afterMethod(Method method) {
        //Get the test name
        String testName = method.getName();

        System.out.println("After Method" + testName);

        //Update the KeepRefer Sheet
        asapDriver.setReferenceData();

        //Close Individual Summary Report & Update Summary Report
        Reporter.closeTestLevelReport(testName);
    }

    @AfterClass
    public void afterClass()
    {
        System.out.println("After Class " + className);

        //Close HTML Summary report
        Reporter.closeTestSummaryReport();

        //QUit webdriver
        if(driver != null) driver.quit();
    }
}
