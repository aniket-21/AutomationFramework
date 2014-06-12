package com.mercury.tours;


import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import com.amdocs.asap.*;

public class RegisterUser {

    public String edtFirstName = "Name:=firstName";
    public String edtLastName = "Name:=lastName";
    public String edtPhoneNo = "Name:=phone";
	public String edtEmail = "ID:=userName";
	public String edtAddressLine1 = "Name:=address1";
	public String edtAddressLine2 = "Name:=address2";
	public String edtCity = "Name:=city";
	public String edtState = "Name:=state";
	public String edtPostalCode = "Name:=postalCode";
	public String lstCountry = "Name:=country";
	public String edtUserName = "Name:=email";
	public String edtPassword = "Name:=password";
	public String edtConfirmPassword = "Name:=confirmPassword";
	public String btnSubmit = "Xpath:=//input[@name=\"register\"]";
	

	private Reporting Reporter;
	private WebDriver driver;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions objCommon = new CommonFunctions()
	;
	//Define the constructor
	public RegisterUser()
	{
		Reporter = Global.Reporter;
		driver = Global.webDriver;
		Dictionary = Global.Dictionary;
		Environment = Global.Environment;
	}

	
	
	 //*****************************************************************************************
    //*	Name		    : fGuiRegisterUser
    //*	Description	    : Function to register a user in the application
    //*	Author		    : Anil Agarwal
    //*	Input Params	: None
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************	
	public RegisterUserResultPage fGuiRegisterUser(){
		try
		{
			//Enter contact information
			//Enter first name
            if (objCommon.fGuiEnterText(edtFirstName, Dictionary.get("FIRSTNAME"))==false)
            {
            	return null;
            }
            //Enter Last Name
            objCommon.getObject(edtLastName).click();
            if (objCommon.fGuiEnterText(edtLastName, Dictionary.get("LASTNAME"))==false)
            {
            	return null;
            }
            //Enter phone no
            if (objCommon.fGuiEnterText(edtPhoneNo, Dictionary.get("PHONENO"))==false)
            {
            	return null;
            }
            //ENter email
            if (objCommon.fGuiEnterText(edtEmail, Dictionary.get("EMAIL"))==false)
            {
            	return null;
            }
            
			//Enter Address Line 1
            if (objCommon.fGuiEnterText(edtAddressLine1, Dictionary.get("ADDLINE1"))==false)
            {
            	return null;
            }
            //Enter City
            if (objCommon.fGuiEnterText(edtCity, Dictionary.get("CITY"))==false)
            {
            	return null;
            }
            //Enter State
            if (objCommon.fGuiEnterText(edtState, Dictionary.get("STATE"))==false)
            {
            	return null;
            }
            //ENter postal code
            if (objCommon.fGuiEnterText(edtPostalCode, Dictionary.get("POSTALCODE"))==false)
            {
            	return null;
            }            
            
			//Function call to select the Country from the drop down 
            if (objCommon.fGuiSelectOptionFromList(lstCountry,Dictionary.get("COUNTRY"))==false)
            {
            	return null;
            }

            //Enter Username
            if (objCommon.fGuiEnterText(edtUserName, Dictionary.get("USERNAME"))==false)
            {
            	return null;
            }
            //ENter Password
            if (objCommon.fGuiEnterText(edtPassword, Dictionary.get("PASSWORD"))==false)
            {
            	return null;
            }            
            
            //Reenter Password 
            objCommon.getObject(edtLastName).click();
            if (objCommon.fGuiEnterText(edtConfirmPassword,Dictionary.get("PASSWORD"))==false)
            {
            	return null;
            }
			
            Thread.sleep(500);
            //Report the filling of all details
            Reporter.fnWriteToHtmlOutput("Registration Form", "Fill all the required details", "All details entered successfully", "Pass");

			//Click on the login button
            if (objCommon.fGuiClick(btnSubmit)==false)
            {
            	return null;
            }

            //Report the filling of all details
            Dictionary.put("CUSTOMER", Dictionary.get("USERNAME"));
            Reporter.fnWriteToHtmlOutput("Register User", "Register User in application", "User registered successfully", "Pass");
			
           
 			//return True in case the test case passes
			return new RegisterUserResultPage();
		} catch (Exception e)
		{
			System.out.print("Exception is " + e);
			return null;
		}
	}
	
	public String getTitle()
	{
		return driver.getTitle();
	}
	
}
