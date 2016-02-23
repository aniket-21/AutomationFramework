package com.framework.components.helpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Timestamp;

/**
 * Created by ANIKETG on 10/3/2014.
 */
public class Generic {

    //*****************************************************************************************
    //*	Name		    : getTimeStamp
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public static String getTimeStamp(){
        java.util.Date today = new java.util.Date();
        Timestamp now = new java.sql.Timestamp(today.getTime());
        String timeStamp = now.toString().replaceAll(":", "").replaceAll("-", "").replaceAll(" ", "");
        timeStamp = timeStamp.split("\\.")[0];
        return timeStamp;
    }

    //*****************************************************************************************
    //*	Name		    : getRandomString()
    //*	Description	    : returns current time stamp
    //*	Author		    : Aniket Gadre
    //*	Input Params	: strDesc - The description of the object to click
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public static String getRandomString(){

        //Get time stamp
        String timeStamp = getTimeStamp();
        String Random = "";

        int z;

        int iLen = timeStamp.length();
        for(int i=0;i<iLen;i++){
            z = (int)(timeStamp.substring(i, i+1).toCharArray()[0]) - 48 + 97;
            Random = Random + (char)z;
        }

        return Random;
    }

    //*****************************************************************************************
    //*    Name         : getTimeDifference
    //*    Description  : The function takes the screenshot
    //*    Author       : Aniket Gadre
    //*    Input Params : startTime - Start time in long format
    //*					  endTime - End time in long format
    //*    Return Values: None
    //*****************************************************************************************
    public static String getTimeDifference(long startTime, long endTime) {

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

    public static String executeCommand(String command) {

        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }
}
