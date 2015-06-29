package com.guru.prediction;

import com.automation.framework.Reporting;
import com.automation.framework.Wrappers;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

/**
 * Created by Aniket on 6/28/2015.
 */
public class WizardActivity {

    private Reporting Reporter;
    private io.appium.java_client.AppiumDriver driver;
    private HashMap<String, String> Dictionary;
    private HashMap<String, String> Environment;
    private Wrappers doAction;

    //Objects
    private String btnGooglePlus = "uiautomator:=new UiSelector().text(\"GOOGLE PLUS\")";
    private String btnGoogleSignIn = "accessibility_id:=com.google.android.gms:id/accept_button";
    private String btnCancelGoogleSignIn = "accessibility_id:=com.google.android.gms:id/cancel_button";


    //Constructor
    public WizardActivity(WebDriver GDriver,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
    {
        Reporter = GReporter;
        driver = (AppiumDriver) GDriver;
        Dictionary = GDictionary;
        Environment = GEnvironment;
        doAction = new Wrappers(driver, Reporter);
    }
}
