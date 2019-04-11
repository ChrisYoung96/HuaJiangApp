package com.chrisyoung.huajiangapp.dto;


import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

/**
 * 用户信息实体
 */
public class AppUser implements Serializable {

  private String uId; //用户ID UUID
  private String uName; //用户昵称
  private String uSex; //性别
  private Date uBirthday; //生日
  private String uPhone; //电话
  private String uMail; //邮箱
  private String uPhoto; //头像路径

  public AppUser(){
    this.uId=UUID.randomUUID().toString().replace("-","");
    this.uName="";
    this.uBirthday=Date.valueOf("1971-1-1");
    this.uSex="";
    this.uMail="";
    this.uPhone="";
    this.uPhoto="";
  }

  public String getuId() {
    return uId;
  }

  public void setuId(String uId) {
    this.uId = uId;
  }


  public String getuName() {
    return uName;
  }

  public void setuName(String uName) {
    this.uName = uName;
  }


  public String getuSex() {
    return uSex;
  }

  public void setuSex(String uSex) {
    this.uSex = uSex;
  }


  public Date getuBirthday() {
    return uBirthday;
  }

  public void setuBirthday(Date uBirthday) {
    this.uBirthday = uBirthday;
  }


  public String getuPhone() {
    return uPhone;
  }

  public void setuPhone(String uPhone) {
    this.uPhone = uPhone;
  }


  public String getuMail() {
    return uMail;
  }

  public void setuMail(String uMail) {
    this.uMail = uMail;
  }


  public String getuPhoto() {
    return uPhoto;
  }

  public void setuPhoto(String uPhoto) {
    this.uPhoto = uPhoto;
  }

}
