package com.linkedin.android;

import io.appium.java_client.AppiumDriver;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import com.amdocs.asap.CommonFunctions;
import com.amdocs.asap.Global;
import com.amdocs.asap.Reporting;

public class HomeActivity {
	
	private Reporting Reporter;
	private io.appium.java_client.AppiumDriver driver;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions objCommon = new CommonFunctions();
	
	//COnstructor
	public HomeActivity()
	{
		Reporter = Global.Reporter;
		driver = (io.appium.java_client.AppiumDriver)Global.webDriver;
		Dictionary = Global.Dictionary;
		Environment = Global.Environment;
	}

}
