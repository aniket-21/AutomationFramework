package com.framework.base;

import com.framework.core.Driver;
import com.framework.core.Reporting;
import com.framework.core.Wrappers;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.HashMap;

/**
 * Created by gadrea on 11/6/2015.
 */
public class BaseTest {

    //Variables
    protected  String env;

    protected String className;
    protected String browser;
    protected String browserVersion;
    protected String OS;
    protected Driver execDriver;
    protected Wrappers doAction;

    public WebDriver driver = null;
    public Reporting Reporter;
    //public HashMap<String, String> environment = new HashMap<String, String>();

    public void beforeClass() throws IOException {
        //Print class name to console
        System.out.println(className);

        //get env
        env = System.getProperty("envName");
        Assert.assertNotNull(env, "No environment Parameter value received");

        //Initiating execDriver
        execDriver = new Driver(env, className, browser);

        //Instantiate reporter
        Reporter = new Reporting(env, className, browser);
        Reporter.createSummaryReport();
        Reporter.createJenkinsReport();
    }

    public void beforeMethod(Method method) throws MalformedURLException {
        String testName = method.getName();
        System.out.println("Before Method for test " + testName);
        Reporter.createTestLevelReport(testName);
    }

    public void afterMethod(Method method) {
        String testName = method.getName();
        System.out.println("After Method" + testName);
        Reporter.closeTestLevelReport(testName);
    }

    public void afterClass(){
        System.out.println("After Class " + className);
        Reporter.closeTestSummaryReport();
        if(driver != null)
            driver.quit();
    }

    protected void setClassName(Object object) {
        String[] strClassNameArray = object.getClass().getName().split("\\.");
        className = strClassNameArray[strClassNameArray.length - 1];
    }

}
