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
import com.mercury.tours.*;

public class TestsForMercuryTours {
	
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
	  System.out.println("Before Class TestsForMercuryTours");
	  	
	  //Set the DataSheet name by getting the class name
	  String[] strClassNameArray = this.getClass().getName().split("\\.");
	  className = strClassNameArray[strClassNameArray.length-1];
	  Global.Environment.put("CLASSNAME", className);		
	  	 
	   //Initiate asapDriver
	   asapDriver = new Driver();	   	  
	   
		//Check if POM has env, if null, get it from config file
	   if(System.getProperty("envName")== null) 		   
		   env = asapDriver.fGetEnv();	  		   
	   else 
		   env = System.getProperty("envName");		
	  
		
		//Add env global environments
		Global.Environment.put("ENV_CODE", env);
				
		//Create folder structure
		asapDriver.createExecutionFolders();	 		  
		
	   //Get Environment Variables
	   asapDriver.fetchEnvironmentDetails();
     
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
   public void testRegisterUser()
   {
	   System.out.println("testRegisterUser");		   
	   
	   //Create object of Launch Application class
		LaunchApplication launchApplication = new LaunchApplication();
		
		//Call  the function to launch the application url that return MercuryHomePage object
		MercuryHomePage mercuryHomePage = launchApplication.openApplication();
		
		//if the returned object is null then return false
		Assert.assertNotNull(mercuryHomePage, "Assert Mercury Home Page object is not null");
		 
	     //Check whether required page is opened
	     Assert.assertTrue(objCommon.fValidatePageDisplayed("Welcome: Mercury Tours"), "Validate title : Welcome: Mercury Tours");
		
		//Click on the register link that returns RegisterUserObject
		RegisterUser registerUser = mercuryHomePage.clickRegister();    
		
		//if the returned object is null then return false
		Assert.assertNotNull(registerUser, "Assert Register User Page object is not null");

		
	    //Check whether required page is opened
		Assert.assertTrue(objCommon.fValidatePageDisplayed("Register: Mercury Tours"), "Validate title : Register: Mercury Tours");      
      
	     //Call the function to register the user that returns RegisterUserResult page object
	     RegisterUserResultPage registerUserResultPage = registerUser.fGuiRegisterUser();

		//if the returned object is null then return false
	     Assert.assertNotNull(registerUserResultPage, "Assert Register User Result Page object is not null");	   		 		   
   }
   
   @Test
   public void testLogin()
   {
	   System.out.println("testLogin");

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
	   System.out.println("After Class TestsForMercuryTours");
	   
	   //Close HTML Summary report
	   Global.Reporter.fnCloseTestSummary();
	   
	   //Copy reports under build path
	   
	   //QUit webdriver
	   if(Global.webDriver != null) Global.webDriver.quit();
   }
	 
}
