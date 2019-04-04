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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss",Locale.CHINA);
        String dateString = formatter.format(date);
        return dateString;
    }

    public static Date stringToDate(String sdate){
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日",Locale.CHINA);
        ParsePosition pos = new ParsePosition(0);
        Date date = format.parse(sdate, pos);
        return date;
    }

    public static String dateToString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日",Locale.CHINA);
        String dateString = formatter.format(date);
        return dateString;
    }

    public static String getMothAndDay(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日",Locale.CHINA);
        String dateString = formatter.format(date);
        return dateString;
    }

    public static String getDay(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd",Locale.CHINA);
        String dateString = formatter.format(date);
        return dateString;
    }

    public static String getMonth(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("MM",Locale.CHINA);
        String dateString = formatter.format(date);
        return dateString;
    }

    public static String getYear(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy",Locale.CHINA);
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


    public static Date getStartOfMonth(long millseconds){
       // String sd=dateToString(new Date(millseconds));
        //sd +=" 00:00:00";
        Date date=new Date(millseconds);
        String year=getYear(date);
        String month=getMonth(date);
        String sd = year + "-" + month + "-01" + " 00:00:00";
        return stringtoDateAndTime(sd);

    }


    public static Date getEndOfMonth(String year,String month) {
        String sd = year + "-" + month + "-" + getDay(year, month) + " 23:59:59";
        return stringtoDateAndTime(sd);
    }

    public static Date getEndOfMonth(long millseconds){
       // String sd=dateToString(new Date(millseconds));
      //  String year=sd.substring(0,4);
       // String month=sd.substring(5,7);
        Date date=new Date(millseconds);
        String year=getYear(date);
        String month=getMonth(date);
        String sd = year + "-" + month + "-" + getDay(year, month) + " 23:59:59";
        return stringtoDateAndTime(sd);
    }

    public static String getYearAndMonth(long millseconds){
        Date date=new Date(millseconds);
        String year=getYear(date);
        String month=getMonth(date);
        return year + "年" + month + "月" ;

    }


    public static Date getStartOfDay(Date day){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
        String temp = formatter.format(day);
        temp+=" 00:00:00";
        return stringtoDateAndTime(temp);
    }

    public static  Date getEndOfDay(Date day){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
        String temp = formatter.format(day);
        temp+=" 23:59:59";
        return stringtoDateAndTime(temp);
    }

    public static Date longDateToshortDate(Date ldate){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
        String temp = formatter.format(ldate);
        return stringToDate(temp);
    }




}
