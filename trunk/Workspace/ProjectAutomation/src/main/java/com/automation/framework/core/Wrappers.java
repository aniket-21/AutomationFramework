package com.automation.framework.core;

import io.appium.java_client.MobileBy;
import io.appium.java_client.AppiumDriver;

import java.util.List;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.NoSuchElementException;


public class Wrappers {
	
	//*************************************************************
	//** List of Methods in this class **//
    //** WebElement getElement(String strDesc)
    //** List<WebElement> getElements(String strDesc)
    //** WebElement getChildElement(WebElement Parent, String strDesc)
    //** List<WebElement> getChildElements(WebElement parentElem, String objDesc)
    //** boolean isWebElementPresent(String strDesc)
    //** boolean verifyElementNonExistence(String strDesc)
    //** boolean isChildWebElementPresent(WebElement objParent, String strDesc)
    //** boolean verifyChildElementNonExistence(WebElement objParent, String strDesc)
	//** boolean isWebElementDisplayed (String strDesc)
	//** boolean verifyElementIsNotDisplayed (String strDesc)
	//** boolean isWebElementEnabled (String strDesc)
	//** boolean verifyElementIsDisabled (String strDesc)
	//** boolean click (String strDesc)
	//** boolean enterText (String strDesc, String strText)
	//** boolean selectOptionFromList (String strDesc, String strText)

	//** boolean checkCheckBox(String strDesc)
	//** boolean uncheckCheckBox(String strDesc)

	//** boolean switchToWindowWithName(strWindowName)
	//** String getRandomString()
	//** String getTimeStamp()
	//** boolean fverifyCheckBoxIsChecked(String strDesc)
	//** boolean fverifyCheckBoxIsChecked(String strDesc)
	/** List of Methods in this class **/
	//*************************************************************
	
	//Properties
	private Reporting Reporter;
	private WebDriver driver;
	
	//Constructor
	public Wrappers(WebDriver GDriver, Reporting GReporter)
	{
		Reporter = GReporter;
		driver = GDriver;
		
	}

    //*****************************************************************************************
    //*	Name		    : getElement
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public WebElement getElement(String objDesc)
    {
        //Delimiters
        String[] delimiters = new String[] {":="};
        String[] arrFindByValues = objDesc.split(delimiters[0]);

        //Get Findby and Value
        String FindBy = arrFindByValues[0];
        String val = arrFindByValues[1];

        //Handle all FindBy cases
        String strElement = FindBy.toLowerCase();

        try{
            if (strElement.equalsIgnoreCase("id"))
            {
                return driver.findElement(By.id(val));
            }
            else if (strElement.equalsIgnoreCase("name"))
            {
                return driver.findElement(By.name(val));
            }
            else if (strElement.equalsIgnoreCase("linktext"))
            {
                return driver.findElement(By.linkText(val));
            }
            else if (strElement.equalsIgnoreCase("classname"))
            {
                return driver.findElement(By.className(val));
            }
            else if (strElement.equalsIgnoreCase("cssselector"))
            {
                return driver.findElement(By.cssSelector(val));
            }
            else if (strElement.equalsIgnoreCase("xpath"))
            {
                return driver.findElement(By.xpath(val));
            }
            else if (strElement.equalsIgnoreCase("accessibility_id"))
            {
                return ((AppiumDriver)driver).findElement(MobileBy.AccessibilityId(val));
            }
            else if (strElement.equalsIgnoreCase("appclassname"))
            {
                return ((AppiumDriver)driver).findElement(By.className(val));
            }
            else if (strElement.equalsIgnoreCase("uiautomator"))
            {
                return ((AppiumDriver)driver).findElement(MobileBy.AndroidUIAutomator(val));
            }
            else if (strElement.equalsIgnoreCase("partiallinktext"))
            {
                return driver.findElement(By.partialLinkText(val));
            }
            else if (strElement.equalsIgnoreCase("tagname"))
            {
                return driver.findElement(By.tagName(val));
            }
            else
            {
                Reporter.writeToTestLevelReport("Get element matching description " + objDesc, "Element should be found and returned", "Property " + FindBy + " specified for element is invalid", "Fail");
                throw(new InvalidSelectorException("Wrapper method getElement() : Property "  + FindBy + " specified for element is invalid"));
            }
        }
        catch(NoSuchElementException ex){
            Reporter.writeToTestLevelReport("Get element matching description " + objDesc, "Element should be found and returned", "Element not found", "Fail");
            throw(ex);
        }

    }

