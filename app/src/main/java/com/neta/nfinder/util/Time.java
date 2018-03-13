package com.neta.nfinder.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Berfu on 4.12.2016.
 */

public class Time {

    public static String timeStampToDate(Long timestamp){
        Calendar myCal = Calendar.getInstance();
        myCal.setTimeInMillis(timestamp*1000);
        Date dateText = new Date(myCal.get(Calendar.YEAR)-1900,
                myCal.get(Calendar.MONTH),
                myCal.get(Calendar.DAY_OF_MONTH),
                myCal.get(Calendar.HOUR_OF_DAY),
                myCal.get(Calendar.MINUTE));

        return android.text.format.DateFormat.format("dd.MM.yyyy hh:mm", dateText).toString();
    }

    public static String milisecondToMinute(Long milisecond)
    {
        double s = milisecond / 1000;
        int second = (int) s;
        int minute = (int)second/ 60;
        second = second - minute*60;

        return String.valueOf(minute) + ":" + String.valueOf(second);
    }

    public static String secondToMinute(Long second){
        Long minute = second/60;
        second = second%60;
        return String.valueOf(Long.toString(minute)+":"+Long.toString(second));
    }
}
