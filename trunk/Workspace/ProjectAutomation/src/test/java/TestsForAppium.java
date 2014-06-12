package test.java;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;

import io.selendroid.SelendroidCapabilities;
import io.selendroid.SelendroidDriver;




import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.testng.*;
import org.testng.annotations.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.amdocs.asap.CommonFunctions;
import com.amdocs.asap.Driver;
import com.amdocs.asap.Global;
//import com.amdocs.asap.AppiumDriver;

import com.linkedin.android.*;

public class TestsForAppium {  
		
	//Variables
	String className;
	String dataSheetName;
	String env;
	
	//Instances
	Driver asapDriver;
	WebDriver driver;
	CommonFunctions objCommon;

	
  @BeforeClass
  public void beforeClass() throws MalformedURLException
  {
	  System.out.println("Before Class TestsForAppium");
	
	  //Set the DataSheet name by getting the class name
	  String[] strClassNameArray = this.getClass().getName().split("\\.");
	  className = strClassNameArray[strClassNameArray.length-1];
	  Global.Environment.put("CLASSNAME", className);		
	  	 
	   //Initiate asapDriver
	   asapDriver = new Driver();	   
	  
	   //Check if POM has env, if null, get it from config file
	   if(System.getProperty("envName")==null) env = asapDriver.fGetEnv();
	   else env = System.getProperty("envName");	   	   
	   
	   //Add env to global environments
	   Global.Environment.put("ENV_CODE", env);
		
	 try {
		   asapDriver.createExecutionFolders();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			 		  
		
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
	   //System.out.println("Before Method");
	   
	   //Get the test name
	   String testName = method.getName();
	   
	   System.out.println("Before Method" + testName);
	   
	   //Get the data from DataSheet corresponding to Class Name & Test Name
	   asapDriver.fGetDataForTest(testName);
	   
	   //Create Individual HTML Report	
	   Global.Reporter.fnCreateHtmlReport(testName);	  
   }
	   
	   
   @Test
   public void testLinkedInSignIn()
   {
	   System.out.println("testLinkedInSignIn");		   
	   
	   //Initialize startup object
	   StartUpActivity objStartUp = new StartUpActivity();
	   
	   //Click on Log in
	   LoginActivity objLoginIn = objStartUp.fClickLogin();
	   Assert.assertNotNull(objLoginIn,"Click on Login");	   	   
	   
	   //Enter Credentials
	   Assert.assertTrue(objLoginIn.fEnterLoginCredentials(Global.Dictionary.get("USERNAME"), Global.Dictionary.get("PASSWORD")), "Enter Login Credentials");
	   
	   //Click on sign in
	   HomeActivity objHome = objLoginIn.fClickSignIn();
	   Assert.assertNotNull(objHome,"Click on Signin");	   		 		   
   }
   
   @Test
   public void testTwo()
   {
	   System.out.println("Test 2");
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
	   Global.Reporter.fnCloseHtmlReport(testName);
	   	   		  
   }
   	   	   
   @AfterClass
   public void afterClass()
   {
	   System.out.println("After Class TestsForAppium");
	   
	   //Close HTML Summary report
	   Global.Reporter.fnCloseTestSummary();
	   
	   //QUit webdriver
	   if(Global.webDriver != null) Global.webDriver.quit();
   }
	

	  
  /* 
			   
			   
   //Instantiate Properties
   prop = new Properties();
   prop.load(new FileInputStream(RootPath + "trunk\\Storage\\Environments\\config.properties"));
   
   //Fetch from properties
   strEnv = prop.getProperty("ENV");
	           

	   
 */	         	          	  
}   
	
