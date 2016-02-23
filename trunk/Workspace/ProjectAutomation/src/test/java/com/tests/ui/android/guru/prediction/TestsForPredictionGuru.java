package com.tests.ui.android.guru.prediction;

import com.ui.pages.android.guru.prediction.ScreenSlideGuideActivity;
import com.ui.pages.android.guru.prediction.WizardActivity;
import com.framework.components.core.Driver;
import com.framework.components.helpers.Generic;
import com.framework.components.core.Reporting;
import com.framework.components.core.Wrappers;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Aniket on 6/26/2015.
 */
public class TestsForPredictionGuru {

    //Variables
    String className;
    String env;
    String browser;
    String appPackage = "com.guru.prediction";
    String appActivity = ".activity.AnimationPage";

    //Instances
    Driver asapDriver;
    WebDriver driver = null;
    Wrappers doAction;
    AppiumDriverLocalService service;

    HashMap<String, String> Environment = new HashMap<String, String>();
    HashMap <String, String> Dictionary = new HashMap<String, String>();
    Reporting Reporter;

   /* @DataProvider(name = "browsers", parallel = true)
    public static Object[][] getBrowsers() {
        String[] browser = System.getProperty("browserName").split(",");
        final int size = browser.length;
        Object[][] browsers = new Object[size][1];
        for(int i=0;i<browser.length;i++) {
            System.out.println(browser[i]);
            browsers[i][0] = browser[i];
        }
        return browsers;
    }*/

    @BeforeClass
    public void beforeClass() throws IOException {
        //Set the DataSheet name by getting the class name
        String[] strClassNameArray = this.getClass().getName().split("\\.");
        className = strClassNameArray[strClassNameArray.length- 1];
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
        Reporter.createSummaryReport();
        Reporter.createJenkinsReport();

        //start appium server
        service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder());
        service.start();
        Assert.assertTrue(service.isRunning(), "Appium server is not running");
    }

    @BeforeMethod
    public void beforeMethod(Method method) throws MalformedURLException {
        /*String testName = method.getName();
        System.out.println("Before Method for test " + testName);
        asapDriver.getDataForTest(testName);
        Reporter.createTestLevelReport(testName);*/

        //Initiate WebDriver
        if(driver == null){
            //String apkPath = Environment.get("STORAGEFOLDERPATH") + "\\apks\\PredictionGuru.apk";
            driver = asapDriver.getAppiumAndroidDriver(appPackage,appActivity,"ASUS Zenfone 5","http://0.0.0.0:4723/wd/hub");
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            Reporter.setDriver(driver);

            //Initialize Common functions
            doAction = new Wrappers(driver,Reporter);
        }
    }

    @Test
    public void testPredictionGuruLoginWithGoogle() throws InterruptedException
    {
        System.out.println("testPredictionGuruLoginWithGoogle");

        //Wait for activity
        doAction.waitForAndroidActivity(".activity.ScreenSlideGuide", 20);

        //G+ Sign In
        ScreenSlideGuideActivity objScreenSlideGuide = new ScreenSlideGuideActivity(driver,Dictionary,Environment,Reporter);
        objScreenSlideGuide.navigateToGoogleSignIn();

        //Wait for Wizard Activity
        doAction.waitForAndroidActivity(".activity.WizardMainActivity", 20);
    }

    @Test
    public void testPredictionGuruLoginWithFacebook() throws InterruptedException
    {
        System.out.println("testPredictionGuruLoginWithFacebook");

        //Wait for activity
        doAction.waitForAndroidActivity(".activity.ScreenSlideGuide", 20);

        //G+ Sign In
        ScreenSlideGuideActivity objScreenSlideGuide = new ScreenSlideGuideActivity(driver,Dictionary,Environment,Reporter);
        objScreenSlideGuide.navigateTofacebookSignIn();

        //Wait for Wizard Activity
        doAction.waitForAndroidActivity(".activity.WizardMainActivity", 20);
    }

    @Test
    public void testWizardTabs() throws InterruptedException {
        //login
        ScreenSlideGuideActivity objScreenSlideGuideActivity = new ScreenSlideGuideActivity(driver,Dictionary,Environment,Reporter);
        WizardActivity objWizardActivity = objScreenSlideGuideActivity.loginWithGooglePlus();

        //get Page source
        driver.switchTo().window("WEB_VIEW");
        System.out.println(driver.getPageSource());

        //Navigate Tabs
        //objWizardActivity
    }

    @AfterMethod
    public void afterMethod(Method method){
        String testName = method.getName();
        System.out.println("After Method for test " + testName);
        asapDriver.setReferenceData();
        Reporter.closeTestLevelReport(testName);
        String cmd = "\"C:\\Program Files (x86)\\Android\\android-sdk\\platform-tools\\adb.exe\" shell pm clear " + appPackage;
        System.out.println("Executing command :" + cmd);
        Generic.executeCommand(cmd);
        if(driver != null) {
            driver.close();
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

        if(service.isRunning())
            service.stop();
    }
}
