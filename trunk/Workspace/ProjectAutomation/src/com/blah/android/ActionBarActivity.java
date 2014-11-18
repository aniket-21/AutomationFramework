package com.blah.android;

import com.amdocs.asap.CommonFunctions;
import com.amdocs.asap.Reporting;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ANIKETG on 11/11/2014.
 */
public class ActionBarActivity {


    private Reporting Reporter;
    private io.appium.java_client.AppiumDriver driver;
    private HashMap<String, String> Dictionary;
    private HashMap<String, String> Environment;
    private CommonFunctions objCommon;

    //Objects
    String btnSettings = "name:=More options";
    String mnuSettings = "uiautomator:=new UiSelector().text(\"Settings\")";
    String imgSearch = "id:=com.blah.app:id/action_search";

    //COnstructor
    public ActionBarActivity(WebDriver GDriver,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
    {
        Reporter = GReporter;
        driver = (AndroidDriver) GDriver;
        Dictionary = GDictionary;
        Environment = GEnvironment;
        objCommon = new CommonFunctions(driver, Reporter);
    }

    //*****************************************************************************************
    //*    Name        	: fNavigateToSettings
    //*    Description 	: Navigates to Settings screen
    //*    Author       : Aniket Gadre
    //*    Input Params : None
    //*    Return Values: boolean
    //*****************************************************************************************
    public SettingsActivity fNavigateToSettings()
    {
        //Check existence of More option button
        if(objCommon.fGuiClick(btnSettings) == false) return null;
        if(objCommon.fGuiClick(mnuSettings) == false) return null;

        //return
        return new SettingsActivity(driver,Dictionary,Environment,Reporter);
    }


    //*****************************************************************************************
    //*    Name        	: fNavigateToSearch
    //*    Description 	: Navigates to Search screen
    //*    Author       : Aniket Gadre
    //*    Input Params : None
    //*    Return Values: boolean
    //*****************************************************************************************
    public MainListsActivity fNavigateToSearch()
    {
        //Click on Search Image
        if(objCommon.fGuiClick(imgSearch) == false) return null;

        //return
        return new MainListsActivity(driver,Dictionary,Environment,Reporter);
    }
}
