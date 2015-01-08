package com.automation.framework;

import io.appium.java_client.AppiumDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;



import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;

public class Driver {
		
	//Variables
	String rootPath;
	String executionPath;
	String storagePath;
	String datasheetPath;
	String enviromentsPath;
	String curExecutionFolder;
	String htmlReportsPath;
	String snapShotsPath;
	String dataSheetsPath;
	String dataSheet;
	String configXML;
	String User;	
	
	HashMap <String, String> orgDictionary = new HashMap<String, String>();
	HashMap <String, String> Dictionary = new HashMap<String, String>();
	HashMap <String, String> Environment = new HashMap<String, String>();
	
	
	
	//Constructor
	public Driver(HashMap <String, String> GDictionary, 	HashMap <String, String> GEnvironment)
	{
		
		Dictionary = GDictionary;
		Environment = GEnvironment;
		
		//Get Root Path
		User = System.getProperty("user.name");
		String workingPath = System.getProperty("user.dir");
		rootPath = workingPath.split("trunk")[0];
		
		//Set paths
		executionPath = rootPath + "Execution";
		storagePath = rootPath + "trunk\\Workspace\\ProjectAutomation";
		dataSheetsPath = storagePath + "\\datasheets";
		enviromentsPath = storagePath + "\\environments\\Environments.xls";	
		
		dataSheet = dataSheetsPath + "\\" + Environment.get("CLASSNAME") + ".xls";
		configXML = storagePath + "\\config\\config.xml";
				
		//Add to Env Variables
		Environment.put("ROOTPATH", rootPath);
		Environment.put("EXECUTIONFOLDERPATH", executionPath);
		Environment.put("STORAGEFOLDERPATH", storagePath);
		Environment.put("ENVIRONMENTXLSPATH", enviromentsPath);
		
	}
	
	//Function to Create Execution Folders
	public boolean createExecutionFolders() throws IOException
	{		
		//Set execution paths
		curExecutionFolder = executionPath + "\\" + Environment.get("ENV_CODE") + "\\" + Environment.get("CLASSNAME");
		htmlReportsPath = curExecutionFolder + "\\" + Environment.get("BROWSER").toUpperCase() + "_HTML_Reports";
		snapShotsPath = htmlReportsPath + "\\Snapshots";	
		
		//Put in Environments
		Environment.put("CURRENTEXECUTIONFOLDER", curExecutionFolder);
		Environment.put("HTMLREPORTSPATH", htmlReportsPath);
		Environment.put("SNAPSHOTSFOLDER", snapShotsPath);		
		
		//Delete if folder already exists
		if (new File(htmlReportsPath).exists())
			delete(new File(htmlReportsPath));		
		return (new File(snapShotsPath)).mkdirs();
	}
	
	//*****************************************************************************************
    //*    Name        		: delete
    //*    Description    : Deletes previous reports
    //*    Author        	:  Aniket Gadre
    //*    Input Params    :     None
    //*    Return Values    :     None
    //*****************************************************************************************
    public static void delete(File file)
	throws IOException{
    	  
		if(file.isDirectory()){
	
		   //list all the directory contents
		   String files[] = file.list();
	
		   for (String temp : files) {
		      //construct the file structure
		      File fileDelete = new File(file, temp);
	
		      //recursive delete
		     delete(fileDelete);
		   }
	
		   //check the directory again, if empty then delete it
		   if(file.list().length==0){
	   	     file.delete();
		     //System.out.println("Directory is deleted : " + file.getAbsolutePath());
	                                          
		   }
	
		}else{
			//if file, then delete it
			file.delete();
			//System.out.println("File is deleted : " + file.getAbsolutePath());
		}
    }
    
