package com.framework.helpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Timestamp;

/**
 * Created by ANIKETG on 10/3/2014.
 */
public class Generic {

    public static String getTimeStamp(){
        java.util.Date today = new java.util.Date();
        Timestamp now = new java.sql.Timestamp(today.getTime());
        String timeStamp = now.toString().replaceAll(":", "").replaceAll("-", "").replaceAll(" ", "");
        timeStamp = timeStamp.split("\\.")[0];
        return timeStamp;
    }

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

    public static String getTimeDifference(long startTime, long endTime) {

        //Finding the difference in milliseconds
        long delta = endTime - startTime;
        int days = (int) delta / (24 * 3600 * 1000); //Finding number of days
        delta = (int) delta % (24 * 3600 * 1000); //Finding the remainder
        int hrs = (int) delta / (3600 * 1000); //Finding number of hrs
        delta = (int) delta % (3600 * 1000); //Finding the remainder
        int min = (int) delta / (60 * 1000); //Finding number of minutes
        delta = (int) delta % (60 * 1000); //Finding the remainder
        int sec = (int) delta / 1000; //Finding number of seconds

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
