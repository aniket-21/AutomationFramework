package com.mercury.tours;

import java.util.Dictionary;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import com.amdocs.asap.*;


public class RegisterUserResultPage {
	
	
	private Reporting Reporter;
	private WebDriver driver;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions objCommon;
	public String lnkFlights = "LinkText:=Flights";
	
	//Define the constructor
	public RegisterUserResultPage(WebDriver GDriver,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
	{
		Reporter = GReporter;
		driver = GDriver;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		objCommon = new CommonFunctions(driver, Reporter);
	}
	
    
    //Method to launch the application
	public String getTitle()
	{
		return driver.getTitle();
	}
	
    //Method to Return object of Register User class
    public FindAFlight clickFlights()
    {
    	//Click on the Link Flights button
    	if (objCommon.fGuiClick(lnkFlights)==false)
    	{
    		return null;
    	}

    	Reporter.fnWriteToHtmlOutput("Click Link", "Link: Flights", "Cliked on Link: Flights", "Done");    	
            	
    	return new FindAFlight(driver,Dictionary,Environment,Reporter);
    }
    
    
    

}
