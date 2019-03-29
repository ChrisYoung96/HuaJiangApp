package com.chrisyoung.huajiangapp.uitils;

import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

public class DateFormatUtilTest {

    @Test
    public void stringtoDate() {
        String s="2018-01-01 12:10:10";
        Date d=DateFormatUtil.stringtoDateAndTime(s);
        System.out.println(d);
        String sd=DateFormatUtil.dateAndTimeToString(d);
        System.out.println(sd);
        System.out.println("Date转换为TimeStamp:"+DateFormatUtil.dateToTimestamp(d).toString());
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        System.out.println("时间戳为："+timestamp.toString());
        Date da=DateFormatUtil.timestampToDate(timestamp);
        System.out.println("转换为Date后："+da.toString());
        System.out.println("转换为String后："+DateFormatUtil.dateAndTimeToString(da));
    }


    @Test
    public void test1(){
        long time=System.currentTimeMillis();
        Date date=new Date(time);
        String st="2019年03月29日 10:03:24";
        System.out.println(DateFormatUtil.getYear(date));
        System.out.println(DateFormatUtil.getMonth(date));
        System.out.println(DateFormatUtil.getDay(date));
        System.out.println(DateFormatUtil.dateAndTimeToString(date));
        System.out.println(DateFormatUtil.getStartOfMonth(time));
        System.out.println(DateFormatUtil.getEndOfMonth(time));
        System.out.println(DateFormatUtil.getMothAndDay(date));
        System.out.println(DateFormatUtil.getYearAndMonth(time));

    }
}