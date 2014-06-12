package test.java;

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
import com.consors.android.GlobalMarketListActivity;
import com.consors.android.GlobalSecurityListActivity;
import com.consors.android.SnapShotActivity;
import com.linkedin.android.HomeActivity;
import com.linkedin.android.LoginActivity;
import com.linkedin.android.StartUpActivity;

public class TestsForConsorsAndroid {
	
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
		  System.out.println("Before Class TestsForConsorsAndroid");
		
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
		   //Get the test name
		   String testName = method.getName();
		   
		   System.out.println("Before Method" + testName);
		   
		   //Get the data from DataSheet corresponding to Class Name & Test Name
		   asapDriver.fGetDataForTest(testName);
		   
		   //Create Individual HTML Report	
		   Global.Reporter.fnCreateHtmlReport(testName);	  
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
	   
	   
	   @Test
	   public void testConsorsAndroidPOC() throws InterruptedException
	   {
		   System.out.println("testConsorsAndroidPOC");	
		   
		   //Wait for 5 secs
		   Thread.sleep(5000);
		   
		   //Validate Activity
		   Assert.assertTrue(objCommon.fValidateCurrentActivity(".ui.GlobalSecurityListActivity"), "Validate if Global Security List Activity Opened");
		   
		   //Create Object
		   GlobalSecurityListActivity objGSL = new GlobalSecurityListActivity();
		   
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
		   Global.Reporter.fnCloseTestSummary();
		   
		   //QUit webdriver
		   if(Global.webDriver != null) Global.webDriver.quit();
	   }

}
