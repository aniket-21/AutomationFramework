package com.consors.web;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.amdocs.asap.CommonFunctions;
import com.amdocs.asap.Global;
import com.amdocs.asap.Reporting;

public class CheckingAccountDetails {

		private Reporting Reporter;
		private WebDriver driver;
		private HashMap<String, String> Dictionary;
		private HashMap<String, String> Environment;
		private CommonFunctions objCommon;
		
		//Objects
		public String edtCreditNumber = "Xpath:=//input[@name=\"creditnumber\"]";	
	    public String rdbtnGenderMale = "Xpath:=//span[@class=\"ev-formfieldradiobutton-label-text\" and text()=\"Herr\"]";   
	    public String rdbtnGenderFemale = "Xpath:=//span[@class=\"ev-formfieldradiobutton-label-text\" and text()=\"Freu\"]";		
	    public String edtFirstName = "name:=personData.firstname";
	    public String edtLastName = "id:=personData_lastname";
	    public String edtBirthName = "name:=personData.birthname";
	    public String edtDayOfBirth = "name:=personData.dayOfBirth";
	    public String edtMonthOfBirth = "name:=personData.monthOfBirth";
	    public String edtYearOfBirth = "name:=personData.yearOfBirth";
	    public String edtBirthPlace = "name:=personData.birthplace";
	    public String lstBirthCountry = "name:=personData.countryCodeOfBirth";
	    public String lstMaritialStatus = "name:=personData.maritalStatus";
	    public String lstNationalityCode = "name:=personData.nationalityCode";
	    public String rdbtnNoUSCitizen = "id:=us-citizenship-false";	    
	    public String rdbtnNoTaxLiability = "Xpath:=.//input[@id='us-taxliability-true']/../span[@class='ev-formfieldradiobutton-label-text']";
	    public String rdbtnYesTaxLiability = "Xpath:=.//input[@id='us-taxliability-false']/../span[@class='ev-formfieldradiobutton-label-text']";
	    
	    
	    //Address details 	    
	    public String edtStreet = "name:=customerAddress.street";
	    public String edtPostalCode = "name:=customerAddress.postalCode";
	    public String edtCity = "name:=customerAddress.city";
	    public String LstCountry= "name:=customerAddress.countryCode";
	    
	    //Contact Details 
	    public String edtEmailID= "name:=contactData.email";
	    public String edtPhoneNumber= "name:=contactData.phoneNumberPrivate";
	    
	    //Professional Details 
	    public String lstProfession= "name:=professionalSituation.profession";
	    public String lstLineofBusiness= "name:=professionalSituation.lineOfBusiness";
	    public String lstNetIncomeYear= "name:=professionalSituation.netIncomeYear";
	    public String lstLiquidity= "name:=professionalSituation.liquidity";
	    public String lstNetIncomeMonth= "name:=professionalSituation.netIncomeMonth";
	    public String edtOtherIncome= "name:=professionalSituation.otherIncome";
	    public String edtTANProcedure1= "name:=tanProcedure.phoneNumber1";
	    public String edtTANProcedure2= "name:=tanProcedure.phoneNumber2";
		
	    
	    public CheckingAccountDetails(WebDriver GDriver,HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment,Reporting GReporter)
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
		
		
		//Enter the Personal Details 
		//*****************************************************************************************
	    //*    Name        	: enterPersonalDetails
	    //*    Description 	: This function enters Personal details
	    //*    Author       : Bharat Joshi
	    //*    Input Params : None
	    //*    Return Values: None
	    //*****************************************************************************************
		public boolean enterPersonalDetails()
		{             
			//Click on link Gender
			
			if (Dictionary.get("GENDER").equalsIgnoreCase("MALE"))
			{
				if (objCommon.fGuiClick(rdbtnGenderMale)==false)
				{
						return false;
				}
			}
			else
			{
				if (objCommon.fGuiClick(rdbtnGenderFemale)==false)
				{
						return false;
				}
			}
			 
			 //Enter the First Name
			if (objCommon.fGuiEnterText(edtFirstName,Dictionary.get("FIRST_NAME"))==false)
	        {
	        	return false;
	        }    
			 
			 //Enter the Last Name
			if (objCommon.fGuiEnterText(edtLastName,Dictionary.get("LAST_NAME"))==false)
	        {
	        	return false;
	        } 
			
			//Enter the Birth Name
			if (objCommon.fGuiEnterText(edtBirthName,Dictionary.get("BIRTH_NAME"))==false)
	        {
	        	return false;
	        } 
		
			//Enter the Day of Birth 
			WebElement objDOB = objCommon.getObject(edtDayOfBirth);
			if (objDOB==null) return false;
			else objDOB.sendKeys(Dictionary.get("DAY_OF_BIRTH"));
			
			//Enter the Month of Birth 
			WebElement objMOB = objCommon.getObject(edtMonthOfBirth);
			if (objMOB==null) return false;
			else objMOB.sendKeys(Dictionary.get("MONTH_OF_BIRTH"));
			
			//Enter the Day of Birth 
			WebElement objYOB = objCommon.getObject(edtYearOfBirth);
			if (objYOB==null) return false;
			else objYOB.sendKeys(Dictionary.get("YEAR_OF_BIRTH"));
			
					
			//Enter the Place of Birth 
			if (objCommon.fGuiEnterText(edtBirthPlace,Dictionary.get("PLACE_OF_BIRTH"))==false)
	        {
	        	return false;
	        } 
			
			// Enter the Country of birth
			if (objCommon.fGuiSelectOptionFromList(lstBirthCountry, Dictionary.get("COUNTRY_OF_BIRTH"))==false)
			{
				return false;
			}			
						
			// Select the Martial  Status
			if (objCommon.fGuiSelectOptionFromList(lstMaritialStatus, Dictionary.get("MARTIAL_STATUS"))==false)
			{
				return false;
			}	
			
			//Select the Nationality Code
			if (objCommon.fGuiSelectOptionFromList(lstNationalityCode, Dictionary.get("NATIONALITY_CODE"))==false)
			{
				return false;
			}	
			
			//Click on No US Citizen
			/*if (objCommon.fGuiClick(rdbtnNoUSCitizen)==false)
		    {
				 return false;
		    }*/
			
			//Click on No Tax Liability
			if (Dictionary.get("TAX_LIABILITY").equalsIgnoreCase("Yes"))
			{
				if (objCommon.fGuiClick(rdbtnYesTaxLiability)==false)
				{
					return false;
				}
				else
				{	
				Reporter.fnWriteToHtmlOutput("Enter Personal Details","Personal details should be Entered", "Personal Details entered Successfully", "Pass");
				}
			}
			else
			{
				if (objCommon.fGuiClick(rdbtnNoTaxLiability)==false)
				{
					return false;
				}
				
			}
			return true;
		}
		
	
		//*****************************************************************************************
	    //*    Name        	: enterAddressDetails
	    //*    Description 	: This function enters Address details
	    //*    Author       : Bharat Joshi
	    //*    Input Params : None
	    //*    Return Values: None
	    //*****************************************************************************************
		public boolean enterAddressDetails()
		{
			//Enter the Street
			if (objCommon.fGuiEnterText(edtStreet,Dictionary.get("STREET"))==false)
	        {
	        	return false;
	        } 
			
			//Enter Postal Code
			if (objCommon.fGuiEnterText(edtPostalCode,Dictionary.get("POSTAL_CODE"))==false)
	        {
	        	return false;
	        } 
			
			//Enter City 
			if (objCommon.fGuiEnterText(edtCity,Dictionary.get("CITY"))==false)
	        {
	        	return false;
	        } 
			
			//Select the Country 
			if (objCommon.fGuiSelectOptionFromList(LstCountry, Dictionary.get("COUNTRY"))==false)
			{
				return false;
			}
			else
			{				
				Reporter.fnWriteToHtmlOutput("Enter Address Details","Address details should be Entered", "Adress Details entered Successfully", "Pass");
			
			}
			
			return true;
		}
		
