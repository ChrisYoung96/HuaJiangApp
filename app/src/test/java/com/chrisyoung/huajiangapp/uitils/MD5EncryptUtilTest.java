package com.chrisyoung.huajiangapp.uitils;

import org.junit.Assert;
import org.junit.Test;

public class MD5EncryptUtilTest {

    @Test
    public void MD5() {
        String s="123456";
        String a=MD5EncryptUtil.getEncrypt(s);
        System.out.println("加密应为：E10ADC3949BA59ABBE56E057F20F883E");
        System.out.println(a);
        Assert.assertEquals("E10ADC3949BA59ABBE56E057F20F883E",a);

    }
}