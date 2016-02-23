package com.ui.pages.android.cortal.consors;

import com.framework.components.core.Wrappers;
import io.appium.java_client.AppiumDriver;

import java.util.HashMap;
import com.framework.components.core.Reporting;
import org.openqa.selenium.WebDriver;

public class SnapShotActivity {

	private Reporting Reporter;
	private io.appium.java_client.AppiumDriver driver;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private Wrappers objCommon;
	
	//Objects
	String tabDetails = "uiautomator:=new UiSelector().text(\"Profil\")";	

	//COnstructor
	public SnapShotActivity(WebDriver GDriver,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
	{
		Reporter = GReporter;
		driver = (AppiumDriver) GDriver;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		objCommon = new Wrappers(driver, Reporter);
	}	
	
	
	//*****************************************************************************************
    //*    Name        	: fSelectDetailsTab
    //*    Description 	: This function selects menu and navigates to Marches
    //*    Author       : Aniket Gadre
    //*    Input Params : None
    //*    Return Values: None
    //*****************************************************************************************
	public void fSelectDetailsTab()
	{				
		//Select Devises	
		objCommon.click(tabDetails);
	}

}
