package com.chrisyoung.huajiangapp.uitils;

import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.domain.CUser;
import com.chrisyoung.huajiangapp.domain.CUserDiyKind;
import com.chrisyoung.huajiangapp.dto.Bill;
import com.chrisyoung.huajiangapp.dto.Record;
import com.chrisyoung.huajiangapp.dto.AppUser;
import com.chrisyoung.huajiangapp.dto.UserDiy;

import java.math.BigDecimal;
import java.sql.Date;

public class EntityConvertUtil {
    public static AppUser CUser2SUser(CUser user){
        AppUser sUser=new AppUser();
        sUser.setuId(user.getuId());
        sUser.setuBirthday((Date) user.getuBirthday());
        sUser.setuMail(user.getuMail());
        sUser.setuName(user.getuName());
        sUser.setuPhone(user.getuPhone());
        sUser.setuSex(user.getuSex());
        sUser.setuPhoto(user.getuPhoto());
        return sUser;
    }

    public static Bill CBill2SBill(CBill bill){
        Bill sBill=new Bill();
        sBill.setbId(bill.getbId());
        sBill.setuId(bill.getuId());
        sBill.setbDate((Date) bill.getbDate());
        sBill.setbDesc(bill.getbDesc());
        return sBill;
    }

    public static UserDiy CUserDiyKind2SUserDiy(CUserDiyKind kind){
        UserDiy sUserDiy=new UserDiy();
        sUserDiy.setDId(kind.getdId());
        sUserDiy.setUId(kind.getuId());
        sUserDiy.setDKind(kind.getdKind());
        sUserDiy.setDType(kind.getdType());
        return sUserDiy;
    }

    public static Record CRecord2SRecord(CRecord record){
        Record sRecord=new Record();
        sRecord.setrId(record.getrId());
        sRecord.setbId(record.getbId());
        sRecord.setrKind(record.getrKind());
        sRecord.setrTime(DateFormatUtil.dateToTimestamp(record.getrTime()));
        sRecord.setrMoney(BigDecimal.valueOf(record.getrMoney()));
        sRecord.setrDesc(record.getrDesc());
        sRecord.setrType(record.getrType());
        sRecord.setrWay(record.getrWay());
        return sRecord;
    }
}
