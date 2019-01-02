package com.chrisyoung.huajiangapp.uitils;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatUtil {
    private static int [][] days={{31,28,31,30,31,30,31,31,30,31,30,31},
                           {31,29,31,30,31,30,31,31,30,31,30,31}};


    private static boolean isLeapYear(int year){
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    private static int getDay(String y,String m){
        int year=Integer.valueOf(y);
        int month=Integer.valueOf(m);
        if(isLeapYear(year)){
            return days[1][month-1];
        }else{
            return days[0][month-1];
        }
    }


    public static Date stringtoDateAndTime(String sdate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
        ParsePosition pos = new ParsePosition(0);
        Date date = format.parse(sdate, pos);
        return date;
    }

    public static String dateAndTimeToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
        String dateString = formatter.format(date);
        return dateString;
    }

    public static Date stringToDate(String sdate){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
        ParsePosition pos = new ParsePosition(0);
        Date date = format.parse(sdate, pos);
        return date;
    }

    public static String dateToString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
        String dateString = formatter.format(date);
        return dateString;
    }

    public static Timestamp dateToTimestamp(Date date){
        return new Timestamp(date.getTime());
    }

    public static Date timestampToDate(Timestamp timestamp){
        return new Date(timestamp.getTime());
    }

    public static Date getStartOfMonth(String year,String month){
        String sd=year+"-"+month+"-01 00:00:00";
        return stringtoDateAndTime(sd);
    }

    public static Date getEndOfMonth(String year,String month) {
        String sd = year + "-" + month + "-" + getDay(year, month) + " 00:00:00";
        return stringtoDateAndTime(sd);
    }



}