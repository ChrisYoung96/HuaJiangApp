package com.chrisyoung.huajiangapp.dao.impl;

import com.chrisyoung.huajiangapp.dao.BaseDao;
import com.chrisyoung.huajiangapp.dao.IRecordDao;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.RealmObject;
import io.realm.RealmResults;

public class RecordDaoImpl extends BaseDao implements IRecordDao {
    public RecordDaoImpl() {
        super();
    }


    @Override
    public boolean addOrUpdateRecord(CRecord newRecord) {
        return insertOrUpdateRealmObject(newRecord);
    }

    public boolean addRecordForBill(CBill bill, CRecord newRecord) {
        realm.beginTransaction();
        boolean result = bill.getcRecords().add(newRecord);
        realm.commitTransaction();
        return result;
    }

    @Override
    public boolean deleteRecord(String rId) {
        return deleteRealmObjectWithCondition("rId", rId, CRecord.class);
    }

    @Override
    public CRecord showARecord(String rId) {
        return realm.where(CRecord.class).equalTo("rId",rId).findFirst();
    }

    @Override
    public ArrayList<CRecord> showAllRecords(String bId) {
        RealmResults<CRecord> records = realm.where(CRecord.class).equalTo("bId", bId).findAll().sort("rTime");
        List<CRecord> results = realm.copyFromRealm(records);
        return new ArrayList<>(results);
    }

    @Override
    public ArrayList<CRecord> showRecordsNeedSynchronize(String bId) {
        RealmResults<CRecord> r=realm.where(CRecord.class).equalTo("bId",bId).lessThan("rVersion",9).findAll().sort("rTime");
        List<CRecord> re=realm.copyFromRealm(r);
        return new ArrayList<>(re);
    }

    @Override
    public ArrayList<CRecord> showRecordsByMonth(String bId, Date monthStart, Date monthEnd) {
        RealmResults<CRecord> records = realm.where(CRecord.class)
                .equalTo("bId", bId)
                .between("rTime", monthStart, monthEnd)
                .findAll()
                .sort("rTime");
        List<CRecord> r = realm.copyFromRealm(records);
        return new ArrayList<>(r);
    }

    @Override
    public ArrayList<CRecord> showRecordsByDay(String bId, Date dayStart, Date dayEnd) {
        return null;
    }

    @Override
    public ArrayList<CRecord> showRecordsByMonthAndType(String bId, Date monthStart, Date monthEnd, String type) {
        RealmResults<CRecord> records = realm.where(CRecord.class)
                .equalTo("bId", bId)
                .between("rTime", monthStart, monthEnd)
                .equalTo("rType", type)
                .findAll()
                .sort("rTime");
        List<CRecord> r = realm.copyFromRealm(records);
        return new ArrayList<>(r);
    }

    @Override
    public ArrayList<CRecord> showRecordsByWayAndMonth(String bId, String way, Date monthStart, Date monthEnd, String type) {
        RealmResults<CRecord> records = realm.where(CRecord.class)
                .between("rTime", monthStart, monthEnd)
                .equalTo("bId", bId)
                .equalTo("rType", type)
                .equalTo("rWay", way)
                .findAll()
                .sort("rTime");
        List<CRecord> r = realm.copyFromRealm(records);
        return new ArrayList<>(r);
    }


    @Override
    public Double sumAllMoneyInAMonth(String bId, Date monthStart, Date monthEnd, String type) {
        RealmResults<CRecord> records = realm.where(CRecord.class)
                .equalTo("bId", bId)
                .between("rTime", monthStart, monthEnd)
                .equalTo("rType", type)
                .findAll();
        return (Double) records.sum("rMoney");
    }

    @Override
    public Double sumAllMoneyOfWayInAMonth(String bId, Date monthStart, Date monthEnd, String type, String way) {
        RealmResults<CRecord> records = realm.where(CRecord.class)
                .equalTo("bId", bId)
                .between("rTime", monthStart, monthEnd)
                .equalTo("rType", type)
                .equalTo("rWay", way)
                .findAll();
        return (Double) records.sum("rMoney");
    }

    @Override
    public Double sumAllMoneyOfKindInAMonth(String bId, Date monthStart, Date monthEnd, String type, String kind) {
        RealmResults<CRecord> records = realm.where(CRecord.class)
                .equalTo("bId", bId)
                .between("rTime", monthStart, monthEnd)
                .equalTo("rType", type)
                .equalTo("rKind", kind)
                .findAll();
        return (Double) records.sum("rMoney");
    }


}
