package com.app.pageobjects.web.autodesk.accounts;

import com.framework.base.BasePage;
import com.framework.core.Reporting;
import com.framework.core.Wrappers;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

/**
 * Created by gadrea on 5/5/2015.
 */
public class ProfilePage extends BasePage{

    private Reporting Reporter;
    private WebDriver driver;
    private Wrappers objWrapper;

    public static final String pageTitle = "Autodesk - User Profile";
    String tbProfile = "classname:=profile";

    //Define the constructor
    public ProfilePage(WebDriver GDriver, Reporting GReporter)
    {
        Reporter = GReporter;
        driver = GDriver;
        objWrapper = new Wrappers(driver, Reporter);
    }

    public boolean shouldHaveMyProfileTab(){
        boolean isProfileTabPresent = objWrapper.isElementPresent(tbProfile);
        if(isProfileTabPresent){
            Reporter.writeToTestLevelReport("Check for Profile Tab", "Profile tab should be present", "Profile tab is present", "Pass");
            return true;
        }

        Reporter.writeToTestLevelReport("Check for Profile Tab", "Profile tab should be present", "Profile tab is not present", "Fail");
        return false;
    }
}
