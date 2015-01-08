package com.amdocs.asap;

import io.appium.java_client.MobileBy;
import io.appium.java_client.AppiumDriver;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    //** boolean verifyElementExistence(String strDesc)
    //** boolean verifyElementNonExistence(String strDesc)
    //** boolean verifyChildElementExistence(WebElement objParent, String strDesc)
    //** boolean verifyChildElementNonExistence(WebElement objParent, String strDesc)
	//** boolean verifyElementIsDisplayed (String strDesc)
	//** boolean verifyElementIsNotDisplayed (String strDesc)
	//** boolean verifyElementIsEnabled (String strDesc)
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
                Reporter.fnWriteToHtmlOutput("Get element matching description " + objDesc, "Element should be found and returned", "Property "  + FindBy + " specified for element is invalid", "Fail");
                throw(new InvalidSelectorException("Wrapper method getElement() : Property "  + FindBy + " specified for element is invalid"));
            }
        }
        catch(NoSuchElementException ex){
            Reporter.fnWriteToHtmlOutput("Get element matching description " + objDesc, "Element should be found and returned", "Element not found", "Fail");
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
            Reporter.fnWriteToHtmlOutput("Get elements matching description " + objDesc, "Element List should be returned", "Property "  + FindBy + " specified for elements is invalid", "Fail");
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
            Reporter.fnWriteToHtmlOutput("Get child object matching description " + objDesc, "Object should be found and returned", "Property "  + FindBy + " specified for object is invalid", "Fail");
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
            Reporter.fnWriteToHtmlOutput("Get child elements matching description " + objDesc, "Child Elements should be found and returned", "Property "  + FindBy + " specified for element is invalid", "Fail");
            throw(new InvalidSelectorException("Wrapper method getChildElements() : Property "  + FindBy + " specified for element is invalid"));
        }

        return elements;
    }


    //*****************************************************************************************
    //*	Name		    : verifyElementExistence
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
	public boolean verifyElementExistence(String strDesc){

        //WebElement Collection
        List<WebElement> lst = getElements(strDesc);

        //check not null
        if(lst == null) return false;

        //check Size
        if(lst.size() > 0){
            Reporter.fnWriteToHtmlOutput("Verify existence of element " + strDesc, "Element should exist", "Element Exist", "Pass");
            return true;
        }

        Reporter.fnWriteToHtmlOutput("Verify existence of element " + strDesc, "Element should exist", "Element does not exist", "Fail");
        return false;
    }

    //*****************************************************************************************
    //*	Name		    : verifyElementNonExistence
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public boolean verifyElementNonExistence(String strDesc){

        //WebElement Collection
        List<WebElement> lst = getElements(strDesc);

        //check not null
        if(lst == null) return false;

        if(lst.size() == 0){
            Reporter.fnWriteToHtmlOutput("Verify non existence of element " + strDesc, "Element should not exist", "Element does not exist", "Pass");
            return true;
        }

        Reporter.fnWriteToHtmlOutput("Verify non existence of element " + strDesc, "Element should not exist", "Element exist", "Fail");
        return false;
    }

    //*****************************************************************************************
    //*	Name		    : verifyChildElementExistence
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
	public boolean verifyChildElementExistence(WebElement objParent, String strDesc){
        
        //WebElement Collection
        List<WebElement> lst = getChildElements(objParent,strDesc);

        //check not null
        if(lst == null) return false;

        if(lst.size() > 0){
            Reporter.fnWriteToHtmlOutput("Verify existence of child element " + strDesc, "Child element exist", "Child element Exist", "Pass");
            return true;
        }

        Reporter.fnWriteToHtmlOutput("Verify existence of child element " + strDesc, "Child element exist", "Child element does not exist", "Fail");
        return false;
    }
	
	//*****************************************************************************************
    //*	Name		    : verifyChildElementNonExistence
    //*	Author		    : Surbhi Shivhare
    //*****************************************************************************************
	public boolean verifyChildElementNonExistence(WebElement objParent, String strDesc){
        
        //WebElement Collection
        List<WebElement> lst = getChildElements(objParent,strDesc);

        //check not null
        if(lst == null) return false;

      	//check size
        if(lst.size() == 0){
            Reporter.fnWriteToHtmlOutput("Verify non existence of child element " + strDesc, "Child element should not exist", "Child element does not exist", "Pass");
            return true;
        }

        Reporter.fnWriteToHtmlOutput("Verify non existence of child element " + strDesc, "Child element should not exist", "Child element exist", "Fail");
        return false;

    }
	
	//*****************************************************************************************
    //*	Name		    : verifyElementIsDisplayed
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
	public boolean verifyElementIsDisplayed(String strDesc) throws InterruptedException {
    		
        //Get WebElement
        WebElement webElement = getElement(strDesc);

        //check null
        if(webElement == null) return false;

        //Set displayed flag
        boolean bIsDisplayed = false;

        int intCount = 1;

        //Loop for around 10 secs to check whether object is being displayed
        while (!(bIsDisplayed) && (intCount <= 20)){
            bIsDisplayed = webElement.isDisplayed();

            //Sleep for half a sec
            Thread.sleep(500);
            intCount++;
        }

        //Validate if the element should be displayed or not
        if(bIsDisplayed){
            Reporter.fnWriteToHtmlOutput("Check if element with description  " + strDesc + " is displayed" , "Element should be displayed", "Element is Displayed", "Pass");
            return true;
        }

        Reporter.fnWriteToHtmlOutput("Check if element with description  " + strDesc + " is displayed" , "Element should be displayed", "Element is not displayed", "Fail");
        return false;
    }
	
	//*****************************************************************************************
    //*	Name		    : verifyElementIsDisplayed
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
	public boolean verifyElementIsDisplayed(WebElement webElement) throws InterruptedException {
		
		//Check if the WebElement is displayed    		
		boolean bIsDisplayed = false;	  
		String strDesc = webElement.toString();
        
        int intCount = 1;    

        //Loop for around 10 secs to check whether object is being displayed
        while (!(bIsDisplayed) && (intCount <= 20)){
             bIsDisplayed = webElement.isDisplayed();

            //Sleep for a sec
            Thread.sleep(500);
            intCount++;
        }

        //Validate if the element should be displayed or not
        if(bIsDisplayed){
            Reporter.fnWriteToHtmlOutput("Check if element with description  " + strDesc + " is displayed" , "Element should be displayed", "Element is Displayed", "Pass");
            return true;
        }

        Reporter.fnWriteToHtmlOutput("Check if element with description  " + strDesc + " is displayed" , "Element should be displayed", "Element is not displayed", "Fail");
        return false;
    }
	
	
	//*****************************************************************************************
    //*	Name		    : verifyElementIsNotDisplayed
    //*	Author		    : Surbhi Shivhare
    //*****************************************************************************************
	public boolean verifyElementIsNotDisplayed(String strDesc) throws InterruptedException {

        //Get WebElement
        WebElement webElement = getElement(strDesc);

        //check not null
        if(webElement == null) return false;

        //Check if the WebElement is displayed
        boolean bNotIsDisplayed = false;

        int intCount = 1;
	        
        //Loop for around 10 secs to check whether object is being displayed
        while (!(bNotIsDisplayed) && (intCount <=20)){
             bNotIsDisplayed = !(webElement.isDisplayed());

            //Sleep for a sec
            Thread.sleep(500);
            intCount++;
        }

        //Validate if the element should be displayed or not
        if(bNotIsDisplayed){
            Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is not displayed" , "Object should not be displayed", "Object is not displayed", "Pass");
            return true;
        }

        Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is not displayed" , "Object should not be displayed", "Object is Displayed", "Fail");
        return false;
    }
	
	//*****************************************************************************************
    //*	Name		    : verifyElementIsNotDisplayed
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
	public boolean verifyElementIsNotDisplayed(WebElement webElement) throws InterruptedException {
		
		//Check if the WebElement is displayed    		
		boolean bNotIsDisplayed = false;	
		String strDesc = webElement.toString();
        
		//Counter
        int intCount = 1;  

        //Loop for around 10 secs to check whether object is being displayed
        while (!(bNotIsDisplayed) && (intCount <= 20)){
            bNotIsDisplayed = !(webElement.isDisplayed());

            //Sleep for a sec
            Thread.sleep(500);
            intCount++;
        }

        //Validate if the element should be displayed or not
        if(bNotIsDisplayed){
            Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is not displayed" , "Object should not be displayed", "Object is not displayed", "Pass");
            return true;
        }

        Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is not displayed" , "Object should not be displayed", "Object is Displayed", "Fail");
        return false;
    }
	
	
	//*****************************************************************************************
    //*	Name		    : verifyElementIsEnabled
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public boolean verifyElementIsEnabled(String strDesc) throws InterruptedException {
        //Get WebElement
        WebElement webElement = getElement(strDesc);

        //check not null
        if(webElement == null) return false;

        //Check if the WebElement is Enabled
        boolean bIsEnabled = false;

        int intCount = 1;

        while (!(bIsEnabled) && (intCount <= 20)){
            bIsEnabled = webElement.isEnabled();

            //Sleep for a sec, increment the counter
            Thread.sleep(500);
            intCount++;
        }
	        
        //Validate if the WebElement is Enabled
        if (!(bIsEnabled)){
            Reporter.fnWriteToHtmlOutput("Verify if element with description  " + strDesc + " is enabled", "Element should be enabled", "Element is not enabled", "Fail");
            return false;
        }

        Reporter.fnWriteToHtmlOutput("Verify if element with description  " + strDesc + " is enabled", "Element should be enabled", "Element is enabled", "Pass");
        return true;
    }	
    
  //*****************************************************************************************
    //*	Name		    : verifyElementIsEnabled
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public boolean verifyElementIsEnabled(WebElement webElement) throws InterruptedException {
    	
    	//Check if the WebElement is Enabled
        boolean bIsEnabled = false;
        String strDesc = webElement.toString();
        int intCount = 1;

        while (!(bIsEnabled) && (intCount <= 20)){
            bIsEnabled = webElement.isEnabled();

            //Sleep for a sec, increment the counter
            Thread.sleep(500);
            intCount++;
        }

        //Validate if the WebElement is Enabled
        if (!(bIsEnabled)){
            Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is enabled", "Object should be enabled", "Object is not enabled", "Fail");
            return false;
        }

        Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is enabled", "Object should be enabled", "Object is enabled", "Pass");
        return true;
    }	
    
    //*****************************************************************************************
    //*	Name		    : verifyElementIsDisabled
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public boolean verifyElementIsDisabled(String strDesc) throws InterruptedException {

        //Get WebElement
        WebElement webElement = getElement(strDesc);

        //check not null
        if(webElement == null) return false;

        //Check if the WebElement is Enabled
        boolean bIsNotEnabled = false;

        int intCount = 1;

        while (!(bIsNotEnabled) && (intCount <= 20)){
            bIsNotEnabled = !(webElement.isEnabled());

            //Sleep for a sec, increment the counter
            Thread.sleep(500);
            intCount++;
        }

        //Validate if the WebElement is Enabled
        if (!(bIsNotEnabled)){
            Reporter.fnWriteToHtmlOutput("Verify element with description  " + strDesc + " is disabled", "Element should be disabled", "Element is not disabled", "Fail");
            return false;
        }

        Reporter.fnWriteToHtmlOutput("Verify element with description  " + strDesc + " is disabled", "Element should be disabled", "Element is disabled", "Pass");
        return true;
    }
    
    //*****************************************************************************************
    //*	Name		    : verifyElementIsDisabled
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public boolean verifyElementIsDisabled(WebElement webElement) throws InterruptedException {
    	
    	//Check if the WebElement is Enabled
        boolean bIsNotEnabled = false;
        String strDesc = webElement.toString();
        int intCount = 1;

        while (!(bIsNotEnabled) && (intCount <= 20)){
            bIsNotEnabled = !(webElement.isEnabled());

            //Sleep for a sec, increment the counter
            Thread.sleep(500);
            intCount++;
        }

        //Validate if the WebElement is Enabled
        if (!(bIsNotEnabled)){
            Reporter.fnWriteToHtmlOutput("Verify element with description  " + strDesc + " is disabled", "Element should be disabled", "Element is not disabled", "Fail");
            return false;
        }

        Reporter.fnWriteToHtmlOutput("Verify element with description  " + strDesc + " is disabled", "Element should be disabled", "Element is disabled", "Pass");
        return true;
    }


    //*****************************************************************************************
    //*	Name		    : verifyElementIsSelected
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public boolean verifyElementIsSelected(String strDesc) {

        //Get WebElement
        WebElement webElement = getElement(strDesc);

        //check not null
        if(webElement == null) return false;

        //Validate if the WebElement is Selected
        if (!(webElement.isSelected())){
            Reporter.fnWriteToHtmlOutput("Verify element with description  " + strDesc + " is selected", "Element should be selected", "Element is not selected", "Fail");
            return false;
        }

        Reporter.fnWriteToHtmlOutput("Verify element with description  " + strDesc + " is selected", "Element should be selected", "Element is selected", "Pass");
        return true;
    }

    //*****************************************************************************************
    //*	Name		    : verifyElementIsSelected
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public boolean verifyElementIsSelected(WebElement webElement){
        String strDesc = webElement.toString();

        //Validate if the WebElement is Selected
        if (!(webElement.isSelected())){
            Reporter.fnWriteToHtmlOutput("Verify element with description  " + strDesc + " is selected", "Element should be selected", "Element is not selected", "Fail");
            return false;
        }

        Reporter.fnWriteToHtmlOutput("Verify element with description  " + strDesc + " is selected", "Element should be selected", "Element is selected", "Pass");
        return true;
    }


    //*****************************************************************************************
    //*	Name		    : verifyElementIsNotSelected
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public boolean verifyElementIsNotSelected(String strDesc) {

        //Get WebElement
        WebElement webElement = getElement(strDesc);

        //check not null
        if(webElement == null) return false;

        //Validate if the WebElement is Selected
        if (webElement.isSelected()){
            Reporter.fnWriteToHtmlOutput("Verify element with description  " + strDesc + " is selected", "Element should not be selected", "Element is selected", "Fail");
            return false;
        }

        Reporter.fnWriteToHtmlOutput("Verify element with description  " + strDesc + " is selected", "Element should not be selected", "Element is not selected", "Pass");
        return true;
    }

    //*****************************************************************************************
    //*	Name		    : verifyElementIsNotSelected
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public boolean verifyElementIsNotSelected(WebElement webElement){
        String strDesc = webElement.toString();

        //Validate if the WebElement is Selected
        if (webElement.isSelected()){
            Reporter.fnWriteToHtmlOutput("Verify element with description  " + strDesc + " is not selected", "Element should not be selected", "Element is selected", "Fail");
            return false;
        }

        Reporter.fnWriteToHtmlOutput("Verify element with description  " + strDesc + " is not selected", "Element should not be selected", "Element is not selected", "Pass");
        return true;
    }


    //*****************************************************************************************
    //*	Name		    : click
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public void click(String strDesc)
    {
        //Initialize
        WebElement objClick = getElement(strDesc);
        	
        //Check if the object is displayed enabled, if yes click the same
        if (objClick.isDisplayed() && objClick.isEnabled()){
            //Click on the object
            objClick.click();
        }
        else{
            Reporter.fnWriteToHtmlOutput("Click element matching description " + strDesc, "Element with description " + strDesc + " should be clicked", "Element is either not displayed or is not enabled", "Fail");
            throw(new ElementNotVisibleException("Wrapper method click() : Element is either not visible or is not enabled"));
            //return false;
        }
            
        Reporter.fnWriteToHtmlOutput("Click object matching description " + strDesc, "Click operation should be successful", "Successfully clicked the object", "Done");
        //return true;
    }
    
  //*****************************************************************************************
    //*	Name		    : click
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public void click(WebElement objClick)
    {
        String strDesc = objClick.toString();

        //Check if the object is enabled, if yes click the same
        if (objClick.isDisplayed() && objClick.isEnabled()){
            //Click on the object
            objClick.click();
        }
        else{
            Reporter.fnWriteToHtmlOutput("Click element matching description " + strDesc, "Element with description " + strDesc + " should be clicked", "Element is either not displayed or is not enabled", "Fail");
            throw(new ElementNotVisibleException("Wrapper method click() : Element is either not visible or is not enabled"));
            //return false;
        }

        Reporter.fnWriteToHtmlOutput("Click object matching description " + objClick.toString(), "Click operation should be successful", "Successfully clicked the object", "Done");
        //return true;
    }	

	//*****************************************************************************************
    //*	Name		    : enterText
    //*	Author		    : Anil Agarwal
    //*****************************************************************************************
    public void enterText(String strDesc, String strText)
    {
        WebElement objEdit = getElement(strDesc);

        //Check if the object is enabled, if yes click the same
        if (objEdit.isDisplayed() && objEdit.isEnabled()){
            //Enter the text in the edit box
            objEdit.clear();
            objEdit.sendKeys(strText);
        }
        else{
            Reporter.fnWriteToHtmlOutput("Set value in element with description " + strDesc, "Value " + strText + " should be set in the edit box", "Element is either not displayed or not enabled", "Fail");
            throw(new ElementNotVisibleException("Wrapper method enterText() : Element with description " + strDesc + " is either not visible or is not enabled"));
            //return false;
        }

        Reporter.fnWriteToHtmlOutput("Set value in element with description " + strDesc, "Value " + strText + " should be set in the edit box", "Value is set in the text field", "Done");
        //return true;
    }	
    
    //*****************************************************************************************
    //*	Name		    : enterText
    //*	Author		    : ANiket Gadre
    //*****************************************************************************************
    public void enterText(WebElement objEdit, String strText)
    {
    	String strDesc = objEdit.toString();

        //Check if the object is enabled, if yes click the same
        if (objEdit.isDisplayed() && objEdit.isEnabled()){
            //Enter the text in the edit box
            objEdit.clear();
            objEdit.sendKeys(strText);
        }
        else{
            Reporter.fnWriteToHtmlOutput("Set value in element with description " + strDesc, "Value " + strText + " should be set in the edit box", "Element is either not displayed or not enabled", "Fail");
            throw(new ElementNotVisibleException("Wrapper method enterText() : Element with description " + strDesc + " is either not visible or is not enabled"));
            //return false;
        }

        Reporter.fnWriteToHtmlOutput("Set value in element with description " + strDesc, "Value " + strText + " should be set in the edit box", "Value is set in the text field", "Done");
        //return true;
    }	


    //*****************************************************************************************
    //*	Name		    : selectOptionFromList
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public void selectOptionFromList(String strDesc, String strText)
    {
        WebElement objSelect = getElement(strDesc);

        //Check if the object is enabled, if yes click the same
        if (objSelect.isEnabled()){
            //Set Select Element and select required value by text
            try{
                Select select = new Select(objSelect);
                select.selectByVisibleText(strText);
            }
            catch(WebDriverException ex){
                Reporter.fnWriteToHtmlOutput("Select value in element with description " + strDesc, "Value " + strText + " should be selected in the list box", "Selecting value failed due to exception " + ex.getMessage(), "Fail");
                throw(ex);
            }
        }
        else{
            Reporter.fnWriteToHtmlOutput("Select value in element with description " + strDesc, "Value " + strText + " should be selected in the list box", "Element is either not displayed or not enabled", "Fail");
            throw(new ElementNotVisibleException("Wrapper method selectOptionFromList() : Element with description " + strDesc + " is either not visible or is not enabled"));
            //return false;
        }

        Reporter.fnWriteToHtmlOutput("Select value from dropdown","Select value " + strText, "Value " + strText +" selected" , "Done");
        //return true;
    }
    
    //*****************************************************************************************
    //*	Name		    : selectOptionFromList
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public void selectOptionFromList(WebElement objSelect, String strText)
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
                Reporter.fnWriteToHtmlOutput("Select value in element with description " + strDesc, "Value " + strText + " should be selected in the list box", "Selecting value failed due to exception " + ex.getMessage(), "Fail");
                throw(ex);
            }
        }
        else{
            Reporter.fnWriteToHtmlOutput("Select value in element with description " + strDesc, "Value " + strText + " should be selected in the list box", "Element is either not displayed or not enabled", "Fail");
            throw(new ElementNotVisibleException("Wrapper method selectOptionFromList() : Element with description " + strDesc + " is either not visible or is not enabled"));
            //return false;
        }

        Reporter.fnWriteToHtmlOutput("Select value from dropdown","Select value " + strText, "Value " + strText +" selected" , "Done");
        //return true;
    }
    

    
    //*****************************************************************************************
    //*	Name		    : checkCheckBox
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public void checkCheckBox(String strDesc)
    {
        //Initialize
        WebElement objChkBox = getElement(strDesc);

        //Check if the object is enabled, if yes click the same
        if (objChkBox.isDisplayed() && objChkBox.isEnabled()){
            //Check state of check box
            boolean isChecked = objChkBox.isSelected();

            //Check if Not Checked
            if(isChecked == false) objChkBox.click();
        }
        else{
            Reporter.fnWriteToHtmlOutput("Check CheckBox element with description " + strDesc, "Checkbox should be checked", "Element is either not displayed or not enabled", "Fail");
            throw(new ElementNotVisibleException("Wrapper method checkCheckBox() : Element with description " + strDesc + " is either not visible or is not enabled"));
            //return false;
        }

        Reporter.fnWriteToHtmlOutput("Check CheckBox element with description " + strDesc, "Check operation should be successful", "Successfully checked the checkbox", "Done");
        //return true;
    }
    
  //*****************************************************************************************
    //*	Name		    : checkCheckBox
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public void checkCheckBox(WebElement objChkBox)
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
            Reporter.fnWriteToHtmlOutput("Check CheckBox element with description " + strDesc, "Checkbox should be checked", "Element is either not displayed or not enabled", "Fail");
            throw(new ElementNotVisibleException("Wrapper method checkCheckBox() : Element with description " + strDesc + " is either not visible or is not enabled"));
            //return false;
        }

        Reporter.fnWriteToHtmlOutput("Check CheckBox element with description " + strDesc, "Check operation should be successful", "Successfully checked the checkbox", "Done");
        //return true;
    }
    
    //*****************************************************************************************
    //*	Name		    : uncheckCheckBox
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public void uncheckCheckBox(String strDesc)
    {
        //Initialize
        WebElement objChkBox = getElement(strDesc);

        //Check if the object is enabled, if yes click the same
        if (objChkBox.isDisplayed() && objChkBox.isEnabled()){
            //Check state of check box
            boolean isChecked = objChkBox.isSelected();

            //Check if Checked
            if(isChecked == true) objChkBox.click();
        }
        else{
            Reporter.fnWriteToHtmlOutput("Check CheckBox element with description " + strDesc, "Checkbox should be unchecked", "Element is either not displayed or not enabled", "Fail");
            throw(new ElementNotVisibleException("Wrapper method checkCheckBox() : Element with description " + strDesc + " is either not visible or is not enabled"));
            //return false;
        }

        Reporter.fnWriteToHtmlOutput("Uncheck CheckBox element with description " + strDesc, "Un-check operation should be successful", "Successfully unchecked the checkbox", "Done");
        //return true;
    }	
    
    //*****************************************************************************************
    //*	Name		    : uncheckCheckBox
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public void uncheckCheckBox(WebElement objChkBox)
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
            Reporter.fnWriteToHtmlOutput("Check CheckBox element with description " + strDesc, "Checkbox should be unchecked", "Element is either not displayed or not enabled", "Fail");
            throw(new ElementNotVisibleException("Wrapper method checkCheckBox() : Element with description " + strDesc + " is either not visible or is not enabled"));
            //return false;
        }

        Reporter.fnWriteToHtmlOutput("Uncheck CheckBox element with description " + strDesc, "Un-check operation should be successful", "Successfully unchecked the checkbox", "Done");
        //return true;
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
			Reporter.fnWriteToHtmlOutput("Get browser name", "Should return Browser Name", "Fetching Browser Name Failed. Exception " + e, "Fail");
			throw(e);
		}
	}


    //*****************************************************************************************
    //*	Name		    : rotateDeviceScreen
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public void rotateDeviceScreen(String Orientation) throws InterruptedException {

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
            Reporter.fnWriteToHtmlOutput("Rotate Screen", "Set screen orientation to " + strOrientation, "Orientation Setting Failed", "Fail");
            throw(ex);
            //return false;
        }

        Reporter.fnWriteToHtmlOutput("Rotate Screen", "Set screen orientation to " + strOrientation, "Orientation Set Successfully", "Pass");
        //return true;
    }

    //*****************************************************************************************
    //*	Name		    : setGeoLocation
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public void setGeoLocation(String lat, String lon){
        //js
        String Script = "window.navigator.geolocation.getCurrentPosition =  function(success){var position = {'coords' : {'latitude': '" + lat + "', 'longitude': '" + lon + "'}}; success(position);}";

        //Update geolocation
        JavascriptExecutor js = (JavascriptExecutor)driver;
        Object[] args = {null};
        js.executeScript(Script, args);
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
    public void maximizeWindow()
    {
        try{
            driver.manage().window().maximize();
        }
        catch(WebDriverException e){
            Reporter.fnWriteToHtmlOutput("Maximize Window","Window should be maximized successfully","Error occured " + e.getMessage(),"Fail");
            throw(e);
            //return false;
        }

        Reporter.fnWriteToHtmlOutput("Maximize Window","Window should be maximized successfully","Window Maximized successfully","Pass");
        //return true;
    }

    //*****************************************************************************************
    //*	Name		    : switchToWindowWithName
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
	public boolean switchToWindowWithName()
	{
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
			Reporter.fnWriteToHtmlOutput("Switch Window", "Switch to new Window ", "Exception occured : " + e, "Fail");
			return false;
		}
		
		return true;
	}

    //*****************************************************************************************
    //*	Name		    : waitForAndroidActivity
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public void waitForAndroidActivity(String expectedActivity,int sec)
    {
    	int i = 0;
    	String actualActivity="";
    	
    	//Loop for activity
    	while(i<sec)
    	{

    		actualActivity = ((AndroidDriver)driver).currentActivity();
    		if(actualActivity.equals(expectedActivity)){
                Reporter.fnWriteToHtmlOutput("Wait for activity " + expectedActivity,"Activity " +  expectedActivity + " should open","Activity " + expectedActivity + " opened","Pass");
                return;
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

        Reporter.fnWriteToHtmlOutput("Wait for activity " + expectedActivity,"Activity " +  expectedActivity + " should open","Activity " + expectedActivity + " didnt open. Current Activity is " + actualActivity,"Done");
    	throw(new TimeoutException("Timeout occured while waiting for Android Activity " + expectedActivity + " Current Activity is " + actualActivity));
    }



   /* //*****************************************************************************************
    //*	Name		    : fValidatePageDisplayed
    //*	Description	    : Function to validate the current window title
    //*	Author		    : Anil Agarwal
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
            Reporter.fnWriteToHtmlOutput("Validate Title", "Title should be " + strExpectedTitle, "Title is " + strActualTitle, "Fail");
            return false;
        }

        //Reporter.fnWriteToHtmlOutput("Validate Title", "Title should be " + strTitle, "Title is " + strActualTitle, "Pass");
        Reporter.fnWriteToHtmlOutput("Validate Title", "Title should be " + strExpectedTitle, "Title is " + strActualTitle, "Pass");
        return true;
    }
    */
}
