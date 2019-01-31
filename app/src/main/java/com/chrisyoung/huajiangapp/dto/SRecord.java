package com.chrisyoung.huajiangapp.dto;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

public class SRecord {

  private String rId; //订单编号
  private String bId; //账本编号
  private String rType; //收入或支出
  private String rKind; //收入或支出的类型（支持自定义）
  private BigDecimal rMoney; //金额
  private String rWay; //支付方式


  private Timestamp rTime; //交易时间

  private String rDesc; //备注

  private int rVersioin;


  public SRecord(){
    this.rId=UUID.randomUUID().toString().replace("-","");
    this.bId="";
    this.rType="";
    this.rKind="";
    this.rMoney=BigDecimal.valueOf(0,2);
    this.rWay="";
    this.rTime=Timestamp.valueOf("2000-01-01 00:00:00");
    this.rDesc="";
  }

  public String getRId() {
    return rId;
  }

  public void setRId(String rId) {
    this.rId = rId;
  }


  public String getBId() {
    return bId;
  }

  public void setBId(String bId) {
    this.bId = bId;
  }


  public String getRType() {
    return rType;
  }

  public void setRType(String rType) {
    this.rType = rType;
  }


  public String getRKind() {
    return rKind;
  }

  public void setRKind(String rKind) {
    this.rKind = rKind;
  }


  public BigDecimal getRMoney() {
    return rMoney;
  }

  public void setRMoney(BigDecimal rMoney) {
    this.rMoney = rMoney;
  }

  public String getrWay() {
    return rWay;
  }

  public void setrWay(String rWay) {
    this.rWay = rWay;
  }


  public Timestamp getRTime() {
    return rTime;
  }


  public void setRTime(Timestamp rTime) {
    this.rTime = rTime;
  }


  public String getRDesc() {
    return rDesc;
  }

  public void setRDesc(String rDesc) {
    this.rDesc = rDesc;
  }

  public int getrVersioin() {
    return rVersioin;
  }

  public void setrVersioin(int rVersioin) {
    this.rVersioin = rVersioin;
  }
}
