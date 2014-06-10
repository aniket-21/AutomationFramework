package com.consors.android;

import io.appium.java_client.AndroidKeyCode;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.amdocs.asap.CommonFunctions;
import com.amdocs.asap.Global;
import com.amdocs.asap.Reporting;

public class GlobalSecurityListActivity {
	
	private Reporting Reporter;
	private io.appium.java_client.AppiumDriver driver;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions objCommon = new CommonFunctions();
	
	//Objects
	String mnuMarkets = "uiautomator:=new UiSelector().text(\"Marchés\")";	
	
	//COnstructor
	public GlobalSecurityListActivity()
	{
		Reporter = Global.Reporter;
		driver = (io.appium.java_client.AppiumDriver)Global.webDriver;
		Dictionary = Global.Dictionary;
		Environment = Global.Environment;
	}
	
	
	//*****************************************************************************************
    //*    Name        	: fNavigateToMarches
    //*    Description 	: This function selects menu and navigates to Marches
    //*    Author       : Aniket Gadre
    //*    Input Params : None
    //*    Return Values: None
    //*****************************************************************************************
	public GlobalMarketListActivity fNavigateToMarches()
	{
		
		//Open Menu
		driver.sendKeyEvent(AndroidKeyCode.MENU);
		
		//Enter Credentials		
		if(objCommon.fGuiClick(mnuMarkets)==false) return null;
		
		//return
		return new GlobalMarketListActivity();
	}
	
	
	//*****************************************************************************************
    //*    Name        	: fSelectCurrenyConversion
    //*    Description 	: This function selects menu and navigates to Marches
    //*    Author       : Aniket Gadre
    //*    Input Params : String : Currency
    //*    Return Values: Object : SnapShotActivity
    //*****************************************************************************************
	public SnapShotActivity fSelectCurrenyConversion(String currency)
	{			
		//Select Currency Conversion
		if(objCommon.fGuiClick("uiautomator:=new UiSelector().text(\"" + currency + "\")")==false) return null;
		
		//return
		return new SnapShotActivity();
	}

}
