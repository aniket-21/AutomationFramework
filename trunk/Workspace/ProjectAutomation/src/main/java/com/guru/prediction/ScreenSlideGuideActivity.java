package com.guru.prediction;

import com.automation.framework.core.Reporting;
import com.automation.framework.core.Wrappers;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

/**
 * Created by Aniket on 6/26/2015.
 */
public class ScreenSlideGuideActivity {

    private Reporting Reporter;
    private io.appium.java_client.AppiumDriver driver;
    private HashMap<String, String> Dictionary;
    private HashMap<String, String> Environment;
    private Wrappers doAction;

    //Objects
    private String btnGooglePlus = "id:=g_plus";
    private String btnFacebook = "id:=fb_button";
    private String btnGoogleSignIn = "accessibility_id:=com.google.android.gms:id/accept_button";
    private String btnCancelGoogleSignIn = "accessibility_id:=com.google.android.gms:id/cancel_button";


    //Constructor
    public ScreenSlideGuideActivity(WebDriver GDriver,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
    {
        Reporter = GReporter;
        driver = (AppiumDriver) GDriver;
        Dictionary = GDictionary;
        Environment = GEnvironment;
        doAction = new Wrappers(driver, Reporter);
    }

    public ScreenSlideGuideActivity navigateToGoogleSignIn(){
        doAction.click(btnGooglePlus);
        return this;
    }

    public ScreenSlideGuideActivity navigateTofacebookSignIn(){
        doAction.click(btnFacebook);
        return this;
    }

    public boolean isPermissionsPopUpDisplayed() throws InterruptedException{
        return doAction.isWebElementDisplayed(btnCancelGoogleSignIn);
    }

    public void googleSignIn(){
        doAction.click(btnGoogleSignIn);
    }

    public WizardActivity loginWithGooglePlus() throws InterruptedException {
        //Wait for activity
        doAction.waitForAndroidActivity(".activity.ScreenSlideGuide", 20);

        //G+ Sign In
        ScreenSlideGuideActivity objScreenSlideGuide = new ScreenSlideGuideActivity(driver,Dictionary,Environment,Reporter);
        objScreenSlideGuide.navigateToGoogleSignIn();

        if(isPermissionsPopUpDisplayed()) googleSignIn();

        //Wait for Wizard Activity
        doAction.waitForAndroidActivity(".activity.WizardMainActivity", 20);
        return new WizardActivity(driver,Dictionary,Environment,Reporter);
    }

    public WizardActivity loginWithFacebook() throws InterruptedException {
        //Wait for activity
        doAction.waitForAndroidActivity(".activity.ScreenSlideGuide", 20);

        //G+ Sign In
        ScreenSlideGuideActivity objScreenSlideGuide = new ScreenSlideGuideActivity(driver,Dictionary,Environment,Reporter);
        objScreenSlideGuide.navigateTofacebookSignIn();

        //Wait for Wizard Activity
        doAction.waitForAndroidActivity(".activity.WizardMainActivity", 30);
        return new WizardActivity(driver,Dictionary,Environment,Reporter);
    }
}
