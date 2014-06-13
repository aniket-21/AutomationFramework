package com.amdocs.asap;

import io.appium.java_client.MobileBy;
import io.appium.java_client.AppiumDriver;

import java.sql.Timestamp;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.Rotatable;

public class CommonFunctions {
	
	//*************************************************************
	/** List of Methods in this class **/
	//** boolean fGuiIsDisplayed (String strDesc)
	//** boolean fGuiIsNotDisplayed (String strDesc)
	//** boolean fGuiIsEnabled (String strDesc)
	//** boolean fGuiIsDisabled (String strDesc)
	//** boolean fGuiClick (String strDesc)
	//** boolean fGuiEnterText (String strDesc, String strText)
	//** boolean fValidatePageDisplayed (String strExpectedTitle)
	//** boolean fGuiSelectOptionFromList (String strDesc, String strText)
	//** boolean fGuiCheckObjectExistence(String strDesc)
	//** boolean fGuiCheckObjectNotExist(String strDesc)
	//** boolean fGuiCheckChildObjectExistence(WebElement Parent, String strDesc)
	//** boolean fGuiCheckChildObjectNonExistence(WebElement Parent, String strDesc)
	//** boolean fGuiCheckCheckBox(String strDesc)
	//** boolean fGuiUncheckCheckBox(String strDesc)
	//** WebElement getObject(String strDesc)
	//** WebElement getChildObject(WebElement Parent, String strDesc)
	//** boolean switchToWindowWithName(strWindowName)
	//** String returnRandomString()
	//** String returnTimeStamp()
	//** boolean fverifyCheckBoxIsChecked(String strDesc)
	//** boolean fverifyCheckBoxIsChecked(String strDesc)
	/** List of Methods in this class **/
	//*************************************************************
	
	//Properties
	private Reporting Reporter;
	private WebDriver driver;
	
	//Constructor
	public  CommonFunctions()
	{
		Reporter = Global.Reporter;
		driver = Global.webDriver;
		
	}
	
	 //*****************************************************************************************
    //*	Name		    : fGuiCheckObjectExistence
    //*	Description	    : Check if the object exists
    //*	Author		    : Surbhi Shivhare
    //*	Input Params	: strDesc(The property of the object)
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
	public boolean fGuiCheckObjectExistence(String strDesc){
		
		 //Delimiters
        String[] delimiters = new String[] {":="};
        String[] arrFindByValues = strDesc.split(delimiters[0]);
        
        //Get Findby and Value 
        String FindBy = arrFindByValues[0];
        String val = arrFindByValues[1];
        
        //WebElement Collection
        List<WebElement> lst;

        try
        {
            //Handle all FindBy cases
        	String strElement = FindBy.toLowerCase();
        	if (strElement.equalsIgnoreCase("linktext"))
        	{
        		lst = driver.findElements(By.linkText(val));
        	}
        	else if (strElement.equalsIgnoreCase("xpath"))
        	{
        		lst =  driver.findElements(By.xpath(val));
        	}
        	else if (strElement.equalsIgnoreCase("name"))
        	{
        		lst =  driver.findElements(By.name(val));
        	}
        	else if (strElement.equalsIgnoreCase("id"))
        	{
        		lst =  driver.findElements(By.id(val));
        	}
        	else if (strElement.equalsIgnoreCase("classname"))
        	{
        		lst =  driver.findElements(By.className(val));
        	}
        	else if (strElement.equalsIgnoreCase("cssselector"))
        	{
        		lst =  driver.findElements(By.cssSelector(val));
        	}
        	else if (strElement.equalsIgnoreCase("tagname"))
        	{
        		lst =  driver.findElements(By.tagName(val));
        	}
        	else
        	{
        		Reporter.fnWriteToHtmlOutput("Check Existence of object " + strDesc, "Object should exist", "Property "  + FindBy + " specified for object is invalid", "Fail");
        		System.out.println("Property name " + FindBy + " specified for object " + strDesc + " is invalid");
        		return false;
        	}
        	
        	if(lst.size() > 0)
        	{
        		Reporter.fnWriteToHtmlOutput("Check Existence of object " + strDesc, "Object Should exist", "Object Exist", "Pass");
        		return true;
        	}
        	else
        	{
        		Reporter.fnWriteToHtmlOutput("Check Existence of object " + strDesc, "Object Should exist", "Object does not exist", "Fail");
        		return false;
        	}
        	
        }
        
        //Catch Block
        catch(Exception e)
        {
        	Reporter.fnWriteToHtmlOutput("Check Existence of object " + strDesc, "Object should exist", "Exception occured while checking existence", "Fail");
        	System.out.println("Exception " + e.toString() + " occured while checking object existence");
        	return false;
        }        	        	         	   	
    }
	
	//*****************************************************************************************
    //*	Name		    : fGuiCheckChildObjectExistence
    //*	Description	    : Check if the object exists
    //*	Author		    : Surbhi Shivhare
    //*	Input Params	: objParent (Parent WebElement)
	//*					: strDesc(The property of the object)
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
	public boolean fGuiCheckChildObjectExistence(WebElement objParent, String strDesc){
		//Delimiters
        String[] delimiters = new String[] {":="};
        String[] arrFindByValues = strDesc.split(delimiters[0]);
        
        //Get Findby and Value 
        String FindBy = arrFindByValues[0];
        String val = arrFindByValues[1];
        
        //WebElement Collection
        List<WebElement> lst;

        try
        {
            //Handle all FindBy cases
        	String strElement = FindBy.toLowerCase();
        	if (strElement.equalsIgnoreCase("linktext"))
        	{
        		lst = objParent.findElements(By.linkText(val));
        	}
        	else if (strElement.equalsIgnoreCase("xpath"))
        	{
        		lst =  objParent.findElements(By.xpath(val));
        	}
        	else if (strElement.equalsIgnoreCase("name"))
        	{
        		lst =  objParent.findElements(By.name(val));
        	}
        	else if (strElement.equalsIgnoreCase("id"))
        	{
        		lst =  objParent.findElements(By.id(val));
        	}
        	else if (strElement.equalsIgnoreCase("classname"))
        	{
        		lst =  objParent.findElements(By.className(val));
        	}
        	else if (strElement.equalsIgnoreCase("cssselector"))
        	{
        		lst =  objParent.findElements(By.cssSelector(val));
        	}
        	else if (strElement.equalsIgnoreCase("tagname"))
        	{
        		lst =  objParent.findElements(By.tagName(val));
        	}
        	else
        	{
        		Reporter.fnWriteToHtmlOutput("Check Existence of child object " + strDesc, "Object should exist", "Property "  + FindBy + " specified for object is invalid", "Fail");
        		System.out.println("Property name " + FindBy + " specified for object " + strDesc + " is invalid");
        		return false;
        	}
        	
        	if(lst.size() > 0)
        	{
        		//Reporter.fnWriteToHtmlOutput("Check Existence of child object " + strDesc, "Object Should exist", "Object Exist", "Pass");
        		return true;
        	}
        	else
        	{
        		//Reporter.fnWriteToHtmlOutput("Check Existence of child object " + strDesc, "Object Should exist", "Object does not exist", "Fail");
        		return false;
        	}
        	
        }
        
        //Catch Block
        catch(Exception e)
        {
        	Reporter.fnWriteToHtmlOutput("Check Existence of child object " + strDesc, "Object should exist", "Exception occured while checking existence", "Fail");
        	System.out.println("Exception " + e.toString() + " occured while checking object existence");
        	return false;
        }  
    }
	
