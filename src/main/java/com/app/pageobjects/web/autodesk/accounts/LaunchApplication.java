package com.ui.pageobjects.web.autodesk.accounts;

import com.framework.core.Reporting;
import org.openqa.selenium.WebDriver;
import java.util.HashMap;

public class LaunchApplication {
	
	private Reporting reporter;
	private WebDriver driver;
	
	public LaunchApplication(WebDriver GDriver,  Reporting GReporter){
		reporter = GReporter;
		driver = GDriver;
	}	
	
	public LoginPage launchIdentityApplication(){
		driver.get(environment.get("OXYGEN_URL") + "?uitype=oldui");
		reporter.writeToTestLevelReport("Navigate to specified URL", "URL: " + environment.get("OXYGEN_URL"), "Navigated to URL: " + environment.get("OXYGEN_URL"), "Done");
		return new LoginPage(driver, reporter);
	}
}
