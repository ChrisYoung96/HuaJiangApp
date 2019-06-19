package com.chrisyoung.huajiangapp.biz.impl;

import com.chrisyoung.huajiangapp.biz.ISyncDataBiz;
import com.chrisyoung.huajiangapp.constant.StatusCode;
import com.chrisyoung.huajiangapp.dao.IBillDao;
import com.chrisyoung.huajiangapp.dao.IRecordDao;
import com.chrisyoung.huajiangapp.dao.ISyncData;
import com.chrisyoung.huajiangapp.dao.IUserDao;
import com.chrisyoung.huajiangapp.dao.IUserDiyKindDao;
import com.chrisyoung.huajiangapp.dao.impl.AsyncDataImpl;
import com.chrisyoung.huajiangapp.dao.impl.BillDaoImpl;
import com.chrisyoung.huajiangapp.dao.impl.RecordDaoImpl;
import com.chrisyoung.huajiangapp.dao.impl.UserDaoImpl;
import com.chrisyoung.huajiangapp.dao.impl.UserDiyKindDaoImpl;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.domain.CUser;
import com.chrisyoung.huajiangapp.domain.CUserDiyKind;
import com.chrisyoung.huajiangapp.dto.AppUser;
import com.chrisyoung.huajiangapp.dto.SychronizeDataItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

public class SyncDataBiz implements ISyncDataBiz {
    private IRecordDao recordDao;
    private IBillDao billDao;
    private IUserDiyKindDao kindDao;
    private IUserDao userDao;
    private ISyncData syncDataDao;

    public SyncDataBiz() {
        recordDao = new RecordDaoImpl();
        billDao = new BillDaoImpl();
        kindDao = new UserDiyKindDaoImpl();
        userDao = new UserDaoImpl();
        syncDataDao = new AsyncDataImpl();
    }

    @Override
    public LinkedList<SychronizeDataItem<CBill>> synchronizeBillC2S(String uId) {
        ArrayList<CBill> cbs = billDao.showBillNeedSynchronize(uId);
        LinkedList<SychronizeDataItem<CBill>> sbs = new LinkedList<>();
        for (CBill b : cbs
                ) {
            SychronizeDataItem<CBill> ssb = new SychronizeDataItem<>();
            ssb.setData(b);
            ssb.setOptCode(b.getbStatus());

            sbs.add(ssb);
        }
        return sbs;
    }

    @Override
    public LinkedList<SychronizeDataItem<CRecord>> synchronizeRecordC2S(String bId) {
        ArrayList<CRecord> crs = recordDao.showRecordsNeedSynchronize(bId);
        LinkedList<SychronizeDataItem<CRecord>> srs = new LinkedList<>();
        for (CRecord r : crs) {
            SychronizeDataItem<CRecord> ssr = new SychronizeDataItem<>();
            ssr.setData(r);
            ssr.setOptCode(r.getrStatus());
            srs.add(ssr);
        }
        return srs;
    }

    @Override
    public LinkedList<SychronizeDataItem<CRecord>> synchronizeRecordC2S() {
        ArrayList<CRecord> crs = recordDao.showRecordsNeedSynchronize();
        LinkedList<SychronizeDataItem<CRecord>> srs = new LinkedList<>();
        for (CRecord r : crs) {
            SychronizeDataItem<CRecord> ssr = new SychronizeDataItem<>();
            ssr.setData(r);
            ssr.setOptCode(r.getrStatus());
            srs.add(ssr);
        }
        return srs;
    }

    @Override
    public LinkedList<SychronizeDataItem<CUserDiyKind>> synchronizeKindC2S(String uId) {
        ArrayList<CUserDiyKind> kinds = kindDao.showKindsNeedSynchronize(uId);
        LinkedList<SychronizeDataItem<CUserDiyKind>> sds = new LinkedList<>();
        for (CUserDiyKind k :
                kinds) {
            SychronizeDataItem<CUserDiyKind> sd = new SychronizeDataItem<>();
            sd.setData(k);
            sd.setOptCode(k.getdStatus());

            sds.add(sd);
        }
        return sds;
    }



