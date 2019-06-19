package com.chrisyoung.huajiangapp.domain;

import com.chrisyoung.huajiangapp.uitils.UUIDUtils;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CUserDiyKind extends RealmObject {
    @PrimaryKey
    private String dId; //自定义收支类型编号
    private String uId; //用户ID
    private String dType; //收入或支出
    private String dKind; //自定义类型
    private int dStatus; //记录状态
    private int dVersion; //记录版本
    private int delflag=0;


    public CUserDiyKind() {
        dId=UUIDUtils.getUUID();
    }

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

    public int getdStatus() {
        return dStatus;
    }

    public void setdStatus(int dStatus) {
        this.dStatus = dStatus;
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
