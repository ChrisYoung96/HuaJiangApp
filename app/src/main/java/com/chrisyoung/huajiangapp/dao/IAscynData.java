package com.chrisyoung.huajiangapp.dao;

import android.content.Context;

import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.domain.CUserDiyKind;

import java.util.ArrayList;

public interface IAscynData {
    void asyncAddOrUpdateBills(ArrayList<CBill> bills, Context context);
    void asyncAddOrUpdateRecords(ArrayList<CRecord> records,Context context);
    void asyncAddorUpdateKinds(ArrayList<CUserDiyKind> kinds,Context context);
}