	//*****************************************************************************************
    //*	Name		    : fGuiCheckChildObjectNonExistence
    //*	Description	    : Check if the object exists
    //*	Author		    : Surbhi Shivhare
    //*	Input Params	: objParent (Parent WebElement)
	//*					: strDesc(The property of the object)
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
	public boolean fGuiCheckChildObjectNonExistence(WebElement objParent, String strDesc){
		 //Delimiters
        String[] delimiters = new String[] {":="};
        String[] arrFindByValues = strDesc.split(delimiters[0]);
        
        //Get Findby and Value 
        String FindBy = arrFindByValues[0];
        String val = arrFindByValues[1];
        
        //WebElement Collection
        List<WebElement> lst;

        try
        {
            //Handle all FindBy cases
        	String strElement = FindBy.toLowerCase();
        	if (strElement.equalsIgnoreCase("linktext"))
        	{
        		lst = objParent.findElements(By.linkText(val));
        	}
        	else if (strElement.equalsIgnoreCase("xpath"))
        	{
        		lst =  objParent.findElements(By.xpath(val));
        	}
        	else if (strElement.equalsIgnoreCase("name"))
        	{
        		lst =  objParent.findElements(By.name(val));
        	}
        	else if (strElement.equalsIgnoreCase("id"))
        	{
        		lst =  objParent.findElements(By.id(val));
        	}
        	else if (strElement.equalsIgnoreCase("classname"))
        	{
        		lst =  objParent.findElements(By.className(val));
        	}
        	else if (strElement.equalsIgnoreCase("cssselector"))
        	{
        		lst =  objParent.findElements(By.cssSelector(val));
        	}
        	else if (strElement.equalsIgnoreCase("tagname"))
        	{
        		lst =  objParent.findElements(By.tagName(val));
        	}
        	
        	else
        	{
        		Reporter.fnWriteToHtmlOutput("Check object Non Existence for child object " + strDesc, "Object should not exist", "Property "  + FindBy + " specified for object is invalid", "Fail");
        		System.out.println("Property name " + FindBy + " specified for object " + strDesc + " is invalid");
        		return false;
        	}
        	
        	if(lst.size() == 0)
        	{
        		Reporter.fnWriteToHtmlOutput("Check Non Existence of child object " + strDesc, "Object Should not exist", "Object does not exist", "Pass");
        		return true;
        	}
        	else
        	{
        		Reporter.fnWriteToHtmlOutput("Check Non Existence of child object " + strDesc, "Object Should not exist", "Object exist", "Fail");
        		return false;
        	}
        	
        }
        
        //Catch Block
        catch(Exception e)
        {
        	Reporter.fnWriteToHtmlOutput("Check Non Existence of child object " + strDesc, "Object should not exist", "Exception occured while checking existence", "Fail");
        	System.out.println("Exception " + e.toString() + " occured while checking object non existence");
        	return false;
        }      	
    }
	
	//*****************************************************************************************
    //*	Name		    : fGuiCheckObjectNonExistence
    //*	Description	    : Check if the object does not exists
    //*	Author		    : Surbhi Shivhare
    //*	Input Params	: strDesc(The property of the object)
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
	public boolean fGuiCheckObjectNonExistence(String strDesc){
		 //Delimiters
        String[] delimiters = new String[] {":="};
        String[] arrFindByValues = strDesc.split(delimiters[0]);
        
        //Get Findby and Value 
        String FindBy = arrFindByValues[0];
        String val = arrFindByValues[1];
        
        //WebElement Collection
        List<WebElement> lst;

        try
        {
            //Handle all FindBy cases
        	String strElement = FindBy.toLowerCase();
        	if (strElement.equalsIgnoreCase("linktext"))
        	{
        		lst = driver.findElements(By.linkText(val));
        	}
        	else if (strElement.equalsIgnoreCase("xpath"))
        	{
        		lst =  driver.findElements(By.xpath(val));
        	}
        	else if (strElement.equalsIgnoreCase("name"))
        	{
        		lst =  driver.findElements(By.name(val));
        	}
        	else if (strElement.equalsIgnoreCase("id"))
        	{
        		lst =  driver.findElements(By.id(val));
        	}
        	else if (strElement.equalsIgnoreCase("classname"))
        	{
        		lst =  driver.findElements(By.className(val));
        	}
        	else if (strElement.equalsIgnoreCase("cssselector"))
        	{
        		lst =  driver.findElements(By.cssSelector(val));
        	}
        	else if (strElement.equalsIgnoreCase("tagname"))
        	{
        		lst =  driver.findElements(By.tagName(val));
        	}
        	else
        	{
        		Reporter.fnWriteToHtmlOutput("Check object Non Existence for object " + strDesc, "Object should not exist", "Property "  + FindBy + " specified for object is invalid", "Fail");
        		System.out.println("Property name " + FindBy + " specified for object " + strDesc + " is invalid");
        		return false;
        	}
        	
        	if(lst.size() == 0)
        	{
        		Reporter.fnWriteToHtmlOutput("Check Non Existence of object " + strDesc, "Object Should not exist", "Object does not exist", "Pass");
        		return true;
        	}
        	else
        	{
        		Reporter.fnWriteToHtmlOutput("Check Non Existence of object " + strDesc, "Object Should not exist", "Object exist", "Fail");
        		return false;
        	}
        	
        }
        
        //Catch Block
        catch(Exception e)
        {
        	Reporter.fnWriteToHtmlOutput("Check Non Existence of object " + strDesc, "Object should not exist", "Exception occured while checking existence", "Fail");
        	System.out.println("Exception " + e.toString() + " occured while checking object non existence");
        	return false;
        }     	
    }	
	
	//*****************************************************************************************
    //*	Name		    : fGuiIsDisplayed
    //*	Description	    : Check if the object is displayed or not as per the choice
    //*	Author		    : Surbhi Shivhare
    //*	Input Params	: strDesc(The property of the object)
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
	public boolean fGuiIsDisplayed(String strDesc){
    	try{
    		
    		//Get WebElement
    		WebElement webElement = getObject(strDesc);		
    		
    		//Check if the WebElement is displayed    		
    		boolean bIsDisplayed = false;	        
	        
	        int intCount = 1;    
	        
	        //Loop for around 10 secs to check whether object is being displayed
	        while (!(bIsDisplayed) && (intCount <=10))
	        {
	        	try {
	        			bIsDisplayed = webElement.isDisplayed();
	        		
        	    }
	        	catch (Exception e){
	        		Reporter.fnWriteToHtmlOutput("Check if element with description  " + strDesc + " is displayed", "Exception occurred","Exception :" + e, "Fail");
	        		e.printStackTrace();
	    			return false;	    	    	
	    	    }	 
	        	
	        	
	        	//Sleep for a sec
	        	Thread.sleep(1000);
				intCount++;			
	        }
	
	        //Validate if the element should be displayed or not	        
        	if(bIsDisplayed){
        		Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is displayed" , "Object should be displayed", "Object is Displayed", "Pass");
	            return true;
        	}
        	else {
        		Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is displayed" , "Object should be displayed", "Object is not displayed", "Fail");
	            return false;
        	}       	
	       	        
    	}catch(Exception e){
    		Reporter.fnWriteToHtmlOutput("Check if element with description  " + strDesc + " is displayed", "Exception occurred","Exception :" + e, "Fail");
    		e.printStackTrace();
			return false;		
    	}    	
    }
	
