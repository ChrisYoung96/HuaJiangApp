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
        if(sdate!=null&&!sdate.equals("")){
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss",Locale.CHINA);
            ParsePosition pos = new ParsePosition(0);
            return format.parse(sdate, pos);
        }else{
            return stringToDate("1800年01月01日");
        }

    }

    public static String dateAndTimeToString(Date date) {
    if(date!=null){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss",Locale.CHINA);
        return formatter.format(date);
    }else{
        return "";
    }

    }

    public static Date stringToDate(String sdate){
        if(sdate!=null&&!sdate.equals("")){
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日",Locale.CHINA);
            ParsePosition pos = new ParsePosition(0);
            return format.parse(sdate, pos);
        }else {
            return stringToDate("1800年01月01日");
        }

    }

    public static String dateToString(Date date){
        if(date!=null){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日",Locale.CHINA);
            return formatter.format(date);
        }else{
            return "";
        }

    }

    public static String getMothAndDay(Date date){
        if(date!=null){
            SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日",Locale.CHINA);
            return formatter.format(date);
        }else{
            return "";
        }

    }

    public static String getDay(Date date){
        if(date!=null){
            SimpleDateFormat formatter = new SimpleDateFormat("dd",Locale.CHINA);
            return formatter.format(date);
        }else{
            return "";
        }

    }

    public static String getMonth(Date date){
        if(date!=null){
            SimpleDateFormat formatter = new SimpleDateFormat("MM",Locale.CHINA);
            return formatter.format(date);
        }else{
            return "";
        }

    }

    public static String getYear(Date date){
        if(date!=null){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy",Locale.CHINA);
            return formatter.format(date);
        }else{
            return "";
        }


    }

    public static Timestamp dateToTimestamp(Date date){
        if(date!=null){
            return new Timestamp(date.getTime());
        }else {
            return new Timestamp(System.currentTimeMillis());
        }
    }

    public static Date timestampToDate(Timestamp timestamp){
        if(timestamp!=null){
            return new Date(timestamp.getTime());
        }else{
            return new Date();
        }

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
        String sd = year + "年" + month + "月01日" + " 00:00:00";
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
        String sd = year + "年" + month + "月" + getDay(year, month) + "日 23:59:59";
        return stringtoDateAndTime(sd);
    }

    public static String getYearAndMonth(long millseconds){
        Date date=new Date(millseconds);
        String year=getYear(date);
        String month=getMonth(date);
        return year + "年" + month + "月" ;

    }


    public static Date getStartOfDay(Date day){
        if(day!=null){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日",Locale.CHINA);
            String temp = formatter.format(day);
            temp+=" 00:00:00";
            return stringtoDateAndTime(temp);
        }else{
            return stringToDate("1800年01月01日");
        }

    }

    public static  Date getEndOfDay(Date day){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日",Locale.CHINA);
        String temp = formatter.format(day);
        temp+=" 23:59:59";
        return stringtoDateAndTime(temp);
    }

    public static Date longDateToshortDate(Date ldate){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss",Locale.CHINA);
        String temp = formatter.format(ldate);
        return stringToDate(temp);
    }




}