    //*****************************************************************************************
    //*    Name        	  : fetchEnvironmentDetails
    //*    Description    : Fetches EnvDetails from Environments.xls and loads it into hashmap 
    //*    Author         : Aniket Gadre
    //*    Input Params   : None
    //*    Return Values  : None
    //*****************************************************************************************
	public boolean fetchEnvironmentDetails() {

		try {
		    int iVersion = -1;
		    int iEnvironment = -1;
		    boolean bFlag = false;
			
		    /*//Get the Column Index for the VERSION Column
		    iVersion = fGetColumnIndex(Environment.get("ENVIRONMENTXLSPATH"), "ENVIRONMENTS", "VERSION");
		    
		    //Check if the index value is proper
		    if (iVersion == -1 ){
		    	System.out.println("Failed to find the Version Column in the file " + Environment.get("ENVIRONMENTXLSPATH"));
		    	return false;
		    }*/

		    //Get the Column Index for the ENVIRONMENT Column
		    iEnvironment = fGetColumnIndex(enviromentsPath, "ENVIRONMENTS", "ENVIRONMENT");
		    
		    //Check if the index value is proper
		    if (iEnvironment == -1 ){
		    	System.out.println("Failed to find the Environment Column in the file " + enviromentsPath);
		    	return false;
		    }
		    
			//Create the FileInputStream obhect			
			FileInputStream file = new FileInputStream(new File(enviromentsPath));		     
		    //Get the workbook instance for XLS file 
		    HSSFWorkbook workbook = new HSSFWorkbook(file);
		 
		    //Get first sheet from the workbook
		    HSSFSheet sheet = workbook.getSheet("ENVIRONMENTS");
		    
		    //Get the Number of Rows
		    int iRowNum = sheet.getLastRowNum();
		    
		    //Get the Column count
		    int iColCount = sheet.getRow(0).getLastCellNum();
		    
		    for (int iRow = 0; iRow <= iRowNum; iRow++)
		    {
	            //Check if the version and the envioronment value is matching
	            //String strVersion = sheet.getRow(iRow).getCell(iVersion).getStringCellValue().trim().toUpperCase();
	            String strEnvironment = sheet.getRow(iRow).getCell(iEnvironment).getStringCellValue().trim().toUpperCase();
	            //Currently checking only on the basis of envrionment
	            if (!strEnvironment.equals(Environment.get("ENV_CODE")))			            	
	            {
	            	continue;
	            }
	            
	            //Set the flag value to true
	            bFlag = true;
	            String strKey = "";
	            String strValue = "";
		        //Loop through all the columns
		        for (int iCell = 0; iCell < iColCount; iCell ++ )
		        {
                    //Put the Details in Environment Hashmap
		            strKey = sheet.getRow(0).getCell(iCell).getStringCellValue().trim().toUpperCase();
		            
	        		//Fetch the value for the Header Row
	        		if (sheet.getRow(iRow).getCell(iCell, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK) == null)
	        		{
	        			strValue = "";
	        		}else{
	        			strValue = sheet.getRow(iRow).getCell(iCell).getStringCellValue();
	        		}

	        		Environment.put(strKey.trim(), strValue.trim());
		        }
		        break;
		    }
		    //Close the file
		    file.close();

		    //If bFlag is true
		    if (bFlag == false)
		    {
		    	System.out.println("Environment Code " + Environment.get("ENV_CODE") + " not found in the Environment xls");
		    	return false;
		    }

		    return true;
		     
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		    return false;
		} catch (IOException e) {
		    e.printStackTrace();
		    return false;
		}
	}
	
