package com.blah.android;

import com.amdocs.asap.CommonFunctions;
import com.amdocs.asap.Reporting;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ANIKETG on 11/11/2014.
 */
public class EditContactActivity {

    private Reporting Reporter;
    private io.appium.java_client.AppiumDriver driver;
    private HashMap<String, String> Dictionary;
    private HashMap<String, String> Environment;
    private CommonFunctions objCommon;

    //Objects
    String edtFirstName =  "uiautomator:=new UiSelector().text(\"First name\")";
    String edtLastName =  "uiautomator:=new UiSelector().text(\"Last name\")";
    String edtCompany =  "uiautomator:=new UiSelector().text(\"Company\")";
    String edtPhone = "id:=com.blah.app:id/edittext_fieldvalue";
    String btnSave = "id:=com.blah.app:id/action_save";
    String btnDeleteContact = "uiautomator:=new UiSelector().text(\"Delete contact\")";

    //Constructor
    public EditContactActivity(WebDriver GDriver,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
    {
        Reporter = GReporter;
        driver = (AndroidDriver) GDriver;
        Dictionary = GDictionary;
        Environment = GEnvironment;
        objCommon = new CommonFunctions(driver, Reporter);
    }

    //*****************************************************************************************
    //*    Name        	: fFillContactDetails
    //*    Description 	: This function fills the required details
    //*    Author       : Aniket Gadre
    //*    Input Params : None
    //*    Return Values: None
    //*****************************************************************************************
    public boolean fFillContactDetails()
    {
        //First Name
        if(objCommon.fGuiClick(edtFirstName) == false) return false;
        objCommon.getObject(edtFirstName).sendKeys(Dictionary.get("FIRSTNAME"));
        //((JavascriptExecutor)driver).executeScript("(new UiObject(new UiSelector().text(\"First name\"))).setText(\"Test\")");

        //Last Name
        if(objCommon.fGuiClick(edtLastName) == false) return false;
        objCommon.getObject(edtLastName).sendKeys(Dictionary.get("LASTNAME"));

        //Company
        if(objCommon.fGuiClick(edtCompany) == false) return false;
        objCommon.getObject(edtCompany).sendKeys(Dictionary.get("COMPANY"));

        //phone
        if(objCommon.fGuiClick(edtPhone) == false) return false;
        if(objCommon.fGuiEnterText(edtPhone,Dictionary.get("PHONE")) == false) return false;

        //return
        Reporter.fnWriteToHtmlOutput("Fill Contact Details", "Contact details should be filled successfully", "Contact details successfully", "Pass");
        return true;
    }

    //*****************************************************************************************
    //*    Name        	: fSaveContactDetails
    //*    Description 	: This function clicks on Save button
    //*    Author       : Aniket Gadre
    //*    Input Params : None
    //*    Return Values: None
    //*****************************************************************************************
    public SettingsActivity fSaveContactDetails(){
        if(objCommon.fGuiClick(btnSave) == false) return null;

        return new SettingsActivity(driver,Dictionary,Environment,Reporter);
    }


    //*****************************************************************************************
    //*    Name        	: fDeleteContact
    //*    Description 	: This function clicks on Delete Contact button
    //*    Author       : Aniket Gadre
    //*    Input Params : None
    //*    Return Values: None
    //*****************************************************************************************
    public boolean fDeleteContact(){
        //Click on Edit Contact
        driver.scrollTo("Delete contact");
        if(objCommon.fGuiClick(btnDeleteContact) == false) return false;

        //return new SettingsActivity(driver,Dictionary,Environment,Reporter);
        return true;
    }
}
