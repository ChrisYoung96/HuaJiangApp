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
import com.chrisyoung.huajiangapp.dto.Bill;
import com.chrisyoung.huajiangapp.dto.Record;
import com.chrisyoung.huajiangapp.dto.UserDiy;
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
    public LinkedList<SychronizeDataItem<Bill>> synchronizeBillC2S(String uId) {
        ArrayList<CBill> cbs=billDao.showBillNeedSynchronize(uId);
        LinkedList<SychronizeDataItem<Bill>> sbs=new LinkedList<>();
        for (CBill b: cbs
             ) {
            Bill sb=new Bill();
            sb.setbId(b.getbId());
            sb.setuId(b.getuId());
            sb.setbDate((Date) b.getbDate());
            sb.setbName(b.getbName());
            sb.setbDesc(b.getbDesc());
            sb.setbVersion(b.getbVersion());

            SychronizeDataItem<Bill> ssb=new SychronizeDataItem<>();
            ssb.setData(sb);
            ssb.setOptCode(b.getbStatus());

            sbs.add(ssb);
        }
        return sbs;
    }

    @Override
    public LinkedList<SychronizeDataItem<Record>> synchronizeRecordC2S(String bId) {
        ArrayList<CRecord> crs=recordDao.showRecordsNeedSynchronize(bId);
        LinkedList<SychronizeDataItem<Record>> srs=new LinkedList<>();
        for(CRecord r : crs){
            Record sr=new Record();
            sr.setrId(r.getrId());
            sr.setbId(r.getbId());
            sr.setrMoney(BigDecimal.valueOf(r.getrMoney()));
            sr.setrKind(r.getrKind());
            sr.setrType(r.getrType());
            sr.setrWay(r.getrWay());
            sr.setrVersioin(r.getrVersion());
            sr.setrDesc(r.getrDesc());
            sr.setrTime((Timestamp) r.getrTime());

            SychronizeDataItem<Record> ssr=new SychronizeDataItem<>();
            ssr.setData(sr);
            ssr.setOptCode(r.getrStatus());
            srs.add(ssr);
        }
        return srs;
    }

    @Override
    public LinkedList<SychronizeDataItem<UserDiy>> synchronizeKindC2S(String uId) {
        ArrayList<CUserDiyKind> kinds=kindDao.showKindsNeedSynchronize(uId);
        LinkedList<SychronizeDataItem<UserDiy>> sds=new LinkedList<>();
        for (CUserDiyKind k :
                kinds) {
            UserDiy sk=new UserDiy();
            sk.setDId(k.getdId());
            sk.setUId(k.getuId());
            sk.setDKind(k.getdKind());
            sk.setDType(k.getdType());
            sk.setdVersion(k.getdVersion());

            SychronizeDataItem<UserDiy> sd=new SychronizeDataItem<>();
            sd.setData(sk);
            sd.setOptCode(k.getdStatus());

            sds.add(sd);
        }
        return sds;
    }

    @Override
    public boolean synchronizeBillS2C(LinkedList<Bill> bills) {
        boolean result=false;
        if(bills==null){
            return false;
        }else{
            while(!bills.isEmpty()){
                Bill b=bills.poll();
                CBill t=billDao.findBill(b.getbId());
                if(t!=null){
                    if(t.getbVersion()>=b.getbVersion()){
                        continue;
                    }
                }
                CBill cb=new CBill();
                cb.setbId(b.getbId());
                cb.setbName(b.getbName());
                cb.setuId(b.getuId());
                cb.setbVersion(b.getbVersion());
                cb.setbDate(b.getbDate());
                cb.setbDesc(b.getbDesc());

                CUser cUser=userDao.showUserInfo(b.getuId());
                if(cUser!=null){
                   result=billDao.addBillForUser(cUser,cb);
                }
            }
        }
        return result;
    }

    @Override
    public boolean synchronizeRecordS2C(LinkedList<Record> records) {
        boolean result=false;
        if(records!=null){
            while(!records.isEmpty()){
                Record r=records.poll();
                CRecord t=recordDao.showARecord(r.getrId());
                if(t!=null){
                    if(t.getrVersion()>=r.getrVersioin()){
                        continue;
                    }
                }
                CRecord cr=new CRecord();
                cr.setrId(r.getrId());
                cr.setbId(r.getbId());
                cr.setrMoney(r.getrMoney().doubleValue());
                cr.setrTime(r.getrTime());
                cr.setrKind(r.getrKind());
                cr.setrWay(r.getrWay());
                cr.setrDesc(r.getrDesc());
                cr.setrType(r.getrType());
                cr.setrVersion(r.getrVersioin());

                CBill b=billDao.findBill(r.getbId());
                if(b!=null){
                    result=recordDao.addRecordForBill(b,cr);
                }
            }
        }
        return result;
    }

    @Override
    public boolean synchronizeKindS2C(LinkedList<UserDiy> kinds) {
        boolean result=false;
        if(kinds!=null){
            while(!kinds.isEmpty()){
                UserDiy d=kinds.poll();
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
