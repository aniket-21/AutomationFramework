package com.app.pageobjects.web.autodesk.accounts;

import com.framework.core.Reporting;
import org.openqa.selenium.WebDriver;

public class LaunchApplication {
	
	private Reporting reporter;
	private WebDriver driver;
	
	public LaunchApplication(WebDriver GDriver,  Reporting GReporter){
		reporter = GReporter;
		driver = GDriver;
	}	
	
	public LoginPage launchIdentityApplication(){
		driver.get("http://accounts.autodesk.com?uitype=oldui");
		reporter.writeToTestLevelReport("Navigate to specified URL", "URL: " , "Navigated to URL: " , "Done");
		return new LoginPage(driver, reporter);
	}
}
