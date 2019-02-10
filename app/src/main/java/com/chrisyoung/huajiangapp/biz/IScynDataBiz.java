package com.chrisyoung.huajiangapp.biz;

import com.chrisyoung.huajiangapp.dto.SBill;
import com.chrisyoung.huajiangapp.dto.SRecord;
import com.chrisyoung.huajiangapp.dto.SUserDiy;
import com.chrisyoung.huajiangapp.dto.SychronizeDataItem;

import java.util.LinkedList;

 public interface IScynDataBiz {
    LinkedList<SychronizeDataItem<SBill>> synchronizeBillC2S(String uId) ;

    LinkedList<SychronizeDataItem<SRecord>> synchronizeRecordC2S(String bId) ;

    LinkedList<SychronizeDataItem<SUserDiy>> synchronizeKindC2S(String uId);

    boolean synchronizeBillS2C(LinkedList<SBill> bills);

    boolean synchronizeRecordS2C(LinkedList<SRecord> records);

    boolean synchronizeKindS2C(LinkedList<SUserDiy> kinds);

}
