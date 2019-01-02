package com.chrisyoung.huajiangapp.dto;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * 用户信息实体
 */
public class SUser {

  private String uId; //用户ID UUID
  private String uName; //用户昵称
  private String uSex; //性别
  private Date uBirthday; //生日
  private String uPhone; //电话
  private String uMail; //邮箱
  private String uPhoto; //头像路径

  public SUser(){
    this.uId=UUID.randomUUID().toString().replace("-","");
    this.uName="";
    this.uBirthday=Date.valueOf("1971-1-1");
    this.uSex="";
    this.uMail="";
    this.uPhone="";
    this.uPhoto="";
  }

  public String getUId() {
    return uId;
  }

  public void setUId(String uId) {
    this.uId = uId;
  }


  public String getUName() {
    return uName;
  }

  public void setUName(String uName) {
    this.uName = uName;
  }


  public String getUSex() {
    return uSex;
  }

  public void setUSex(String uSex) {
    this.uSex = uSex;
  }


  public Date getUBirthday() {
    return uBirthday;
  }

  public void setUBirthday(Date uBirthday) {
    this.uBirthday = uBirthday;
  }


  public String getUPhone() {
    return uPhone;
  }

  public void setUPhone(String uPhone) {
    this.uPhone = uPhone;
  }


  public String getUMail() {
    return uMail;
  }

  public void setUMail(String uMail) {
    this.uMail = uMail;
  }


  public String getUPhoto() {
    return uPhoto;
  }

  public void setUPhoto(String uPhoto) {
    this.uPhoto = uPhoto;
  }

}
