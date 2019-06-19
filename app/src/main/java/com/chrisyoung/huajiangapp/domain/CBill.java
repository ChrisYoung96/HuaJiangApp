package com.chrisyoung.huajiangapp.domain;

import com.chrisyoung.huajiangapp.uitils.UUIDUtils;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CBill extends RealmObject {
    @PrimaryKey
    private String bId; //账本ID UUID
    private String uId; //用户ID
    private String bName; //账本名称
    private Date bDate; //创建日期
    private String bDesc; //备注
    private int bStatus; //记录状态
    private int bVersion;  //记录版本
    private int delflag=0;

    private RealmList<CRecord> cRecords;


    public CBill() {
        bId=UUIDUtils.getUUID();
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

    public Date getbDate() {
        return bDate;
    }

    public void setbDate(Date bDate) {
        this.bDate = bDate;
    }

    public String getbDesc() {
        return bDesc;
    }

    public void setbDesc(String bDesc) {
        this.bDesc = bDesc;
    }

    public RealmList<CRecord> getcRecords() {
        return cRecords;
    }

    public void setcRecords(RealmList<CRecord> cRecords) {
        this.cRecords = cRecords;
    }

    public int getbStatus() {
        return bStatus;
    }

    public void setbStatus(int bStatus) {
        this.bStatus = bStatus;
    }

    public int getbVersion() {
        return bVersion;
    }

    public void setbVersion(int bVersion) {
        this.bVersion = bVersion;
    }

    public int getDelflag() {
        return delflag;
    }

    public void setDelflag(int delflag) {
        this.delflag = delflag;
    }
}
