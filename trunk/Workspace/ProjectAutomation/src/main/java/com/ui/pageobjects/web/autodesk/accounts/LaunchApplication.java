package com.ui.pageobjects.web.autodesk.accounts;

import com.framework.core.Reporting;
import org.openqa.selenium.WebDriver;
import java.util.HashMap;

public class LaunchApplication {
	
	private Reporting reporter;
	private WebDriver driver;
	private HashMap<String, String> dictionary;
	private HashMap<String, String> environment;
	
	public LaunchApplication(WebDriver GDriver, HashMap<String, String> dictionary, HashMap<String, String> environment, Reporting GReporter){
		reporter = GReporter;
		driver = GDriver;
		this.dictionary = dictionary;
		this.environment = environment;
	}	
	
	public LoginPage launchIdentityApplication(){
		driver.get(environment.get("OXYGEN_URL"));
		reporter.writeToTestLevelReport("Navigate to specified URL", "URL: " + environment.get("OXYGEN_URL"), "Navigated to URL: " + environment.get("OXYGEN_URL"), "Done");
		return new LoginPage(driver, dictionary, environment, reporter);
	}
}
