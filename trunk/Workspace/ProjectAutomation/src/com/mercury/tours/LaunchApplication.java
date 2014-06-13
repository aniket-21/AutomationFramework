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
	
	public LaunchApplication(WebDriver GDriver,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
	{
		Reporter = GReporter;
		driver = GDriver;
		Dictionary = GDictionary;
		Environment = GEnvironment;
	}
	
	public MercuryHomePage openApplication()
	{
		driver.get("http://newtours.demoaut.com/");
		Reporter.fnWriteToHtmlOutput("Navigate to specified URL", "URL: http://newtours.demoaut.com", "Navigated to URL: http://newtours.demoaut.com" , "Done");
		return new MercuryHomePage(driver,Dictionary,Environment,Reporter);
	}
	
	public String getTitle()
	{
		return driver.getTitle();
	}

}
