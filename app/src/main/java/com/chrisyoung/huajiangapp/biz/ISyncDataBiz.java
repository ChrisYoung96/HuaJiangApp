package com.chrisyoung.huajiangapp.biz;

import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.domain.CUser;
import com.chrisyoung.huajiangapp.domain.CUserDiyKind;
import com.chrisyoung.huajiangapp.dto.Bill;
import com.chrisyoung.huajiangapp.dto.Record;
import com.chrisyoung.huajiangapp.dto.UserDiy;
import com.chrisyoung.huajiangapp.dto.SychronizeDataItem;

import java.util.LinkedList;

 public interface ISyncDataBiz {
    LinkedList<SychronizeDataItem<CBill>> synchronizeBillC2S(String uId) ;

    LinkedList<SychronizeDataItem<CRecord>> synchronizeRecordC2S(String bId) ;

    LinkedList<SychronizeDataItem<CRecord>> synchronizeRecordC2S() ;

    LinkedList<SychronizeDataItem<CUserDiyKind>> synchronizeKindC2S(String uId);

    boolean synchronizeBillS2C(LinkedList<SychronizeDataItem<CBill>> bills);

    boolean synchronizeRecordS2C(LinkedList<SychronizeDataItem<CRecord>> records);

    boolean synchronizeKindS2C(LinkedList<SychronizeDataItem<CUserDiyKind>> kinds);

    void updateBillSattus(LinkedList<SychronizeDataItem<CBill>> cBills);

    void updateRecordSattus(LinkedList<SychronizeDataItem<CRecord>> records);

    void updateKindSattus(LinkedList<SychronizeDataItem<CUserDiyKind>> kinds);

    CUser synchronizeUserInfo(String uId);

    void closeRealm();
}
