package com.framework.base;

import com.framework.core.Driver;
import com.framework.core.Reporting;
import com.framework.core.Wrappers;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by gadrea on 11/6/2015.
 */
public class BaseTest {

    //Variables
    private  String env;

    protected String className;
    protected String browser;
    protected String browserVersion;
    protected String OS;
    protected Driver asapDriver;
    protected Wrappers doAction;

    public WebDriver driver = null;
    public Reporting Reporter;
    public HashMap<String, String> environment = new HashMap<String, String>();
    public HashMap<String, String> dictionary = new HashMap<String, String>();

    public void beforeClass() throws IOException {
        //Print class name to console
        System.out.println(className);

        //get env
        env = System.getProperty("envName");
        Assert.assertNotNull(env, "No environment Parameter value received");

        //Add to envs
        environment.put("CLASSNAME", className);
        environment.put("BROWSER", browser);
        environment.put("ENV_CODE", env);

        //Initiating asapDriver
        asapDriver = new Driver(dictionary, environment);
        asapDriver.createExecutionFolders();
        asapDriver.fetchEnvironmentDetailsFromConfigXML();

        //Instantiate reporter
        Reporter = new Reporting(dictionary, environment);
        Reporter.createSummaryReport();
        Reporter.createJenkinsReport();
    }

    public void beforeMethod(Method method){
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

}
