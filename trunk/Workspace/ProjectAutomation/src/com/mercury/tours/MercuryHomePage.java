package com.mercury.tours;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import com.amdocs.asap.*;


public class MercuryHomePage {
	
	
	private Reporting Reporter;
	private WebDriver driver;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions objCommon;
	
	//Define the constructor
	public MercuryHomePage(WebDriver GDriver,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
	{
		Reporter = GReporter;
		driver = GDriver;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		objCommon = new CommonFunctions(driver, Reporter);
	}

	public String lnkRegister = "LinkText:=REGISTER";
    public String edtLogin = "Name:=userName";
    public String edtPassword = "Name:=password";
    public String webbtnLogin = "Xpath:=//input[@name=\"login\"]";
    
    //Method to launch the application
    //Method to launch the application
    public FindAFlight loginMercury()
    {
    	//Enter Username
        if (objCommon.fGuiEnterText(edtLogin,Dictionary.get("USERNAME"))==false)
        {
        	return null;
        }
        
    	//Enter Password
        if (objCommon.fGuiEnterText(edtPassword,Dictionary.get("PASSWORD"))==false)
        {
        	return null;
        }        
    	
    	//Click on the sign in button. This will return 
    	if (objCommon.fGuiClick(webbtnLogin)==false)
    	{
    		return null;
    	}
    	return new FindAFlight(driver,Dictionary,Environment,Reporter);
    }
    
    //Method to Return object of Register User class
    public RegisterUser clickRegister()
    {
    	if (objCommon.fGuiClick(lnkRegister)==false)
    	{
    		return null;
    	}
    	Reporter.fnWriteToHtmlOutput("Click Link", "Link: REGISTER", "Cliked on Link: REGISTER", "Done");    	
            	
    	return new RegisterUser(driver,Dictionary,Environment,Reporter);
    }
    
    
	public String getTitle()
	{
		return driver.getTitle();
	}

}
