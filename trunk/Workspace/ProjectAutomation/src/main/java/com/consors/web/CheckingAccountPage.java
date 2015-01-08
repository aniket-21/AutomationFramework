package com.consors.web;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import com.automation.framework.Wrappers;
import com.automation.framework.Reporting;

public class CheckingAccountPage {

	private Reporting Reporter;
	private WebDriver driver;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private Wrappers objCommon;
	
	public String rdbtnNonExistingCustomer = "id:=ev-customer-of-cc-no";    
    public String rdbtnNoJointAccount="id:=ev-joint-account-no";
    public String btnMore="id:=expoButtonNext";
    
	//Define the constructor
    public CheckingAccountPage(WebDriver GDriver, HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment, Reporting GReporter)
	{
		Reporter = GReporter;
		driver = GDriver;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		objCommon = new Wrappers(driver, Reporter);
	}
	
	//Wrappers objCommon = new Wrappers(driver, Reporter);
	
	//*****************************************************************************************
    //*    Name        	: selectNonExistingCustomer
    //*    Author       : Bharat Joshi
    //*****************************************************************************************
	public void selectNonExistingCustomer(){
		//Select radio button non existing Customer
		 objCommon.click(rdbtnNonExistingCustomer);
	}
	  
	//*****************************************************************************************
    //*    Name        	: selectNoJointAccount
    //*    Author       : Bharat Joshi
    //*****************************************************************************************
	public void selectNoJointAccount(){
		 //select Radio button No Joint account
	     objCommon.click(rdbtnNoJointAccount);
	}

	//*****************************************************************************************
    //*    Name        	: openCheckingAccountForm
    //*    Author       : Bharat Joshi
    //*****************************************************************************************
	public CheckingAccountDetailsPage openCheckingAccountForm(){
		//click on Button More
		objCommon.click(btnMore);

		return new CheckingAccountDetailsPage(driver, Dictionary,Environment,Reporter);
	}
}
