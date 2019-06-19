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
        sUser.setuBirthday(new Date(user.getuBirthday().getTime()));
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
        sBill.setbDate(new Date(bill.getbDate().getTime()));
        sBill.setbDesc(bill.getbDesc());
        sBill.setbVersion(bill.getbVersion());
        sBill.setbName(bill.getbName());
        return sBill;
    }

    public static UserDiy CUserDiyKind2SUserDiy(CUserDiyKind kind){
        UserDiy sUserDiy=new UserDiy();
        sUserDiy.setdId(kind.getdId());
        sUserDiy.setuId(kind.getuId());
        sUserDiy.setdKind(kind.getdKind());
        sUserDiy.setdType(kind.getdType());
        sUserDiy.setdVersion(kind.getdVersion());
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
        sRecord.setrVersion(record.getrVersion());
        return sRecord;
    }

    public static CBill SBill2CBill(Bill bill){
        CBill cBill=new CBill();
        cBill.setbName(bill.getbName());
        cBill.setbId(bill.getbId());
        cBill.setuId(bill.getuId());
        cBill.setbDate(new java.util.Date(bill.getbDate().getTime()));
        cBill.setbDesc(bill.getbDesc());
        cBill.setbVersion(bill.getbVersion());
        return cBill;
    }


    public static CUserDiyKind SUserDiyKind2CUserDiyKind(UserDiy kind){
        CUserDiyKind cUserDiy=new CUserDiyKind();
        cUserDiy.setdId(kind.getdId());
        cUserDiy.setuId(kind.getuId());
        cUserDiy.setdKind(kind.getdKind());
        cUserDiy.setdType(kind.getdType());
        cUserDiy.setdVersion(kind.getdVersion());
        return cUserDiy;
    }

    public static CRecord SRecord2CRecord(Record record){
        CRecord cRecord=new CRecord();
        cRecord.setrId(record.getrId());
        cRecord.setbId(record.getbId());
        cRecord.setrKind(record.getrKind());
        cRecord.setrTime(DateFormatUtil.dateToTimestamp(record.getrTime()));
        cRecord.setrMoney(record.getrMoney().doubleValue());
        cRecord.setrDesc(record.getrDesc());
        cRecord.setrType(record.getrType());
        cRecord.setrWay(record.getrWay());
        cRecord.setrVersion(record.getrVersion());
        return cRecord;
    }


}
