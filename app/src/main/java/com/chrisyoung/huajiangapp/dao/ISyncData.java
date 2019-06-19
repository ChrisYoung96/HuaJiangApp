package com.chrisyoung.huajiangapp.dao;

import android.content.Context;

import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.domain.CUserDiyKind;

import java.util.ArrayList;

public interface ISyncData {
    boolean asyncAddOrUpdateBills(ArrayList<CBill> bills);

    boolean asyncAddOrUpdateRecords(ArrayList<CRecord> records);

    boolean asyncAddOrUpdateKinds(ArrayList<CUserDiyKind> kinds);

    ArrayList<CBill> asyncQueryBills(String uId);

    ArrayList<CRecord> asyncQueryRecords(String bId);

    ArrayList<CUserDiyKind> asyncQueryKinds(String uId);

    void closeRealm();

}
