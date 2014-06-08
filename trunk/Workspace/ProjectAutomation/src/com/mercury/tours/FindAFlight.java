package com.mercury.tours;

import java.util.Dictionary;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import com.amdocs.asap.*;


public class FindAFlight {
	
	
	private Reporting Reporter;
	private WebDriver driver;
	private HashMap<String, String> Dictionary;
	private HashMap<String, String> Environment;
	private CommonFunctions objCommon = new CommonFunctions();
	
	//Define the constructor
	public FindAFlight()
	{
		Reporter = Global.Reporter;
		driver = Global.webDriver;
		Dictionary = Global.Dictionary;
		Environment = Global.Environment;
	}

	
	public String webradbtnRoundTrip = "Xpath:=//input[@value=\"roundtrip\"]";
	public String webradbtnOneWay ="Xpath:=//input[@value=\"oneway\"]";
	public String weblstPassengerCnt = "Name:=passCount";
	public String weblstDepartingFrom = "Name:=fromPort";
	public String weblstArrivingTo = "Name:=toPort";
	public String weblstDepartingMonth = "Name:=fromMonth";
	public String weblstDepartingDay = "Name:=fromDay";
	public String weblstArrivingMonth = "Name:=toMonth";
	public String weblstArrivingDay = "Name:=toDay";
	public String webradbtnEconomyClass = "Xpath:=//input[@value=\"Coach\"]";
	public String webradbtnBusinessClass = "Xpath:=//input[@value=\"Business\"]";
	public String webradbtnFirstClass = "Xpath:=//input[@value=\"First\"]";
	public String weblstAirline = "Name:=airline";
	public String webbtnFindFlights = "Xpath:=//input[@name=\"findFlights\"]";	
	
	public String getTitle()
	{
		return driver.getTitle();
	}
	
	
	public SelectFlight findFlight()
	{
        //Select Flight Details to Search for the Flight
		if (objCommon.fGuiClick(webradbtnOneWay)==false)
		{
			return null;
		}
		//Select passenger count
		if (objCommon.fGuiSelectOptionFromList(weblstPassengerCnt, Dictionary.get("PASSENGERCOUNT"))==false)
		{
			return null;
		}

        //Departing From     
		if (objCommon.fGuiSelectOptionFromList(weblstDepartingFrom, Dictionary.get("DEPARTINGFROM"))==false)
		{
			return null;
		}
        //Departing Month		
		if (objCommon.fGuiSelectOptionFromList(weblstDepartingMonth, Dictionary.get("DEPARTINGMONTH"))==false)
		{
			return null;
		}
        //Departing Day		
		if (objCommon.fGuiSelectOptionFromList(weblstDepartingDay, Dictionary.get("DEPARTINGDAY"))==false)
		{
			return null;
		}

        //Arrival To     
		if (objCommon.fGuiSelectOptionFromList(weblstArrivingTo, Dictionary.get("ARRIVINGTO"))==false)
		{
			return null;
		}
        //Arrival Month		
		if (objCommon.fGuiSelectOptionFromList(weblstArrivingMonth, Dictionary.get("ARRIVINGMONTH"))==false)
		{
			return null;
		}
        //Arrival Day		
		if (objCommon.fGuiSelectOptionFromList(weblstArrivingDay, Dictionary.get("ARRIVINGDAY"))==false)
		{
			return null;
		}

        //Select Radio button
		if (objCommon.fGuiClick(webradbtnFirstClass)==false)
		{
			return null;
		}
		//Selecting airlines
		if (objCommon.fGuiSelectOptionFromList(weblstAirline, Dictionary.get("AIRLINES"))==false)
		{
			return null;
		}
		
        //Click Find Flights
		if (objCommon.fGuiClick(webbtnFindFlights)==false)
		{
			return null;
		}
	
		return new SelectFlight();
	}
}
