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
public class ChatActivity {
    private Reporting Reporter;
    private io.appium.java_client.AppiumDriver driver;
    private HashMap<String, String> Dictionary;
    private HashMap<String, String> Environment;
    private CommonFunctions objCommon;

    //Objects
    String edtSendText = "id:=com.blah.app:id/sendText";
    String btnSend = "id:=com.blah.app:id/send";
    String btnSmiley = "id:=com.blah.app:id/chat_select_emoticon_icon";

    //COnstructor
    public ChatActivity(WebDriver GDriver,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
    {
        Reporter = GReporter;
        driver = (AndroidDriver) GDriver;
        Dictionary = GDictionary;
        Environment = GEnvironment;
        objCommon = new CommonFunctions(driver, Reporter);
    }

    //*****************************************************************************************
    //*    Name        	: fSendText
    //*    Description 	: This function sends text to selected User
    //*    Author       : Aniket Gadre
    //*    Input Params : None
    //*    Return Values: boolean
    //*****************************************************************************************
    public boolean fSendText(String strText)
    {
        //Click on Search Image
        objCommon.getObject(edtSendText).sendKeys(strText);
        objCommon.fGuiClick(btnSend);

        //return
        Reporter.fnWriteToHtmlOutput("Send chat text","Chat text should be sent successfully","Chat text sent successfully","Pass");
        return true;
        //return new EditContactActivity(driver,Dictionary,Environment,Reporter);
    }

    //*****************************************************************************************
    //*    Name        	: fSendSmiley
    //*    Description 	: This function sends simley to selected User
    //*    Author       : Aniket Gadre
    //*    Input Params : None
    //*    Return Values: boolean
    //*****************************************************************************************
    public boolean fSendSmiley() throws InterruptedException {
        //Get smiley board
        if(objCommon.fGuiClick(btnSmiley) == false) return false;


        Thread.sleep(500);

        //Select Smiley and send
        List<WebElement> smileys =objCommon.getObjects("classname:=android.widget.ImageView");
        smileys.get(21).click();
        objCommon.fGuiClick(btnSend);

        //return
        Reporter.fnWriteToHtmlOutput("Send Smiley","Smiley should be sent successfully","Smiley sent successfully","Pass");
        return true;
        //return new EditContactActivity(driver,Dictionary,Environment,Reporter);
    }
}
