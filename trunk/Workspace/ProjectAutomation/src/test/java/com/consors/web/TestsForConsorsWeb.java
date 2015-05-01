package com.consors.web;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.automation.framework.Driver;
import com.automation.framework.Reporting;
import com.automation.framework.Wrappers;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class TestsForConsorsWeb {

	//Variables
	String className;
	String env;
    String browser;
	
	//Instances
	Driver asapDriver;
	WebDriver driver;
    Wrappers objCommon;
	
	HashMap <String, String> Environment = new HashMap<String, String>();
	HashMap <String, String> Dictionary = new HashMap<String, String>();
	Reporting Reporter;

    @DataProvider(name = "browsers", parallel = true)
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
    public TestsForConsorsWeb(String browser) {
        this.browser = browser;
    }

	
    @BeforeClass
    public void beforeClass() throws IOException {

        //Set the DataSheet name by getting the class name
        String[] strClassNameArray = this.getClass().getName().split("\\.");
        className = strClassNameArray[strClassNameArray.length-1];
        Environment.put("CLASSNAME", className);
        Environment.put("BROWSER", browser);

        System.out.println("Before Class method for " + className);

        //Initiate asapDriver
        asapDriver = new Driver(Dictionary, Environment);

        //Get Env
        env = System.getProperty("envName");
        Assert.assertNotNull(env, "No Environment Parameter value received");

        //Add env global environments
        Environment.put("ENV_CODE", env);
        Assert.assertTrue(asapDriver.createExecutionFolders(),"Creating Execution Folders");
        Assert.assertTrue(asapDriver.fetchEnvironmentDetailsFromConfigXML(),"Fetching Environment Details");

        //Initiate WebDriver
        driver = asapDriver.fGetWebDriver(browser);

        //Set implicit time
        if(driver != null) driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        //Instantiate reporter
        Reporter = new Reporting(driver, Dictionary, Environment);
        Reporter.fnCreateSummaryReport();
        Reporter.fnJenkinsReport();

        //Initialize Common functions
        objCommon = new Wrappers(driver,Reporter);
    }
	   
   @BeforeMethod
   public void beforeMethod(Method method){
	   String testName = method.getName();
	   System.out.println("Before Method for test " + testName);
	   asapDriver.fGetDataForTest(testName);
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
		Assert.assertNotNull(objHP, "Assert Cortol Consors Home Page object is not null");
				
		// Click on link 
		objHP.clickCurrentAccount();
        Assert.assertEquals(objCommon.getTitle(), "Girokonto");
		 
	    //Click on Button open Checking Account
		CheckingAccountPage objCA= objHP.clickOpenCheckingAccount();
		Assert.assertNotNull(objCA, "Assert Checking Account Entry Page object is not null");		

       //Checking Account Details
		CheckingAccountDetailsPage objCAD= objCA.openCheckingAccountForm();
		Assert.assertNotNull(objCAD, "Assert Checking Account Details  Page object is not null"); 
	
		//Enter Details
		objCAD.enterPersonalDetails();
		objCAD.enterAddressDetails();
		objCAD.enterContactDetails();
		objCAD.enterProfessionalDetails();
   }

   @AfterMethod
   public void afterMethod(Method method){
	   //Get the test name
	   String testName = method.getName();
	   System.out.println("After Method for test " + testName);
	   asapDriver.fSetReferenceData();
	   Reporter.fnCloseHtmlReport(testName);
   }
   	   	   
   @AfterClass
   public void afterClass(){
	   System.out.println("After Class method for " + className);
	   Reporter.fnCloseTestSummary();
	   if(driver != null) driver.quit();
   }
	 
}