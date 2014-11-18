package com.blah.android;

import com.amdocs.asap.CommonFunctions;
import com.amdocs.asap.Reporting;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

/**
 * Created by ANIKETG on 11/11/2014.
 */
public class SettingsActivity {

    private Reporting Reporter;
    private io.appium.java_client.AppiumDriver driver;
    private HashMap<String, String> Dictionary;
    private HashMap<String, String> Environment;
    private CommonFunctions objCommon;

    //Objects
    String txtAddAContact = "uiautomator:=new UiSelector().text(\"Add a contact\")";

    //COnstructor
    public SettingsActivity(WebDriver GDriver,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
    {
        Reporter = GReporter;
        driver = (AndroidDriver) GDriver;
        Dictionary = GDictionary;
        Environment = GEnvironment;
        objCommon = new CommonFunctions(driver, Reporter);
    }

    //*****************************************************************************************
    //*    Name        	: fSelectAddAContact
    //*    Description 	: This function selects Add a contact option in Setting
    //*    Author       : Aniket Gadre
    //*    Input Params : None
    //*    Return Values: boolean
    //*****************************************************************************************
    public EditContactActivity fSelectAddAContact()
    {
        //Click on Search Image
        if(objCommon.fGuiClick(txtAddAContact) == false) return null;

        //return
        return new EditContactActivity(driver,Dictionary,Environment,Reporter);
    }
}
