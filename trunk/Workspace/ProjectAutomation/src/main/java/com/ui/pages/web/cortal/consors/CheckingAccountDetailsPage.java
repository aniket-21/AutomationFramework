package com.ui.pages.web.cortal.consors;

import java.util.HashMap;

import com.framework.components.core.Wrappers;
import org.openqa.selenium.WebDriver;

import com.framework.components.core.Reporting;

public class CheckingAccountDetailsPage {

		private Reporting Reporter;
		private WebDriver driver;
		private HashMap<String, String> Dictionary;
		private HashMap<String, String> Environment;
		private Wrappers objCommon;
		
		//Objects
		public String edtCreditNumber = "Xpath:=//input[@name=\"creditnumber\"]";	
	    public String rdbtnGenderMale = "Xpath:=//span[@class=\"ev-formfieldradiobutton-label-text\" and contains(text(),\"Herr\")]";
	    public String rdbtnGenderFemale = "Xpath:=//span[@class=\"ev-formfieldradiobutton-label-text\" and contains(text(),\"Freu\")]";
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
		
	    
	    public CheckingAccountDetailsPage(WebDriver GDriver, HashMap<String, String> GDictionary, HashMap<String, String> GEnvironment, Reporting GReporter)
		{
			Reporter = GReporter;
			driver = GDriver;
			Dictionary = GDictionary;
			Environment = GEnvironment;
			objCommon = new Wrappers(driver, Reporter);
		}
		


		//Enter the Personal Details 
		//*****************************************************************************************
	    //*    Name        	: enterPersonalDetails
	    //*    Author       : Bharat Joshi
	    //*****************************************************************************************
		public void enterPersonalDetails(){

			//Click on link Gender
			if (Dictionary.get("GENDER").equalsIgnoreCase("MALE")){
				objCommon.click(rdbtnGenderMale);
			}
			else{
				objCommon.click(rdbtnGenderFemale);
			}
			 
			 //Enter the First Name, Last name and Birth name
			objCommon.enterText(edtFirstName, Dictionary.get("FIRST_NAME"));
			objCommon.enterText(edtLastName, Dictionary.get("LAST_NAME"));
			objCommon.enterText(edtBirthName, Dictionary.get("BIRTH_NAME"));
		
			//Enter the Day of Birth 
			objCommon.enterText(edtDayOfBirth, Dictionary.get("DAY_OF_BIRTH"));
			objCommon.enterText(edtMonthOfBirth, Dictionary.get("MONTH_OF_BIRTH"));
			objCommon.enterText(edtYearOfBirth, Dictionary.get("YEAR_OF_BIRTH"));

			//Enter the Place and country of Birth
			objCommon.enterText(edtBirthPlace, Dictionary.get("PLACE_OF_BIRTH"));
			objCommon.selectOptionFromList(lstBirthCountry, Dictionary.get("COUNTRY_OF_BIRTH"));
						
			// Select the Martial  Status
			objCommon.selectOptionFromList(lstMaritialStatus, Dictionary.get("MARTIAL_STATUS"));
			
			//Select the Nationality Code
		    objCommon.selectOptionFromList(lstNationalityCode, Dictionary.get("NATIONALITY_CODE"));
			
			//Click on No US Citizen
			/*if (objCommon.click(rdbtnNoUSCitizen)==false)
		    {
				 return false;
		    }*/
			
			//Click on No Tax Liability
			if (Dictionary.get("TAX_LIABILITY").equalsIgnoreCase("Yes")){
				objCommon.click(rdbtnYesTaxLiability);
			}
			else {
				objCommon.click(rdbtnNoTaxLiability);
			}

            Reporter.writeToTestLevelReport("Enter Personal Details", "Personal details should be Entered", "Personal Details entered Successfully", "Pass");
			//return true;
		}
		
	
		//*****************************************************************************************
	    //*    Name        	: enterAddressDetails
	    //*    Author       : Bharat Joshi
	    //*****************************************************************************************
		public void enterAddressDetails()
		{
			//Enter Address details
			objCommon.enterText(edtStreet, Dictionary.get("STREET"));
			objCommon.enterText(edtPostalCode, Dictionary.get("POSTAL_CODE"));
			objCommon.enterText(edtCity, Dictionary.get("CITY"));
			objCommon.selectOptionFromList(LstCountry, Dictionary.get("COUNTRY"));

			Reporter.writeToTestLevelReport("Enter Address Details", "Address details should be Entered", "Address Details entered Successfully", "Pass");
		}
		
		//*****************************************************************************************
	    //*    Name        	: enterContactDetails
	    //*    Author       : Bharat Joshi
	    //*****************************************************************************************
		public void enterContactDetails()
		{
			//Enter Email ID
			objCommon.enterText(edtEmailID, Dictionary.get("EMAIL_ID"));
			objCommon.enterText(edtPhoneNumber, Dictionary.get("PHONE_NUMBER"));

			Reporter.writeToTestLevelReport("Enter Contact Details", "Contact details should be Entered", "Contact Details entered Successfully", "Pass");
		}
		
		//*****************************************************************************************
	    //*    Name        	: enterProfessionalDetails
	    //*    Description 	: This function enters Profesional details
	    //*    Author       : Bharat Joshi
	    //*    Input Params : None
	    //*    Return Values: None
	    //*****************************************************************************************
		public void enterProfessionalDetails()
		{
			//Select Professional details
			objCommon.selectOptionFromList(lstProfession, Dictionary.get("PROFESSION"));
			objCommon.selectOptionFromList(lstNetIncomeYear, Dictionary.get("NET_YEARLY_INCOME"));
			objCommon.selectOptionFromList(lstLiquidity, Dictionary.get("LIQUIDITY"));
			objCommon.enterText(lstNetIncomeMonth, Dictionary.get("NET_MONTHLY_INCOME"));
			objCommon.enterText(edtTANProcedure1, Dictionary.get("TAN_PROCEDURE_1"));
			objCommon.enterText(edtTANProcedure2, Dictionary.get("TAN_PROCEDURE_2"));

			Reporter.writeToTestLevelReport("Enter Profesional Details", "Professional details should be Entered", "Professional Details entered Successfully", "Pass");

		}				
	}