	//*****************************************************************************************
    //*	Name		    : fGuiIsDisplayed
    //*	Description	    : Check if the object is displayed or not as per the choice
    //*	Author		    : Surbhi Shivhare
    //*	Input Params	: Webelement
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
	public boolean fGuiIsDisplayed(WebElement webElement){
		
		//Check if the WebElement is displayed    		
		boolean bIsDisplayed = false;	  
		String strDesc = webElement.toString();
        
        int intCount = 1;    
	        
	    try{
	        
	        //Loop for around 10 secs to check whether object is being displayed
	        while (!(bIsDisplayed) && (intCount <=10))
	        {
	        	try {
	        			bIsDisplayed = webElement.isDisplayed();
	        		
        	    }
	        	catch (Exception e){
	        		Reporter.fnWriteToHtmlOutput("Check if element with description  " + strDesc + " is displayed", "Exception occurred","Exception :" + e, "Fail");
	        		e.printStackTrace();
	    			return false;	    	    	
	    	    }	 
	        	
	        	
	        	//Sleep for a sec
	        	Thread.sleep(1000);
				intCount++;			
	        }
	
	        //Validate if the element should be displayed or not	        
        	if(bIsDisplayed){
        		Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is displayed" , "Object should be displayed", "Object is Displayed", "Pass");
	            return true;
        	}
        	else {
        		Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is displayed" , "Object should be displayed", "Object is not displayed", "Fail");
	            return false;
        	}       	
	       	        
    	}catch(Exception e){
    		Reporter.fnWriteToHtmlOutput("Check if element with description  " + strDesc + " is displayed", "Exception occurred","Exception :" + e, "Fail");
    		e.printStackTrace();
			return false;		
    	}    	
    }
	
	
	//*****************************************************************************************
    //*	Name		    : fGuiIsNotDisplayed
    //*	Description	    : Check if the object is displayed or not as per the choice
    //*	Author		    : Surbhi Shivhare
    //*	Input Params	: strDesc(The property of the object), choiceYesOrNo(displayed->Yes; notDisplayed->No)
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
	public boolean fGuiIsNotDisplayed(String strDesc){
    	try{
    		
    		//Get WebElement
    		WebElement webElement = getObject(strDesc);		
    		
    		//Check if the WebElement is displayed    		
    		boolean bNotIsDisplayed = false;	        
	        
	        int intCount = 1;    
	        
	        //Loop for around 10 secs to check whether object is being displayed
	        while (!(bNotIsDisplayed) && (intCount <=10))
	        {
	        	try {
	        		bNotIsDisplayed = !(webElement.isDisplayed());
	        		
        	    }
	        	catch (Exception e){
	        		Reporter.fnWriteToHtmlOutput("Check if element with description  " + strDesc + " is not displayed", "Exception occurred","Exception :" + e, "Fail");
	        		e.printStackTrace();
	    			return false;	    	    	
	    	    }	 
	        	
	        	
	        	//Sleep for a sec
	        	Thread.sleep(1000);
				intCount++;			
	        }
	
	        //Validate if the element should be displayed or not	        
        	if(bNotIsDisplayed){
        		Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is not displayed" , "Object should not be displayed", "Object is not displayed", "Pass");
	            return true;
        	}
        	else {
        		Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is not displayed" , "Object should not be displayed", "Object is Displayed", "Fail");
	            return false;
        	}       	
	       	        
    	}catch(Exception e){
    		Reporter.fnWriteToHtmlOutput("Check if element with description  " + strDesc + " is displayed", "Exception occurred","Exception :" + e, "Fail");
    		e.printStackTrace();
			return false;		
    	}    	
    }
	
	//*****************************************************************************************
    //*	Name		    : fGuiIsNotDisplayed
    //*	Description	    : Check if the object is displayed or not as per the choice
    //*	Author		    : Surbhi Shivhare
    //*	Input Params	: WebElement
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
	public boolean fGuiIsNotDisplayed(WebElement webElement){
		
		//Check if the WebElement is displayed    		
		boolean bNotIsDisplayed = false;	
		String strDesc = webElement.toString();
        
		//Counter
        int intCount = 1;  
        
        
        try{      
	        //Loop for around 10 secs to check whether object is being displayed
	        while (!(bNotIsDisplayed) && (intCount <=10))
	        {
	        	try {
	        		bNotIsDisplayed = !(webElement.isDisplayed());
	        		
        	    }
	        	catch (Exception e){
	        		Reporter.fnWriteToHtmlOutput("Check if element with description  " + strDesc + " is not displayed", "Exception occurred","Exception :" + e, "Fail");
	        		e.printStackTrace();
	    			return false;	    	    	
	    	    }	 
	        	
	        	
	        	//Sleep for a sec
	        	Thread.sleep(1000);
				intCount++;			
	        }
	
	        //Validate if the element should be displayed or not	        
        	if(bNotIsDisplayed){
        		Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is not displayed" , "Object should not be displayed", "Object is not displayed", "Pass");
	            return true;
        	}
        	else {
        		Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is not displayed" , "Object should not be displayed", "Object is Displayed", "Fail");
	            return false;
        	}       	
	       	        
    	}catch(Exception e){
    		Reporter.fnWriteToHtmlOutput("Check if element with description  " + strDesc + " is displayed", "Exception occurred","Exception :" + e, "Fail");
    		e.printStackTrace();
			return false;		
    	}    	
    }
	
	
	//*****************************************************************************************
    //*	Name		    : fGuiIsEnabled
    //*	Description	    : Check if the object is enabled or not
    //*	Author		    : Surbhi Shivhare
    //*	Input Params	: strDesc - The property of the object which is expected to be  enabled
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiIsEnabled(String strDesc){
    	try{
    		//Get WebElement
    		WebElement webElement = getObject(strDesc);
    		
    		//Check if the WebElement is Enabled
	        boolean bIsEnabled = false;
	        
	        int intCount = 1;    
	        
	        while (!(bIsEnabled) && (intCount <=10)){
	        	try {	        				
	        		bIsEnabled = webElement.isEnabled();
        	    }
	        	catch (Exception e){
	        		Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is enabled", "Exception occurred","Exception :" + e, "Fail");
	        		e.printStackTrace();
	    			return false;	    	    	
	    	    }   
	        	
	        	//Sleep for a sec, increment the counter
	        	Thread.sleep(1000);
				intCount++;			
	        }
	        
	        //Validate if the WebElement is Enabled
	        if (!(bIsEnabled)){
	        	Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is enabled", "Object should be enabled", "Object is not enabled", "Fail");
	            return false;
	        }
	        Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is enabled", "Object should be enabled", "Object is enabled", "Pass");
	        return true;
	        
    	}catch (Exception e) {
			Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is enabled","Exception Occured", "Exception: " + e, "Fail");
			return false;
		}  
    }	
    
