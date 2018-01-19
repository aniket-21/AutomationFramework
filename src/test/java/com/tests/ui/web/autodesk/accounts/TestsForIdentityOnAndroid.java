package com.tests.ui.web.autodesk.accounts;

import com.app.pageobjects.web.autodesk.accounts.LaunchApplication;
import com.app.pageobjects.web.autodesk.accounts.LoginPage;
import com.app.pageobjects.web.autodesk.accounts.ProfilePage;
import com.framework.base.BaseAppiumAndroidTest;
import com.framework.handlers.BMPHandler;
import org.openqa.selenium.Proxy;
import org.testng.Assert;
import org.testng.annotations.*;
import net.lightbody.bmp.proxy.BlacklistEntry;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by Aniket on 6/29/2015.
 */
public class TestsForIdentityOnAndroid extends BaseAppiumAndroidTest{

    //Variables
    BMPHandler bmphandler;

    @BeforeClass
    public void beforeClass() throws IOException {
        setClassName(this);
        super.beforeClass();

        bmphandler = new BMPHandler(env, className, browser);
    }

    @BeforeMethod
    public void beforeMethod(Method method) throws MalformedURLException {
        super.beforeMethod(method);
        //Initiate WebDriver
        if(driver==null){
            try{
                //Start proxy
                Proxy proxy = bmphandler.startBrowserMobProxy();
                BlacklistEntry ble = new BlacklistEntry("http:\\/\\/[a-z]*\\/|http:\\/\\/metrics.autodesk.com\\/.*",200);

                Collection<BlacklistEntry> blackListCollection = new ArrayList<BlacklistEntry>(Arrays.asList(ble));
                bmphandler.setBlacklist(blackListCollection);

                setAppiumChromeDriver("Asus Zenfone 5", "http://0.0.0.0:4723/wd/hub", proxy);
            }
            catch(UnknownHostException e){
                System.out.print("Exception " + e);
            }

        }
    }

    @Test
    public void testValidLogin(){

        bmphandler.createNewHar("LoginPage");

        LoginPage loginPage = new LaunchApplication(driver, Reporter)
                .launchIdentityApplication()
                .enterLoginCredentials("aniket@autodesk","Jaguar21");

        bmphandler.createNewHarPage("HomePage");

        ProfilePage profilePage = loginPage.clickSignIn(ProfilePage.class);
        Assert.assertTrue(profilePage.shouldHaveMyProfileTab(), "Assert Profile page has Profile Tab");
    }

    @AfterMethod
    public void afterMethod(Method method){
        super.afterMethod(method);
        if(driver != null) {
            bmphandler.generateHar(method.getName());
            bmphandler.stopBrowserMobProxy();
        }
    }

    @AfterClass
    public void afterClass(){
        super.afterClass();
    }


}
