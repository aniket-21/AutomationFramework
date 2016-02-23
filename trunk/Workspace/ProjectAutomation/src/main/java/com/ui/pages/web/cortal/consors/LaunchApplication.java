package com.ui.pages.web.cortal.consors;

import java.util.HashMap;

import com.framework.components.core.Reporting;
import org.openqa.selenium.WebDriver;

public class LaunchApplication {
	
	private Reporting Reporter;
	private WebDriver driver;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	
	public LaunchApplication(WebDriver GDriver,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter){
		Reporter = GReporter;
		driver = GDriver;
		Dictionary = GDictionary;
		Environment = GEnvironment;
	}	
	
	public HomePage openApplication(){
		driver.get(Environment.get("CONSORS_URL"));
		Reporter.writeToTestLevelReport("Navigate to specified URL", "URL: https://www.cortalconsors.de/", "Navigated to URL:https://www.cortalconsors.de/", "Done");
		return new HomePage(driver, Dictionary,Environment,Reporter);
	}
}
