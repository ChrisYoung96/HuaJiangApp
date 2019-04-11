package com.chrisyoung.huajiangapp.biz;

import com.chrisyoung.huajiangapp.dto.Bill;
import com.chrisyoung.huajiangapp.dto.Record;
import com.chrisyoung.huajiangapp.dto.UserDiy;
import com.chrisyoung.huajiangapp.dto.SychronizeDataItem;

import java.util.LinkedList;

 public interface IScynDataBiz {
    LinkedList<SychronizeDataItem<Bill>> synchronizeBillC2S(String uId) ;

    LinkedList<SychronizeDataItem<Record>> synchronizeRecordC2S(String bId) ;

    LinkedList<SychronizeDataItem<UserDiy>> synchronizeKindC2S(String uId);

    boolean synchronizeBillS2C(LinkedList<Bill> bills);

    boolean synchronizeRecordS2C(LinkedList<Record> records);

    boolean synchronizeKindS2C(LinkedList<UserDiy> kinds);

}
