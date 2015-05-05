package com.autodesk.oxygen;

import com.automation.framework.Reporting;
import com.automation.framework.Wrappers;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

/**
 * Created by gadrea on 4/2/2015.
 */
public class LoginPage {

    private Reporting Reporter;
    private WebDriver driver;
    private HashMap<String, String> Dictionary;
    private HashMap<String, String> Environment;
    private Wrappers objCommon;

    //Page UI Objects
    public String edtUserName = "id:=userName_str";
    public String edtPassword = "id:=password_str";
    public String btnSubmit = "xpath:=//div[@id='login_container']//button[@type='submit']";

    //Define the constructor
    public LoginPage(WebDriver GDriver, HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment, Reporting GReporter)
    {
        Reporter = GReporter;
        driver = GDriver;
        Dictionary = GDictionary;
        Environment = GEnvironment;
        objCommon = new Wrappers(driver, Reporter);
    }

    //*****************************************************************************************
    //*    Name        	: enterLoginCredentials
    //*    Author       : Aniket Gadre
    //*****************************************************************************************
    public void enterLoginCredentials()
    {
        //Enter Address details
        objCommon.enterText(edtUserName, Dictionary.get("USERNAME"));
        objCommon.enterText(edtPassword, Dictionary.get("PASSWORD"));

        Reporter.fnWriteToHtmlOutput("Enter Login Credentials","Login details should be Entered", "Login Details entered Successfully", "Pass");
    }
}
