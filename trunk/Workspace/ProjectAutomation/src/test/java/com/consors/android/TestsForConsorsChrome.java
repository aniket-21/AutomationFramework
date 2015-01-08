package com.consors.android;

import com.amdocs.asap.Driver;
import com.amdocs.asap.Reporting;
import com.amdocs.asap.Wrappers;
import com.consors.web.CheckingAccountDetailsPage;
import com.consors.web.CheckingAccountPage;
import com.consors.web.HomePage;
import com.consors.web.LaunchApplication;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class TestsForConsorsChrome {
	//Variables
	String className;
	String dataSheetName;
	String env;
	String buildNumber;
	String jobName;
    String browser;
	
	//Instances
	Driver asapDriver;
	WebDriver driver;
    Wrappers objCommon;
	
	HashMap <String, String> Environment = new HashMap<String, String>();
	HashMap <String, String> Dictionary = new HashMap<String, String>();
	Reporting Reporter;

   /* @DataProvider(name = "browsers", parallel = true)
    public static Object[][] getData() {
        String[] browser = System.getProperty("browserName").split(",");
        final int size = browser.length;
        Object[][] browsers = new Object[size][1];
        for(int i=0;i<browser.length;i++) {
            System.out.println(browser[i]);
            browsers[i][0] = browser[i];
        }
        return browsers;
    }

    @Factory(dataProvider = "browsers")
    //Created with values from @DataProvider in @Factory
    public TestsForConsorsChrome(String browser) {
        this.browser = browser;
    } */

	
    @BeforeClass
    public void beforeClass() throws IOException {
        System.out.println("Before Class TestForConsorsChrome");

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
        DC.setCapability("browserName", "Chrome");
        DC.setCapability("deviceName", "ASUS Zenfone 5");

        //Initiate WebDriver
        driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), DC);

        //Set implicit time
        if(driver != null) driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        //Instantiate reporter
        Reporter = new Reporting(driver, Dictionary, Environment);

        //Create HTML Summary Report
        Reporter.fnCreateSummaryReport();

        //Update Jenkins report
        Reporter.fnJenkinsReport();

        //Initialize Common functions
        objCommon = new Wrappers(driver, Reporter);

    }
	   
   @BeforeMethod
   public void beforeMethod(Method method){
	   //Get the test name
	   String testName = method.getName();
	   
	   System.out.println("Before Method" + testName);
	   
	   //Get the data from DataSheet corresponding to Class Name & Test Name
	   asapDriver.fGetDataForTest(testName);
	   
	   //Create Individual HTML Report	
	   Reporter.fnCreateHtmlReport(testName);	  
   }
	   
	   
   @Test
   public void testConsorsWebPOC(){
	   System.out.println("testConsorsWebPOC");		   
	   
	   //Create object of Launch Application class
		LaunchApplication launchApplication = new LaunchApplication(driver, Dictionary,Environment,Reporter);
		
		//Maximise window
		//objCommon.maximizeWindow();
		
		//Call  the function to launch the application url that return MercuryHomePage object
		HomePage objHP = launchApplication.openApplication();
		
		//if the returned object is null then return false
		Assert.assertNotNull(objHP, "Assert Cortol Consors Home Page object is not null");
				
		// Click on link 
		objHP.clickCurrentAccount();

		//Check whether required page is opened
	     Assert.assertEquals(objCommon.getTitle(), "Girokonto");
        Reporter.fnWriteToHtmlOutput("Check Title","Title should be Girokonto","Title is Girokonto","Pass");
		 
	    //Click on Button open Checking Account
		CheckingAccountPage objCA= objHP.clickOpenCheckingAccount();
		
		//if the returned object is null then return false
		Assert.assertNotNull(objCA, "Assert Checking Account Entry Page object is not null");		
					
		CheckingAccountDetailsPage objCAD= objCA.openCheckingAccountForm();
		
		//if the returned object is null then return false
		Assert.assertNotNull(objCAD, "Assert Checking Account Details  Page object is not null"); 
	
		//Enter PErsonal Details
		objCAD.enterPersonalDetails();
		
		//Enter Address Details
		objCAD.enterAddressDetails();
		
		//Enter Contact Details
		objCAD.enterContactDetails();
		
		//Enter Professional Details
		objCAD.enterProfessionalDetails();
   }
   
   
	   
   @AfterMethod
   public void afterMethod(Method method){
	   //Get the test name
	   String testName = method.getName();
	   
	   System.out.println("After Method" + testName);
	   	   
	   //Update the KeepRefer Sheet
	   asapDriver.fSetReferenceData();
	   
	   //Close Individual Summary Report & Update Summary Report
	   Reporter.fnCloseHtmlReport(testName);
   }
   	   	   
   @AfterClass
   public void afterClass(){
	   System.out.println("After Class TestsForConsorsChrome");
	   
	   //Close HTML Summary report
	   Reporter.fnCloseTestSummary();
	   
	   //QUit webdriver
	   if(driver != null) driver.quit();
   }
	 
}