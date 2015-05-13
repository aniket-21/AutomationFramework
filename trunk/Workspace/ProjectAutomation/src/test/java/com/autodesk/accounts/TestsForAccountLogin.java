package com.autodesk.accounts;

import com.automation.framework.Driver;
import com.automation.framework.Reporting;
import com.automation.framework.Wrappers;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by gadrea on 5/5/2015.
 */
public class TestsForAccountLogin {

    //Variables
    String className;
    String env;
    String browser;

    //Instances
    Driver asapDriver;
    WebDriver driver = null;
    Wrappers objCommon;

    HashMap<String, String> Environment = new HashMap<String, String>();
    HashMap <String, String> Dictionary = new HashMap<String, String>();
    Reporting Reporter;

    @DataProvider(name = "browsers", parallel = true)
    public static Object[][] getData() {
        String[] browser = System.getProperty("browserName").split(",");
        final int size = browser.length;
        Object[][] browsers = new Object[size][1];
        for(int i=0;i<browser.length;i++) {
            System.out.println(browser[i]);
            browsers[i][0] = browser[i];
        }
        return browsers;
    }

    @Factory(dataProvider = "browsers")
    //Created with values from @DataProvider in @Factory
    public TestsForAccountLogin(String browser) {
        this.browser = browser;
    }


    @BeforeClass
    public void beforeClass() throws IOException {

        //Set the DataSheet name by getting the class name
        String[] strClassNameArray = this.getClass().getName().split("\\.");
        className = strClassNameArray[strClassNameArray.length-1];
        Environment.put("CLASSNAME", className);
        Environment.put("BROWSER", browser);

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
    }

    @BeforeMethod
    public void beforeMethod(Method method) throws MalformedURLException {
        String testName = method.getName();
        System.out.println("Before Method for test " + testName);
        asapDriver.getDataForTest(testName);
        Reporter.createTestLevelReport(testName);

        //Initiate WebDriver
        if(driver==null){
            driver = asapDriver.getWebDriver(browser);
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
}
