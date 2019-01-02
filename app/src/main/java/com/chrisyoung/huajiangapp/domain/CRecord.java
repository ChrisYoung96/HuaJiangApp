package com.chrisyoung.huajiangapp.domain;

import com.chrisyoung.huajiangapp.uitils.UUIDUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

public class CRecord extends RealmObject {
    @PrimaryKey
    private String rId; //订单编号
    private String bId; //账本编号
    private String rType; //收入或支出
    private String rKind; //收入或支出的类型（支持自定义）
    private Double rMoney; //金额
    private String rWay; //支付方式


    @Index
    private Date rTime; //交易时间

    private String rDesc; //备注
    private boolean isSychronized;

    public CRecord() {
        rId=UUIDUtils.getUUID();
    }

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    public String getbId() {
        return bId;
    }

    public void setbId(String bId) {
        this.bId = bId;
    }

    public String getrType() {
        return rType;
    }

    public void setrType(String rType) {
        this.rType = rType;
    }

    public String getrKind() {
        return rKind;
    }

    public void setrKind(String rKind) {
        this.rKind = rKind;
    }

    public Double getrMoney() {
        return rMoney;
    }

    public void setrMoney(Double rMoney) {
        this.rMoney = rMoney;
    }

    public String getrWay() {
        return rWay;
    }

    public void setrWay(String rWay) {
        this.rWay = rWay;
    }

    public Date getrTime() {
        return rTime;
    }

    public void setrTime(Date rTime) {
        this.rTime = rTime;
    }

    public String getrDesc() {
        return rDesc;
    }

    public void setrDesc(String rDesc) {
        this.rDesc = rDesc;
    }

    public boolean isSychronized() {
        return isSychronized;
    }

    public void setSychronized(boolean sychronized) {
        isSychronized = sychronized;
    }
}
