package com.consors.web;

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
	
	public HomePage openApplication()
	{
		//driver.get("https://www.cortalconsors.de/home");
		driver.get(Environment.get("CORTOL_URL"));
		Reporter.fnWriteToHtmlOutput("Navigate to specified URL", "URL: https://www.cortalconsors.de/", "Navigated to URL:https://www.cortalconsors.de/" , "Done");
		return new HomePage(driver, Dictionary,Environment,Reporter);
	}
	
	public String getTitle()
	{
		return driver.getTitle();
	}
	

}
