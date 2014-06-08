package com.amdocs.asap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Reporting {
	
	
	private String g_strTestCaseReport;
    private String g_strSnapshotFolderName;
    private String g_strSnapshotRelativePath;
    private String g_strScriptName;       

    //Counters and Integers
    private int g_iSnapshotCount;
    private int g_OperationCount;
    private int g_iPassCount;
    private int g_iFailCount;
    private int g_iTCPassed;
    private int g_iTestCaseNo;
	//public static int g_iFailCount;

    private Date g_StartTime;
    private Date g_EndTime;
    private Date g_SummaryStartTime;
    private Date g_SummaryEndTime;
	//private WebDriver driver;
	
	private FileOutputStream foutStrm = null;
	
	public Reporting()
	{
		//driver = Global.webDriver;
	}
	
	
	//*****************************************************************************************
    //*    Name        : fnCreateSummaryReport
    //*    Description    : The function creates the summary HTML file
    //*    Author        :  Anil Agarwal
    //*    Input Params    :     None
    //*    Return Values    :     None
    //*****************************************************************************************
    public void fnCreateSummaryReport() 
    {
        //Setting counter value
        g_iTCPassed = 0;
        g_iTestCaseNo = 0;
        g_SummaryStartTime = new Date();
        
		try 
		{ 
	        //Open the test case report for writing                   
	        foutStrm = new FileOutputStream(Global.Environment.get("HTMLREPORTSPATH")+ "\\SummaryReport.html", true);
	           
			//Close the html file
	        new PrintStream(foutStrm).println("<HTML><BODY><TABLE BORDER=0 CELLPADDING=3 CELLSPACING=1 WIDTH=100% BGCOLOR=BLACK>");
			new PrintStream(foutStrm).println("<TR><TD WIDTH=90% ALIGN=CENTER BGCOLOR=WHITE><FONT FACE=VERDANA COLOR=ORANGE SIZE=3><B>AMDOCS</B></FONT></TD></TR><TR><TD ALIGN=CENTER BGCOLOR=ORANGE><FONT FACE=VERDANA COLOR=WHITE SIZE=3><B>Selenium Framework Reporting</B></FONT></TD></TR></TABLE><TABLE CELLPADDING=3 WIDTH=100%><TR height=30><TD WIDTH=100% ALIGN=CENTER BGCOLOR=WHITE><FONT FACE=VERDANA COLOR=//0073C5 SIZE=2><B>&nbsp; Automation Result : " + new Date() + " on Machine " + InetAddress.getLocalHost().getHostName() + " by user " + System.getProperty("user.name") + "</B></FONT></TD></TR><TR HEIGHT=5></TR></TABLE>");  
	        new PrintStream(foutStrm).println("<TABLE  CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");           
	        new PrintStream(foutStrm).println("<TR COLS=6 BGCOLOR=ORANGE><TD WIDTH=10%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>TC No.</B></FONT></TD><TD  WIDTH=70%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Test Name</B></FONT></TD><TD BGCOLOR=ORANGE WIDTH=30%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Status</B></FONT></TD></TR>");
	        
	        //Close the object
	        foutStrm.close();
	        
		} catch (IOException io) 
		{
			io.printStackTrace();
		} 
		
		foutStrm = null;
    }	
    
    //*****************************************************************************************
    //*    Name            : fnCreateHtmlReport
    //*    Description        : The function creates the result HTML file
    //*                      In Case the file already exists, it will overwrite it and also delete the existing folders.
    //*    Author            : Anil Agarwal
    //*    Input Params    : None
    //*    Return Values    : None
    //*****************************************************************************************
	public void fnCreateHtmlReport(String strTestName) {

        //Set the default Operation count as 0
        g_OperationCount = 0;
        
        //Number of default Pass and Fail cases to 0
        g_iPassCount = 0;
        g_iFailCount = 0;
        
        //Snapshot count to start from 0
        g_iSnapshotCount = 0;
        
        //script name
        g_strScriptName = strTestName;		

        //Set the name for the Test Case Report File
        g_strTestCaseReport = Global.Environment.get("HTMLREPORTSPATH") + "\\Report_" + g_strScriptName + ".html";
        //System.out.print(" Detailed Report path is " + g_strTestCaseReport);
             
        //Snap Shot folder
        g_strSnapshotFolderName = Global.Environment.get("SNAPSHOTSFOLDER") + "\\" +  g_strScriptName;
        
        //Snapshot relative path
        g_strSnapshotRelativePath = "Snapshots\\" + g_strScriptName;
 
        //Delete the Summary Folder if present
		File file = new File(g_strSnapshotFolderName);

		if (file.exists()) {
			file.delete();
		}

		//Make a new snapshot folder
		file.mkdir();

		//Open the report file to write the report

		try {
			foutStrm = new FileOutputStream(g_strTestCaseReport);
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		}

		//Write the Test Case name and allied headers into the file
        //Write the Test Case name and allied headers into the file
		//Close the html file
		try 
		{		
			new PrintStream(foutStrm).println("<HTML><BODY><TABLE BORDER=0 CELLPADDING=3 CELLSPACING=1 WIDTH=100% BGCOLOR=ORANGE>");
			new PrintStream(foutStrm).println("<TR><TD WIDTH=90% ALIGN=CENTER BGCOLOR=WHITE><FONT FACE=VERDANA COLOR=ORANGE SIZE=3><B>AMDOCS</B></FONT></TD></TR><TR><TD ALIGN=CENTER BGCOLOR=ORANGE><FONT FACE=VERDANA COLOR=WHITE SIZE=3><B>Selenium Framework Reporting</B></FONT></TD></TR></TABLE><TABLE CELLPADDING=3 WIDTH=100%><TR height=30><TD WIDTH=100% ALIGN=CENTER BGCOLOR=WHITE><FONT FACE=VERDANA COLOR=//0073C5 SIZE=2><B>&nbsp; Automation Result : " + new Date() + " on Machine " + InetAddress.getLocalHost().getHostName() + " by user " + System.getProperty("user.name") + "</B></FONT></TD></TR><TR HEIGHT=5></TR></TABLE>");
			new PrintStream(foutStrm).println("<TABLE BORDER=0 BORDERCOLOR=WHITE CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
			new PrintStream(foutStrm).println("<TR><TD BGCOLOR=BLACK WIDTH=20%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Test     Name:</B></FONT></TD><TD COLSPAN=6 BGCOLOR=BLACK><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>" + g_strScriptName + "</B></FONT></TD></TR>");
	        //new PrintStream(foutStrm).println("<TR><TD BGCOLOR=BLACK WIDTH=20%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Test    Iteration:</B></FONT></TD><TD COLSPAN=6 BGCOLOR=BLACK><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B> </B></FONT></TD></TR>");
			new PrintStream(foutStrm).println("</TABLE><BR/><TABLE WIDTH=100% CELLPADDING=3>");
			new PrintStream(foutStrm).println("<TR WIDTH=100%><TH BGCOLOR=ORANGE WIDTH=5%><FONT FACE=VERDANA SIZE=2>Step No.</FONT></TH><TH BGCOLOR=ORANGE WIDTH=28%><FONT FACE=VERDANA SIZE=2>Step Description</FONT></TH><TH BGCOLOR=ORANGE WIDTH=25%><FONT FACE=VERDANA SIZE=2>Expected Value</FONT></TH><TH BGCOLOR=ORANGE WIDTH=25%><FONT FACE=VERDANA SIZE=2>Obtained Value</FONT></TH><TH BGCOLOR=ORANGE WIDTH=7%><FONT FACE=VERDANA SIZE=2>Result</FONT></TH></TR>");
		
			foutStrm.close();
		} catch (IOException io) 
		{
			io.printStackTrace();
		}
		//Deference the file pointer
		foutStrm = null;

		//Get the start time of the execution
		g_StartTime = new Date();

	}
	
	
	//*****************************************************************************************
    //*    Name        : fnCloseHtmlReport
    //*    Description    : The function Closes the HTML file
    //*    Author        : Anil Agarwal
    //*    Input Params    :     None
    //*    Return Values    :     None
    //*****************************************************************************************
	public void fnCloseHtmlReport(String strTestName) {

		//Declaring variables

		String strTestCaseResult = null;

		//Open the report file to write the report
		try {
			foutStrm = new FileOutputStream(g_strTestCaseReport, true);

		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		}

		//Get the current time
		g_EndTime = new Date();
		
		//Fetch the time difference
		String strTimeDifference = fnTimeDiffference(g_StartTime.getTime(),g_EndTime.getTime());
		
		//Close the html file
		try {		
	          //Write the number of test steps passed/failed and the time which the test case took to run
			new PrintStream(foutStrm).println("<TR></TR><TR><TD BGCOLOR=BLACK WIDTH=5%></TD><TD BGCOLOR=BLACK WIDTH=28%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Time Taken : "+ strTimeDifference + "</B></FONT></TD><TD BGCOLOR=BLACK WIDTH=25%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Pass Count : " + g_iPassCount + "</B></FONT></TD><TD BGCOLOR=BLACK WIDTH=25%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Fail Count : " + g_iFailCount + "</b></FONT></TD><TD BGCOLOR=Black WIDTH=7%></TD></TR>");
	           //Close the HTML tags
			new PrintStream(foutStrm).println("</TABLE><TABLE WIDTH=100%><TR><TD ALIGN=RIGHT><FONT FACE=VERDANA COLOR=ORANGE SIZE=1>&copy; amdocs - Integrated Customer Management</FONT></TD></TR></TABLE></BODY></HTML>");
			//Close File stream
			foutStrm.close();
			
		} catch (IOException io) {
			io.printStackTrace();
		}

		//Deference the file pointer
		foutStrm = null;

		//Check if test case passed or failed

		if (g_iFailCount != 0) {
			strTestCaseResult = "Fail";
		} else 
		{
			strTestCaseResult = "Pass";
		}
		
        //fnCloseHtmlReport = strTestCaseResult
        
        //Write into the Summary Report
        fnWriteTestSummary ("Report_"+ strTestName ,strTestCaseResult);
		
	}
	
	 //*****************************************************************************************
    //*    Name         : fnTimeDiffference
    //*    Description  : The function takes the screenshot
    //*    Author       : Anil Agarwal
    //*    Input Params : startTime - Start time in long format
	//*					  endTime - End time in long format	
    //*    Return Values: None
    //*****************************************************************************************
	public String fnTimeDiffference(long startTime, long endTime) {

		//Finding the difference in milliseconds
		long delta = endTime - startTime;

		//Finding number of days
		int days = (int) delta / (24 * 3600 * 1000);

		//Finding the remainder
		delta = (int) delta % (24 * 3600 * 1000);

		//Finding number of hrs
		int hrs = (int) delta / (3600 * 1000);

		//Finding the remainder
		delta = (int) delta % (3600 * 1000);

		//Finding number of minutes
		int min = (int) delta / (60 * 1000);

		//Finding the remainder
		delta = (int) delta % (60 * 1000);

		//Finding number of seconds
		int sec = (int) delta / 1000;

		//Concatenting to get time difference in the form day:hr:min:sec 
		String strTimeDifference = days + ":" + hrs + ":" + min + ":" + sec;
		return strTimeDifference;
	}
	
	
	//*****************************************************************************************
    //*    Name        : fnWriteTestSummary
    //*    Description    : The function Writes the final outcome of a test case to a summary file.
    //*    Author        :  Aniket Gadre
    //*    Input Params    :     
    //*            strTestCaseName(String) - the name of the test case
    //*            strResult(String) - the result (Pass/Fail)
    //*    Return Values    :     
    //*            (Boolean) TRUE - Succeessful write
    //*                 FALSE - Report file not created
    //*****************************************************************************************
    public void fnWriteTestSummary(String strTestCaseName, String strResult)
    {
     
        String sColor,sRowColor;
        
        //Close the file
        try
        {        
	        //Open the test case report for writing                   
	        foutStrm = new FileOutputStream(Global.Environment.get("HTMLREPORTSPATH")+ "\\SummaryReport.html", true);
	        
	        //Check color result
	        if (strResult.toUpperCase().equals("PASSED") || strResult.toUpperCase().equals("PASS"))
	        {
	            sColor = "GREEN";
	            g_iTCPassed ++;
	        }
	        else if (strResult.toUpperCase().equals("FAILED") || strResult.toUpperCase().equals("FAIL"))
	        {
	            sColor = "RED";
	            
	        }
	        else
	        {
	            sColor = "ORANGE";
	        }
	
	        g_iTestCaseNo++;
	
	        if (g_iTestCaseNo % 2 == 0)
	        {
	            //sRowColor = "//BEBEBE";
	            sRowColor = "#EEEEEE";
	        }
	        else
	        {
	            sRowColor = "#D3D3D3";
	        }
	       //Write the result of Individual Test Case
	        new PrintStream(foutStrm).println ("<TR COLS=3 BGCOLOR=" + sRowColor + "><TD  WIDTH=10%><FONT FACE=VERDANA SIZE=2>" + g_iTestCaseNo + "</FONT></TD><TD  WIDTH=70%><FONT FACE=VERDANA SIZE=2>" + strTestCaseName + "</FONT></TD><TD  WIDTH=20%><A HREF='" + strTestCaseName + ".html'><FONT FACE=VERDANA SIZE=2 COLOR=" + sColor + "><B>" + strResult + "</B></FONT></A></TD></TR>");
      
        	foutStrm.close();
        }
        catch (IOException io) 
		{
			io.printStackTrace();
		}
       foutStrm = null;

    }
    
    
  //*****************************************************************************************
    //*    Name        : fnCloseTestSummary
    //*    Description    : The function Closes the summary file
    //*    Author        :  Aniket Gadre
    //*    Input Params    :     None
    //*    Return Values    :     None
    //*****************************************************************************************
    public void fnCloseTestSummary()
    {
        g_SummaryEndTime = new Date();
        
        //Open the Test Summary Report File
		try {         
			foutStrm = new FileOutputStream(Global.Environment.get("HTMLREPORTSPATH")+ "\\SummaryReport.html", true);
       
            new PrintStream(foutStrm).println("</TABLE><TABLE WIDTH=100%><TR>");
	        new PrintStream(foutStrm).println("<TD BGCOLOR=BLACK WIDTH=10%></TD><TD BGCOLOR=BLACK WIDTH=70%><FONT FACE=VERDANA SIZE=2 COLOR=WHITE><B></B></FONT></TD><TD BGCOLOR=BLACK WIDTH=20%><FONT FACE=WINGDINGS SIZE=4>2</FONT><FONT FACE=VERDANA SIZE=2 COLOR=WHITE><B>Total Passed: " + g_iTCPassed + "</B></FONT></TD>");
	        new PrintStream(foutStrm).println("</TR></TABLE>");
	        new PrintStream(foutStrm).println("<TABLE WIDTH=100%><TR><TD ALIGN=RIGHT><FONT FACE=VERDANA COLOR=ORANGE SIZE=1>&copy; amdocs - Integrated Customer Management</FONT></TD></TR></TABLE></BODY></HTML>");
       
			//Close File stream
			foutStrm.close();
			
		} catch (IOException io) {
			io.printStackTrace();
		}

		//Deference the file pointer
		foutStrm = null;
    }
    
    
  //*****************************************************************************************
    //*    Name            : fnWriteToHtmlOutput
    //*    Description        : The function Writes output to the HTML file
    //*    Author            : Aniket Gadre
    //*    Input Params    :     
    //*                        strDescription(String) - the description of the object
    //*                        strExpectedValue(String) - the expected value
    //*                        strObtainedValue(String) - the actual/obtained value
    //*                        strResult(String) - the result (Pass/Fail)
    //*    Return Values    :     
    //*                        (Boolean) TRUE - Successful write
    //*                                  FALSE - Report file not created
    //*****************************************************************************************
    public void fnWriteToHtmlOutput(String strDescription, String strExpectedValue, String strObtainedValue, String strResult)
    {
      
        //Declaring Variables
        String snapshotFilePath,sRowColor,snapshotFile;

        //Open the test case report for writing
        //Open the HTML file
		//Open the report file to write the report
		try {
			foutStrm = new FileOutputStream(g_strTestCaseReport, true);
			//System.out.print("reports path is " + g_strTestCaseReport );

		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		}
        
        //Increment the Operation Count
        g_OperationCount = g_OperationCount + 1;
        
        //Row Color
        if (g_OperationCount % 2 == 0)
        {
            sRowColor = "#EEEEEE";
        }
        else
        {
            sRowColor = "#D3D3D3";
        
        }
        
        //Check if the result is Pass or Fail
        if (strResult.toUpperCase().equals("PASS"))
        {               
            //Increment the Pass Count
            g_iPassCount++;
            //Increment the snapshot count
            g_iSnapshotCount++;
            
            //Get the Full path of the snapshot
            snapshotFilePath = g_strSnapshotFolderName + "\\SS_" + g_iSnapshotCount + ".png";

            //Get the relative path of the snapshot
            snapshotFile = g_strSnapshotRelativePath +  "\\SS_" + g_iSnapshotCount + ".png";
            
            //Capture the Snapshot
            fTakeScreenshot(snapshotFilePath);

            
            //Write the result into the file
            //new PrintStream(foutStrm).println("<TR WIDTH=100%><TD  BGCOLOR=" + sRowColor + " WIDTH=5% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2><B>" + g_OperationCount + "</B></FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=28%><FONT FACE=VERDANA SIZE=2>" +  strDescription + " </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=25%><FONT FACE=VERDANA SIZE=2>" + strExpectedValue + " </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=25%><FONT FACE=VERDANA SIZE=2>" + strObtainedValue + " </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=7% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2 COLOR=GREEN><B>" + strResult + "</B></FONT></TD></TR>");
            new PrintStream(foutStrm).println("<TR WIDTH=100%><TD BGCOLOR=" + sRowColor + " WIDTH=5% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2 ><B>" + g_OperationCount + "</B></FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=28%><FONT FACE=VERDANA SIZE=2>" + strDescription + " </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=25%><FONT FACE=VERDANA SIZE=2>" + strExpectedValue +" </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=25%><FONT FACE=VERDANA SIZE=2>" + strObtainedValue +" </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=7% ALIGN=CENTER><A HREF='" + snapshotFile + "'><FONT FACE=VERDANA SIZE=2 COLOR=GREEN><B>" + strResult + " </B></FONT></A></TD></TR>");
        }
        else
        {
            if (strResult.toUpperCase().equals("FAIL"))
            {
                //Increment the SnapShot count
                g_iSnapshotCount++ ;

                //Increment the Fail Count
                g_iFailCount++;

                   //Get the Full path of the snapshot
                snapshotFilePath = g_strSnapshotFolderName + "\\SS_" + g_iSnapshotCount + ".png";

              //Get the relative path of the snapshot
                snapshotFile = g_strSnapshotRelativePath +  "\\SS_" + g_iSnapshotCount + ".png";

                //Increment the snapshot count
                //g_iSnapshotCount++;

                //Capture the Snapshot
                fTakeScreenshot(snapshotFilePath);
                
                //Write the result into the file
                new PrintStream(foutStrm).println("<TR WIDTH=100%><TD BGCOLOR=" + sRowColor + " WIDTH=5% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2 ><B>" + g_OperationCount + "</B></FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=28%><FONT FACE=VERDANA SIZE=2>" + strDescription + " </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=25%><FONT FACE=VERDANA SIZE=2>" + strExpectedValue +" </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=25%><FONT FACE=VERDANA SIZE=2>" + strObtainedValue +" </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=7% ALIGN=CENTER><A HREF='" + snapshotFile + "'><FONT FACE=VERDANA SIZE=2 COLOR=RED><B>" + strResult + " </B></FONT></A></TD></TR>");
            }
            else
            {                   
                //Write Results into the file
            	new PrintStream(foutStrm).println("<TR WIDTH=100%><TD BGCOLOR=" + sRowColor + " WIDTH=5% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2><B>" + g_OperationCount +"</B></FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=28%><FONT FACE=VERDANA SIZE=2>"+ strDescription +"</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=25%><FONT FACE=VERDANA SIZE=2>" + strExpectedValue + "</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=25%><FONT FACE=VERDANA SIZE=2>"+ strObtainedValue +"</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=7% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2 COLOR=orange><B>"+ strResult +"</B></FONT></TD></TR>");
            }
            
        }
        //Function call to write the summary in QC
        /*if (objTD!= null && objTD.connected() == true)
        {
	        if (QC.fQCStepUpdate(objTD, Dictionary,strDescription, strDescription, strExpectedValue, strObtainedValue, strResult)==false)
	        {
	        	System.out.println("Failed to update step in QC");
	        	System.exit(0);
	        }
        }*/
        
        try
        {			
        	//Close File stream
			foutStrm.close();
			
		} catch (IOException io) {
			io.printStackTrace();
		}
    }
 
    //*****************************************************************************************
    //*    Name        : fTakeScreenshot
    //*    Description    : The function takes the screenshot
    //*    Author        :  Anil Agarwal
    //*    Input Params    :     SSPath - Screenshot path
    //*    Return Values    :     None
    //*****************************************************************************************
	public void fTakeScreenshot(String SSPath)
    {
    	try
    	{
    	    File scrFile = ((TakesScreenshot)Global.webDriver).getScreenshotAs(OutputType.FILE);
    	    FileUtils.copyFile(scrFile, new File(SSPath));
	    	
    	} catch (IOException io) {
			io.printStackTrace();
		}
    	catch (Exception e) {
			e.printStackTrace();
		}

    }

}
