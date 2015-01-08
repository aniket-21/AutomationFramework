package com.consors.android;

import io.appium.java_client.AppiumDriver;
import java.util.HashMap;
import com.automation.framework.Reporting;
import org.openqa.selenium.WebDriver;
import com.automation.framework.Wrappers;

public class GlobalMarketListActivity {
	
	
	private Reporting Reporter;
	private io.appium.java_client.AppiumDriver driver;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private Wrappers objCommon;
	
	//Objects
	String txtDevises = "uiautomator:=new UiSelector().text(\"Währungen\")";
	
	
	//COnstructor
	public GlobalMarketListActivity(WebDriver GDriver,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
	{
		Reporter = GReporter;
		driver = (AppiumDriver) GDriver;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		objCommon = new Wrappers(driver, Reporter);
	}
	
	//*****************************************************************************************
    //*    Name        	: fSelectDevises
    //*    Description 	: This function selects menu and navigates to Marches
    //*    Author       : Aniket Gadre
    //*    Input Params : None
    //*    Return Values: None
    //*****************************************************************************************
	public GlobalSecurityListActivity fSelectCurrencies(){
		//Select Devises	
		objCommon.click(txtDevises);
		
		//return
		return new GlobalSecurityListActivity(driver, Dictionary, Environment, Reporter);
	}

}
