package com.framework.core;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import com.framework.handlers.XMLHandler;
import io.appium.java_client.android.AndroidDriver;
import io.selendroid.common.SelendroidCapabilities;

import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.w3c.dom.*;

public class Driver {

	private String executionPath;
	private String storagePath;
	private String envConfigPath;
	private String dataPath;

	private String env;
	private String className;
	private String browserName;


	//Constructor
	public Driver(String env, String className, String browserName) {

		this.env = env;
		this.className = className;
		this.browserName = browserName;
		
		//Get Root Path
		String workingPath = System.getProperty("user.dir");
		String rootPath = workingPath;
		
		//Set paths
		executionPath = rootPath + "/execution";
		storagePath = rootPath;
		dataPath = storagePath + "/data";
		envConfigPath = storagePath + "/config/envConfig.xml";
	}

	public WebDriver getWebDriver(String browser) throws MalformedURLException {
		String osName = System.getProperty("os.name");
        String webDriverType = browser;

		System.out.println("Executing Tests on OS " + osName);

        if (webDriverType.equalsIgnoreCase("firefox") || webDriverType.isEmpty()){

			System.setProperty("webdriver.gecko.driver", storagePath + "/drivers/geckodriver.exe");
			return new FirefoxDriver();
        }
        else if (webDriverType.equalsIgnoreCase("chrome")){
			if(osName.toLowerCase().contains("linux")) {
				System.setProperty("webdriver.chrome.driver", storagePath + "/drivers/chromedriver");
				return new ChromeDriver();
			}
			else {
				System.setProperty("webdriver.chrome.driver", storagePath + "/drivers/chromedriver.exe");
				return new ChromeDriver();
			}
        }
        else if (webDriverType.equalsIgnoreCase("ie")){
            System.setProperty("webdriver.ie.driver", storagePath + "/drivers/IEDriverServer32.exe");
            return new InternetExplorerDriver();
        }
        else {
            System.out.println("Driver type " + webDriverType + " is invalid");
            return null;
        }
	}

	public WebDriver getRemoteWebDriver(String URL, DesiredCapabilities dc) throws MalformedURLException {
		return new RemoteWebDriver(new URL(URL),dc);
	}

	public AndroidDriver getAppiumAndroidDriver(String appPackage, String appActivity, String deviceName, String appiumServerURL) throws MalformedURLException {
		//Desired Caps
		DesiredCapabilities DC = new DesiredCapabilities();
		DC.setCapability("automationName", "uiautomator2");
		DC.setCapability("platformName", "Android");
		DC.setCapability("appPackage", appPackage);
		DC.setCapability("appActivity", appActivity);
		DC.setCapability("deviceName", deviceName);

		//Initiate WebDriver
		return new AndroidDriver(new URL(appiumServerURL), DC);
	}

	public AndroidDriver getAndroidChromeDriver(String deviceName, String appiumServerURL, Proxy proxy) throws MalformedURLException {
		//Desired Caps
		DesiredCapabilities DC = new DesiredCapabilities();
		DC.setCapability("automationName", "Appium");
		DC.setCapability("platformName", "Android");
		DC.setCapability("browserName", "Chrome");
		DC.setCapability("deviceName", deviceName);

		if(proxy != null)
			DC.setCapability(CapabilityType.PROXY,proxy);

		//Initiate WebDriver
		return new AndroidDriver(new URL(appiumServerURL), DC);
	}

	public AndroidDriver getSelendroidDriver(String apkPath, String appPackage, String appWaitActivity, String deviceName, String appiumServerURL) throws MalformedURLException {
		//Selendroid Caps
		SelendroidCapabilities DC = new SelendroidCapabilities(appPackage);
		DC.setCapability("appWaitActivity",appWaitActivity);
		DC.setCapability("deviceName",deviceName);
		DC.setCapability("app",apkPath);
		DC.setCapability("automationName", "Selendroid");
		DC.setCapability("platformName", "Android");
		DC.setCapability("newCommandTimeout",3000);

		return new AndroidDriver(new URL(appiumServerURL), DC);
	}

	public Platform getPlatform(String platformName) {
		String osName = platformName.toUpperCase();

		if(osName.equals("WIN8.1"))
			return Platform.WIN8_1;
		else if (osName.equals("WIN8"))
			return Platform.WIN8;
		else if (osName.equals("ANDROID"))
			return Platform.ANDROID;
		else if (osName.equals("LINUX"))
			return Platform.LINUX;
		else if (osName.equals("MAC"))
			return Platform.MAC;
		else if (osName.equals("WIN"))
			return Platform.WINDOWS;
		else
			return Platform.ANY;
	}
}
