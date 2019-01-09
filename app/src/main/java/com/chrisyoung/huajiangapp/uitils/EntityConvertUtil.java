package com.chrisyoung.huajiangapp.uitils;

import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.domain.CUser;
import com.chrisyoung.huajiangapp.domain.CUserDiyKind;
import com.chrisyoung.huajiangapp.dto.SBill;
import com.chrisyoung.huajiangapp.dto.SRecord;
import com.chrisyoung.huajiangapp.dto.SUser;
import com.chrisyoung.huajiangapp.dto.SUserDiy;

import java.math.BigDecimal;
import java.sql.Date;

public class EntityConvertUtil {
    public static SUser CUser2SUser(CUser user){
        SUser sUser=new SUser();
        sUser.setUId(user.getuId());
        sUser.setUBirthday((Date) user.getuBirthday());
        sUser.setUMail(user.getuMail());
        sUser.setUName(user.getuName());
        sUser.setUPhone(user.getuPhone());
        sUser.setUSex(user.getuSex());
        sUser.setUPhoto(user.getuPhoto());
        return sUser;
    }

    public static SBill CBill2SBill(CBill bill){
        SBill sBill=new SBill();
        sBill.setBId(bill.getbId());
        sBill.setUId(bill.getuId());
        sBill.setbDate((Date) bill.getbDate());
        sBill.setBDesc(bill.getbDesc());
        return sBill;
    }

    public static SUserDiy CUserDiyKind2SUserDiy(CUserDiyKind kind){
        SUserDiy sUserDiy=new SUserDiy();
        sUserDiy.setDId(kind.getdId());
        sUserDiy.setUId(kind.getuId());
        sUserDiy.setDKind(kind.getdKind());
        sUserDiy.setDType(kind.getdType());
        return sUserDiy;
    }

    public static SRecord CRecord2SRecord(CRecord record){
        SRecord sRecord=new SRecord();
        sRecord.setRId(record.getrId());
        sRecord.setBId(record.getbId());
        sRecord.setRKind(record.getrKind());
        sRecord.setRTime(DateFormatUtil.dateToTimestamp(record.getrTime()));
        sRecord.setRMoney(BigDecimal.valueOf(record.getrMoney()));
        sRecord.setRDesc(record.getrDesc());
        sRecord.setRType(record.getrType());
        sRecord.setrWay(record.getrWay());
        return sRecord;
    }
}