    //*****************************************************************************************
    //*	Name		    : getElements
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public List<WebElement> getElements(String objDesc)
    {
        //Delimiters
        String[] delimiters = new String[] {":="};
        String[] arrFindByValues = objDesc.split(delimiters[0]);

        //Get Findby and Value
        String FindBy = arrFindByValues[0];
        String val = arrFindByValues[1];

        List<WebElement> elements = null;

        //Handle all FindBy cases
        String strElement = FindBy.toLowerCase();
        if (strElement.equalsIgnoreCase("linktext")){
            elements = driver.findElements(By.linkText(val));
        }
        else if (strElement.equalsIgnoreCase("partiallinktext")){
            elements = driver.findElements(By.partialLinkText(val));
        }
        else if (strElement.equalsIgnoreCase("xpath")){
            elements = driver.findElements(By.xpath(val));
        }
        else if (strElement.equalsIgnoreCase("name")){
            elements = driver.findElements(By.name(val));
        }
        else if (strElement.equalsIgnoreCase("id")){
            elements = driver.findElements(By.id(val));
        }
        else if (strElement.equalsIgnoreCase("classname")){
            elements = driver.findElements(By.className(val));
        }
        else if (strElement.equalsIgnoreCase("cssselector")){
            elements = driver.findElements(By.cssSelector(val));
        }
        else if (strElement.equalsIgnoreCase("tagname")){
            elements = driver.findElements(By.tagName(val));
        }
        else if (strElement.equalsIgnoreCase("accessibility_id")){
            elements = ((AppiumDriver)driver).findElements(MobileBy.AccessibilityId(val));
        }
        else if (strElement.equalsIgnoreCase("appclassname")){
            elements = ((AppiumDriver)driver).findElements(By.className(val));
        }
        else{
            Reporter.writeToTestLevelReport("Get elements matching description " + objDesc, "Element List should be returned", "Property " + FindBy + " specified for elements is invalid", "Fail");
            throw(new InvalidSelectorException("Wrapper method getElements() : Property "  + FindBy + " specified for element is invalid"));
        }

        return elements;
    }

    //*****************************************************************************************
    //*	Name		    : getChildElement
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public WebElement getChildElement(WebElement parentElem, String objDesc)
    {
        //Delimiters
        String[] delimiters = new String[] {":="};
        String[] arrFindByValues = objDesc.split(delimiters[0]);

        //Get Findby and Value
        String FindBy = arrFindByValues[0];
        String val = arrFindByValues[1];

        //Handle all FindBy cases
        String strElement = FindBy.toLowerCase();
        if (strElement.equalsIgnoreCase("id")) {
            return parentElem.findElement(By.id(val));
        }
        else if (strElement.equalsIgnoreCase("name")) {
            return parentElem.findElement(By.name(val));
        }
        else if (strElement.equalsIgnoreCase("linktext")){
            return parentElem.findElement(By.linkText(val));
        }
        else if (strElement.equalsIgnoreCase("classname")){
            return parentElem.findElement(By.className(val));
        }
        else if (strElement.equalsIgnoreCase("cssselector")){
            return parentElem.findElement(By.cssSelector(val));
        }
        else if (strElement.equalsIgnoreCase("xpath")){
            return parentElem.findElement(By.xpath(val));
        }
        else if (strElement.equalsIgnoreCase("accessibility_id")){
            return parentElem.findElement(MobileBy.AccessibilityId(val));
        }
        else if (strElement.equalsIgnoreCase("appclassname")){
            return parentElem.findElement(By.className(val));
        }
        else if (strElement.equalsIgnoreCase("uiautomator")){
            return parentElem.findElement(MobileBy.AndroidUIAutomator(val));
        }
        else if (strElement.equalsIgnoreCase("partiallinktext")){
            return parentElem.findElement(By.partialLinkText(val));
        }
        else if (strElement.equalsIgnoreCase("tagname")){
            return parentElem.findElement(By.tagName(val));
        }
        else{
            Reporter.writeToTestLevelReport("Get child object matching description " + objDesc, "Object should be found and returned", "Property " + FindBy + " specified for object is invalid", "Fail");
            throw(new InvalidSelectorException("Wrapper method getChildElement() : Property "  + FindBy + " specified for element is invalid"));
        }
    }

