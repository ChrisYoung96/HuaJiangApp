package com.chrisyoung.huajiangapp.dao.impl;

import com.chrisyoung.huajiangapp.dao.BaseDao;
import com.chrisyoung.huajiangapp.dao.IBillDao;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CUser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.realm.RealmObject;
import io.realm.RealmResults;

public class BillDaoImpl extends BaseDao implements IBillDao {

    public BillDaoImpl() {
        super();
    }

    @Override
    public boolean addOrUpdateBill(CBill newBill) {
        return insertOrUpdateRealmObject(newBill);
    }

    @Override
    public boolean deleteBill(String bId) {
        return deleteRealmObjectWithCondition("bId",bId,CBill.class);
    }

    public boolean addBillForUser(CUser user, CBill newBill){
        boolean result;
        realm.beginTransaction();
        result=user.getBills().add(newBill);
        realm.commitTransaction();
        return result;
    }

    @Override
    public ArrayList<CBill> showAllBills(String uId) {
        ArrayList<CBill> bills=new ArrayList<>();
        for (RealmObject b:queryRealmObjectsWithCondition("uId",uId,CBill.class)
             ) {
            CBill cb=(CBill)b;
            bills.add(cb);
        }
        return bills;
    }

    @Override
    public ArrayList<CBill> showBillNeedSynchronize(String uId) {

        RealmResults<CBill> bs=realm.where(CBill.class).equalTo("uId",uId).lessThan("bStatus",9).findAll();
        List<CBill> tem =realm.copyFromRealm(bs);
        return new ArrayList<>(tem);
    }

    @Override
    public CBill findBill(String bId) {
        CBill b=(CBill)queryRealmObjectWithCondition("bId",bId,CBill.class);
        return b;
    }


}
