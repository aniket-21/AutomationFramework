package test.java;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.amdocs.asap.CommonFunctions;
import com.amdocs.asap.Driver;
import com.amdocs.asap.Global;
import com.consors.web.CheckingAccountDetails;
import com.consors.web.CheckingAccountEntry;
import com.consors.web.HomePage;
import com.consors.web.LaunchApplication;

public class TestsForConsorsWeb {	
	//Variables
	String className;
	String dataSheetName;
	String env;
	String buildNumber;
	String jobName;
	
	//Instances
	Driver asapDriver;
	WebDriver driver;
	CommonFunctions objCommon;

	
  @BeforeClass
  public void beforeClass() throws IOException
  {
	  System.out.println("Before Class TestForCortalConsorsWeb");
	  	
	  //Set the DataSheet name by getting the class name
	  String[] strClassNameArray = this.getClass().getName().split("\\.");
	  className = strClassNameArray[strClassNameArray.length-1];
	  Global.Environment.put("CLASSNAME", className);		
	  	 
	   //Initiate asapDriver
	   asapDriver = new Driver();	   	  
	   
		//Check if POM has env, if null, get it from config file
	   	env = System.getProperty("envName");	
	   	Assert.assertNotNull(env);
	  		
		//Add env global environments
		Global.Environment.put("ENV_CODE", env);
				
		//Create folder structure
		Assert.assertTrue(asapDriver.createExecutionFolders());	 		  
		
	   //Get Environment Variables
		Assert.assertTrue(asapDriver.fetchEnvironmentDetails());
     
	   //Create HTML Summary Report
	   Global.Reporter.fnCreateSummaryReport();
	   
	   //Update Jenkins report
	   Global.Reporter.fnJenkinsReport();
	   
	   //Initiate WebDriver
	   Global.webDriver = asapDriver.fGetWebDriver();
	   driver = Global.webDriver;
	   
	   //Set implicit time
	   if(driver != null) driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	   
	   //Initialize Common functions
	   objCommon = new CommonFunctions();
	   
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
	   Global.Reporter.fnCreateHtmlReport(testName);	  
   }
	   
	   
   @Test
   public void testConsorsWebPOC()
   {
	   System.out.println("testConsorsWebPOC");		   
	   
	   //Create object of Launch Application class
		LaunchApplication launchApplication = new LaunchApplication();
		
		//Maximise window
		Assert.assertTrue(objCommon.fMaximizeWindow());
		
		//Call  the function to launch the application url that return MercuryHomePage object
		HomePage objHP = launchApplication.openApplication();
		
		//if the returned object is null then return false
		Assert.assertNotNull(objHP, "Assert Cortol Consors Home Page object is not null");
				
		// Click on link 
		Assert.assertTrue(objHP.clickCurrentAccount(), "Click Link: Current account");

		//Check whether required page is opened
	     Assert.assertTrue(objCommon.fValidatePageDisplayed("Girokonto"), "Validate title : Girokonto");
		 
	    //Click on Button open Checking Account
		CheckingAccountEntry objCA= objHP.clickOpenCheckingAccount();
		
		//if the returned object is null then return false
		Assert.assertNotNull(objCA, "Assert Checking Account Entry Page object is not null");		
					
		CheckingAccountDetails objCAD= objCA.openCheckingAccountForm();
		
		//if the returned object is null then return false
		Assert.assertNotNull(objCAD, "Assert Checking Account Details  Page object is not null"); 
	
		//Enter PErsonal Details
		Assert.assertTrue(objCAD.enterPersonalDetails(), "Enter Personal Details");
		
		//Enter Address Details
		Assert.assertTrue(objCAD.enterAddressDetails(), "Address Details");
		
		//Enter Contact Details
		Assert.assertTrue(objCAD.enterContactDetails(), "Contact Details");
		
		//Enter Professional Details
		Assert.assertTrue(objCAD.enterProfessionalDetails(), "Professional Details");			     		   		 		 
   }
   
   
	   
   @AfterMethod
   public void afterMethod(Method method)
   {
	   //System.out.println("After Method");
	   
	   //Get the test name
	   String testName = method.getName();
	   
	   System.out.println("After Method" + testName);
	   	   
	   //Update the KeepRefer Sheet
	   asapDriver.fSetReferenceData();
	   
	   //Close Individual Summary Report & Update Summary Report
	   Global.Reporter.fnCloseHtmlReport(testName);
	   	   		  
   }
   	   	   
   @AfterClass
   public void afterClass()
   {
	   System.out.println("After Class TestsForConsorsWeb");
	   
	   //Close HTML Summary report
	   Global.Reporter.fnCloseTestSummary();
	   
	   //Copy reports under build path
	   
	   //QUit webdriver
	   if(Global.webDriver != null) Global.webDriver.quit();
   }
	 
}