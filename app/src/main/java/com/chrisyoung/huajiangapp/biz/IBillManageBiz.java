package com.chrisyoung.huajiangapp.biz;

import com.chrisyoung.huajiangapp.domain.CBill;

import java.util.ArrayList;

public interface IBillManageBiz {
    boolean addBill(String uId,CBill newBill);

    boolean updateBill(CBill bill);

    boolean deleteBill(String bId);

    ArrayList<CBill> showAllBills(String uId);

}
