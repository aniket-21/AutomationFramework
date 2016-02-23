package com.tests.ui.web.autodesk.accounts;

import com.ui.pages.web.autodesk.accounts.LaunchApplication;
import com.ui.pages.web.autodesk.accounts.LoginPage;
import com.ui.pages.web.autodesk.accounts.ProfilePage;
import com.framework.components.Global;
import com.framework.components.base.BaseSeleniumWebTest;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

/**
 * Created by gadrea on 7/6/2015.
 */
public class TestsForGridAccountsLogin extends BaseSeleniumWebTest{

    @DataProvider(name = "browsers", parallel = true)
    public static Object[][] getData() {
        return Global.getBrowsersForGrid();
    }

    @Factory(dataProvider = "browsers")
    //Created with values from @DataProvider in @Factory
    public TestsForGridAccountsLogin(String browser, String browserVersion, String OS) {
        this.browser = browser;
        this.browserVersion = browserVersion;
        this.OS = OS;
    }

    @BeforeClass
    public void beforeClass() throws IOException {
        String[] strClassNameArray = this.getClass().getName().split("\\.");
        className = strClassNameArray[strClassNameArray.length-1];
        super.beforeClass();
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setBrowserName(browser);
        dc.setVersion(browserVersion);
        dc.setPlatform(asapDriver.getPlatform(OS));

        super.beforeMethod(method);

        try{
            setRemoteWebDriver("http://localhost:4444/wd/hub",dc);
        }
        catch(MalformedURLException e){
            System.out.println("Exception " + e);
        }
    }

    @Test
    public void testValidLogin(){
        LaunchApplication app = new LaunchApplication(driver,Dictionary,Environment,Reporter);
        LoginPage loginPage = app.launchIdentityApplication();
        loginPage.enterLoginCredentials("aniket@autodesk","Jaguar21");
        ProfilePage profilePage = loginPage.clickSignIn();
        Assert.assertTrue(profilePage.shouldHaveMyProfileTab(),"Assert Profile page has Profile Tab");
    }

    @Test
    public void testInvalidLoginError(){
        LaunchApplication app = new LaunchApplication(driver,Dictionary,Environment,Reporter);
        LoginPage loginPage = app.launchIdentityApplication();
        loginPage.enterLoginCredentials("aniket@autodesk","Jaguar22")
                .clickSignIn();
        Assert.assertTrue(loginPage.shouldDisplayError("Autodesk ID / email and password do not match."));
    }

    @Test
    public void testBlankCredentialsError(){
        LaunchApplication app = new LaunchApplication(driver,Dictionary,Environment,Reporter);
        LoginPage loginPage = app.launchIdentityApplication();
        loginPage.enterLoginCredentials("","")
                .clickSignIn();
        Assert.assertTrue(loginPage.shouldDisplayError("Please enter an Autodesk ID or email address."));
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
