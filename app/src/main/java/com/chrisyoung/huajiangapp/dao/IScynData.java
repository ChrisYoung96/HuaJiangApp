package com.chrisyoung.huajiangapp.dao;

import android.content.Context;

import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.domain.CUserDiyKind;

import java.util.ArrayList;

public interface IScynData {
    boolean asyncAddOrUpdateBills(ArrayList<CBill> bills);
    boolean asyncAddOrUpdateRecords(ArrayList<CRecord> records);
    boolean asyncAddorUpdateKinds(ArrayList<CUserDiyKind> kinds);

}