	//*****************************************************************************************
    //*	Name		    : fGetColumnIndex
    //*	Description	    : Function to get the Column Index
    //*	Author		    : Aniket Gadre
    //* Input Params	: int row - Row number to skip
    //*	Return Values	: None
    //***********************************************************************
	public int fGetColumnIndex (String strXLS, String strSheetName, String strColumnName)
	{
		try
		{
			//Create the FileInputStream obhect			
			FileInputStream file = new FileInputStream(new File(strXLS));		     
		    //Get the workbook instance for XLS file 
		    HSSFWorkbook workbook = new HSSFWorkbook(file);
		 
		    //Get first sheet from the workbook
		    HSSFSheet sheet = workbook.getSheet(strSheetName);
		     
		    //Iterate through each rows from first sheet
		    Row row = sheet.getRow(0);
		    
		    //Get the Column count
		    int iColCount = row.getLastCellNum();
		    int iCell = 0;
		    int iIndex = -1;
	        String strTemp = "";

	        //Loop through all the columns
	        for (iCell = 0; iCell < iColCount; iCell ++)
	        {
        		//Get the index for Version and Enviornment
        		strTemp = sheet.getRow(0).getCell(iCell).getStringCellValue().trim().toUpperCase();
        		
        		//if the strColumnName contains Header then check for HEADER or HEADER_IND
        		if (strColumnName.equals("HEADER_IND") || strColumnName.equals("HEADER"))
        		{
        			if (strTemp.equals("HEADER") || strTemp.equals("HEADER_IND"))
        			{
        				iIndex = iCell;
        				//Exit the Loop
        				break;
        			}
        			
        		}else{ 
        			if (strTemp.equals(strColumnName.trim().toUpperCase()))
        			{
        				iIndex = iCell;
        				//Exit the Loop
        				break;
        			}
        		}
	        }
	        //Close the file
		    file.close();
		    
		    //Validate if index is returned properly or not
		    if (iIndex != -1)
		    {
		    	//Print the Column Index
//		    	System.out.println("Column Id for Column " + strColumnName + " is " + iIndex);
		    	return iIndex;
		    	
		    }else{
		    	System.out.println("Failed to find the Column Id for Column " + strColumnName);
		    	return -1;
		    	
		    }
			
		}catch (Exception e){
			System.out.println("Got exception while finding the Index column. Exception is " + e);
			return -1;
		}
	}
	
	
	//*****************************************************************************************
    //*	Name		    : fGetDataForTest
    //*	Description	    : Function to get Data corresponding to Test
    //*	Author		    : Aniket Gadre
    //* Input Params	: int row - Row number to skip
    //*	Return Values	: None
    //***********************************************************************
	public boolean fGetDataForTest(String testName)
	{
		//DataSheet
		final String dataSheet = dataSheetsPath + "\\" + Environment.get("CLASSNAME") + ".xls";
		final String mainSheet = "MAIN"; 
		final String testNameColumn = "TEST_NAME";
		
		//Clear Dictionary
		Dictionary.clear();
		orgDictionary.clear();
		
		//Get column index of test name column
	    int iColTestName = fGetColumnIndex(dataSheet, mainSheet, testNameColumn);
	    
	    try{
	    	
	    	//Create the FileInputStream obhect			
			FileInputStream file = new FileInputStream(new File(dataSheet));		     
		    //Get the workbook instance for XLS file 
		    HSSFWorkbook workbook = new HSSFWorkbook(file);
		 
		    //Get MAIN sheet from the workbook
		    HSSFSheet sheet = workbook.getSheet(mainSheet);
		     
		    //Iterate through each rows from first sheet
		    int iRowCnt = sheet.getLastRowNum();

		    //Loop
		    int iRow;
		    for(iRow=0;iRow<iRowCnt;iRow++)
		    {
		    	//Get row with test name and exist
		    	if(sheet.getRow(iRow).getCell(iColTestName).getStringCellValue().equalsIgnoreCase(testName)) break;
		    }
		    
		    //Check if test found
		    if(iRow == iRowCnt){
		    	System.out.println("Test with name: " + testName + " not found in datasheet: " +  dataSheet);
		    	return false;
		    }
		    		    
		    //Set Header & DataRow
		    Row headerRow = sheet.getRow(iRow-1);
		    Row dataRow = sheet.getRow(iRow);
		    
		    //Get Column count for test-1 row
		    int iParamCnt = headerRow.getLastCellNum();		 
		    
		    //
		    String key="";
		    String value="";
		    		    
		    //Loop through params
		    int iCol;
		    for(iCol=0;iCol<iParamCnt;iCol++){
		    	
		    	//Fetch the value for the Header Row
        		if (headerRow.getCell(iCol, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK) == null)
        		{
        			key = "";
        		}else{
        			key = headerRow.getCell(iCol).getStringCellValue();
        		}
		    	
        		//Fetch the value for the Header Row
        		if (dataRow.getCell(iCol, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK) == null)
        		{
        			value = "";
        		}else{
        			value = dataRow.getCell(iCol).getStringCellValue();
        		}
        		
        		//Check key value
        		if(key.isEmpty()) break;
        		else {
        			Dictionary.put(key,value);
        			orgDictionary.put(key,value);
        		}
		    	
		    }
		    
		    //Give call to get Reference data to replace & parameters
		    if (fGetReferenceData() == false) return false;		    		
		    			    		   
	        return true;
	    }
	    catch(Exception e)
	    {
	    	System.out.println("Exception " + e + " occured while fetching data from datasheet " + dataSheet + " for test " + testName);
	    	return false;
	    }		
	}
	
