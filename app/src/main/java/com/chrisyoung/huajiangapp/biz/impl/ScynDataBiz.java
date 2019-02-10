package com.chrisyoung.huajiangapp.biz.impl;

import com.chrisyoung.huajiangapp.biz.IScynDataBiz;
import com.chrisyoung.huajiangapp.dao.IBillDao;
import com.chrisyoung.huajiangapp.dao.IRecordDao;
import com.chrisyoung.huajiangapp.dao.IUserDao;
import com.chrisyoung.huajiangapp.dao.IUserDiyKindDao;
import com.chrisyoung.huajiangapp.dao.impl.BillDaoImpl;
import com.chrisyoung.huajiangapp.dao.impl.RecordDaoImpl;
import com.chrisyoung.huajiangapp.dao.impl.UserDaoImpl;
import com.chrisyoung.huajiangapp.dao.impl.UserDiyKindDaoImpl;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.domain.CUser;
import com.chrisyoung.huajiangapp.domain.CUserDiyKind;
import com.chrisyoung.huajiangapp.dto.SBill;
import com.chrisyoung.huajiangapp.dto.SRecord;
import com.chrisyoung.huajiangapp.dto.SUserDiy;
import com.chrisyoung.huajiangapp.dto.SychronizeDataItem;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;

public class ScynDataBiz implements IScynDataBiz {
    private IRecordDao recordDao;
    private IBillDao billDao;
    private IUserDiyKindDao kindDao;
    private IUserDao userDao;

    public ScynDataBiz(){
        recordDao=new RecordDaoImpl();
        billDao=new BillDaoImpl();
        kindDao=new UserDiyKindDaoImpl();
        userDao=new UserDaoImpl();
    }
    @Override
    public LinkedList<SychronizeDataItem<SBill>> synchronizeBillC2S(String uId) {
        ArrayList<CBill> cbs=billDao.showBillNeedSynchronize(uId);
        LinkedList<SychronizeDataItem<SBill>> sbs=new LinkedList<>();
        for (CBill b: cbs
             ) {
            SBill sb=new SBill();
            sb.setBId(b.getbId());
            sb.setUId(b.getuId());
            sb.setbDate((Date) b.getbDate());
            sb.setBName(b.getbName());
            sb.setBDesc(b.getbDesc());
            sb.setbVersion(b.getbVersion());

            SychronizeDataItem<SBill> ssb=new SychronizeDataItem<>();
            ssb.setData(sb);
            ssb.setOptCode(b.getbStatus());

            sbs.add(ssb);
        }
        return sbs;
    }

    @Override
    public LinkedList<SychronizeDataItem<SRecord>> synchronizeRecordC2S(String bId) {
        ArrayList<CRecord> crs=recordDao.showRecordsNeedSynchronize(bId);
        LinkedList<SychronizeDataItem<SRecord>> srs=new LinkedList<>();
        for(CRecord r : crs){
            SRecord sr=new SRecord();
            sr.setRId(r.getrId());
            sr.setBId(r.getbId());
            sr.setRMoney(BigDecimal.valueOf(r.getrMoney()));
            sr.setRKind(r.getrKind());
            sr.setRType(r.getrType());
            sr.setrWay(r.getrWay());
            sr.setrVersioin(r.getrVersion());
            sr.setRDesc(r.getrDesc());
            sr.setRTime((Timestamp) r.getrTime());

            SychronizeDataItem<SRecord> ssr=new SychronizeDataItem<>();
            ssr.setData(sr);
            ssr.setOptCode(r.getrStatus());
            srs.add(ssr);
        }
        return srs;
    }

    @Override
    public LinkedList<SychronizeDataItem<SUserDiy>> synchronizeKindC2S(String uId) {
        ArrayList<CUserDiyKind> kinds=kindDao.showKindsNeedSynchronize(uId);
        LinkedList<SychronizeDataItem<SUserDiy>> sds=new LinkedList<>();
        for (CUserDiyKind k :
                kinds) {
            SUserDiy sk=new SUserDiy();
            sk.setDId(k.getdId());
            sk.setUId(k.getuId());
            sk.setDKind(k.getdKind());
            sk.setDType(k.getdType());
            sk.setdVersion(k.getdVersion());

            SychronizeDataItem<SUserDiy> sd=new SychronizeDataItem<>();
            sd.setData(sk);
            sd.setOptCode(k.getdStatus());

            sds.add(sd);
        }
        return sds;
    }

    @Override
    public boolean synchronizeBillS2C(LinkedList<SBill> bills) {
        boolean result=false;
        if(bills==null){
            return false;
        }else{
            while(!bills.isEmpty()){
                SBill b=bills.poll();
                CBill t=billDao.findBill(b.getBId());
                if(t!=null){
                    if(t.getbVersion()>=b.getbVersion()){
                        continue;
                    }
                }
                CBill cb=new CBill();
                cb.setbId(b.getBId());
                cb.setbName(b.getBName());
                cb.setuId(b.getUId());
                cb.setbVersion(b.getbVersion());
                cb.setbDate(b.getbDate());
                cb.setbDesc(b.getBDesc());

                CUser cUser=userDao.showUserInfo(b.getUId());
                if(cUser!=null){
                   result=billDao.addBillForUser(cUser,cb);
                }
            }
        }
        return result;
    }

    @Override
    public boolean synchronizeRecordS2C(LinkedList<SRecord> records) {
        boolean result=false;
        if(records!=null){
            while(!records.isEmpty()){
                SRecord r=records.poll();
                CRecord t=recordDao.showARecord(r.getRId());
                if(t!=null){
                    if(t.getrVersion()>=r.getrVersioin()){
                        continue;
                    }
                }
                CRecord cr=new CRecord();
                cr.setrId(r.getRId());
                cr.setbId(r.getBId());
                cr.setrMoney(r.getRMoney().doubleValue());
                cr.setrTime(r.getRTime());
                cr.setrKind(r.getRKind());
                cr.setrWay(r.getrWay());
                cr.setrDesc(r.getRDesc());
                cr.setrType(r.getRType());
                cr.setrVersion(r.getrVersioin());

                CBill b=billDao.findBill(r.getBId());
                if(b!=null){
                    result=recordDao.addRecordForBill(b,cr);
                }
            }
        }
        return result;
    }

    @Override
    public boolean synchronizeKindS2C(LinkedList<SUserDiy> kinds) {
        boolean result=false;
        if(kinds!=null){
            while(!kinds.isEmpty()){
                SUserDiy d=kinds.poll();
                CUserDiyKind t=kindDao.findAKind(d.getDId());
                if(t.getdVersion()>=d.getdVersion()){
                    continue;
                }
                CUserDiyKind cd=new CUserDiyKind();
                cd.setdId(d.getDId());
                cd.setuId(d.getUId());
                cd.setdKind(d.getDType());
                cd.setdType(d.getDType());
                cd.setdVersion(d.getdVersion());

                CUser u=userDao.showUserInfo(d.getUId());
                if(u!=null){
                    result=kindDao.addKindForUser(u,cd);
                }
            }

        }
        return result;
    }
}
