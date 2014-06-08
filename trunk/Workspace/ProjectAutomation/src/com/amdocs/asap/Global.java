package com.amdocs.asap;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import com.amdocs.asap.Reporting;

public class Global {
	
	//Define all public static variables
	public static HashMap <String, String> Environment = new HashMap<String, String>();
	public static HashMap <String, String> Dictionary = new HashMap<String, String>();
	public static final Reporting Reporter = new Reporting();
	public static WebDriver webDriver;

}