  //*****************************************************************************************
    //*	Name		    : fGuiIsEnabled
    //*	Description	    : Check if the object is enabled or not
    //*	Author		    : Surbhi Shivhare
    //*	Input Params	: strDesc - The property of the object which is expected to be  enabled
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiIsEnabled(WebElement webElement){
    	
    	//Check if the WebElement is Enabled
        boolean bIsEnabled = false;
        String strDesc = webElement.toString();
        int intCount = 1;
    	
    	
    	try{
    	    		  	        
	        while (!(bIsEnabled) && (intCount <=10)){
	        	try {	        				
	        		bIsEnabled = webElement.isEnabled();
        	    }
	        	catch (Exception e){
	        		Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is enabled", "Exception occurred","Exception :" + e, "Fail");
	        		e.printStackTrace();
	    			return false;	    	    	
	    	    }   
	        	
	        	//Sleep for a sec, increment the counter
	        	Thread.sleep(1000);
				intCount++;			
	        }
	        
	        //Validate if the WebElement is Enabled
	        if (!(bIsEnabled)){
	        	Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is enabled", "Object should be enabled", "Object is not enabled", "Fail");
	            return false;
	        }
	        Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is enabled", "Object should be enabled", "Object is enabled", "Pass");
	        return true;
	        
    	}catch (Exception e) {
			Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is enabled","Exception Occured", "Exception: " + e, "Fail");
			return false;
		}  
    }	
    
  //*****************************************************************************************
    //*	Name		    : fGuiIsDisabled
    //*	Description	    : Check if the object is enabled or not
    //*	Author		    : Surbhi Shivhare
    //*	Input Params	: strDesc - The property of the object which is expected to be  enabled
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiIsDisabled(String strDesc){
    	try{
    		//Get WebElement
    		WebElement webElement = getObject(strDesc);
    		
    		//Check if the WebElement is Enabled
	        boolean bIsNotEnabled = false;
	        
	        int intCount = 1;    
	        
	        while (!(bIsNotEnabled) && (intCount <=10)){
	        	try {	        				
	        		bIsNotEnabled = !(webElement.isEnabled());
        	    }
	        	catch (Exception e){
	        		Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is disabled", "Exception occurred","Exception :" + e, "Fail");
	        		e.printStackTrace();
	    			return false;	    	    	
	    	    }   
	        	
	        	//Sleep for a sec, increment the counter
	        	Thread.sleep(1000);
				intCount++;			
	        }
	        
	        //Validate if the WebElement is Enabled
	        if (!(bIsNotEnabled)){
	        	Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is disabled", "Object should be disabled", "Object is not disabled", "Fail");
	            return false;
	        }
	        Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is disabled", "Object should be disabled", "Object is disabled", "Pass");
	        return true;
	        
    	}catch (Exception e) {
			Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is disabled","Exception Occured", "Exception: " + e, "Fail");
			return false;
		}  
    }
    
  //*****************************************************************************************
    //*	Name		    : fGuiIsDisabled
    //*	Description	    : Check if the object is enabled or not
    //*	Author		    : Surbhi Shivhare
    //*	Input Params	: strDesc - The property of the object which is expected to be  enabled
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiIsDisabled(WebElement webElement){
    	
    	//Check if the WebElement is Enabled
        boolean bIsNotEnabled = false;
        String strDesc = webElement.toString();
        int intCount = 1; 
    	
    	
    	try{    		    		  
	        
	        while (!(bIsNotEnabled) && (intCount <=10)){
	        	try {	        				
	        		bIsNotEnabled = !(webElement.isEnabled());
        	    }
	        	catch (Exception e){
	        		Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is disabled", "Exception occurred","Exception :" + e, "Fail");
	        		e.printStackTrace();
	    			return false;	    	    	
	    	    }   
	        	
	        	//Sleep for a sec, increment the counter
	        	Thread.sleep(1000);
				intCount++;			
	        }
	        
	        //Validate if the WebElement is Enabled
	        if (!(bIsNotEnabled)){
	        	Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is disabled", "Object should be disabled", "Object is not disabled", "Fail");
	            return false;
	        }
	        Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is disabled", "Object should be disabled", "Object is disabled", "Pass");
	        return true;
	        
    	}catch (Exception e) {
			Reporter.fnWriteToHtmlOutput("Check if object with description  " + strDesc + " is disabled","Exception Occured", "Exception: " + e, "Fail");
			return false;
		}  
    }
	

	//*****************************************************************************************
    //*	Name		    : fGuiClick
    //*	Description	    : Click on the passed object
    //*	Author		    : Anil Agarwal
    //*	Input Params	: strDesc - The description of the object to click
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiClick(String strDesc)
    {
    	try
    	{
        	//Initialize
    		WebElement objClick;
        	
        	//Call the function to get the webelement based on the description
        	objClick = getObject(strDesc);
        	
        	//if null is returned
        	if (objClick==null) return false;
        	
    		//Check if the object is enabled, if yes click the same
    		if (objClick.isEnabled())
    		{
    			//Click on the object
    			objClick.click();
    		}
    		else
    		{
        		Reporter.fnWriteToHtmlOutput("Check if object is enabled " + strDesc, "Object with description " + strDesc + " should be enabled", "Object is not enabled", "Fail");
        		return false;   			
    		}
            
            Reporter.fnWriteToHtmlOutput("Click object matching description " + strDesc, "Click operation should be successful", "Successfully clicked the object", "Done");
            return true;
    		
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		Reporter.fnWriteToHtmlOutput("Click object matching description " + strDesc, "Click operation failed", "Exception occured while click object", "Fail");
    		return false;
    	}
    }	
    
  //*****************************************************************************************
    //*	Name		    : fGuiClick
    //*	Description	    : Click on the passed object
    //*	Author		    : Anil Agarwal
    //*	Input Params	: strDesc - The description of the object to click
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiClick(WebElement objClick)
    {
    	try
    	{
        	
    		//Check if the object is enabled, if yes click the same
    		if (objClick.isEnabled())
    		{
    			//Click on the object
    			objClick.click();
    		}
    		else
    		{
        		//Reporter.fnWriteToHtmlOutput("Check if object is enabled " + objClick.toString(), "Object with description " + objClick.toString() + " should be enabled", "Object is not enabled", "Fail");
        		return false;   			
    		}
            
            //Reporter.fnWriteToHtmlOutput("Click object matching description " + objClick.toString(), "Click operation should be successful", "Successfully clicked the object", "Done");
            return true;
    		
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		Reporter.fnWriteToHtmlOutput("Click object matching description " + objClick.toString(), "Click operation failed", "Exception occured while click object", "Fail");
    		return false;
    	}
    }	