    //*****************************************************************************************
    //*	Name		    : getChildElements
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public List<WebElement> getChildElements(WebElement parentElem, String objDesc)
    {
        //Delimiters
        String[] delimiters = new String[] {":="};
        String[] arrFindByValues = objDesc.split(delimiters[0]);

        //Get Findby and Value
        String FindBy = arrFindByValues[0];
        String val = arrFindByValues[1];

        //List
        List<WebElement> elements = null;

        //Handle all FindBy cases
        String strElement = FindBy.toLowerCase();
        if (strElement.equalsIgnoreCase("id")){
            elements = parentElem.findElements(By.id(val));
        }
        else if (strElement.equalsIgnoreCase("name")){
            elements = parentElem.findElements(By.name(val));
        }
        else if (strElement.equalsIgnoreCase("linktext")){
            elements = parentElem.findElements(By.linkText(val));
        }
        else if (strElement.equalsIgnoreCase("classname")){
            elements = parentElem.findElements(By.className(val));
        }
        else if (strElement.equalsIgnoreCase("cssselector")){
            elements = parentElem.findElements(By.cssSelector(val));
        }
        else if (strElement.equalsIgnoreCase("xpath")){
            elements = parentElem.findElements(By.xpath(val));
        }
        else if (strElement.equalsIgnoreCase("accessibility_id")){
            elements = parentElem.findElements(MobileBy.AccessibilityId(val));
        }
        else if (strElement.equalsIgnoreCase("appclassname")){
            elements = parentElem.findElements(By.className(val));
        }
        else if (strElement.equalsIgnoreCase("uiautomator")){
            elements = parentElem.findElements(MobileBy.AndroidUIAutomator(val));
        }
        else if (strElement.equalsIgnoreCase("partiallinktext")){
            elements = parentElem.findElements(By.partialLinkText(val));
        }
        else if (strElement.equalsIgnoreCase("tagname")){
            elements = parentElem.findElements(By.tagName(val));
        }
        else{
            Reporter.writeToTestLevelReport("Get child elements matching description " + objDesc, "Child Elements should be found and returned", "Property " + FindBy + " specified for element is invalid", "Fail");
            throw(new InvalidSelectorException("Wrapper method getChildElements() : Property "  + FindBy + " specified for element is invalid"));
        }

        return elements;
    }


    //*****************************************************************************************
    //*	Name		    : isWebElementPresent
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
	public boolean isWebElementPresent(String strDesc){
        List<WebElement> lst = getElements(strDesc);
        boolean isPresent = (lst == null || lst.size() == 0) ? false : true;
        Reporter.writeToTestLevelReport("Element existence with description " + strDesc, "", "Element presence state is " + isPresent, "Done");
        return isPresent;
    }


    //*****************************************************************************************
    //*	Name		    : isChildWebElementPresent
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
	public boolean isChildWebElementPresent(WebElement objParent, String strDesc){
        List<WebElement> lst = getChildElements(objParent,strDesc);
        boolean isPresent = (lst == null || lst.size() == 0) ? false : true;
        Reporter.writeToTestLevelReport("Child Element existence with description " + strDesc, "", "Child Element presence state is " + isPresent, "Done");
        return isPresent;
    }
	
	//*****************************************************************************************
    //*	Name		    : isWebElementDisplayed
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
	public boolean isWebElementDisplayed(String strDesc) throws InterruptedException {
        WebElement webElement = getElement(strDesc);
        return isWebElementDisplayed(webElement);
    }
	
	//*****************************************************************************************
    //*	Name		    : isWebElementDisplayed
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
	public boolean isWebElementDisplayed(WebElement webElement) throws InterruptedException {
        boolean bIsDisplayed = webElement.isDisplayed();
        String state = bIsDisplayed ? "displayed" : "not displayed";
        String strDesc = webElement.toString();
        Reporter.writeToTestLevelReport("Check if object with description  " + strDesc + " is displayed", "", "Object is " + state, "Done");

        return  bIsDisplayed;
    }

	//*****************************************************************************************
    //*	Name		    : isWebElementEnabled
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public boolean isWebElementEnabled(String strDesc) throws InterruptedException {
        //Get WebElement
        WebElement webElement = getElement(strDesc);
        return isWebElementEnabled(webElement);
    }	
    
