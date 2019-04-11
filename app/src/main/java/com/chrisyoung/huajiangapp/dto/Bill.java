package com.chrisyoung.huajiangapp.dto;


import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

/**
 * 账本实体
 */
public class Bill implements Serializable {

  private String bId; //账本ID UUID
  private String uId; //用户ID
  private String bName; //账本名称
  private Date bDate; //创建日期
  private String bDesc; //备注
  private int bVersion;//记录版本

  public Bill(){
    this.bId=UUID.randomUUID().toString().replace("-","");
    this.uId="";
    this.bName="";
    this.bDesc="";
  }


  public String getbId() {
    return bId;
  }

  public void setbId(String bId) {
    this.bId = bId;
  }


  public String getuId() {
    return uId;
  }

  public void setuId(String uId) {
    this.uId = uId;
  }


  public String getbName() {
    return bName;
  }

  public void setbName(String bName) {
    this.bName = bName;
  }


  public String getbDesc() {
    return bDesc;
  }

  public void setbDesc(String bDesc) {
    this.bDesc = bDesc;
  }

  public Date getbDate() {
    return bDate;
  }

  public void setbDate(Date bDate) {
    this.bDate = bDate;
  }

  public int getbVersion() {
    return bVersion;
  }

  public void setbVersion(int bVersion) {
    this.bVersion = bVersion;
  }


}