	//*****************************************************************************************
    //*	Name		    : fGuiEnterText
    //*	Description	    : Click on the passed object
    //*	Author		    : Anil Agarwal
    //*	Input Params	: WebElement - The webelement to click, strText = Text to enter
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiEnterText(String strDesc, String strText)
    {
    	try
    	{
        	WebElement objEdit;
        	
        	//Call the function to get the webelement based on the description
        	objEdit = getObject(strDesc);
        	
        	//if null is returned
        	if (objEdit==null) return false;
        	
    		//Check if the object is enabled, if yes click the same
    		if (objEdit.isEnabled())
    		{
    			//Enter the text in the edit box
    			objEdit.clear();
    			objEdit.sendKeys(strText);
    		}
    		else
    		{
        		Reporter.fnWriteToHtmlOutput("Check if object is enabled " + strDesc, "Object with description " + strDesc + " should be enabled", "Object is not enabled", "Fail");
        		return false;   			
    		}
            
            Reporter.fnWriteToHtmlOutput("Set value in object with description " + strDesc, "Value " + strText + " should be set in the edit box", "Value is set in the text field", "Done");
            return true;	
    	}
    	catch(Exception e)
    	{
    		Reporter.fnWriteToHtmlOutput("Set value in object with description " + strDesc, "Value " + strText + " should be set in the edit box", "Exception " + e + " occured while setting the value", "Fail");    		
    		return false;
    	}
    	
    }	
    
  //*****************************************************************************************
    //*	Name		    : fGuiEnterText
    //*	Description	    : Click on the passed object
    //*	Author		    : Anil Agarwal
    //*	Input Params	: WebElement - The webelement to click, strText = Text to enter
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiEnterText(WebElement objEdit, String strText)
    {
    	
    	String strDesc = objEdit.toString();
    	try
    	{        	
        	//if null is returned
        	if (objEdit==null) return false;
        	
    		//Check if the object is enabled, if yes click the same
    		if (objEdit.isEnabled())
    		{
    			//Enter the text in the edit box
    			objEdit.sendKeys(strText);
    		}
    		else
    		{
        		Reporter.fnWriteToHtmlOutput("Check if object is enabled " + strDesc, "Object with description " + strDesc + " should be enabled", "Object is not enabled", "Fail");
        		return false;   			
    		}
            
            Reporter.fnWriteToHtmlOutput("Set value in object with description " + strDesc, "Value " + strText + " should be set in the edit box", "Value is set in the text field", "Done");
            return true;	
    	}
    	catch(Exception e)
    	{
    		Reporter.fnWriteToHtmlOutput("Set value in object with description " + strDesc, "Value " + strText + " should be set in the edit box", "Exception " + e + " occured while setting the value", "Fail");    		
    		return false;
    	}
    	
    }	

       
    //*****************************************************************************************
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
    
    
    //*****************************************************************************************
    //*	Name		    : fGuiSelectOptionFromList
    //*	Description	    : Function to select the specified option from the list
    //*	Author		    : Anil Agarwal
    //*	Input Params	: None
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiSelectOptionFromList(String strDesc, String strText)
    {
    	try
    	{
        	WebElement objSelect;
        	
        	//Call the function to get the webelement based on the description
        	objSelect = getObject(strDesc);
        	
        	//if null is returned
        	if (objSelect==null) return false;
        	
        	//Check if the object is enabled, if yes click the same
    		if (objSelect.isEnabled())
    		{
    			//Set Select Element and select required value by text
            	Select select = new Select(objSelect);
            	select.selectByVisibleText(strText);    			
    		}
    		else
    		{
        		Reporter.fnWriteToHtmlOutput("Check if object is enabled " + strDesc, "Object with description " + strDesc + " should be enabled", "Object is not enabled", "Fail");
        		return false;   			
    		}
        	
        	
        	Thread.sleep(500);
        	Reporter.fnWriteToHtmlOutput("Select value from dropdown","Select value " + strText, "Value " + strText +" selected" , "Done");
        	return true;
    		
		} catch (Exception e)
		{
			Reporter.fnWriteToHtmlOutput("Select value from dropdown with description " + strDesc,"Select value " + strText, "Exception " + e + "occured while selecting the value" , "Fail");
			return false;
		}
    }
    
  //*****************************************************************************************
    //*	Name		    : fGuiSelectOptionFromList
    //*	Description	    : Function to select the specified option from the list
    //*	Author		    : Anil Agarwal
    //*	Input Params	: None
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiSelectOptionFromList(WebElement objSelect, String strText)
    {
    	String strDesc = objSelect.toString();
    	
    	try
    	{        	
        	//if null is returned
        	if (objSelect==null) return false;
        	
        	//Check if the object is enabled, if yes click the same
    		if (objSelect.isEnabled())
    		{
    			//Set Select Element and select required value by text
            	Select select = new Select(objSelect);
            	select.selectByVisibleText(strText);    			
    		}
    		else
    		{
        		Reporter.fnWriteToHtmlOutput("Check if object is enabled " + strDesc, "Object with description " + strDesc + " should be enabled", "Object is not enabled", "Fail");
        		return false;   			
    		}
        	
        	
        	Thread.sleep(500);
        	Reporter.fnWriteToHtmlOutput("Select value from dropdown","Select value " + strText, "Value " + strText +" selected" , "Done");
        	return true;
    		
		} catch (Exception e)
		{
			Reporter.fnWriteToHtmlOutput("Select value from dropdown with description " + strDesc,"Select value " + strText, "Exception " + e + "occured while selecting the value" , "Fail");
			return false;
		}
    }
    
	//*****************************************************************************************
    //*	Name		    : getObject
    //*	Description	    : Returns the webelement based on the description
    //*	Author		    : Anil Agarwal
    //*	Input Params	: objDesc - Description of the object
    //*	Return Values	: Webelement - Webelement based on the description
    //*****************************************************************************************		
    public WebElement getObject(String objDesc)
    {
        //Delimiters
        String[] delimiters = new String[] {":="};
        String[] arrFindByValues = objDesc.split(delimiters[0]);
        
        //Get Findby and Value 
        String FindBy = arrFindByValues[0];
        String val = arrFindByValues[1];

        try
        {
            //Handle all FindBy cases
        	String strElement = FindBy.toLowerCase();
        	if (strElement.equalsIgnoreCase("linktext"))
        	{
        		return driver.findElement(By.linkText(val));
        	}
        	else if (strElement.equalsIgnoreCase("partiallinktext"))
        	{
        		return driver.findElement(By.partialLinkText(val));
        	}
        	else if (strElement.equalsIgnoreCase("xpath"))
        	{
        		return driver.findElement(By.xpath(val));
        	}
        	else if (strElement.equalsIgnoreCase("name"))
        	{
        		return driver.findElement(By.name(val));
        	}
        	else if (strElement.equalsIgnoreCase("id"))
        	{
        		return driver.findElement(By.id(val));
        	}
        	else if (strElement.equalsIgnoreCase("classname"))
        	{
        		return driver.findElement(By.className(val));
        	}
        	else if (strElement.equalsIgnoreCase("cssselector"))
        	{
        		return driver.findElement(By.cssSelector(val));
        	}
        	else if (strElement.equalsIgnoreCase("tagname"))
        	{
        		return driver.findElement(By.tagName(val));
        	}
        	else if (strElement.equalsIgnoreCase("accessibility_id"))
        	{
        		return ((io.appium.java_client.AppiumDriver)driver).findElement(MobileBy.AccessibilityId(val));
        	}
        	else if (strElement.equalsIgnoreCase("appclassname"))
        	{
        		return ((io.appium.java_client.AppiumDriver)driver).findElement(By.className(val));
        	}
        	else if (strElement.equalsIgnoreCase("uiautomator"))
        	{
        		return ((AppiumDriver)driver).findElement(MobileBy.AndroidUIAutomator(val));
        	}
        	else
        	{
        		Reporter.fnWriteToHtmlOutput("Get object matching description " + objDesc, "Object should be found and returned", "Property "  + FindBy + " specified for object is invalid", "Fail");
        		System.out.println("Property name " + FindBy + " specified for object " + objDesc + " is invalid");
        		return null;
        	}
        	
        }
        
        //Catch Block
        catch(Exception e)
        {
        	Reporter.fnWriteToHtmlOutput("Get object matching description " + objDesc, "Object should be found and returned", "Unable to find required object", "Fail");
        	System.out.println("Exception " + e.toString() + " occured while fetching the object");
        	return null;
        }
       
    }   
    