		//*****************************************************************************************
	    //*    Name        	: enterContactDetails
	    //*    Description 	: This function enters Contact details
	    //*    Author       : Bharat Joshi
	    //*    Input Params : None
	    //*    Return Values: None
	    //*****************************************************************************************
		public boolean enterContactDetails()
		{
			
			//Enter Email ID
			if (objCommon.fGuiEnterText(edtEmailID,Dictionary.get("EMAIL_ID"))==false)
			{
				return false;
			} 
		
			//Enter Postal Code
			if (objCommon.fGuiEnterText(edtPhoneNumber,Dictionary.get("PHONE_NUMBER"))==false)
	        {
	        	return false;
	        }
			else
			{				
				Reporter.fnWriteToHtmlOutput("Enter Contact Details","Contact details should be Entered", "Contact Details entered Successfully", "Pass");
			
			}
		
		 return true;
		}
		
		//*****************************************************************************************
	    //*    Name        	: enterProfessionalDetails
	    //*    Description 	: This function enters Profesional details
	    //*    Author       : Bharat Joshi
	    //*    Input Params : None
	    //*    Return Values: None
	    //*****************************************************************************************
		public boolean enterProfessionalDetails()
		{
			//Select Profession
			
		    
			if (objCommon.fGuiSelectOptionFromList(lstProfession, Dictionary.get("PROFESSION"))==false)
			{
				return false;
			}
			
			/*if (objCommon.fGuiSelectOptionFromList(lstLineofBusiness, Dictionary.get("BUSINESS"))==false)
			{
				return false;
			}*/
			
			if (objCommon.fGuiSelectOptionFromList(lstNetIncomeYear, Dictionary.get("NET_YEARLY_INCOME"))==false)
			{
				return false;
			}
			if (objCommon.fGuiSelectOptionFromList(lstLiquidity, Dictionary.get("LIQUIDITY"))==false)
			{
				return false;
			}
			
			if (objCommon.fGuiEnterText(lstNetIncomeMonth,Dictionary.get("NET_MONTHLY_INCOME"))==false)
	        {
	        	return false;
	        } 
					
			/*if (objCommon.fGuiEnterText(edtOtherIncome,Dictionary.get("OTHER_INCOME"))==false)
	        {
	        	return false;
	        }*/
			
			if (objCommon.fGuiEnterText(edtTANProcedure1,Dictionary.get("TAN_PROCEDURE_1"))==false)
	        {
	        	return false;
	        } 
			
			if (objCommon.fGuiEnterText(edtTANProcedure2,Dictionary.get("TAN_PROCEDURE_2"))==false)
	        {
	        	return false;
	        }
			else
			{				
				Reporter.fnWriteToHtmlOutput("Enter Profesional Details","Professional details should be Entered", "Professional Details entered Successfully", "Pass");
			
			}
			return true;
		}				
	}


