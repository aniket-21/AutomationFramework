package com.autodesk.accounts;

import com.automation.framework.Driver;
import com.automation.framework.Reporting;
import com.automation.framework.Wrappers;
import com.automation.framework.BMPHandler;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Aniket on 6/29/2015.
 */
public class TestsForIdentityPerformance {

    //Variables
    String className;
    String env;
    BMPHandler bmphandler;

    //Instances
    Driver asapDriver;
    WebDriver driver = null;
    Wrappers objCommon;

    HashMap<String, String> Environment = new HashMap<String, String>();
    HashMap <String, String> Dictionary = new HashMap<String, String>();
    Reporting Reporter;

    @BeforeClass
    public void beforeClass() throws IOException {

        //Set the DataSheet name by getting the class name
        String[] strClassNameArray = this.getClass().getName().split("\\.");
        className = strClassNameArray[strClassNameArray.length-1];
        Environment.put("CLASSNAME", className);
        Environment.put("BROWSER", "appium");

        System.out.println("Before Class method for " + className);

        //Initiate asapDriver
        asapDriver = new Driver(Dictionary, Environment);

        //Get Env
        env = System.getProperty("envName");
        Assert.assertNotNull(env, "No Environment Parameter value received");

        //Add env global environments
        Environment.put("ENV_CODE", env);
        Assert.assertTrue(asapDriver.createExecutionFolders(),"Creating Execution Folders");
        Assert.assertTrue(asapDriver.fetchEnvironmentDetailsFromConfigXML(),"Fetching Environment Details");

        //Instantiate reporter
        Reporter = new Reporting(Dictionary, Environment);
        bmphandler = new BMPHandler(Environment);
        Reporter.createSummaryReport();
        Reporter.createJenkinsReport();
    }

    @BeforeMethod
    public void beforeMethod(Method method) throws MalformedURLException, UnknownHostException {
        String testName = method.getName();
        System.out.println("Before Method for test " + testName);
        asapDriver.getDataForTest(testName);
        Reporter.createTestLevelReport(testName);
        //bmphandler.createHARFolder(testName);

        //Initiate WebDriver
        if(driver==null){
            //Start proxy
            Proxy proxy = bmphandler.startBrowserMobProxy();

            driver = asapDriver.getAndroidChromeDriver("ASUS Zenfone 5","http://0.0.0.0:4723/wd/hub",proxy);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            Reporter.setDriver(driver);
        }

        //Initialize Common functions
        objCommon = new Wrappers(driver,Reporter);

    }

    @AfterMethod
    public void afterMethod(Method method){
        String testName = method.getName();
        System.out.println("After Method for test " + testName);
        asapDriver.setReferenceData();
        Reporter.closeTestLevelReport(testName);
        if(driver != null) {
            bmphandler.generateHar(testName);
            bmphandler.stopBrowserMobProxy();
            driver.quit();
            driver = null;
        }
    }

    @AfterClass
    public void afterClass(){
        System.out.println("After Class method for " + className);
        Reporter.closeTestSummaryReport();
        if(driver != null) {
            driver.quit();
            driver = null;
        }
    }


    @Test
    public void testValidLogin(){
        bmphandler.createNewHar("LoginPage");
        LaunchApplication app = new LaunchApplication(driver,Dictionary,Environment,Reporter);
        LoginPage loginPage = app.launchIdentityApplication();
        loginPage.enterLoginCredentials("aniket@autodesk","Jaguar21");
        bmphandler.createNewHarPage("HomePage");
        ProfilePage profilePage = loginPage.clickSignIn();
        Assert.assertTrue(profilePage.shouldHaveMyProfileTab(), "Assert Profile page has Profile Tab");
    }
}
