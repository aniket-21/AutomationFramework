package com.consors.android;

import io.appium.java_client.AndroidKeyCode;

import java.util.HashMap;

import com.amdocs.asap.CommonFunctions;
import com.amdocs.asap.Global;
import com.amdocs.asap.Reporting;

public class GlobalMarketListActivity {
	
	
	private Reporting Reporter;
	private io.appium.java_client.AppiumDriver driver;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions objCommon = new CommonFunctions();
	
	//Objects
	String txtDevises = "uiautomator:=new UiSelector().text(\"Währungen\")";	
	
	//COnstructor
	public GlobalMarketListActivity()
	{
		Reporter = Global.Reporter;
		driver = (io.appium.java_client.AppiumDriver)Global.webDriver;
		Dictionary = Global.Dictionary;
		Environment = Global.Environment;
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
		return new GlobalSecurityListActivity();
	}

}