  //*****************************************************************************************
    //*	Name		    : isWebElementEnabled
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public boolean isWebElementEnabled(WebElement webElement) throws InterruptedException {
    	//Check if the WebElement is Enabled
        boolean bIsEnabled = webElement.isEnabled();
        String state = bIsEnabled ? "enabled" : "disabled";
        String strDesc = webElement.toString();
        Reporter.writeToTestLevelReport("Check enabled state of object with description  " + strDesc, "", "Object state is " + state, "Done");

        return  bIsEnabled;
    }	
    

    //*****************************************************************************************
    //*	Name		    : isWebElementSelected
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public boolean isWebElementSelected(String strDesc) {
        //Get WebElement
        WebElement webElement = getElement(strDesc);
        return isWebElementSelected(webElement);
    }

    //*****************************************************************************************
    //*	Name		    : isWebElementSelected
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public boolean isWebElementSelected(WebElement webElement){
        boolean bIsSelected = webElement.isSelected();
        String state = bIsSelected ? "selected" : "unselected";
        String strDesc = webElement.toString();
        Reporter.writeToTestLevelReport("Check selected state of object with description  " + strDesc, "", "Object state is " + state, "Done");

        return  bIsSelected;
    }

    //*****************************************************************************************
    //*	Name		    : click
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public Wrappers click(String strDesc)
    {
        //Initialize
        WebElement webElement = getElement(strDesc);
        return click(webElement);
    }
    
  //*****************************************************************************************
    //*	Name		    : click
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public Wrappers click(WebElement objClick)
    {
        String strDesc = objClick.toString();

        //Check if the object is enabled, if yes click the same
        if (objClick.isDisplayed() && objClick.isEnabled()){
            //Click on the object
            objClick.click();
        }
        else{
            Reporter.writeToTestLevelReport("Click element matching description " + strDesc, "Element with description " + strDesc + " should be clicked", "Element is either not displayed or is not enabled", "Fail");
            throw(new ElementNotVisibleException("Wrapper method click() : Element is either not visible or is not enabled"));
            //return false;
        }

        Reporter.writeToTestLevelReport("Click object matching description " + objClick.toString(), "Click operation should be successful", "Successfully clicked the object", "Done");
        return this;
    }	

	//*****************************************************************************************
    //*	Name		    : enterText
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public Wrappers enterText(String strDesc, String strText)
    {
        WebElement webElement = getElement(strDesc);
        return enterText(webElement,strText);
    }	
    
    //*****************************************************************************************
    //*	Name		    : enterText
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public Wrappers enterText(WebElement objEdit, String strText)
    {
    	String strDesc = objEdit.toString();

        //Check if the object is enabled, if yes click the same
        if (objEdit.isDisplayed() && objEdit.isEnabled()){
            //Enter the text in the edit box
            objEdit.clear();
            objEdit.sendKeys(strText);
        }
        else{
            Reporter.writeToTestLevelReport("Set value in element with description " + strDesc, "Value " + strText + " should be set in the edit box", "Element is either not displayed or not enabled", "Fail");
            throw(new ElementNotVisibleException("Wrapper method enterText() : Element with description " + strDesc + " is either not visible or is not enabled"));
            //return false;
        }

        Reporter.writeToTestLevelReport("Set value in element with description " + strDesc, "Value " + strText + " should be set in the edit box", "Value is set in the text field", "Done");
        return this;
    }	


    //*****************************************************************************************
    //*	Name		    : selectOptionFromList
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public Wrappers selectOptionFromList(String strDesc, String strText)
    {
        WebElement webElement = getElement(strDesc);
        return selectOptionFromList(webElement,strText);
    }
    
    //*****************************************************************************************
    //*	Name		    : selectOptionFromList
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public Wrappers selectOptionFromList(WebElement objSelect, String strText)
    {
    	String strDesc = objSelect.toString();

        //Check if the object is enabled, if yes click the same
        if (objSelect.isDisplayed() && objSelect.isEnabled()){
            //Set Select Element and select required value by text
            try{
                Select select = new Select(objSelect);
                select.selectByVisibleText(strText);
            }
            catch(WebDriverException ex){
                Reporter.writeToTestLevelReport("Select value in element with description " + strDesc, "Value " + strText + " should be selected in the list box", "Selecting value failed due to exception " + ex.getMessage(), "Fail");
                throw(ex);
            }
        }
        else{
            Reporter.writeToTestLevelReport("Select value in element with description " + strDesc, "Value " + strText + " should be selected in the list box", "Element is either not displayed or not enabled", "Fail");
            throw(new ElementNotVisibleException("Wrapper method selectOptionFromList() : Element with description " + strDesc + " is either not visible or is not enabled"));
            //return false;
        }

        Reporter.writeToTestLevelReport("Select value from dropdown", "Select value " + strText, "Value " + strText + " selected", "Done");
        return this;
    }
    

    
    //*****************************************************************************************
    //*	Name		    : checkCheckBox
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public Wrappers checkCheckBox(String strDesc)
    {
        //Initialize
        WebElement webElement = getElement(strDesc);
        return checkCheckBox(webElement);
    }
    
