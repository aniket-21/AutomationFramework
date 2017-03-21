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
	private String User;

	private HashMap <String, String> dictionary = new HashMap<String, String>();
	private HashMap <String, String> environment = new HashMap<String, String>();

	//Constructor
	public Driver(HashMap <String, String> dictionary, HashMap <String, String> environment) {
		
		this.dictionary = dictionary;
		this.environment = environment;
		
		//Get Root Path
		User = System.getProperty("user.name");
		String workingPath = System.getProperty("user.dir");
		String rootPath = workingPath;
		
		//Set paths
		executionPath = rootPath + "/execution";
		storagePath = rootPath;
		dataPath = storagePath + "/data";
		envConfigPath = storagePath + "/config/envConfig.xml";

		//Add to Env Variables
		this.environment.put("ROOTPATH", rootPath);
		this.environment.put("EXECUTIONFOLDERPATH", executionPath);
		this.environment.put("STORAGEFOLDERPATH", storagePath);
		this.environment.put("DATAPATH", dataPath);
	}
	
	//Function to Create Execution Folders
	public boolean createExecutionFolders() throws IOException {
		//Set execution paths
		String curExecutionFolder = executionPath + "/" + environment.get("ENV_CODE") + "/" + environment.get("CLASSNAME");
		String htmlReportsPath = curExecutionFolder + "/" + environment.get("BROWSER").toUpperCase() + "_HTML_Reports";
		String snapShotsPath = htmlReportsPath + "/Snapshots";
		String harFilePath = htmlReportsPath + "/Hars";
		
		//Put in Environments
		environment.put("CURRENTEXECUTIONFOLDER", curExecutionFolder);
		environment.put("HTMLREPORTSPATH", htmlReportsPath);
		environment.put("SNAPSHOTSFOLDER", snapShotsPath);
		environment.put("HARFOLDER", harFilePath);

		//Delete if folder already exists
		if (new File(htmlReportsPath).exists())
			deleteFile(new File(htmlReportsPath));
		if ((new File(snapShotsPath)).mkdirs() && (new File(harFilePath)).mkdirs())
			return true;
		else
			return false;
	}

    public static void deleteFile(File file) throws IOException {
    	  
        if(file.isDirectory()){
            String files[] = file.list();

            for (String temp : files) {
                File fileDelete = new File(file, temp);
                deleteFile(fileDelete);
            }
            if(file.list().length == 0)
            	file.delete();
        }

        else
        	file.delete();
    }

	public boolean fetchEnvironmentDetailsFromConfigXML() {

		try{
			Document doc = XMLHandler.getXMLDocument(envConfigPath);
			if(doc == null)
				return false;

			Element elemEnvironment = XMLHandler.getElementByName(doc, environment.get("ENV_CODE").toLowerCase());
			if(elemEnvironment == null){
				System.out.println("Unable to find Env node with ID " + environment.get("ENV_CODE"));
				return false;
			}

			List<Element> Parameters = XMLHandler.getChildElements(elemEnvironment);

			for(Element Parameter : Parameters)
				environment.put(Parameter.getTagName().trim().toUpperCase(), Parameter.getTextContent().trim());

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
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
		DC.setCapability("automationName", "Appium");
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