    @Override
    public boolean synchronizeBillS2C(LinkedList<SychronizeDataItem<CBill>> bills) {
        boolean result = false;
        if (bills == null) {
            return false;
        } else {
            while (!bills.isEmpty()) {
                CBill b = Objects.requireNonNull(bills.poll().getData());
                CBill t = billDao.findBill(b.getbId());
                if (t != null) {
                    if (t.getbVersion() >= b.getbVersion()) {
                        continue;
                    }
                }
                b.setbStatus(StatusCode.Synced);
                CUser appUser=userDao.showUserInfo(b.getuId());
                result=billDao.addBillForUser(appUser,b);
            }

            return result;
        }
    }

    @Override
    public boolean synchronizeRecordS2C(LinkedList<SychronizeDataItem<CRecord>> records) {
        boolean result = false;
        if (records != null) {
            while (!records.isEmpty()) {
                CRecord r = Objects.requireNonNull(records.poll()).getData();
                CRecord t = recordDao.showARecord(r.getrId());
                if (t != null) {
                    if (t.getrVersion() >= r.getrVersion()) {
                        continue;
                    }
                }

                r.setrStatus(StatusCode.Synced);
                CBill bill=new CBill();
                bill=billDao.findBill(r.getbId());
                result=recordDao.addRecordForBill(bill,r);
            }

        }
        return result;
    }

    @Override
    public boolean synchronizeKindS2C(LinkedList<SychronizeDataItem<CUserDiyKind>> kinds) {
        boolean result = false;
        if (kinds != null) {
            while (!kinds.isEmpty()) {
                CUserDiyKind d = Objects.requireNonNull(kinds.poll()).getData();
                CUserDiyKind t = kindDao.findAKind(d.getdId());
                if (t != null) {
                    if (t.getdVersion() >= d.getdVersion()) {
                        continue;
                    }
                }
                d.setdStatus(StatusCode.Synced);
                CUser cUser=new CUser();
                cUser=userDao.showUserInfo(d.getuId());
                result=kindDao.addKindForUser(cUser,d);
            }

        }
        return result;
    }

    @Override
    public void closeRealm() {
        syncDataDao.closeRealm();
        kindDao.closeRealm();
        billDao.closeRealm();
        recordDao.closeRealm();
    }

    @Override
    public void updateBillSattus(LinkedList<SychronizeDataItem<CBill>> cBills){
        if(cBills!=null && !cBills.isEmpty()){
            while (!cBills.isEmpty()){
                CBill cBill=Objects.requireNonNull(cBills.poll()).getData();
                cBill.setbStatus(StatusCode.Synced);
                billDao.addOrUpdateBill(cBill);
            }
        }

    }

    @Override
    public void updateRecordSattus(LinkedList<SychronizeDataItem<CRecord>> records){
        if(records!=null && !records.isEmpty()){
            while (!records.isEmpty()){
                CRecord cRecord=Objects.requireNonNull(records.poll()).getData();
                cRecord.setrStatus(StatusCode.Synced);
                recordDao.addOrUpdateRecord(cRecord);
            }
        }

    }

    @Override
    public void updateKindSattus(LinkedList<SychronizeDataItem<CUserDiyKind>> kinds){
        if(kinds!=null && !kinds.isEmpty()){
            while (!kinds.isEmpty()){
               CUserDiyKind cUserDiyKind=Objects.requireNonNull(kinds.poll()).getData();
               cUserDiyKind.setdStatus(StatusCode.Synced);
               kindDao.addOrUpdateKind(cUserDiyKind);
            }
        }

    }

    @Override
    public CUser synchronizeUserInfo(String uId) {
        return userDao.showUserInfo(uId);
    }
}
