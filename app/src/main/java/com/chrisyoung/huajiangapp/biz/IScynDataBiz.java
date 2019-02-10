package com.chrisyoung.huajiangapp.biz;

import com.chrisyoung.huajiangapp.dto.SBill;
import com.chrisyoung.huajiangapp.dto.SRecord;
import com.chrisyoung.huajiangapp.dto.SUserDiy;
import com.chrisyoung.huajiangapp.dto.SychronizeDataItem;

import java.util.LinkedList;

 public interface IScynDataBiz {
    LinkedList<SychronizeDataItem<SBill>> synchronizeBillC2S() ;

    LinkedList<SychronizeDataItem<SRecord>> synchronizeRecordC2S() ;

    LinkedList<SychronizeDataItem<SUserDiy>> synchronizeKindC2S();

    boolean synchronizeBillS2C(LinkedList<SBill> bills);

    boolean synchronizeRecords(LinkedList<SRecord> records);

    boolean synchronizeKinds(LinkedList<SUserDiy> kinds);

}
