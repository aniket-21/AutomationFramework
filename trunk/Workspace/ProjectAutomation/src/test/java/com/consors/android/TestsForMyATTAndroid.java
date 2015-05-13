package com.consors.android;

import com.automation.framework.Driver;
import com.automation.framework.Reporting;
import com.automation.framework.Wrappers;
import io.appium.java_client.android.AndroidDriver;
import io.selendroid.SelendroidCapabilities;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by ANIKETG on 10/14/2014.
 */
public class TestsForMyATTAndroid {

    //Variables
    String className;
    String dataSheetName;
    String env;

    //Instances
    Driver asapDriver;
    WebDriver driver;
    Wrappers objCommon;

    HashMap<String, String> Environment = new HashMap<String, String>();
    HashMap <String, String> Dictionary = new HashMap<String, String>();
    Reporting Reporter;

    @BeforeClass
    public void beforeClass() throws Exception {
        System.out.println("Before Class TestsForMyATTAndroid");

        //Set the DataSheet name by getting the class name
        String[] strClassNameArray = this.getClass().getName().split("\\.");
        className = strClassNameArray[strClassNameArray.length-1];
        Environment.put("CLASSNAME", className);
        Environment.put("BROWSER", "appium");

        //Initiate asapDriver
        asapDriver = new Driver(Dictionary, Environment);

        //Check if POM has env, if null, get it from config file
        env = System.getProperty("envName");
        Assert.assertNotNull(env);

        //Add env global environments
        Environment.put("ENV_CODE", env);

        //Create folder structure
        Assert.assertTrue(asapDriver.createExecutionFolders());

        //Get Environment Variables
        Assert.assertTrue(asapDriver.fetchEnvironmentDetails());

/*        //Desired Caps
        DesiredCapabilities DC = new DesiredCapabilities();
        DC.setCapability("automationName", "Appium");
        DC.setCapability("platformName", "Android");
        DC.setCapability("app","C:\\Users\\aniketg\\Downloads\\MyATT.apk");
        //DC.setCapability("appPackage", "com.att.myWireless");
        //DC.setCapability("appActivity", "com.att.myWireless.activity.login.SplashScreenActivity");
        DC.setCapability("appWaitActivity",".activities.EULAActivity,.activities.LoginActivity");
        DC.setCapability("deviceName","ASUS Zenfone 5");

        //Initiate WebDriver
        driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), DC);
*/


        //Selendroid Caps
        SelendroidCapabilities DC = new SelendroidCapabilities("com.att.myWireless:4.2.1");
        DC.setCapability("app","C:\\Users\\aniketg\\Downloads\\MyATT.apk");
        DC.setCapability("automationName", "Selendroid");
        DC.setCapability("deviceName","ASUS Zenfone 5");
        DC.setCapability("platformName", "Android");
        DC.setCapability("newCommandTimeout",3000);
        DC.setCapability("appWaitActivity",".activities.EULAActivity,.activities.LoginActivity");


       // driver = new SelendroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), DC);
        driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), DC);


        //Set implicit time
       // if(driver != null) driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        //Instantiate reporter
        Reporter = new Reporting(driver, Dictionary, Environment);

        //Create HTML Summary Report
        Reporter.createSummaryReport();

        //Update Jenkins report
        Reporter.createJenkinsReport();

        //Initialize Common functions
        objCommon = new Wrappers(driver, Reporter);
    }

    @BeforeMethod
    public void beforeMethod(Method method){
        //Get the test name
        String testName = method.getName();

        System.out.println("Before Method" + testName);

        //Get the data from DataSheet corresponding to Class Name & Test Name
        asapDriver.getDataForTest(testName);

        //Create Individual HTML Report
        Reporter.createTestLevelReport(testName);
    }


    @AfterMethod
    public void afterMethod(Method method){
        //Get the test name
        String testName = method.getName();

        System.out.println("After Method" + testName);

        //Update the KeepRefer Sheet
        asapDriver.setReferenceData();

        //Close Individual Summary Report & Update Summary Report
        Reporter.closeTestLevelReport(testName);
    }

    @AfterClass
    public void afterClass(){
        System.out.println("After Class TestsForAppium");

        //Close HTML Summary report
        Reporter.closeTestSummaryReport();

        //QUit webdriver
        if(driver != null) driver.quit();
    }

    @Test
    public void testMyATTHybridTest() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver,30);

        JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;

        //System.out.println("title: " + driver.getTitle());
        System.out.println("activity: " + ((AndroidDriver)driver).currentActivity());


// ############################### UIAutomator #######################################################
        //If EULA Activity is displayed
       if(((AndroidDriver)driver).currentActivity().equals(".activities.EULAActivity")){
            //driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"Accept\")")).click();
           wait.until(ExpectedConditions.elementToBeClickable(By.id("acceptButton")));
           driver.findElement(By.id("acceptButton")).click();
        }



       //If Login Activity is displayed
       if(((AndroidDriver)driver).currentActivity().equals(".activities.LoginActivity")){

           try{
                wait.until(ExpectedConditions.presenceOfElementLocated(By.id("loginWebView")));
                driver.switchTo().window("WEBVIEW");
                wait.until(ExpectedConditions.presenceOfElementLocated(By.id("loginRadio")));
                Thread.sleep(2000);
                driver.findElement(By.id("loginRadio")).click();
                System.out.println(driver.getPageSource());
                driver.findElements(By.id("continue_btn")).get(1).click();

            }
            catch(TimeoutException ex){

            }

            //Enter Credentials and Click on Login
           driver.switchTo().window("NATIVE_APP");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("wirelessNumField")));
            driver.findElement(By.id("wirelessNumField")).sendKeys("2085990990");
            driver.findElement(By.id("passwordField")).sendKeys("spt031212");
            //((AndroidDriver) driver).hideKeyboard();
            //driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"Log in\")")).click();
           driver.findElement(By.id("loginBtn")).click();
        }

        //Swipe till "Check upgrade eligibility" is displayed
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("loginWebView")));
        driver.switchTo().window("WEBVIEW");
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Check upgrade eligibility")));
        driver.findElement(By.linkText("Check upgrade eligibility")).click();

        System.out.print(driver.getPageSource());

        //Upgrade your device Heading --
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h3[contains(text(),'(208) 599-0990')]")));
        driver.findElement(By.xpath("//h3[contains(text(),'(208) 599-0990')]")).click();

        //Phones & devices Heading
        wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Quick Messaging phones")));
        driver.findElement(By.partialLinkText("Quick Messaging phones")).click();

        //Quick Messaging phones
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//dt[text()='LG Xpression']")));
        driver.findElement(By.xpath("//dt[text()='LG Xpression']")).click();

        //Add to cart
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Add to cart']")));
        driver.findElement(By.xpath("//span[text()='Add to cart']")).click();

        //Choose a pricing option
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Select")));
        jsExecutor.executeScript("arguments[0].click()",driver.findElement(By.linkText("Select")));

        //Protect your device
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='No, thank you']")));
        driver.findElement((By.xpath("//span[text()='No, thank you']"))).click();
        driver.findElement(By.linkText("Select")).click();

        //Continue
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Continue")));
        driver.findElement(By.linkText("Continue")).click();

        //Continue to cart
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Continue to cart")));
        driver.findElement(By.linkText("Continue to cart")).click();

        //checkagree
        wait.until(ExpectedConditions.elementToBeClickable(By.id("checkagree")));
        driver.findElement(By.id("checkagree")).click();


    }
}
