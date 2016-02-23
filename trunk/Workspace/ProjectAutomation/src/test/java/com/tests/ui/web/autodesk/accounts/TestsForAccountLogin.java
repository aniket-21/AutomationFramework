package com.tests.ui.web.autodesk.accounts;

import com.ui.pages.web.autodesk.accounts.LaunchApplication;
import com.ui.pages.web.autodesk.accounts.LoginPage;
import com.ui.pages.web.autodesk.accounts.ProfilePage;
import com.framework.components.Global;
import com.framework.components.base.BaseSeleniumWebTest;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;


/**
 * Created by gadrea on 5/5/2015.
 */
public class TestsForAccountLogin extends BaseSeleniumWebTest{

    //DataProvider
    @DataProvider(name = "getBrowsers", parallel = true)
    public static Object[][] getBrowsers() {
        return Global.getBrowsers();
    }

    @Factory(dataProvider = "getBrowsers")
    //Created with values from @DataProvider in @Factory
    public TestsForAccountLogin(String browser) {
        this.browser = browser;
    }


    @BeforeClass
    public void beforeClass() throws IOException {
        String[] strClassNameArray = this.getClass().getName().split("\\.");
        className = strClassNameArray[strClassNameArray.length-1];
        super.beforeClass();
    }

    @BeforeMethod
    public void beforeMethod(Method method){
        super.beforeMethod(method);
        try{
            setWebDriver();
        }
        catch(MalformedURLException e){
            System.out.println("Exception " + e);
        }
    }

    @Test
    public void testValidLogin(){
        LaunchApplication app = new LaunchApplication(driver,Dictionary,Environment,Reporter);
        LoginPage loginPage = app.launchIdentityApplication();
        loginPage.enterLoginCredentials(Dictionary.get("USERNAME"),Dictionary.get("PASSWORD"));
        ProfilePage profilePage = loginPage.clickSignIn();
        Assert.assertTrue(profilePage.shouldHaveMyProfileTab(),"Assert Profile page has Profile Tab");
    }

    @Test
    public void testInvalidLoginError(){
        LaunchApplication app = new LaunchApplication(driver,Dictionary,Environment,Reporter);
        LoginPage loginPage = app.launchIdentityApplication();
        loginPage.enterLoginCredentials(Dictionary.get("USERNAME"),Dictionary.get("PASSWORD"))
                .clickSignIn();
        Assert.assertTrue(loginPage.shouldDisplayError("Email address / username and password do not match."));
    }

    @Test
    public void testBlankCredentialsError(){
        LaunchApplication app = new LaunchApplication(driver,Dictionary,Environment,Reporter);
        LoginPage loginPage = app.launchIdentityApplication();
        loginPage.enterLoginCredentials("","")
                .clickSignIn();
        Assert.assertTrue(loginPage.shouldDisplayError("Please enter an email address or username."));
        Assert.assertTrue(loginPage.shouldDisplayError("Please enter your password."));
    }

    @AfterMethod
    public void afterMethod(Method method){
        super.afterMethod(method);
    }

    @AfterClass
    public void afterClass(){
        super.afterClass();
    }
}
