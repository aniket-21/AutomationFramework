package com.automation.framework;

public class Global {
	//Define all public static variables
	public static boolean flgJenkinsHtml = false;
	public static int g_iTestSuiteNo;

	public static Object[][] getBrowsers(){
		String[] browser = System.getProperty("browserName").split(",");
		final int size = browser.length;
		Object[][] browsers = new Object[size][1];
		for(int i=0;i<browser.length;i++) {
			System.out.println(browser[i]);
			browsers[i][0] = browser[i];
		}
		return browsers;
	}

	public static Object[][] getBrowsersForGrid(){
		String[] browser = System.getProperty("browserName").split(",");
		final int size = browser.length;
		Object[][] browsers = new Object[size][3];
		for(int i=0;i<browser.length;i++) {
			System.out.println(browser[i]);
			browsers[i][0] = browser[i].split("-")[0];
			browsers[i][1] = browser[i].split("-")[1];
			browsers[i][2] = browser[i].split("-")[2];
		}
		return browsers;
	}

}