  //*****************************************************************************************
    //*	Name		    : getObjects
    //*	Description	    : Returns the list of webelement based on the description
    //*	Author		    : Anil Agarwal
    //*	Input Params	: objDesc - Description of the object
    //*	Return Values	: Webelement - Webelement based on the description
    //*****************************************************************************************		
    public List<WebElement> getObjects(String objDesc)
    {
        //Delimiters
        String[] delimiters = new String[] {":="};
        String[] arrFindByValues = objDesc.split(delimiters[0]);
        
        //Get Findby and Value 
        String FindBy = arrFindByValues[0];
        String val = arrFindByValues[1];

        try
        {
            //Handle all FindBy cases
        	String strElement = FindBy.toLowerCase();
        	if (strElement.equalsIgnoreCase("linktext"))
        	{
        		return driver.findElements(By.linkText(val));
        	}
        	else if (strElement.equalsIgnoreCase("partiallinktext"))
        	{
        		return driver.findElements(By.partialLinkText(val));
        	}
        	else if (strElement.equalsIgnoreCase("xpath"))
        	{
        		return driver.findElements(By.xpath(val));
        	}
        	else if (strElement.equalsIgnoreCase("name"))
        	{
        		return driver.findElements(By.name(val));
        	}
        	else if (strElement.equalsIgnoreCase("id"))
        	{
        		return driver.findElements(By.id(val));
        	}
        	else if (strElement.equalsIgnoreCase("classname"))
        	{
        		return driver.findElements(By.className(val));
        	}
        	else if (strElement.equalsIgnoreCase("cssselector"))
        	{
        		return driver.findElements(By.cssSelector(val));
        	}
        	else if (strElement.equalsIgnoreCase("tagname"))
        	{
        		return driver.findElements(By.tagName(val));
        	}
        	else if (strElement.equalsIgnoreCase("accessibility_id"))
        	{
        		return ((io.appium.java_client.AppiumDriver)driver).findElements(MobileBy.AccessibilityId(val));
        	}
        	else if (strElement.equalsIgnoreCase("appclassname"))
        	{
        		return ((io.appium.java_client.AppiumDriver)driver).findElements(By.className(val));
        	}
        	else
        	{
        		Reporter.fnWriteToHtmlOutput("Get objects matching description " + objDesc, "Object List should be found and returned", "Property "  + FindBy + " specified for object is invalid", "Fail");
        		System.out.println("Property name " + FindBy + " specified for objects " + objDesc + " is invalid");
        		return null;
        	}
        	
        }
        
        //Catch Block
        catch(Exception e)
        {
        	Reporter.fnWriteToHtmlOutput("Get objects matching description " + objDesc, "Object should be found and returned", "Unable to find required object", "Fail");
        	System.out.println("Exception " + e.toString() + " occured while fetching the object");
        	return null;
        }
       
    }   
    
  //*****************************************************************************************
    //*	Name		    : getChildObject
    //*	Description	    : Returns the child element of webelement based on the description
    //*	Author		    : Aniket Gadre    
    //*	Input Params	: objParent - Parent WebElement
    //*					: objDesc - Description of the object
    //*	Return Values	: Webelement - Webelement based on the description
    //*****************************************************************************************		
    public WebElement getChildObject(WebElement parentElem, String objDesc)
    {
        //Delimiters
        String[] delimiters = new String[] {":="};
        String[] arrFindByValues = objDesc.split(delimiters[0]);
        
        //Get Findby and Value 
        String FindBy = arrFindByValues[0];
        String val = arrFindByValues[1];

        try
        {
            //Handle all FindBy cases
        	String strElement = FindBy.toLowerCase();
        	if (strElement.equalsIgnoreCase("linktext"))
        	{
        		return parentElem.findElement(By.linkText(val));
        	}
        	else if (strElement.equalsIgnoreCase("partiallinktext"))
        	{
        		return parentElem.findElement(By.partialLinkText(val));
        	}
        	else if (strElement.equalsIgnoreCase("xpath"))
        	{
        		return parentElem.findElement(By.xpath(val));
        	}
        	else if (strElement.equalsIgnoreCase("name"))
        	{
        		return parentElem.findElement(By.name(val));
        	}
        	else if (strElement.equalsIgnoreCase("id"))
        	{
        		return parentElem.findElement(By.id(val));
        	}
        	else if (strElement.equalsIgnoreCase("classname"))
        	{
        		return parentElem.findElement(By.className(val));
        	}
        	else if (strElement.equalsIgnoreCase("cssselector"))
        	{
        		return parentElem.findElement(By.cssSelector(val));
        	}
        	else if (strElement.equalsIgnoreCase("tagname"))
        	{
        		return parentElem.findElement(By.tagName(val));
        	}
        	else
        	{	
        		Reporter.fnWriteToHtmlOutput("Get child object matching description " + objDesc, "Object should be found and returned", "Property "  + FindBy + " specified for object is invalid", "Fail");
        		System.out.println("Property name " + FindBy + " specified for object " + objDesc + " is invalid");
        		return null;
        	}        	
        }
        
        //Catch Block
        catch(Exception e)
        {
        	Reporter.fnWriteToHtmlOutput("Get child object matching description " + objDesc, "Object should be found and returned", "Unable to find required object", "Fail");
        	System.out.println("Exception " + e.toString() + " occured while fetching the object");
        	return null;
        }
       
    }   
    
    
    //*****************************************************************************************
    //*	Name		    : fGuiCheckCheckBox
    //*	Description	    : Checks the required checkbox if its not checked already
    //*	Author		    : Aniket Gadre
    //*	Input Params	: strDesc - The description of the object to click
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiCheckCheckBox(String strDesc)
    {
    	try
    	{
        	//Initialize
    		WebElement objChkBox;
        	
        	//Call the function to get the webelement based on the description
    		objChkBox = getObject(strDesc);        	        
        	
        	//if null is returned
        	if (objChkBox==null) return false;
        	        	
    		//Check if the object is enabled, if yes click the same
    		if (objChkBox.isEnabled())
    		{
    			//Check state of check box
            	boolean isChecked = objChkBox.isSelected();
            	
            	//Check if Not Checked
            	if(isChecked == false) objChkBox.click();
            	
    		}
    		else
    		{
			if(objChkBox.isSelected()) {
    				Reporter.fnWriteToHtmlOutput("Click object matching description " + strDesc, "Check operation should be successful", "Object is disabled and already checked", "Done");
    				return true;
    			}
        		Reporter.fnWriteToHtmlOutput("Check if object is enabled " + strDesc, "Object with description " + strDesc + " should be enabled", "Object is not enabled", "Fail");
        		return false;   			
    		}
            
            Reporter.fnWriteToHtmlOutput("Click object matching description " + strDesc, "CHeck operation should be successful", "Successfully checked the checkbox", "Done");
            return true;
    		
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		Reporter.fnWriteToHtmlOutput("Click object matching description " + strDesc, "Checking checkbox operation failed", "Exception occured while checking check box", "Fail");
    		return false;
    	}
    }
    
