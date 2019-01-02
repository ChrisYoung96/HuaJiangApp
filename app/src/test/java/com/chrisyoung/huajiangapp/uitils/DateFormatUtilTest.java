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
}