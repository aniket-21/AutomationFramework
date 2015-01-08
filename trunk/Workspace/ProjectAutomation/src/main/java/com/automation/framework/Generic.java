package com.automation.framework;

import java.sql.Timestamp;

/**
 * Created by ANIKETG on 10/3/2014.
 */
public class Generic {

    //*****************************************************************************************
    //*	Name		    : getTimeStamp
    //*	Author		    : Aniket Gadre
    //*****************************************************************************************
    public String getTimeStamp(){
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
    public String getRandomString(){

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
}
