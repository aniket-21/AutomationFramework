package com.autodesk.oxygen;

import com.automation.framework.Reporting;
import com.consors.web.HomePage;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

public class LaunchApplication {
	
	private Reporting Reporter;
	private WebDriver driver;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	
	public LaunchApplication(WebDriver GDriver, HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment, Reporting GReporter){
		Reporter = GReporter;
		driver = GDriver;
		Dictionary = GDictionary;
		Environment = GEnvironment;
	}	
	
	public HomePage openApplication(){
		driver.get(Environment.get("OXYGEN_URL"));
		Reporter.fnWriteToHtmlOutput("Navigate to specified URL", "URL: " + Environment.get("OXYGEN_URL"), "Navigated to URL: " + Environment.get("OXYGEN_URL") , "Done");
		return new HomePage(driver, Dictionary,Environment,Reporter);
	}
}
