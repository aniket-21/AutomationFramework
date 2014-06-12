package com.consors.android;

import java.util.HashMap;

import com.amdocs.asap.CommonFunctions;
import com.amdocs.asap.Global;
import com.amdocs.asap.Reporting;

public class SnapShotActivity {
	
	
	private Reporting Reporter;
	private io.appium.java_client.AppiumDriver driver;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions objCommon = new CommonFunctions();
	
	//Objects
	String tabDetails = "uiautomator:=new UiSelector().text(\"Profil\")";	
	
	//COnstructor
	public SnapShotActivity()
	{
		Reporter = Global.Reporter;
		driver = (io.appium.java_client.AppiumDriver)Global.webDriver;
		Dictionary = Global.Dictionary;
		Environment = Global.Environment;
	}
	
	
	//*****************************************************************************************
    //*    Name        	: fSelectDetailsTab
    //*    Description 	: This function selects menu and navigates to Marches
    //*    Author       : Aniket Gadre
    //*    Input Params : None
    //*    Return Values: None
    //*****************************************************************************************
	public boolean fSelectDetailsTab()
	{				
		//Select Devises	
		if(objCommon.fGuiClick(tabDetails)==false) {
			Reporter.fnWriteToHtmlOutput("Select Details Tab", "Details tab should be selected", "Tab not selected successfully", "Fail");
			return false;
		}
		
		//return
		Reporter.fnWriteToHtmlOutput("Select Details Tab", "Details tab should be selected", "Tab selected successfully", "Pass");
		return true;
	}

}
