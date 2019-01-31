package com.chrisyoung.huajiangapp.dto;


public class SUserDiy {

  private String dId; //自定义收支类型编号
  private String uId; //用户ID
  private String dType; //收入或支出
  private String dKind; //自定义类型
  private int dVersion;//记录版本


  public String getDId() {
    return dId;
  }

  public void setDId(String dId) {
    this.dId = dId;
  }


  public String getUId() {
    return uId;
  }

  public void setUId(String uId) {
    this.uId = uId;
  }


  public String getDType() {
    return dType;
  }

  public void setDType(String dType) {
    this.dType = dType;
  }


  public String getDKind() {
    return dKind;
  }

  public void setDKind(String dKind) {
    this.dKind = dKind;
  }

  public int getdVersion() {
    return dVersion;
  }

  public void setdVersion(int dVersion) {
    this.dVersion = dVersion;
  }

}