  //*****************************************************************************************
    //*	Name		    : fGuiCheckCheckBox
    //*	Description	    : Checks the required checkbox if its not checked already
    //*	Author		    : Aniket Gadre
    //*	Input Params	: WebElement - The description of the object to click
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiCheckCheckBox(WebElement objChkBox)
    {
    	String strDesc = objChkBox.toString();
    	
    	try
    	{
        	       		
        	//if null is returned
        	if (objChkBox==null) return false;
        	        	
    		//Check if the object is enabled, if yes click the same
    		if (objChkBox.isEnabled())
    		{
    			//Check state of check box
            	boolean isChecked = objChkBox.isSelected();
            	
            	//Check if Not Checked
            	if(isChecked == false) objChkBox.click();
            	
    		}
    		else
    		{
        		Reporter.fnWriteToHtmlOutput("Check if object is enabled " + strDesc, "Object with description " + strDesc + " should be enabled", "Object is not enabled", "Fail");
        		return false;   			
    		}
            
            Reporter.fnWriteToHtmlOutput("Click object matching description " + strDesc, "CHeck operation should be successful", "Successfully checked the checkbox", "Done");
            return true;
    		
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		Reporter.fnWriteToHtmlOutput("Click object matching description " + strDesc, "Checking checkbox operation failed", "Exception occured while checking check box", "Fail");
    		return false;
    	}
    }
    
    //*****************************************************************************************
    //*	Name		    : fGuiUncheckCheckBox
    //*	Description	    : Unchecks the required check box if its  checked already
    //*	Author		    : Aniket Gadre
    //*	Input Params	: strDesc - The description of the object to click
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiUncheckCheckBox(String strDesc)
    {
    	try
    	{
        	//Initialize
    		WebElement objChkBox;
        	
        	//Call the function to get the webelement based on the description
    		objChkBox = getObject(strDesc);        	        
        	
        	//if null is returned
        	if (objChkBox==null) return false;
        	        	
    		//Check if the object is enabled, if yes click the same
    		if (objChkBox.isEnabled())
    		{
    			//Check state of check box
    			boolean isChecked = objChkBox.isSelected();
            	
            	//Check if Not Checked
            	if(isChecked == true) objChkBox.click();
            	
    		}
    		else
    		{
        		Reporter.fnWriteToHtmlOutput("Check if object is enabled " + strDesc, "Object with description " + strDesc + " should be enabled", "Object is not enabled", "Fail");
        		return false;   			
    		}
            
            Reporter.fnWriteToHtmlOutput("Click object matching description " + strDesc, "Uncheck operation should be successful", "Successfully unchecked the checkbox", "Done");
            return true;
    		
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		Reporter.fnWriteToHtmlOutput("Click object matching description " + strDesc, "Unchecking checkbox operation failed", "Exception occured while unchecking check box", "Fail");
    		return false;
    	}
    }	
    
    //*****************************************************************************************
    //*	Name		    : fGuiUncheckCheckBox
    //*	Description	    : Unchecks the required check box if its  checked already
    //*	Author		    : Aniket Gadre
    //*	Input Params	: strDesc - The description of the object to click
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiUncheckCheckBox(WebElement objChkBox)
    {
    	String strDesc = objChkBox.toString();
    	
    	try
    	{
                	
        	//if null is returned
        	if (objChkBox==null) return false;
        	        	
    		//Check if the object is enabled, if yes click the same
    		if (objChkBox.isEnabled())
    		{
    			//Check state of check box
    			boolean isChecked = objChkBox.isSelected();
            	
            	//Check if Not Checked
            	if(isChecked == true) objChkBox.click();
            	
    		}
    		else
    		{
        		Reporter.fnWriteToHtmlOutput("Check if object is enabled " + strDesc, "Object with description " + strDesc + " should be enabled", "Object is not enabled", "Fail");
        		return false;   			
    		}
            
            Reporter.fnWriteToHtmlOutput("Click object matching description " + strDesc, "Uncheck operation should be successful", "Successfully unchecked the checkbox", "Done");
            return true;
    		
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		Reporter.fnWriteToHtmlOutput("Click object matching description " + strDesc, "Unchecking checkbox operation failed", "Exception occured while unchecking check box", "Fail");
    		return false;
    	}
    }	
    
    
    //*****************************************************************************************
    //*	Name		    : setGeoLocation
    //*	Description	    : Checks whether required listing detail is displayed
    //*	Author		    : Aniket Gadre
    //*	Input Params	: None
    //*	Return Values	: None
    //*****************************************************************************************
	public boolean setGeoLocation(String lat, String lon)
	{
		try
		{
			String Script = "window.navigator.geolocation.getCurrentPosition =  function(success){var position = {'coords' : {'latitude': '" + lat + "', 'longitude': '" + lon + "'}}; success(position);}";
			
			//Update geolocation
			JavascriptExecutor js = (JavascriptExecutor)driver;
			Object[] args = {null};
			js.executeScript(Script, args);
		}
		catch(Exception e)
		{
			Reporter.fnWriteToHtmlOutput("Set geo location", "Should be set successfully", "Setting geo location failed. Exception " + e, "Fail");
			return false;
		}
		//return
		return true;
	}
	
	//*****************************************************************************************
    //*	Name		    : getCurrentBrowser
    //*	Description	    : Checks whether required listing detail is displayed
    //*	Author		    : Aniket Gadre
    //*	Input Params	: None
    //*	Return Values	: None
    //*****************************************************************************************
	public String getCurrentBrowser()
	{
		try
		{
			Capabilities DC = ((RemoteWebDriver)driver).getCapabilities();
			return DC.getBrowserName();
		}
		catch(Exception e)
		{
			Reporter.fnWriteToHtmlOutput("Get browser name", "Should return Browser Name", "Fetching Browser Name Failed. Exception " + e, "Fail");
			return "";
		}
	}
	
