package com.linkedin.android;

import io.appium.java_client.AppiumDriver;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.amdocs.asap.CommonFunctions;
import com.amdocs.asap.Global;
import com.amdocs.asap.Reporting;

public class LoginActivity {
	
	
	private Reporting Reporter;
	private io.appium.java_client.AppiumDriver driver;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions objCommon = new CommonFunctions();
	
	//Objects
	String edtCredentials = "AppClassName:=android.widget.EditText";
	String btnSignIn = "Accessibility_id:=Sign In";
	
	//COnstructor
	public LoginActivity()
	{
		Reporter = Global.Reporter;
		driver = (io.appium.java_client.AppiumDriver)Global.webDriver;
		Dictionary = Global.Dictionary;
		Environment = Global.Environment;
	}
	
	//*****************************************************************************************
    //*    Name        	: fEnterLoginCredentials
    //*    Description 	: This function enters login details
    //*    Author       : Aniket Gadre
    //*    Input Params : None
    //*    Return Values: None
    //*****************************************************************************************
	public boolean fEnterLoginCredentials(String strUserName, String Password)
	{
		
		//Get credential edit boxes
		List<WebElement> credentials = objCommon.getObjects(edtCredentials);
			
		//Enter Credentials
		if(objCommon.fGuiEnterText(credentials.get(0), strUserName)==false) return false;
		if(objCommon.fGuiEnterText(credentials.get(1), Password)==false) return false;
		
		//return
		return true;
	}
	
	
	//*****************************************************************************************
    //*    Name        	: fClickSignIn
    //*    Description 	: This function clicks on sign in on Login Page
    //*    Author       : Aniket Gadre
    //*    Input Params : None
    //*    Return Values: None
    //*****************************************************************************************
	public HomeActivity fClickSignIn()
	{
		//Click on Sign in  
		if(objCommon.fGuiClick(btnSignIn)==false) return null;
		
		//return
		return new HomeActivity();
	}

}
