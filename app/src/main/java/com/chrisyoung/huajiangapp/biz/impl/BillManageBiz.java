package com.chrisyoung.huajiangapp.biz.impl;

import com.chrisyoung.huajiangapp.biz.IBillManageBiz;
import com.chrisyoung.huajiangapp.constant.StatusCode;
import com.chrisyoung.huajiangapp.dao.IBillDao;
import com.chrisyoung.huajiangapp.dao.impl.BillDaoImpl;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CUser;

import java.util.ArrayList;

public class BillManageBiz implements IBillManageBiz {
    private IBillDao billDao;

    public BillManageBiz(){
        billDao=new BillDaoImpl();
    }

    @Override
    public boolean addBill(CUser user, CBill newBill) {
        newBill.setbStatus(StatusCode.INSERT);
        newBill.setbVersion(1);
        return billDao.addBillForUser(user,newBill);
    }

    @Override
    public boolean updateBill(CBill bill) {
        int version=bill.getbVersion();
        version++;
        bill.setbVersion(version);
        bill.setbStatus(StatusCode.UPDATE);
        return billDao.addOrUpdateBill(bill);
    }

    @Override
    public boolean deleteBill(String bId) {
        return billDao.deleteBill(bId);
    }

    @Override
    public boolean fakeDeleteBill(String bId) {
        CBill bill=billDao.findBill(bId);
        if(bill!=null){
            CBill b=new CBill();
            b.setbId(bill.getbId());
            b.setuId(bill.getuId());
            b.setbName(bill.getbName());
            b.setbDate(bill.getbDate());
            b.setbDesc(bill.getbDesc());
            b.setbVersion(b.getbVersion());
            b.setbStatus(StatusCode.DELETE);
            b.setDelflag(1);
            return billDao.addOrUpdateBill(b);
        }
        return false;
    }

    @Override
    public ArrayList<CBill> showAllBills(String uId) {
        return billDao.showAllBills(uId);
    }

    @Override
    public CBill queryBill(String bId) {
        return billDao.findBill(bId);
    }

    @Override
    public void closeRealm() {
        billDao.closeRealm();
    }
}
