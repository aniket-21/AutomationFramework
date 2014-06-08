package com.mercury.tours;

import java.util.Dictionary;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import com.amdocs.asap.*;


public class SelectFlight {
	
	
	private Reporting Reporter;
	private WebDriver driver;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions objCommon;
	
	public String webbtnReserveFlights = "Xpath:=//input[@name=\"reserveFlights\"]";
	//Define the constructor
	public SelectFlight()
	{
		Reporter = Global.Reporter;
		driver = Global.webDriver;
		Dictionary = Global.Dictionary;
		Environment = Global.Environment;
	}
	//CommonFunctions objCommon = new CommonFunctions(driver, Reporter);
	
	public String getTitle()
	{
		return driver.getTitle();
	}
	
	public BookFlight selectFlight()
	{
		//Decide Flights
        String webradbtnDepartFlight = "Xpath:=//input[@value=\"" + Dictionary.get("DEPARTFLIGHT") + "$" + Dictionary.get("DEPARTFLIGHTNO") + "$" + Dictionary.get("DEPARTPRICE") + "$" + Dictionary.get("DEPARTTIME") + "\"]";
        String webradbtnReturnFlight = "Xpath:=//input[@value=\"" + Dictionary.get("RETURNFLIGHT") + "$" + Dictionary.get("RETURNFLIGHTNO") + "$" + Dictionary.get("RETURNPRICE") + "$" + Dictionary.get("RETURNTIME") + "\"]";
		//call function to select Depart flight radio button
        if (objCommon.fGuiClick(webradbtnDepartFlight)==false)
        {
        	return null;
       	}
		//call function to select Return flight radio button      
        if (objCommon.fGuiClick(webradbtnReturnFlight)==false)
        {
        	return null;
       	}
        
        Reporter.fnWriteToHtmlOutput("Select Flight", "Select a Flight", "Flight Selected successfully", "Pass");
        //function all to click Reserve Flight button
        if (objCommon.fGuiClick(webbtnReserveFlights)==false)
        {
        	return null;
       	}

		return new BookFlight();
	}
	
	
}
