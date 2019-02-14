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
    private long createTime;

    public VerificationCode(String code) {
        this.code = code;
        this.createTime = System.currentTimeMillis();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}