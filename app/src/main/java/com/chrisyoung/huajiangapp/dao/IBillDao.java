package com.chrisyoung.huajiangapp.dao;

import android.content.Context;

import com.chrisyoung.huajiangapp.domain.CBill;

import java.util.ArrayList;

public interface IBillDao {
    boolean addOrUpdateBill(CBill newBill);

    boolean deleteBill(String bId);

    ArrayList<CBill> showAllBills(String uId);

    ArrayList<CBill> showBillNeedSynchronize(String uId);

    CBill findBill(String bId);

}
