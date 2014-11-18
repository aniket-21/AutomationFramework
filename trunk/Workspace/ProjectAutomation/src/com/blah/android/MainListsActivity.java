package com.blah.android;

import com.amdocs.asap.CommonFunctions;
import com.amdocs.asap.Reporting;
import com.consors.android.GlobalSecurityListActivity;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ANIKETG on 11/11/2014.
 */
public class MainListsActivity {

    private Reporting Reporter;
    private io.appium.java_client.AppiumDriver driver;
    private HashMap<String, String> Dictionary;
    private HashMap<String, String> Environment;
    private CommonFunctions objCommon;

    //Objects
    String imgSearch = "id:=com.blah.app:id/action_search";
    String edtSearch = "id:=com.blah.app:id/searchbar_edittext";

    //Constructor
    public MainListsActivity(WebDriver GDriver,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
    {
        Reporter = GReporter;
        driver = (AndroidDriver) GDriver;
        Dictionary = GDictionary;
        Environment = GEnvironment;
        objCommon = new CommonFunctions(driver, Reporter);
    }





    //*****************************************************************************************
    //*    Name        	: fSearchAndSelectContact
    //*    Description 	: This function searches for already added contact
    //*    Author       : Aniket Gadre
    //*    Input Params : None
    //*    Return Values: boolean
    //*****************************************************************************************
    public UserActivity fSearchAndSelectContact()
    {
        //Enter name
        //if(objCommon.fGuiEnterText(edtSearch,"Test Test") == false) return null;
        objCommon.getObject(edtSearch).sendKeys(Dictionary.get("FIRSTNAME") + " " + Dictionary.get("LASTNAME"));

        //Get elements count
        List<WebElement> searchResults = objCommon.getObjects("classname:=android.widget.TextView");
        if(searchResults.size() > 0){
            Reporter.fnWriteToHtmlOutput("Validate that search results are displayed","Search results should be displayed","Search results displayed","Pass");
        }
        else{
            Reporter.fnWriteToHtmlOutput("Validate that search results are displayed","Search results should be displayed","Search results not displayed","Fail");
            return null;
        }

        //Select 1st contact
        searchResults.get(0).click();

        //return
        return new UserActivity(driver,Dictionary,Environment,Reporter);
    }


}