		//*****************************************************************************************
	   //*	Name		    : fGetReferenceData
	   //*	Description	    : Fetch the data from keep refer sheet
	   //*	Author		    : Aniket Gadre
	   //*	Input Params	: None
	   //*	Return Values	: Boolean 
	   //*****************************************************************************************	
		public boolean fGetReferenceData()
		{
			//Declare few variables
			String key, value;
			Map.Entry me;
			
			//DataSheet			
			final String krSheet = "KEEP_REFER"; 
			
			//Get a set of the entries 
			Set set = Dictionary.entrySet(); 
	   	
		   	//Get an iterator 
		   	Iterator i = set.iterator(); 
		   	try
	   		{
			    int iRow = 0;
			    String strKeyName = "";
			    String strKeyValue = "";
			    boolean bFoundFlag = false;			    
			    
		        //Set KEY_VALUE column as per Test Name
		        String colName = Environment.get("BROWSER").toUpperCase() + "_KEY_VALUE";
		        
		        //Call the function to get the Column Index in the KEEP_REFER sheet
		        int iColIndex = fGetColumnIndex(dataSheet, krSheet, colName);
		        
		        //Validate if we get the index
		        if (iColIndex == -1){
		        	System.out.println("Failed to find the " + colName + " Column in the KEEP_REFER sheet in file " + dataSheet);
			    	return false;
		        }
		        
				//Create the FileInputStream obhect			
				FileInputStream file = new FileInputStream(new File(dataSheet));		     
			    //Get the workbook instance for XLS file 
			    HSSFWorkbook workbook = new HSSFWorkbook(file);
			 
			    //Get first sheet from the workbook
			    HSSFSheet sheet = workbook.getSheet(krSheet);
			    
			    //Get the RowNum
			    int iRowNum = sheet.getLastRowNum();

			    
			    //Looping through the iterator
			   	while(i.hasNext()) 
			   	{ 	
		   		
		    		me = (Map.Entry)i.next();
		    		key = me.getKey().toString();
		    		value = me.getValue().toString();
		    		
		    		//If we need to get data from KEEP refer sheet
		    		if (value.startsWith("&",0))
		    		{
		    			value = value.substring(1);
		    			
		    			
		    			//Loop thorugh all the rows in the KEEP_REFER sheet
		    		    for (iRow = 0; iRow <= iRowNum; iRow++)
		    		    {
		    		    	strKeyName = "";
		    		    	strKeyValue = "";
		    		    	bFoundFlag = false;
		    		    	
 		        		//Check if the key is present in the Keep_Refer sheet
 		        		try
 		        		{
	    		        		if (sheet.getRow(iRow).getCell(0, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK) == null)
	    		        		{
	    		        			strKeyName = "";
	    		        		}else{
	    		        			strKeyName = sheet.getRow(iRow).getCell(0).getStringCellValue();
	    		        		}
 		        		}catch (NullPointerException e){
 		        			strKeyName = "";
 		        		}
 		        		
 		        		//Check if the key matches the expected key
 		        		if (strKeyName.equals(value))
 		        		{
 		        			//Set the boolean value to true
 		        			bFoundFlag = true;
 		        			
 		        			//Fetch the corresponding value
	    		        		if (sheet.getRow(iRow).getCell(iColIndex, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK) == null)
	    		        		{
	    		        			strKeyValue = "";
	    		        		}else{
	    		        			strKeyValue = sheet.getRow(iRow).getCell(iColIndex).getStringCellValue();
	    		        		}

 		        			break;
 		        		}
		    		    }
		    		    
		    		    //If the Key is not found then check the same in environment hashmap
		    		    if (bFoundFlag){
		    		    	System.out.println("Key " + value + " found in KEEP_REFER and value is " + strKeyValue);
		    		    }else{
		    		    	//Fetch the value from the Environment Hashmap
		    		    	System.out.println("Key " + value + " not found in KEEP_REFER sheet. Checking in the Enviornment Hashmap");
		    		    	strKeyValue = Environment.get(value);
		    		    	System.out.println("Key " + value + " found in Environment hashmap and value is " + strKeyValue);
		    		    }
	    			
		    	        //Check if Key value is not null
		    		    if (strKeyValue == null)
		    		    {
		    		    	System.out.println("Value for Key " + value + " is null and not found in the KEEP_REFER sheet and in the Environment hashmap");
		    		    	return false;
		    		    }
		    		    //Assign the value to Dictionary key
		    	        me.setValue(strKeyValue);
			    	        
		    	        
		    		}else if (value.startsWith("@",0))
		    		{
		    	        me.setValue(value); 			
		    		}
		   		}
			   	
			   	//Close the file
			   	file.close();
			   	return true;
	   		}
	   		catch (Exception err)
	   		{
	        	System.out.print("Exception " + err + " occured in fGetReferenceData");
	        	err.printStackTrace();
	        	return false;
	   		}
	   }	
		