  //*****************************************************************************************
    //*	Name		    : checkCheckBox
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public Wrappers checkCheckBox(WebElement objChkBox)
    {
    	String strDesc = objChkBox.toString();

        //Check if the object is enabled, if yes click the same
        if (objChkBox.isDisplayed() && objChkBox.isEnabled()){
            //Check state of check box
            boolean isChecked = objChkBox.isSelected();

            //Check if Not Checked
            if(isChecked == false) objChkBox.click();
        }
        else{
            Reporter.writeToTestLevelReport("Check CheckBox element with description " + strDesc, "Checkbox should be checked", "Element is either not displayed or not enabled", "Fail");
            throw(new ElementNotVisibleException("Wrapper method checkCheckBox() : Element with description " + strDesc + " is either not visible or is not enabled"));
            //return false;
        }

        Reporter.writeToTestLevelReport("Check CheckBox element with description " + strDesc, "Check operation should be successful", "Successfully checked the checkbox", "Done");
        return this;
    }
    
    //*****************************************************************************************
    //*	Name		    : uncheckCheckBox
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public Wrappers uncheckCheckBox(String strDesc)
    {
        //Initialize
        WebElement webElement = getElement(strDesc);
        return uncheckCheckBox(webElement);
    }	
    
    //*****************************************************************************************
    //*	Name		    : uncheckCheckBox
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public Wrappers uncheckCheckBox(WebElement objChkBox)
    {
    	String strDesc = objChkBox.toString();

        //Check if the object is enabled, if yes click the same
        if (objChkBox.isDisplayed() && objChkBox.isEnabled()){
            //Check state of check box
            boolean isChecked = objChkBox.isSelected();

            //Check if Checked
            if(isChecked == true) objChkBox.click();
        }
        else{
            Reporter.writeToTestLevelReport("Check CheckBox element with description " + strDesc, "Checkbox should be unchecked", "Element is either not displayed or not enabled", "Fail");
            throw(new ElementNotVisibleException("Wrapper method checkCheckBox() : Element with description " + strDesc + " is either not visible or is not enabled"));
            //return false;
        }

        Reporter.writeToTestLevelReport("Uncheck CheckBox element with description " + strDesc, "Un-check operation should be successful", "Successfully unchecked the checkbox", "Done");
        return this;
    }
	
	//*****************************************************************************************
    //*	Name		    : getCurrentBrowser
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
	public String getCurrentBrowser()
	{
		try
		{
			Capabilities DC = ((RemoteWebDriver)driver).getCapabilities();
			return DC.getBrowserName();
		}
		catch(WebDriverException e)
		{
			Reporter.writeToTestLevelReport("Get browser name", "Should return Browser Name", "Fetching Browser Name Failed. Exception " + e, "Fail");
			throw(e);
		}
	}

    //*****************************************************************************************
    //*	Name		    : rotateDeviceScreen
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public Wrappers rotateDeviceScreen(String Orientation) throws InterruptedException {

        String strOrientation = "";
        ScreenOrientation iOrientation;

        try{
            if (Orientation.equalsIgnoreCase("L")){
                ((AppiumDriver)driver).rotate(ScreenOrientation.LANDSCAPE);
                iOrientation = ScreenOrientation.LANDSCAPE;
                strOrientation = "Landscape";
            }
            else {
                ((AppiumDriver)driver).rotate(ScreenOrientation.PORTRAIT);
                iOrientation = ScreenOrientation.PORTRAIT;
                strOrientation = "Portrait";
            }

            //wait till orientation change
            Thread.sleep(1000);
        }
        catch(WebDriverException ex){
            Reporter.writeToTestLevelReport("Rotate Screen", "Set screen orientation to " + strOrientation, "Orientation Setting Failed", "Fail");
            throw(ex);
            //return false;
        }

        Reporter.writeToTestLevelReport("Rotate Screen", "Set screen orientation to " + strOrientation, "Orientation Set Successfully", "Pass");
        return this;
    }

