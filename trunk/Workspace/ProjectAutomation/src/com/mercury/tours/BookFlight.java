package com.mercury.tours;

import java.util.Dictionary;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import com.amdocs.asap.*;

public class BookFlight {
	
	
	private Reporting Reporter;
	private WebDriver driver;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions objCommon;
	
	public String edtCreditNumber = "Xpath:=//input[@name=\"creditnumber\"]";
    public String webbtnBuyFlights = "Name:=buyFlights";
    
	public BookFlight(WebDriver GDriver,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
	{
		Reporter = GReporter;
		driver = GDriver;
		Dictionary = GDictionary;
		Environment = GEnvironment;
		objCommon = new CommonFunctions(driver, Reporter);
	}
	
	//CommonFunctions objCommon = new CommonFunctions(driver, Reporter);
	
	public String getTitle()
	{
		return driver.getTitle();
	}
	
	public FlightConfirmation bookFlight()
	{
		
        String edtPsgnrFirstName, edtPsgnrLastName;
        
        //Enter Booking Details
        int PsngrCnt = Integer.parseInt(Dictionary.get("PASSENGERCOUNT")); 

        for (int iCnt = 1; iCnt <= PsngrCnt; iCnt++)
        {
            //Enter Passenger names
            edtPsgnrFirstName = "Xpath:=//input[@name=\"passFirst" + Integer.toString(iCnt - 1) + "\"]";
            edtPsgnrLastName = "Xpath:=//input[@name=\"passLast" + Integer.toString(iCnt - 1) + "\"]";
            //Enter passenger first name
            if (objCommon.fGuiEnterText(edtPsgnrFirstName, Dictionary.get("PSNGR_FIRSTNAME_" + iCnt))==false)
            {
            	return null;
            }
            //Enter passenger Last name
            if (objCommon.fGuiEnterText(edtPsgnrLastName, Dictionary.get("PSNGR_LASTNAME_" + iCnt))==false)
            {
            	return null;
            }
        }

        //Enter Credit Card Number
        if (objCommon.fGuiEnterText(edtCreditNumber, Dictionary.get("CREDITCARDNO"))==false)
        {
        	return null;
        }                  
        
      
        //function all to click Reserve Flight button
        if (objCommon.fGuiClick(webbtnBuyFlights)==false)
        {
        	return null;
       	}
		return new FlightConfirmation(driver,Dictionary,Environment,Reporter);
	}
	
	
}
