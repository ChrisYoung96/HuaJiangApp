package com.chrisyoung.huajiangapp.biz;

import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CUser;

import java.util.ArrayList;

public interface IBillManageBiz {
    boolean addBill(CUser user, CBill newBill);

    boolean updateBill(CBill bill);

    boolean deleteBill(String bId);

    boolean fakeDeleteBill(CBill bill);

    ArrayList<CBill> showAllBills(String uId);

    CBill queryBill(String bId);

}
