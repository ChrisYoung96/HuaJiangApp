package com.chrisyoung.huajiangapp.dto;


import java.io.Serializable;

public class UserDiy implements Serializable {

  private String dId; //自定义收支类型编号
  private String uId; //用户ID
  private String dType; //收入或支出
  private String dKind; //自定义类型
  private int dVersion;//记录版本
  private int delflag=0;



  public String getdId() {
    return dId;
  }

  public void setdId(String dId) {
    this.dId = dId;
  }


  public String getuId() {
    return uId;
  }

  public void setuId(String uId) {
    this.uId = uId;
  }


  public String getdType() {
    return dType;
  }

  public void setdType(String dType) {
    this.dType = dType;
  }


  public String getdKind() {
    return dKind;
  }

  public void setdKind(String dKind) {
    this.dKind = dKind;
  }

  public int getdVersion() {
    return dVersion;
  }

  public void setdVersion(int dVersion) {
    this.dVersion = dVersion;
  }

  public int getDelflag() {
    return delflag;
  }

  public void setDelflag(int delflag) {
    this.delflag = delflag;
  }
}
