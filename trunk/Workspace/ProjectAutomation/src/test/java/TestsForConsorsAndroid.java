package test.java;

import io.appium.java_client.AppiumDriver;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.amdocs.asap.CommonFunctions;
import com.amdocs.asap.Driver;
import com.amdocs.asap.Reporting;
import com.consors.android.GlobalMarketListActivity;
import com.consors.android.GlobalSecurityListActivity;
import com.consors.android.SnapShotActivity;


public class TestsForConsorsAndroid {
	
	//Variables
	String className;
	String dataSheetName;
	String env;
	
	//Instances
	Driver asapDriver;
	WebDriver driver;
	CommonFunctions objCommon;
	
	HashMap <String, String> Environment = new HashMap<String, String>();
	HashMap <String, String> Dictionary = new HashMap<String, String>();
	Reporting Reporter;
		
	@BeforeClass
	  public void beforeClass() throws IOException
	  {
		  System.out.println("Before Class TestsForConsorsAndroid");
		
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
		   DC.setCapability("appPackage", "com.consors.android.de");
		   DC.setCapability("appActivity", "com.consors.android.ui.LauncherActivity");
		   DC.setCapability("deviceName", "Samsung Galaxy Note");
		   
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
	   
	   
	   @Test
	   public void testConsorsAndroidPOC() throws InterruptedException
	   {
		   System.out.println("testConsorsAndroidPOC");	
		   
		   //Wait for 5 secs
		   Thread.sleep(5000);
		   
		   //Validate Activity
		   Assert.assertTrue(objCommon.fValidateCurrentActivity(".ui.GlobalSecurityListActivity"), "Validate if Global Security List Activity Opened");
		   
		   //Create Object
		   GlobalSecurityListActivity objGSL = new GlobalSecurityListActivity(driver,Dictionary,Environment,Reporter);
		   
		   //Navigate to Marches
		   GlobalMarketListActivity objGML = objGSL.fNavigateToMarches();
		   Assert.assertNotNull(objGML);		   		   
		   
		  //Validate Activity
		   Assert.assertTrue(objCommon.fValidateCurrentActivity("com.consors.android.ui.GlobalMarketListActivity"), "Validate if Global Market List Activity Opened");
		   
		   //Navigate to Currencies
		   objGSL = objGML.fSelectCurrencies();
		   Assert.assertNotNull(objGSL);
		   
		  //Validate Activity
		   Assert.assertTrue(objCommon.fValidateCurrentActivity(".ui.GlobalSecurityListActivity"), "Validate if Global Security List Activity Opened");
		   		   
		   //Select Currency Conversion
		   SnapShotActivity objSSA = objGSL.fSelectCurrenyConversion("EUR/USD");
		   Assert.assertNotNull(objSSA);
		   
		   //Validate Activity
		   Assert.assertTrue(objCommon.fValidateCurrentActivity(".ui.SnapShotActivity"), "Validate if Global Security List Activity Opened");
		   
		   //Select Details Tad
		   Assert.assertTrue(objSSA.fSelectDetailsTab(), "Selecting Details Tab");
		   
		   //Rotate screen
		   Assert.assertTrue(objCommon.fAndroidRotateScreen("L"));
		   
		   //Wait
		   Thread.sleep(3000);
		   		   		 		   
	   }
	   	   	   
	   @AfterClass
	   public void afterClass()
	   {
		   System.out.println("After Class TestsForAppium");
		   
		   //Close HTML Summary report
		   Reporter.fnCloseTestSummary();
		   
		   //QUit webdriver
		   if(driver != null) driver.quit();
	   }

}
