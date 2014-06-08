package com.mercury.tours;

import java.util.Dictionary;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import com.amdocs.asap.*;

public class LaunchApplication {
	
	private Reporting Reporter;
	private WebDriver driver;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	
	public LaunchApplication()
	{
		Reporter = Global.Reporter;
		driver = Global.webDriver;
		Dictionary = Global.Dictionary;
		Environment = Global.Environment;
	}
	
	public MercuryHomePage openApplication()
	{
		driver.get("http://newtours.demoaut.com/");
		Reporter.fnWriteToHtmlOutput("Navigate to specified URL", "URL: http://newtours.demoaut.com", "Navigated to URL: http://newtours.demoaut.com" , "Done");
		return new MercuryHomePage();
	}
	
	public String getTitle()
	{
		return driver.getTitle();
	}

}