	//*****************************************************************************************
   //*	Name		    : fSetReferenceData
   //*	Description	    : Set the data in keep refer sheet
   //*	Author		    : Aniket Gadre
   //*	Input Params	: None
   //*	Return Values	: Boolean 
   //*****************************************************************************************	
	public boolean fSetReferenceData()
	{
		
		//Declare few variables
		String key, value, tempKey, tempValue;
	   	Map.Entry me;
	   	int Field_Count;
	   	
	  //DataSheet			
		final String krSheet = "KEEP_REFER"; 
		final String colName = Environment.get("BROWSER").toUpperCase() + "_KEY_VALUE";
   	
		//Get a set of the entries 
	   	Set set = orgDictionary.entrySet();
	   	
	   	//Get an iterator 
	   	Iterator i = set.iterator(); 
	   	
	   	try
   		{	 
		    int iRow = 0;
		    String strKeyName = "";
		    boolean bFoundFlag = false;
		    
	        //Set KEY_VALUE column as per Test Name
	        
	        
	        //Call the function to get the Column Index in the KEEP_REFER sheet
	        int iColIndex = fGetColumnIndex(dataSheet, krSheet, colName);
	        
	        //Validate if we get the index
	        if (iColIndex == -1){
	        	System.out.println("Failed to find the " + colName + " Column in the KEEP_REFER sheet in file " + dataSheet);
		    	return false;
	        }
	        
			//Create the FileInputStream obhect			
			FileInputStream file = new FileInputStream(new File(dataSheet));		     
		    //Get the workbook instance for XLS file 
		    HSSFWorkbook workbook = new HSSFWorkbook(file);
		 
		    //Get first sheet from the workbook
		    HSSFSheet sheet = workbook.getSheet(krSheet);
		    
		    //Get the RowNum
		    int iRowNum = sheet.getLastRowNum();
		    
	        
		   	//Looping through the iterator
		   	while(i.hasNext()) 
		   	{ 	
	   		
	    		me = (Map.Entry)i.next();
	    		key = me.getKey().toString();
	    		value = me.getValue().toString();
	    		
	    		//System.out.println("orgKey: " + key + "  orgValue: " + value);
	    		
	    		//If we need to get data from KEEP refer sheet
	    		if (value.startsWith("@",0))
	    		{
	    			tempKey = value.substring(1);
	    			
	    			//if Dictionary item has been changed from the objGlobalDictOriginal item
	    			if (!(Dictionary.get(key)).equalsIgnoreCase(orgDictionary.get(key).substring(1)))
	    			{
	    				tempValue = Dictionary.get(key);
	    			}
	    			else
	    			{
	    				tempValue ="";
	    			}
	    		
	    			//Loop thorugh all the rows in the KEEP_REFER sheet
	    		    for (iRow = 0; iRow <= iRowNum; iRow++)
	    		    {
	    		    	strKeyName = "";
	    		    	bFoundFlag = false;
		        		try
		        		{
    		        		//Check if the key is present in the Keep_Refer sheet
    		        		if (sheet.getRow(iRow).getCell(0, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK) == null)
    		        		{
    		        			strKeyName = "";
    		        		}else
    		        		{
    		        			strKeyName = sheet.getRow(iRow).getCell(0).getStringCellValue();
    		        		}
		        		}catch (NullPointerException e)
		        		{
		        			strKeyName = "";
		        		}
		        		
		        		//Check if the key matches the expected key
		        		if (strKeyName.equals(tempKey))
		        		{
		        			//Set the boolean value to true
		        			bFoundFlag = true;
		        			break;
		        		}
	    		        		    		    	
	    		    }
	    		    
	    		    //If the Key is present then update the value else add the key value in KEEP_REFER
	    		    if (bFoundFlag){
	    		    	//Update the key value in KEEP_REFER
    		    		sheet.getRow(iRow).createCell(iColIndex);
    		    		sheet.getRow(iRow).getCell(iColIndex).setCellValue(tempValue);
	    		    }else{
	    		    	//Set value for iRowNum
	    		    	iRowNum = iRowNum + 1;
	    		    	sheet.createRow(iRowNum);
	    		    	sheet.getRow(iRowNum).createCell(0);
	    		    	sheet.getRow(iRowNum).createCell(iColIndex);
	    		    	//Add the key and the value in KEEP_REFER sheet
	    		    	sheet.getRow(iRowNum).getCell(0).setCellValue(tempKey);
	    		    	sheet.getRow(iRowNum).getCell(iColIndex).setCellValue(tempValue);
	    		    }
	    		}
	   		}
		   	

		   	file.close();
		   	
		   	//Save the file
		    //Save the Changes made 
		    FileOutputStream outFile =new FileOutputStream(new File(dataSheet));
		    workbook.write(outFile);
		    outFile.close();
	    			
		   	return true;
	
   		}catch (Exception err)
   		{
   			System.out.print("Exception " + err + " occured in fSetReferenceData");
   			err.printStackTrace();
   			return false;
   		}
		
   }
	
