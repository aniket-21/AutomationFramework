package com.ui.pages.android.cortal.consors;

//import io.appium.java_client.
import io.appium.java_client.AppiumDriver;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import com.framework.components.core.Wrappers;
import com.framework.components.core.Reporting;

public class GlobalSecurityListActivity {
	
	private Reporting Reporter;
	private io.appium.java_client.AppiumDriver driver;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private Wrappers objCommon;
	
	//Objects
	String btnMarkets = "uiautomator:=new UiSelector().text(\"Kurse/Märkte\")";
	
	//COnstructor
	public GlobalSecurityListActivity(WebDriver GDriver,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
	{
		Reporter = GReporter;
		driver = (AppiumDriver) GDriver;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		objCommon = new Wrappers(driver, Reporter);
	}
	
	
	//*****************************************************************************************
    //*    Name        	: fNavigateToMarches
    //*    Description 	: This function selects menu and navigates to Marches
    //*    Author       : Aniket Gadre
    //*    Input Params : None
    //*    Return Values: None
    //*****************************************************************************************
	public GlobalMarketListActivity fNavigateToMarches(){
		//Open Menu
		//driver.sendKeyEvent(AndroidKeyCode.MENU);
		
		//Click Menu Markets
		objCommon.click(btnMarkets);
		
		//return
		return new GlobalMarketListActivity(driver, Dictionary, Environment, Reporter);
	}
	
	
	//*****************************************************************************************
    //*    Name        	: fSelectCurrenyConversion
    //*    Description 	: This function selects menu and navigates to Marches
    //*    Author       : Aniket Gadre
    //*    Input Params : String : Currency
    //*    Return Values: Object : SnapShotActivity
    //*****************************************************************************************
	public SnapShotActivity fSelectCurrenyConversion(String currency){
		//Select Currency Conversion
		objCommon.click("uiautomator:=new UiSelector().text(\"" + currency + "\")");
		
		//return
		return new SnapShotActivity(driver, Dictionary, Environment, Reporter);
	}

}
