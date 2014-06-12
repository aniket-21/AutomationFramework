package com.linkedin.android;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.appium.java_client.*;

import com.amdocs.asap.*;

public class StartUpActivity {
	
	private Reporting Reporter;
	private io.appium.java_client.AppiumDriver driver;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions objCommon = new CommonFunctions();
	
	//Objects
	String btnLogin = "Accessibility_id:=Log In";
	String btnLogins = "AppClassName:=android.widget.Button";
	
	
	//COnstructor
	public StartUpActivity()
	{
		Reporter = Global.Reporter;
		driver = (io.appium.java_client.AppiumDriver)Global.webDriver;
		Dictionary = Global.Dictionary;
		Environment = Global.Environment;
	}
	
	
	//*****************************************************************************************
    //*    Name        	: fClickLoginIn
    //*    Description 	: This function clicks Login on Linked In Page
    //*    Author       : Aniket Gadre
    //*    Input Params : None
    //*    Return Values: None
    //*****************************************************************************************
	public LoginActivity fClickLogin()
	{
		
		
		//Click on Login 
		//if(objCommon.fGuiClick(btnLogin)==false) return null;
		
		
		//Get credential edit boxes
		List<WebElement> objButtons = objCommon.getObjects(btnLogins);
			
		//Enter Credentials		
		if(objCommon.fGuiClick(objButtons.get(1))==false) return null;
		
		//return
		return new LoginActivity();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//**************************************** LINKEDIN ANDROID ********************************************************************
	/*Log into LinkedIn
	driver.findElement(By.name("Log In")).click();	
	
	//Enter Credentials & click on Sign in
	List<WebElement> edtLogin = driver.findElements(By.tagName("textfield"));
	edtLogin.get(0).sendKeys("aniket.g21@gmail.com");
	edtLogin.get(1).sendKeys("aniketaki");
	driver.findElement(By.name("Sign In")).click();
	
	//Click on Do not Sync contacts
	driver.findElement(By.name("Do not sync LinkedIn contacts")).click();
	
	//Click on Magnifying Glass icon and search for contact
	driver.findElement(By.name("Search")).click();
	driver.findElement(By.name("Search...")).sendKeys("Jack Deng");
	
	//Get profile and click on Message
	driver.findElement(By.name("1st")).click();
	driver.findElement(By.name("Message")).click();
	driver.findElement(By.name("Subject")).sendKeys("Appium: Test Message");
	driver.findElement(By.name("Sent from LinkedIn")).sendKeys("Appium: Test Message");
	
	//Click on Linked in icon
	driver.findElements(By.tagName("image")).get(0).click();
	driver.findElement(By.name("Aniket Gadre")).click();
	
	//sleep
	Thread.sleep(10000);
	
	//Click on back - Example of handling Menu items
	JavascriptExecutor js = (JavascriptExecutor)driver ;
	HashMap<String, Integer> menu = new HashMap<String , Integer>();
	menu.put("keycode", 4);
	js.executeScript("mobile: keyevent", new Object[]{menu});
	
	//sleep
	Thread.sleep(10000);
	
	//CLick on More options
	driver.findElement(By.name("More options")).click();
	driver.findElement(By.name("Settings")).click();
	
	System.out.print(js.executeScript("mobile: currentActivity", (Object)null).toString());
	while(!js.executeScript("mobile: currentActivity", (Object)null).equals("settings.v2.LiSettings.Activity"))
	{}
	driver.findElement(By.name("Settings"));
	
	HashMap<String, String> scrollText = new HashMap<String, String>();
	scrollText.put("3", "Sign Out");
	
	HashMap<String , HashMap<String, String>> objScroll = new HashMap<String, HashMap<String,String>>();
	objScroll.put("scroll", scrollText);
	js.executeScript("mobile: find", new Object[]{objScroll});
	driver.findElement(By.name("Sign Out")).click();*/
	//**************************************** LINKEDIN ANDROID ********************************************************************


}