   //*****************************************************************************************
   //*	Name		    : fGetEnv
   //*	Description	    : Returns the current environment
   //*	Author		    : Aniket Gadre
   //*	Input Params	: None
   //*	Return Values	: WebDriver 
   //*****************************************************************************************
	public String fGetEnv(){
				
		try{	
			File fXmlFile = new File(configXML);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
						
			doc.getDocumentElement().normalize();
			
			//Get Env Node
			NodeList nEnv = doc.getElementsByTagName("env");
			return ((Element)nEnv.item(0)).getTextContent();
			
		}
		catch(Exception e){
			e.printStackTrace();			
		}
		
		return "";
	}
	
	
   //*****************************************************************************************
   //*	Name		    : fGetWebDriver
   //*	Description	    : Returns the required webdriver
   //*	Author		    : Aniket Gadre
   //*	Input Params	: None
   //*	Return Values	: WebDriver 
   //*****************************************************************************************
	public WebDriver fGetWebDriver(String browser) throws MalformedURLException
	{
		//String webDriverType = System.getProperty("browserName");
        String webDriverType = browser;

        //Define webdriver
        WebDriver wbDriver;
        if (webDriverType.equalsIgnoreCase("chrome") || webDriverType.isEmpty())
        {
            System.setProperty("webdriver.chrome.driver", storagePath + "\\drivers\\chromedriver.exe");
            return new ChromeDriver();
        }
        else if (webDriverType.equalsIgnoreCase("firefox")){
            return new FirefoxDriver();
        }
        else if (webDriverType.equalsIgnoreCase("ie")){
            System.setProperty("webdriver.ie.driver", storagePath + "\\drivers\\IEDriver.exe");
            return new InternetExplorerDriver();
        }
        else{
            System.out.println("Driver type " + webDriverType + " is invalid");
            return null;
        }

		/*String URL = "";
		String strDCName, strDCValue;
		DesiredCapabilities DC = new DesiredCapabilities();
		String className = Environment.get("CLASSNAME");		
		
		try
		{
			File fXmlFile = new File(configXML);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			//doc.getDocumentElement().normalize();
			
			//Get Test Class Node
			NodeList nTestClasses = doc.getElementsByTagName("class");
			int iClass = nTestClasses.getLength();
			
			//loop
			int z=0;
			Element nTestClass = null;
			for(z=0;z<iClass;z++)
			{
				if(((Element)nTestClasses.item(z)).getAttribute("id").equalsIgnoreCase(className)) 
				{
					nTestClass = (Element)nTestClasses.item(z);
					break;
				}
			}			
			
			//check
			if(nTestClass == null) 
			{
				System.out.println("Node with ID " + className + " not found");
				return null;
			}
			
			//convert node to element
			Element eTestClass = nTestClass;
			
			//Check if driver node exist
			if(eTestClass.getElementsByTagName("driver").getLength() == 0)
			{
				System.out.println("No <driver> node found");
				return null;
			}
			
			//get node with tagname type
			NodeList nType = eTestClass.getElementsByTagName("type");
			
			if(nType.getLength() == 0)
			{
				System.out.println("No node with tag name <type> found");
				return null;
			}
			
			webDriverType = ((Element)nType.item(0)).getTextContent();
			
			//get node with tagname type
			NodeList nURL = eTestClass.getElementsByTagName("url");
			
			if(nURL.getLength() == 0)
			{
				System.out.println("No node with tag name <url> found");
				//return null;
			}
			else
			{
				URL = ((Element)nURL.item(0)).getTextContent();
			}
		
			//get nodes with tagname capabilities
			NodeList nCapabilities = eTestClass.getElementsByTagName("capability");
			int iCapabilities = nCapabilities.getLength();
			
			//loop through capabilities
			for(int i=0;i<iCapabilities;i++)
			{
				strDCName = ((Element)nCapabilities.item(i)).getElementsByTagName("name").item(0).getTextContent();
				strDCValue = ((Element)nCapabilities.item(i)).getElementsByTagName("value").item(0).getTextContent();
				DC.setCapability(strDCName, strDCValue);
			}
							
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}*/
		

	}

}
