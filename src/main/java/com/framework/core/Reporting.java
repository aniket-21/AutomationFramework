package com.framework.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;

import com.framework.Global;
import com.framework.helpers.Generic;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Reporting {
	
	private String testCaseReport;
    private String snapshotFolderName;
    private String snapshotRelativePath;
	private String summaryReportPath;
    private String scriptName;

    //Counters and Integers
    private int snapshotCount;
    private int operationCount;
    private int passCount;
    private int failCount;
    private int tcsPassed;
    private int testCaseNo;

    private Date startTime;
    private Date endTime;

    private WebDriver driver;
	private HashMap <String, String> dictionary = new HashMap<String, String>();
	private HashMap <String, String> environment = new HashMap<String, String>();
	
	private FileOutputStream foutStrm = null;
	
	public Reporting(WebDriver GDriver, HashMap <String, String> GDictionary, 	HashMap <String, String> GEnvironment) {
		this(GDictionary, GEnvironment);
        driver = GDriver;
	}

    public Reporting(HashMap <String, String> dictionary, 	HashMap <String, String> environment) {
        this.dictionary = dictionary;
        this.environment = environment;
        this.summaryReportPath = environment.get("HTMLREPORTSPATH")+ "/SummaryReport.html";
    }

    public void setDriver(WebDriver webDriver){
        driver = webDriver;
    }

    public void createSummaryReport() {
        //Setting counter value
        tcsPassed = 0;
        testCaseNo = 0;
        //Date summaryStartTime = new Date();
        
		try {
	        //Open the test case report for writing                   
	        foutStrm = new FileOutputStream(summaryReportPath, true);
	           
			//Close the html file
	        new PrintStream(foutStrm).println("<HTML><BODY><TABLE BORDER=0 CELLPADDING=3 CELLSPACING=1 WIDTH=100% BGCOLOR=BLACK>");
			new PrintStream(foutStrm).println("<TR><TD WIDTH=90% ALIGN=CENTER BGCOLOR=WHITE><FONT FACE=VERDANA COLOR=ORANGE SIZE=3><B>AUTODESK</B></FONT></TD></TR><TR><TD ALIGN=CENTER BGCOLOR=ORANGE><FONT FACE=VERDANA COLOR=WHITE SIZE=3><B>Selenium Framework Reporting</B></FONT></TD></TR></TABLE><TABLE CELLPADDING=3 WIDTH=100%><TR height=30><TD WIDTH=100% ALIGN=CENTER BGCOLOR=WHITE><FONT FACE=VERDANA COLOR=//0073C5 SIZE=2><B>&nbsp; Automation Result : " + new Date() + " on Machine " + InetAddress.getLocalHost().getHostName() + " by user " + System.getProperty("user.name") + "</B></FONT></TD></TR><TR HEIGHT=5></TR></TABLE>");
	        new PrintStream(foutStrm).println("<TABLE  CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");           
	        new PrintStream(foutStrm).println("<TR COLS=6 BGCOLOR=ORANGE><TD WIDTH=10%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>TC No.</B></FONT></TD><TD  WIDTH=70%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Test Name</B></FONT></TD><TD BGCOLOR=ORANGE WIDTH=30%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Status</B></FONT></TD></TR>");
	        
	        //Close the object
	        foutStrm.close();
	        
		} catch (IOException io) {
			io.printStackTrace();
		} 
		
		foutStrm = null;
    }	

	public void createTestLevelReport(String strTestName) {

        operationCount = 0;
        passCount = 0;
        failCount = 0;
        snapshotCount = 0;
        scriptName = strTestName;

        testCaseReport = environment.get("HTMLREPORTSPATH") + "/Report_" + scriptName + ".html";

        snapshotFolderName = environment.get("SNAPSHOTSFOLDER") + "/" + scriptName;
        snapshotRelativePath = "Snapshots/" + scriptName;

		File file = new File(snapshotFolderName);
		if (file.exists())
			file.delete();

		//Make a new snapshot folder
		file.mkdir();

		//Open the report file to write the report
		try {
			foutStrm = new FileOutputStream(testCaseReport);
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		}

		//Close the html file
		try {
			new PrintStream(foutStrm).println("<HTML><BODY><TABLE BORDER=0 CELLPADDING=3 CELLSPACING=1 WIDTH=100% BGCOLOR=ORANGE>");
			new PrintStream(foutStrm).println("<TR><TD WIDTH=90% ALIGN=CENTER BGCOLOR=WHITE><FONT FACE=VERDANA COLOR=ORANGE SIZE=3><B>AUTODESK</B></FONT></TD></TR><TR><TD ALIGN=CENTER BGCOLOR=ORANGE><FONT FACE=VERDANA COLOR=WHITE SIZE=3><B>Selenium Framework Reporting</B></FONT></TD></TR></TABLE><TABLE CELLPADDING=3 WIDTH=100%><TR height=30><TD WIDTH=100% ALIGN=CENTER BGCOLOR=WHITE><FONT FACE=VERDANA COLOR=//0073C5 SIZE=2><B>&nbsp; Automation Result : " + new Date() + " on Machine " + InetAddress.getLocalHost().getHostName() + " by user " + System.getProperty("user.name") + "</B></FONT></TD></TR><TR HEIGHT=5></TR></TABLE>");
			new PrintStream(foutStrm).println("<TABLE BORDER=0 BORDERCOLOR=WHITE CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
			new PrintStream(foutStrm).println("<TR><TD BGCOLOR=BLACK WIDTH=20%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Test     Name:</B></FONT></TD><TD COLSPAN=6 BGCOLOR=BLACK><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>" + scriptName + "</B></FONT></TD></TR>");
			new PrintStream(foutStrm).println("</TABLE><BR/><TABLE WIDTH=100% CELLPADDING=3>");
			new PrintStream(foutStrm).println("<TR WIDTH=100%><TH BGCOLOR=ORANGE WIDTH=5%><FONT FACE=VERDANA SIZE=2>Step No.</FONT></TH><TH BGCOLOR=ORANGE WIDTH=28%><FONT FACE=VERDANA SIZE=2>Step Description</FONT></TH><TH BGCOLOR=ORANGE WIDTH=25%><FONT FACE=VERDANA SIZE=2>Expected Value</FONT></TH><TH BGCOLOR=ORANGE WIDTH=25%><FONT FACE=VERDANA SIZE=2>Obtained Value</FONT></TH><TH BGCOLOR=ORANGE WIDTH=7%><FONT FACE=VERDANA SIZE=2>Result</FONT></TH></TR>");
		
			foutStrm.close();
		} catch (IOException io) {
			io.printStackTrace();
		}
		//Deference the file pointer
		foutStrm = null;

		//Get the start time of the execution
		startTime = new Date();
	}

	public void closeTestLevelReport(String strTestName) {

		String strTestCaseResult = null;

		try {
			foutStrm = new FileOutputStream(testCaseReport, true);
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		}

		//Get the current time
		endTime = new Date();
		
		//Fetch the time difference
		String strTimeDifference = Generic.getTimeDifference(startTime.getTime(), endTime.getTime());

		try {
			new PrintStream(foutStrm).println("<TR></TR><TR><TD BGCOLOR=BLACK WIDTH=5%></TD><TD BGCOLOR=BLACK WIDTH=28%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Time Taken : "+ strTimeDifference + "</B></FONT></TD><TD BGCOLOR=BLACK WIDTH=25%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Pass Count : " + passCount + "</B></FONT></TD><TD BGCOLOR=BLACK WIDTH=25%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Fail Count : " + failCount + "</b></FONT></TD><TD BGCOLOR=Black WIDTH=7%></TD></TR>");
			new PrintStream(foutStrm).println("</TABLE><TABLE WIDTH=100%><TR><TD ALIGN=RIGHT><FONT FACE=VERDANA COLOR=ORANGE SIZE=1>&copy; Autodesk</FONT></TD></TR></TABLE></BODY></HTML>");
			//Close File stream
			foutStrm.close();
		} catch (IOException io) {
			io.printStackTrace();
		}

		//Deference the file pointer
		foutStrm = null;

		if (failCount != 0) strTestCaseResult = "Fail";
		else strTestCaseResult = "Pass";

        writeToTestSummary("Report_" + strTestName, strTestCaseResult);
	}

    public void writeToTestSummary(String strTestCaseName, String strResult) {
        String sColor,sRowColor;

        try {
	        //Open the test case report for writing                   
	        foutStrm = new FileOutputStream(summaryReportPath, true);
	        
	        //Check color result
	        if (strResult.toUpperCase().equals("PASSED") || strResult.toUpperCase().equals("PASS")){
	            sColor = "GREEN";
	            tcsPassed++;
	        }
	        else if (strResult.toUpperCase().equals("FAILED") || strResult.toUpperCase().equals("FAIL"))
	        	sColor = "RED";
	        else
	        	sColor = "ORANGE";
	
	        testCaseNo++;
	
	        if (testCaseNo % 2 == 0)
	        	sRowColor = "#EEEEEE";
	        else
	        	sRowColor = "#D3D3D3";

	        new PrintStream(foutStrm).println ("<TR COLS=3 BGCOLOR=" + sRowColor + "><TD  WIDTH=10%><FONT FACE=VERDANA SIZE=2>" + testCaseNo + "</FONT></TD><TD  WIDTH=70%><FONT FACE=VERDANA SIZE=2>" + strTestCaseName + "</FONT></TD><TD  WIDTH=20%><A HREF='" + strTestCaseName + ".html'><FONT FACE=VERDANA SIZE=2 COLOR=" + sColor + "><B>" + strResult + "</B></FONT></A></TD></TR>");
        	foutStrm.close();
        }
        catch (IOException io) {
			io.printStackTrace();
		}
       foutStrm = null;
    }

    public void closeTestSummaryReport() {
        //Date summaryEndTime = new Date();
        
        //Open the Test Summary Report File
		try {         
			foutStrm = new FileOutputStream(summaryReportPath, true);
       
            new PrintStream(foutStrm).println("</TABLE><TABLE WIDTH=100%><TR>");
	        new PrintStream(foutStrm).println("<TD BGCOLOR=BLACK WIDTH=10%></TD><TD BGCOLOR=BLACK WIDTH=70%><FONT FACE=VERDANA SIZE=2 COLOR=WHITE><B></B></FONT></TD><TD BGCOLOR=BLACK WIDTH=20%><FONT FACE=WINGDINGS SIZE=4>2</FONT><FONT FACE=VERDANA SIZE=2 COLOR=WHITE><B>Total Passed: " + tcsPassed + "</B></FONT></TD>");
	        new PrintStream(foutStrm).println("</TR></TABLE>");
	        new PrintStream(foutStrm).println("<TABLE WIDTH=100%><TR><TD ALIGN=RIGHT><FONT FACE=VERDANA COLOR=ORANGE SIZE=1>&copy; Autodesk</FONT></TD></TR></TABLE></BODY></HTML>");

			foutStrm.close();
			
		} catch (IOException io) {
			io.printStackTrace();
		}

		//Deference the file pointer
		foutStrm = null;
    }

    public void writeToTestLevelReport(String strDescription, String strExpectedValue, String strObtainedValue, String strResult) {
        String snapshotFilePath,sRowColor,snapshotFile;

		try {
			foutStrm = new FileOutputStream(testCaseReport, true);
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		}
        
        //Increment the Operation Count
        operationCount = operationCount + 1;
        
        //Row Color
        if (operationCount % 2 == 0)
        	sRowColor = "#EEEEEE";
        else
        	sRowColor = "#D3D3D3";
        
        //Check if the result is Pass or Fail
        if (strResult.toUpperCase().equals("PASS")){
            passCount++;
            snapshotCount++;

            snapshotFilePath = snapshotFolderName + "/SS_" + snapshotCount + ".png";
            snapshotFile = snapshotRelativePath +  "/SS_" + snapshotCount + ".png";
            takeScreenshot(snapshotFilePath);

            new PrintStream(foutStrm).println("<TR WIDTH=100%><TD BGCOLOR=" + sRowColor + " WIDTH=5% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2 ><B>" + operationCount + "</B></FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=28%><FONT FACE=VERDANA SIZE=2>" + strDescription + " </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=25%><FONT FACE=VERDANA SIZE=2>" + strExpectedValue +" </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=25%><FONT FACE=VERDANA SIZE=2>" + strObtainedValue +" </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=7% ALIGN=CENTER><A HREF='" + snapshotFile + "'><FONT FACE=VERDANA SIZE=2 COLOR=GREEN><B>" + strResult + " </B></FONT></A></TD></TR>");
        }
        else {
            if (strResult.toUpperCase().equals("FAIL")) {
                snapshotCount++ ;
                failCount++;

                snapshotFilePath = snapshotFolderName + "/SS_" + snapshotCount + ".png";
                snapshotFile = snapshotRelativePath +  "/SS_" + snapshotCount + ".png";
                takeScreenshot(snapshotFilePath);

                new PrintStream(foutStrm).println("<TR WIDTH=100%><TD BGCOLOR=" + sRowColor + " WIDTH=5% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2 ><B>" + operationCount + "</B></FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=28%><FONT FACE=VERDANA SIZE=2>" + strDescription + " </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=25%><FONT FACE=VERDANA SIZE=2>" + strExpectedValue +" </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=25%><FONT FACE=VERDANA SIZE=2>" + strObtainedValue +" </FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=7% ALIGN=CENTER><A HREF='" + snapshotFile + "'><FONT FACE=VERDANA SIZE=2 COLOR=RED><B>" + strResult + " </B></FONT></A></TD></TR>");
            }
            else {
            	new PrintStream(foutStrm).println("<TR WIDTH=100%><TD BGCOLOR=" + sRowColor + " WIDTH=5% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2><B>" + operationCount +"</B></FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=28%><FONT FACE=VERDANA SIZE=2>"+ strDescription +"</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=25%><FONT FACE=VERDANA SIZE=2>" + strExpectedValue + "</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=25%><FONT FACE=VERDANA SIZE=2>"+ strObtainedValue +"</FONT></TD><TD BGCOLOR=" + sRowColor + " WIDTH=7% ALIGN=CENTER><FONT FACE=VERDANA SIZE=2 COLOR=orange><B>"+ strResult +"</B></FONT></TD></TR>");
            }
        }

        try {
			foutStrm.close();
		} catch (IOException io) {
			io.printStackTrace();
		}
    }

	public void takeScreenshot(String SSPath){
    	try {
    	    File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    	    FileUtils.copyFile(scrFile, new File(SSPath));
	    	
    	} catch (IOException io) {
			io.printStackTrace();
		}
    	catch (Exception e) {
			e.printStackTrace();
		}
    }

    public void createJenkinsReport() {
    	String jenkinsFilePath = environment.get("EXECUTIONFOLDERPATH") + "/" + environment.get("ENV_CODE");
    	String jenkinsHTMLRep = jenkinsFilePath + "/Jenkins_html_report.html";
    	String relativeClassSummary = environment.get("CLASSNAME") + "/" + environment.get("BROWSER").toUpperCase() + "_HTML_Reports" + "/SummaryReport.html";
    	String sRowColor = "";    	

    	try {
    		File htmlReport = new File(jenkinsHTMLRep);
    		
    		//If jenkins flag is false 
    		if(Global.flgJenkinsHtml == false) {
    	        Global.flgJenkinsHtml = true;

    			if(htmlReport.exists())
    				htmlReport.delete();

    	        foutStrm = new FileOutputStream(jenkinsHTMLRep, true);

    	        new PrintStream(foutStrm).println("<HTML><BODY><TABLE BORDER=0 CELLPADDING=3 CELLSPACING=1 WIDTH=100% BGCOLOR=BLACK>");
    			new PrintStream(foutStrm).println("<TR><TD WIDTH=90% ALIGN=CENTER BGCOLOR=WHITE><FONT FACE=VERDANA COLOR=ORANGE SIZE=3><B>AUTODESK</B></FONT></TD></TR><TR><TD ALIGN=CENTER BGCOLOR=ORANGE><FONT FACE=VERDANA COLOR=WHITE SIZE=3><B>Selenium Framework Reporting</B></FONT></TD></TR></TABLE><TABLE CELLPADDING=3 WIDTH=100%><TR height=30><TD WIDTH=100% ALIGN=CENTER BGCOLOR=WHITE><FONT FACE=VERDANA COLOR=//0073C5 SIZE=2><B>&nbsp; Automation Result : " + new Date() + " on Machine " + InetAddress.getLocalHost().getHostName() + " by user " + System.getProperty("user.name") + "</B></FONT></TD></TR><TR HEIGHT=5></TR></TABLE>");
    	        new PrintStream(foutStrm).println("<TABLE  BORDER=1 CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");           
    	        new PrintStream(foutStrm).println("<TR COLS=6 BGCOLOR=ORANGE><TD WIDTH=10%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Suite No.</B></FONT></TD><TD  WIDTH=90%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Suite Name</B></FONT></TD></TR>");

    	        foutStrm.close();  	            	      
    		}    	           		

	        foutStrm = new FileOutputStream(jenkinsHTMLRep, true);

	        Global.g_iTestSuiteNo++;
	
	        if (Global.g_iTestSuiteNo % 2 == 0)
	        	sRowColor = "#EEEEEE";
	        else
	        	sRowColor = "#D3D3D3";

	        new PrintStream(foutStrm).println ("<TR COLS=3 BGCOLOR=" + sRowColor + "><TD  WIDTH=10%><FONT FACE=VERDANA SIZE=2>" + Global.g_iTestSuiteNo + "</FONT></TD><TD  WIDTH=90%><A HREF='" + relativeClassSummary + "'><FONT FACE=VERDANA SIZE=2 COLOR=BLUE><FONT FACE=VERDANA SIZE=2><B>" + environment.get("CLASSNAME") + "_" + environment.get("BROWSER").toLowerCase() + "</B></FONT></A></TD></TR>");
	        foutStrm.close();

		} catch (IOException io) {
			io.printStackTrace();
		} 
		
		foutStrm = null;
    }	

}