    //*****************************************************************************************
    //*	Name		    : setGeoLocation
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public Wrappers setGeoLocation(String lat, String lon){
        //js
        String Script = "window.navigator.geolocation.getCurrentPosition =  function(success){var position = {'coords' : {'latitude': '" + lat + "', 'longitude': '" + lon + "'}}; success(position);}";

        //Update geolocation
        JavascriptExecutor js = (JavascriptExecutor)driver;
        Object[] args = {null};
        js.executeScript(Script, args);

        return this;
    }

    //*****************************************************************************************
    //*	Name		    : getPageTitle
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public String getTitle(){
        return driver.getTitle();
    }

    //*****************************************************************************************
    //*	Name		    : getCurrentAndroidActivity
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public String getCurrentAndroidActivity(){
        return ((AndroidDriver)driver).currentActivity();
    }

    //*****************************************************************************************
    //*	Name		    : maximizeWindow
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public Wrappers maximizeWindow()
    {
        try{
            driver.manage().window().maximize();
        }
        catch(WebDriverException e){
            Reporter.writeToTestLevelReport("Maximize Window", "Window should be maximized successfully", "Error occured " + e.getMessage(), "Fail");
            throw(e);
            //return false;
        }

        Reporter.writeToTestLevelReport("Maximize Window", "Window should be maximized successfully", "Window Maximized successfully", "Pass");
        return this;
    }

    //*****************************************************************************************
    //*	Name		    : switchToWindowWithName
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
	public Wrappers switchToWindowWithName() throws Exception {
		try
		{
			//driver.switchTo().window(strWindowName);
			//Switch to new window opened
			for(String winHandle : driver.getWindowHandles()){
			    driver.switchTo().window(winHandle);
			}
		}
		catch(Exception e)
		{
			Reporter.writeToTestLevelReport("Switch Window", "Switch to new Window ", "Exception occured : " + e, "Fail");
			throw(e);
		}
		
		return this;
	}

    //*****************************************************************************************
    //*	Name		    : waitForAndroidActivity
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public Wrappers waitForAndroidActivity(String expectedActivity,int sec)
    {
    	int i = 0;
    	String actualActivity="";
    	
    	//Loop for activity
    	while(i<sec)
    	{

    		actualActivity = ((AndroidDriver)driver).currentActivity();
    		if(actualActivity.equals(expectedActivity)){
                Reporter.writeToTestLevelReport("Wait for activity " + expectedActivity, "Activity " + expectedActivity + " should open", "Activity " + expectedActivity + " opened", "Pass");
                return this;
            }
    		
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            //increment
    		i++;
    	}

        Reporter.writeToTestLevelReport("Wait for activity " + expectedActivity, "Activity " + expectedActivity + " should open", "Activity " + expectedActivity + " didnt open. Current Activity is " + actualActivity, "Done");
    	throw(new TimeoutException("Timeout occured while waiting for Android Activity " + expectedActivity + " Current Activity is " + actualActivity));
    }



   /* //*****************************************************************************************
    //*	Name		    : fValidatePageDisplayed
    //*	Description	    : Function to validate the current window title
    //*	Author		    : Aniket Gadre
    //*	Input Params	:
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fValidatePageDisplayed(String strExpectedTitle)
    {
    	int intCount = 1;
    	String strActualTitle = "";

    	//Fetch current title
    	strActualTitle = driver.getTitle();


    	//while(!((strActualTitle.equalsIgnoreCase(strExpectedTitle)) || (intCount >= 31)))
    	while(!((strActualTitle.equalsIgnoreCase(strExpectedTitle)) || (intCount >= 20)))
    	{
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			strActualTitle = driver.getTitle();
			intCount++;
    	}

        //Validate
        if (!(driver.getTitle().equalsIgnoreCase(strExpectedTitle)))
        {
            Reporter.writeToTestLevelReport("Validate Title", "Title should be " + strExpectedTitle, "Title is " + strActualTitle, "Fail");
            return false;
        }

        //Reporter.writeToTestLevelReport("Validate Title", "Title should be " + strTitle, "Title is " + strActualTitle, "Pass");
        Reporter.writeToTestLevelReport("Validate Title", "Title should be " + strExpectedTitle, "Title is " + strActualTitle, "Pass");
        return true;
    }
    */
}
