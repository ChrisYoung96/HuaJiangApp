package com.chrisyoung.huajiangapp.dto;

import java.sql.Time;

/**
 * @program: appserver
 * @author: Chris Young
 * @create: 2018-12-03 17:37
 * @description: 验证码
 **/


public class VerificationCode {
    private String code;
    private Time createTime;
    private Time verifyTime;

    public VerificationCode(String code) {
        this.code=code;
        this.createTime=new Time(System.currentTimeMillis());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Time getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Time createTime) {
        this.createTime = createTime;
    }

    public Time getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(Time verifyTime) {
        this.verifyTime = verifyTime;
    }
}
