package com.chrisyoung.huajiangapp.dto;


import com.chrisyoung.huajiangapp.uitils.UUIDUtils;

import java.util.UUID;

public class UserAuths {

  private String uaId;
  private String uId;  //用户ID
  private String identityType;  //登陆类型
  private String identify;  //电话、邮箱、第三方ID
  private String credential; //密码、第三方登陆为access_token&refresh_token
  private String refreshtoken;
  private String role;

  public UserAuths(){
    this.uId=UUID.randomUUID().toString().replace("-","");
    this.uaId=UUIDUtils.getUUID();
    this.identityType="";
    this.identify="";
    this.credential="";
    this.refreshtoken="";
    this.role="APPUSER";
  }


  public String getUaId() {
    return uaId;
  }

  public void setUaId(String uaId) {
    this.uaId = uaId;
  }

  public String getUId() {
    return uId;
  }

  public void setUId(String uId) {
    this.uId = uId;
  }


  public String getIdentityType() {
    return identityType;
  }

  public void setIdentityType(String identityType) {
    this.identityType = identityType;
  }


  public String getIdentify() {
    return identify;
  }

  public void setIdentify(String identify) {
    this.identify = identify;
  }


  public String getCredential() {
    return credential;
  }

  public void setCredential(String credential) {
    this.credential = credential;
  }

  public String getRefreshtoken() {
    return refreshtoken;
  }

  public void setRefreshtoken(String refreshtoken) {
    this.refreshtoken = refreshtoken;
  }


  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

}
