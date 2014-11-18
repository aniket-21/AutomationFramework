package com.consors.android;

import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.AppiumDriver;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import com.amdocs.asap.CommonFunctions;
import com.amdocs.asap.Global;
import com.amdocs.asap.Reporting;

public class GlobalMarketListActivity {
	
	
	private Reporting Reporter;
	private io.appium.java_client.AppiumDriver driver;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions objCommon;
	
	//Objects
	String txtDevises = "uiautomator:=new UiSelector().text(\"W�hrungen\")";	
	
	
	//COnstructor
	public GlobalMarketListActivity(WebDriver GDriver,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
	{
		Reporter = GReporter;
		driver = (AppiumDriver) GDriver;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		objCommon = new CommonFunctions(driver, Reporter);
	}
	
	//*****************************************************************************************
    //*    Name        	: fSelectDevises
    //*    Description 	: This function selects menu and navigates to Marches
    //*    Author       : Aniket Gadre
    //*    Input Params : None
    //*    Return Values: None
    //*****************************************************************************************
	public GlobalSecurityListActivity fSelectCurrencies()
	{				
		//Select Devises	
		if(objCommon.fGuiClick(txtDevises)==false) return null;
		
		//return
		return new GlobalSecurityListActivity(driver, Dictionary, Environment, Reporter);
	}

}
