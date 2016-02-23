package com.tests.ui.android.cortal.consors;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.ui.pages.android.cortal.consors.GlobalMarketListActivity;
import com.ui.pages.android.cortal.consors.GlobalSecurityListActivity;
import com.ui.pages.android.cortal.consors.SnapShotActivity;
import com.framework.components.core.Driver;
import com.framework.components.core.Reporting;
import com.framework.components.core.Wrappers;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;

public class    TestsForConsorsAndroid {
	
	//Variables
	String className;
	String dataSheetName;
	String env;
	
	//Instances
	Driver asapDriver;
	WebDriver driver;
	Wrappers objCommon;
	
	HashMap <String, String> Environment = new HashMap<String, String>();
	HashMap <String, String> Dictionary = new HashMap<String, String>();
	Reporting Reporter;
		
	@BeforeClass
    public void beforeClass() throws IOException {
        System.out.println("Before Class TestsForConsorsAndroid");

        //Set the DataSheet name by getting the class name
        String[] strClassNameArray = this.getClass().getName().split("\\.");
        className = strClassNameArray[strClassNameArray.length-1];
        Environment.put("CLASSNAME", className);
        Environment.put("BROWSER", "appium");

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
        DC.setCapability("appPackage", "com.consors.android.de");
        DC.setCapability("appActivity", "com.consors.android.ui.LauncherActivity");
        DC.setCapability("deviceName","ASUS Zenfone 5");

        //Initiate WebDriver
        driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), DC);

        //Set implicit time
        if(driver != null) driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        //Instantiate reporter
        Reporter = new Reporting(driver, Dictionary, Environment);

        //Create HTML Summary Report
        Reporter.createSummaryReport();

        //Update Jenkins report
        Reporter.createJenkinsReport();

        //Initialize Common functions
        objCommon = new Wrappers(driver, Reporter);
    }
		   
    @BeforeMethod
    public void beforeMethod(Method method)
    {
       //Get the test name
       String testName = method.getName();

       System.out.println("Before Method" + testName);

       //Get the data from DataSheet corresponding to Class Name & Test Name
       asapDriver.getDataForTest(testName);

       //Create Individual HTML Report
       Reporter.createTestLevelReport(testName);
    }
	   
	   
    @AfterMethod
    public void afterMethod(Method method)
    {
       //Get the test name
       String testName = method.getName();

       System.out.println("After Method" + testName);

       //Update the KeepRefer Sheet
       asapDriver.setReferenceData();

       //Close Individual Summary Report & Update Summary Report
       Reporter.closeTestLevelReport(testName);
    }
	   
	   
    @Test
    public void testConsorsAndroidPOC() throws InterruptedException
    {
        System.out.println("testConsorsAndroidPOC");

        //Wait for activity
        objCommon.waitForAndroidActivity(".ui.GlobalSecurityListActivity",20);

        //Create Object
        GlobalSecurityListActivity objGSL = new GlobalSecurityListActivity(driver,Dictionary,Environment,Reporter);
        GlobalMarketListActivity objGML = objGSL.fNavigateToMarches();
        objCommon.waitForAndroidActivity("com.consors.android.ui.GlobalMarketListActivity",20);

        //Navigate to Currencies
        objGSL = objGML.fSelectCurrencies();
        objCommon.waitForAndroidActivity(".ui.GlobalSecurityListActivity",20);

        //Select Currency Conversion
        SnapShotActivity objSSA = objGSL.fSelectCurrenyConversion("EUR/USD");
        objCommon.waitForAndroidActivity(".ui.SnapShotActivity",20);

       //Select Details Tad
       objSSA.fSelectDetailsTab();

       //Rotate screen
       objCommon.rotateDeviceScreen("L");

       //Wait
       Thread.sleep(3000);
    }
	   	   	   
    @AfterClass
    public void afterClass()
    {
       System.out.println("After Class TestsForAppium");

       //Close HTML Summary report
       Reporter.closeTestSummaryReport();

       //QUit webdriver
       if(driver != null) driver.quit();
    }
}
