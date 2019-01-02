package com.chrisyoung.huajiangapp.domain;

import com.chrisyoung.huajiangapp.uitils.UUIDUtils;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CUser extends RealmObject {
    @PrimaryKey
    private String uId; //用户ID UUID
    private String uName; //用户昵称
    private String uSex; //性别
    private Date uBirthday; //生日
    private String uPhone; //电话
    private String uMail; //邮箱
    private String uPhoto; //头像路径

    private RealmList<CBill> bills;
    private RealmList<CUserDiyKind> cUserDiyKinds;

    public CUser() {
        this.uId=UUIDUtils.getUUID();
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

    public RealmList<CBill> getBills() {
        return bills;
    }

    public void setBills(RealmList<CBill> bills) {
        this.bills = bills;
    }

    public RealmList<CUserDiyKind> getcUserDiyKinds() {
        return cUserDiyKinds;
    }

    public void setcUserDiyKinds(RealmList<CUserDiyKind> cUserDiyKinds) {
        this.cUserDiyKinds = cUserDiyKinds;
    }
}
