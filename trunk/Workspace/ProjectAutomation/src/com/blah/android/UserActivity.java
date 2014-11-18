package com.blah.android;

import com.amdocs.asap.CommonFunctions;
import com.amdocs.asap.Reporting;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

/**
 * Created by ANIKETG on 11/11/2014.
 */
public class UserActivity {

    private Reporting Reporter;
    private io.appium.java_client.AppiumDriver driver;
    private HashMap<String, String> Dictionary;
    private HashMap<String, String> Environment;
    private CommonFunctions objCommon;

    //Objects
    String btnChat = "uiautomator:=new UiSelector().text(\"Chat\")";
    String btnEditContact = "uiautomator:=new UiSelector().text(\"Edit contact\")";

    //COnstructor
    public UserActivity(WebDriver GDriver,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
    {
        Reporter = GReporter;
        driver = (AndroidDriver) GDriver;
        Dictionary = GDictionary;
        Environment = GEnvironment;
        objCommon = new CommonFunctions(driver, Reporter);
    }

    //*****************************************************************************************
    //*    Name        	: fStartChat
    //*    Description 	: This function starts the chat for currently selected User
    //*    Author       : Aniket Gadre
    //*    Input Params : None
    //*    Return Values: boolean
    //*****************************************************************************************
    public ChatActivity fStartChat()
    {
        //Click on Chat
        if(objCommon.fGuiClick(btnChat) == false) return null;

        //return
        return new ChatActivity(driver,Dictionary,Environment,Reporter);
    }

    //*****************************************************************************************
    //*    Name        	: fEditContact
    //*    Description 	: This function clicks on edit contact
    //*    Author       : Aniket Gadre
    //*    Input Params : None
    //*    Return Values: boolean
    //*****************************************************************************************
    public EditContactActivity fEditContact()
    {
        //Click on Edit Contact
        driver.scrollTo("Edit contact");
        if(objCommon.fGuiClick(btnEditContact) == false) return null;

        //return
        return new EditContactActivity(driver,Dictionary,Environment,Reporter);
    }
}
