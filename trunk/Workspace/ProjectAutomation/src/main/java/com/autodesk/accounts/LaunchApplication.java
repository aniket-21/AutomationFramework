package com.autodesk.accounts;

import com.automation.framework.Reporting;
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
	
	public LoginPage launchIdentityApplication(){
		driver.get(Environment.get("OXYGEN_URL"));
		Reporter.writeToTestLevelReport("Navigate to specified URL", "URL: " + Environment.get("OXYGEN_URL"), "Navigated to URL: " + Environment.get("OXYGEN_URL"), "Done");
		return new LoginPage(driver, Dictionary,Environment,Reporter);
	}
}
