package com.ui.pages.web.cortal.consors;

import java.util.HashMap;

import com.framework.components.core.Wrappers;
import com.framework.components.core.Reporting;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class HomePage {
	
	
	private Reporting Reporter;
	private WebDriver driver;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private Wrappers objCommon;
	
	public String lnkCurrentAccount = "partiallinktext:=Girokonto";
	public String webbtnOpenCheckingAccount = "partiallinktext:=Girokonto eröffnen";
    public String webbtnMenu = "classname:=ev-pagehead-mobile-nav-trigger";
    
	//Define the constructor
	//Define the constructor
    public HomePage(WebDriver GDriver,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
	{
		Reporter = GReporter;
		driver = GDriver;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		objCommon = new Wrappers(driver, Reporter);
	}
	
	//*****************************************************************************************
    //*    Name        	: clickCurrentAccount
    //*    Description 	: This function Click Link Current Account
    //*    Author       : Bharat Joshi
    //*    Input Params : None
    //*    Return Values: None
    //*****************************************************************************************
	public void clickCurrentAccount()
	{             
	    if(driver.toString().contains("ANDROID")){

            ((JavascriptExecutor)driver).executeScript("document.getElementsByClassName('ev-pagehead-mobile-nav-menu')[0].getElementsByTagName('a')[2].click();");
        }
        else{

            //Click on link Current Account
            objCommon.click(lnkCurrentAccount);
        }
	}
	
	//*****************************************************************************************
    //*    Name        	: clickOpenCheckingAccount
    //*    Author       : Bharat Joshi
    //*****************************************************************************************
	public CheckingAccountPage clickOpenCheckingAccount()
	{             
		//Click on link Current Account
        objCommon.click(webbtnOpenCheckingAccount);
		 
		return  new CheckingAccountPage(driver, Dictionary,Environment,Reporter);
	}
	
	
}
