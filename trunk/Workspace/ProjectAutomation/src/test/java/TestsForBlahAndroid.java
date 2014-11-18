package test.java;

import com.amdocs.asap.CommonFunctions;
import com.amdocs.asap.Driver;
import com.amdocs.asap.Reporting;
import com.blah.android.*;
import com.consors.android.GlobalSecurityListActivity;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidKeyCode;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidDriver;


/**
 * Created by ANIKETG on 11/10/2014.
 */
public class TestsForBlahAndroid {

    String className;
    String dataSheetName;
    String env;

    //Instances
    Driver asapDriver;
    WebDriver driver;
    CommonFunctions objCommon;

    HashMap<String, String> Environment = new HashMap<String, String>();
    HashMap <String, String> Dictionary = new HashMap<String, String>();
    Reporting Reporter;

    WebDriverWait wait;
    JavascriptExecutor jsExecutor;

    @BeforeClass
    public void beforeClass() throws IOException
    {
        System.out.println("Before Class TestsForBlahAndroid");

        //Set the DataSheet name by getting the class name
        String[] strClassNameArray = this.getClass().getName().split("\\.");
        className = strClassNameArray[strClassNameArray.length-1];
        Environment.put("CLASSNAME", className);

        //Initiate asapDriver
        asapDriver = new Driver(Dictionary, Environment);

        //Check if POM has env, if null, get it from config file
        env = System.getProperty("envName");
        Assert.assertNotNull(env);

        //Add env global environments
        Environment.put("ENV_CODE", env);

        //Create folder structure
        Assert.assertTrue(asapDriver.createExecutionFolders());

        //Get Environment Variables
        Assert.assertTrue(asapDriver.fetchEnvironmentDetails());

        //Desired Caps
        DesiredCapabilities DC = new DesiredCapabilities();
        DC.setCapability("automationName", "Appium");
        DC.setCapability("platformName", "Android");
        DC.setCapability("appPackage", "com.blah.app");
        DC.setCapability("appActivity", "com.openmarket.telephony.uc.activity.SplashActivity");
        DC.setCapability("deviceName", "Asus Zenfone 5");
        DC.setCapability("newCommandTimeout",3000);
        DC.setCapability("appWaitActivity","com.openmarket.telephony.uc.activity.MainListsActivity");

        //Initiate WebDriver
        driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), DC);

        //Set implicit time
        if(driver != null) driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        //wait = new WebDriverWait(driver, 30);
        jsExecutor = (JavascriptExecutor) driver;

        //Instantiate reporter
        Reporter = new Reporting(driver, Dictionary, Environment);

        //Create HTML Summary Report
        Reporter.fnCreateSummaryReport();

        //Update Jenkins report
        Reporter.fnJenkinsReport();

        //Initialize Common functions
        objCommon = new CommonFunctions(driver, Reporter);

    }

    @BeforeMethod
    public void beforeMethod(Method method)
    {
        //Get the test name
        String testName = method.getName();

        System.out.println("Before Method" + testName);

        //Get the data from DataSheet corresponding to Class Name & Test Name
        asapDriver.fGetDataForTest(testName);

        //Create Individual HTML Report
        Reporter.fnCreateHtmlReport(testName);
    }


    @AfterMethod
    public void afterMethod(Method method)
    {

        //Get the test name
        String testName = method.getName();

        System.out.println("After Method" + testName);

        //Update the KeepRefer Sheet
        asapDriver.fSetReferenceData();

        //Close Individual Summary Report & Update Summary Report
        Reporter.fnCloseHtmlReport(testName);

    }

    @AfterClass
    public void afterClass()
    {
        System.out.println("After Class TestsForBlahAndroid");

        //Close HTML Summary report
        Reporter.fnCloseTestSummary();

        //QUit webdriver
        if(driver != null) driver.quit();
    }




    @Test
    public void testAddAContact() throws InterruptedException {

        //Naviagte to Settings
        ActionBarActivity objActionBar = new ActionBarActivity(driver,Dictionary,Environment,Reporter);
        SettingsActivity objSettings = objActionBar.fNavigateToSettings();

        //Validate not null
        Assert.assertNotNull(objSettings);
        Assert.assertTrue(objCommon.fValidateCurrentActivity("com.openmarket.telephony.uc.activity.MainListsActivity"), "Validate if MainList Activity Opened");

        //Go to Add Contacts
        EditContactActivity objContact = objSettings.fSelectAddAContact();

        //Validate Activity
        Assert.assertNotNull(objContact);
        Assert.assertTrue(objCommon.fValidateCurrentActivity("com.openmarket.telephony.uc.activity.EditContactActivity"), "Validate if Edit Contact Activity Opened");

        //Fill contact Details
        Assert.assertTrue(objContact.fFillContactDetails());
        objSettings = objContact.fSaveContactDetails();

        //Validate not null
        Assert.assertNotNull(objSettings);
        Assert.assertTrue(objCommon.fValidateCurrentActivity("com.openmarket.telephony.uc.activity.MainListsActivity"), "Validate if MainList Activity Opened");

        System.out.println(((AndroidDriver)driver).currentActivity());
    }


    @Test
    public void testSearchAndPingAContact() throws InterruptedException {

        //Naviagte to Settings
        ActionBarActivity objActionBar = new ActionBarActivity(driver,Dictionary,Environment,Reporter);
        MainListsActivity objMain = objActionBar.fNavigateToSearch();

        //Validate not null
        Assert.assertNotNull(objMain);
        Assert.assertTrue(objCommon.fValidateCurrentActivity("com.openmarket.telephony.uc.activity.MainListsActivity"), "Validate if MainList Activity Opened");

        //Search and Select Contact
        UserActivity objUser = objMain.fSearchAndSelectContact();

        //Validate not null
        Assert.assertNotNull(objUser);
        Assert.assertTrue(objCommon.fValidateCurrentActivity("com.openmarket.telephony.uc.activity.UserActivity"), "Validate if User Activity Opened");

        //Open chat
        ChatActivity objChat = objUser.fStartChat();

        //Validate not null
        Assert.assertNotNull(objChat);
        Assert.assertTrue(objCommon.fValidateCurrentActivity("com.openmarket.telephony.uc.activity.ChatActivity"), "Validate if Chat Activity Opened");

        //Send text and smiley
        objChat.fSendText("This is an automation test done at " + (new Date()).toString());
        objChat.fSendSmiley();

        //Hide Keyboard
        //((AndroidDriver)driver).hideKeyboard();
        ((AndroidDriver)driver).sendKeyEvent(AndroidKeyCode.BACK);

        //Validate not null
        Assert.assertTrue(objCommon.fValidateCurrentActivity("com.openmarket.telephony.uc.activity.UserActivity"), "Validate if User Activity Opened");
    }


    @Test
    public void testSearchAndDeleteContact() throws InterruptedException {

        //Naviagte to Settings
        ActionBarActivity objActionBar = new ActionBarActivity(driver, Dictionary, Environment, Reporter);
        MainListsActivity objMain = objActionBar.fNavigateToSearch();

        //Validate not null
        Assert.assertNotNull(objMain);
        //Assert.assertTrue(objCommon.fValidateCurrentActivity("com.openmarket.telephony.uc.activity.MainListsActivity"), "Validate if MainList Activity Opened");

        //Search and Select Contact
        UserActivity objUser = objMain.fSearchAndSelectContact();

        //Validate not null
        Assert.assertNotNull(objUser);
        Assert.assertTrue(objCommon.fValidateCurrentActivity("com.openmarket.telephony.uc.activity.UserActivity"), "Validate if User Activity Opened");

        //Edit Contact
        EditContactActivity objEdit = objUser.fEditContact();

        //Validate not null
        Assert.assertNotNull(objEdit);
        Assert.assertTrue(objCommon.fValidateCurrentActivity("com.openmarket.telephony.uc.activity.EditContactActivity"), "Validate if Edit contact Activity Opened");

        //Delete contact
        objEdit.fDeleteContact();

        Assert.assertTrue(objCommon.fValidateCurrentActivity("com.openmarket.telephony.uc.activity.MainListsActivity"), "Validate if MainList Activity Opened");

        System.out.println(((AndroidDriver) driver).currentActivity());
    }
}