	//*****************************************************************************************
    //*	Name		    : switchToWindowWithName
    //*	Description	    : Checks whether required listing detail is displayed
    //*	Author		    : Aniket Gadre
    //*	Input Params	: None
    //*	Return Values	: None
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
    //*	Name		    : fverifyCheckBoxIsChecked
    //*	Description	    : Checks the required checkbox if its not checked already
    //*	Author		    : Aniket Gadre
    //*	Input Params	: strDesc - The description of the object to click
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fverifyCheckBoxIsChecked(String strDesc)
    {
    	try
    	{
        	//Initialize
    		WebElement objChkBox;
        	
        	//Call the function to get the webelement based on the description
    		objChkBox = getObject(strDesc);        	        
        	
        	//if null is returned
        	if (objChkBox==null) return false;
        	        	
    		//Check if the object is Checked, if yes click the same
    		if (objChkBox.isSelected())
    		{
    			 //Reporter.fnWriteToHtmlOutput("Check is checkbox is checked with description " + strDesc, "CHeckbox should be  checked", "Checkbox is checked", "Done");
    	         return true;
            	
    		}
    		else
    		{
        		//Reporter.fnWriteToHtmlOutput("Check if object is enabled " + strDesc, "Object with description " + strDesc + " should be enabled", "Object is not enabled", "Fail");
        		return false;   			
    		}
            
            
    		
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		Reporter.fnWriteToHtmlOutput("Verify is checkbox is checked with description " + strDesc, "Verifying checkbox is checked operation failed", "Exception occured while checking check box", "Fail");
    		return false;
    	}
    }	
    
    //*****************************************************************************************
    //*	Name		    : fverifyCheckBoxIsUnchecked
    //*	Description	    : Checks the required checkbox if its not checked already
    //*	Author		    : Aniket Gadre
    //*	Input Params	: strDesc - The description of the object to click
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fverifyCheckBoxIsUnchecked(String strDesc)
    {
    	try
    	{
        	//Initialize
    		WebElement objChkBox;
        	
        	//Call the function to get the webelement based on the description
    		objChkBox = getObject(strDesc);        	        
        	
        	//if null is returned
        	if (objChkBox==null) return false;
        	        	
    		//Check if the object is Checked, if yes click the same
    		if (!objChkBox.isSelected())
    		{
    			 //Reporter.fnWriteToHtmlOutput("Check is checkbox is checked with description " + strDesc, "CHeckbox should be  checked", "Checkbox is checked", "Done");
    	         return true;            
    		}
    		else
    		{
        		//Reporter.fnWriteToHtmlOutput("Check if object is enabled " + strDesc, "Object with description " + strDesc + " should be enabled", "Object is not enabled", "Fail");
        		return false;   			
    		}                    
    		
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		Reporter.fnWriteToHtmlOutput("Verify is checkbox is unchecked with description " + strDesc, "Verifying checkbox is checked operation failed", "Exception occured while checking check box", "Fail");
    		return false;
    	}
    }
	
	//*****************************************************************************************
    //*	Name		    : returnTimeStamp
    //*	Description	    : returns current time stamp
    //*	Author		    : Aniket Gadre
    //*	Input Params	: strDesc - The description of the object to click
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public String returnTimeStamp()
    {
    	java.util.Date today = new java.util.Date();
    	Timestamp now = new java.sql.Timestamp(today.getTime());
        String timeStamp = now.toString().replaceAll(":", "").replaceAll("-", "").replaceAll(" ", "");
        timeStamp = timeStamp.split("\\.")[0];
        return timeStamp;
    }
    
    //*****************************************************************************************
    //*	Name		    : returnRandomString()
    //*	Description	    : returns current time stamp
    //*	Author		    : Aniket Gadre
    //*	Input Params	: strDesc - The description of the object to click
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public String returnRandomString()
    {
    	//Get time stamp
        String timeStamp = returnTimeStamp();
        String Random = "";
        
        int z;

        int iLen = timeStamp.length();
        for(int i=0;i<iLen;i++)
        {
        	z = (int)(timeStamp.substring(i, i+1).toCharArray()[0]) - 48 + 97;
      
        	Random = Random + (char)z;
        }
        
        
        return Random;
    }
    
    
    //*****************************************************************************************
    //*	Name		    : fGetCurrentActivity
    //*	Description	    : returns current time stamp
    //*	Author		    : Aniket Gadre
    //*	Input Params	: strDesc - The description of the object to click
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public String fGetCurrentActivity()
    {
    	return ((AppiumDriver)driver).currentActivity();
    }
    
    
  //*****************************************************************************************
    //*	Name		    : fValidateCurrentActivity
    //*	Description	    : returns current time stamp
    //*	Author		    : Aniket Gadre
    //*	Input Params	: strDesc - The description of the object to click
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fValidateCurrentActivity(String expectedActivity)
    {
    	int i = 0;
    	String actualActivity="";
    	
    	//Loop for activity
    	while(i<30)
    	{
    		
    		actualActivity = ((AppiumDriver)driver).currentActivity();
    		if(actualActivity.equals(expectedActivity)) break;
    		
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
    		
    		i++;
    	}
    	
    	//Check Counter value
    	if(i==30) {
    		
    		Reporter.fnWriteToHtmlOutput("Check if Activity " + expectedActivity + " is opened", "Activity should be invoked successfully" , "Activity " + actualActivity +  " opened", "Fail");
    		return false;
    	}
    	else {
    		
    		Reporter.fnWriteToHtmlOutput("Check if Activity " + expectedActivity + " is opened", "Activity should be invoked successfully" , "Activity opened successfully", "Pass");
    		return true;
    	}
    }
    
    
    //*****************************************************************************************
    //*	Name		    : fAndroidRotateScreen
    //*	Description	    : returns current time stamp
    //*	Author		    : Aniket Gadre
    //*	Input Params	: strDesc - The description of the object to click
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fAndroidRotateScreen(String Orientation)
    {
    	
    	String strOrientation = "";
    	
    	try{
    		if (Orientation.equalsIgnoreCase("L")){
        		((AppiumDriver)driver).rotate(ScreenOrientation.LANDSCAPE);
        		strOrientation = "Landscape";
        	}
        	else {
        		((AppiumDriver)driver).rotate(ScreenOrientation.PORTRAIT);
        		strOrientation = "Portrait";
        	}
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		Reporter.fnWriteToHtmlOutput("Rotate Screen", "Set screen orientation to " + strOrientation, "Orientation Setting Failed", "Fail");
    		return false;
    	}    	    	    
    	
    	try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	Reporter.fnWriteToHtmlOutput("Rotate Screen", "Set screen orientation to " + strOrientation, "Orientation Set Successfully", "Pass");
    	return true;
   }     
    
    
  //*****************************************************************************************
    //*	Name		    : fMaximizeWindow
    //*	Description	    : returns current time stamp
    //*	Author		    : Aniket Gadre
    //*	Input Params	: strDesc - The description of the object to click
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fMaximizeWindow()
    {
    	
    	try{
    		driver.manage().window().maximize();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}    	    	    
    	
    	return true;
   }      
    
    
    
}
