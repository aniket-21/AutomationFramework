package com.consors.web;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import com.amdocs.asap.CommonFunctions;
import com.amdocs.asap.Global;
import com.amdocs.asap.Reporting;

public class CheckingAccountEntry {

	private Reporting Reporter;
	private WebDriver driver;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions objCommon;
	
	public String rdbtnNonExistingCustomer = "id:=ev-customer-of-cc-no";    
    public String rdbtnNoJointAccount="id:=ev-joint-account-no";
    public String btnMore="id:=expoButtonNext";
    
	//Define the constructor
    public CheckingAccountEntry(WebDriver GDriver,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
	{
		Reporter = GReporter;
		driver = GDriver;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		objCommon = new CommonFunctions(driver, Reporter);
	}
	
	//CommonFunctions objCommon = new CommonFunctions(driver, Reporter);
	
	public String getTitle()
	{
		return driver.getTitle();
	}
	
	//*****************************************************************************************
    //*    Name        	: selectNonExistingCustomer
    //*    Description 	: This function Selects Radiobutton Non Existing Customer
    //*    Author       : Bharat Joshi
    //*    Input Params : None
    //*    Return Values: None
    //*****************************************************************************************
	public boolean selectNonExistingCustomer()
	{             
		//Select radio button non existing Customer
		 if (objCommon.fGuiClick(rdbtnNonExistingCustomer)==false)
	     {
	     	return false;
	     }  
		 
		 return true;
	}
	  
	//*****************************************************************************************
    //*    Name        	: selectNoJointAccount
    //*    Description 	: This function Selects Radiobutton Joint Account
    //*    Author       : Bharat Joshi
    //*    Input Params : None
    //*    Return Values: None
    //*****************************************************************************************
	public boolean selectNoJointAccount()
	{             
		
		 //select Radio button No Joint account
	     if (objCommon.fGuiClick(rdbtnNoJointAccount)==false)
	     {
	     	return false;
	     }			
		 
		 return true;
	}
	
    
	//Click on Button More
	//*****************************************************************************************
    //*    Name        	: openCheckingAccountForm
    //*    Description 	: This function Opens Accoutn Details Forms
    //*    Author       : Bharat Joshi
    //*    Input Params : None
    //*    Return Values: None
    //*****************************************************************************************
	public CheckingAccountDetails openCheckingAccountForm()
	{
		
		//click on Button More
		if (objCommon.fGuiClick(btnMore)==false)
	     {
	     	return null;
	     }			
		 
		 
		return new CheckingAccountDetails(driver, Dictionary,Environment,Reporter);  

       
	}
}
