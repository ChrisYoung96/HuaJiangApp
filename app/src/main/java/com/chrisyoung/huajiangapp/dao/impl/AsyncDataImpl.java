package com.chrisyoung.huajiangapp.dao.impl;

import android.util.Log;

import com.chrisyoung.huajiangapp.dao.BaseDao;
import com.chrisyoung.huajiangapp.dao.ISyncData;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.domain.CUserDiyKind;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

import static android.content.ContentValues.TAG;


public class AsyncDataImpl extends BaseDao implements ISyncData {
    private RealmAsyncTask insertOrUpdateKindsTask;
    private RealmAsyncTask insertOrUpdateBillsTask;
    private RealmAsyncTask insertOrUpdateRecordsTask;

    private RealmResults<CBill> asyncBills;

    private RealmResults<CRecord> asyncRecords;

    private RealmResults<CUserDiyKind> asyncKinds;

    public RealmAsyncTask getInsertOrUpdateKindsTask() {
        return insertOrUpdateKindsTask;
    }

    public void setInsertOrUpdateKindsTask(RealmAsyncTask insertOrUpdateKindsTask) {
        this.insertOrUpdateKindsTask = insertOrUpdateKindsTask;
    }

    public RealmAsyncTask getInsertOrUpdateBillsTask() {
        return insertOrUpdateBillsTask;
    }

    public void setInsertOrUpdateBillsTask(RealmAsyncTask insertOrUpdateBillsTask) {
        this.insertOrUpdateBillsTask = insertOrUpdateBillsTask;
    }

    public RealmAsyncTask getInsertOrUpdateRecordsTask() {
        return insertOrUpdateRecordsTask;
    }

    public void setInsertOrUpdateRecordsTask(RealmAsyncTask insertOrUpdateRecordsTask) {
        this.insertOrUpdateRecordsTask = insertOrUpdateRecordsTask;
    }


    public RealmResults<CBill> getAsyncBills() {
        return asyncBills;
    }

    public void setAsyncBills(RealmResults<CBill> asyncBills) {
        this.asyncBills = asyncBills;
    }

    public RealmResults<CRecord> getAsyncRecords() {
        return asyncRecords;
    }

    public void setAsyncRecords(RealmResults<CRecord> asyncRecords) {
        this.asyncRecords = asyncRecords;
    }

    public RealmResults<CUserDiyKind> getAsyncKinds() {
        return asyncKinds;
    }

    public void setAsyncKinds(RealmResults<CUserDiyKind> asyncKinds) {
        this.asyncKinds = asyncKinds;
    }

    @Override
    public boolean asyncAddOrUpdateBills(ArrayList<CBill> bills) {
        AtomicBoolean result= new AtomicBoolean(false);
        insertOrUpdateBillsTask = realm.executeTransactionAsync(
                realm1 -> {
                    realm1.copyToRealmOrUpdate(bills);
                },
                () -> {
                    Log.i(TAG, "asyncAddOrUpdateBills: success");
                    result.set(true);
                },
                error -> {
                    Log.i(TAG, "asyncAddOrUpdateBills: failed");
                    result.set(false);
                });

        insertOrUpdateBillsTask.cancel();
        return result.get();
    }

    @Override
    public boolean asyncAddOrUpdateRecords(ArrayList<CRecord> records) {
        AtomicBoolean result= new AtomicBoolean(false);
        insertOrUpdateRecordsTask = realm.executeTransactionAsync(
                realm1 -> realm1.copyToRealmOrUpdate(records),
                () -> {
                    Log.i(TAG, "asyncAddOrUpdateRecords: success");
                    result.set(true);
                },
                error -> {
                    Log.i(TAG, "asyncAddOrUpdateRecords: failed");
                    result.set(false);
                });
        insertOrUpdateRecordsTask.cancel();
        return result.get();
    }

    @Override
    public boolean asyncAddOrUpdateKinds(ArrayList<CUserDiyKind> kinds) {
        AtomicBoolean result= new AtomicBoolean(false);
        insertOrUpdateKindsTask = realm.executeTransactionAsync(
                (Realm realm1) -> {
                    realm1.copyToRealmOrUpdate(kinds);
                },
                () -> {
                    Log.i(TAG, "asyncAddOrUpdateKinds: succcess");
                    result.set(true);
                },
                error -> {
                    Log.i(TAG, "asyncAddOrUpdateKinds: failed");
                    result.set(false);
                });
        insertOrUpdateKindsTask.cancel();
        return result.get();
    }

    @Override
    public ArrayList<CBill> asyncQueryBills(String uId) {
        asyncBills=realm.where(CBill.class)
                .equalTo("uId",uId)
                .lessThan("bStatus",9)
                .findAllAsync();
        asyncBills.addChangeListener(new RealmChangeListener<RealmResults<CBill>>() {
            @Override
            public void onChange(RealmResults<CBill> cBills) {
              //  asyncBills.removeAllChangeListeners();
            }
        });
        List<CBill> bills=realm.copyFromRealm(asyncBills);
        removeBillsOnChangeListener();
        return new ArrayList<>(bills);
    }

    @Override
    public ArrayList<CRecord> asyncQueryRecords(String bId) {
        asyncRecords=realm.where(CRecord.class)
                .equalTo("bId",bId)
                .lessThan("rStatus",9)
                .findAllAsync()
                .sort("rTime");
        asyncRecords.addChangeListener(new RealmChangeListener<RealmResults<CRecord>>() {
            @Override
            public void onChange(RealmResults<CRecord> cRecords) {
               // asyncRecords.removeAllChangeListeners();
            }
        });
        List<CRecord> records=realm.copyFromRealm(asyncRecords);
        removeRecordsOnChangeListener();
        return new ArrayList<>(records);
    }

    @Override
    public ArrayList<CUserDiyKind> asyncQueryKinds(String uId) {
        asyncKinds=realm.where(CUserDiyKind.class)
                .equalTo("uId",uId)
                .lessThan("dStatus",9)
                .findAllAsync();
        asyncKinds.addChangeListener(new RealmChangeListener<RealmResults<CUserDiyKind>>() {
            @Override
            public void onChange(RealmResults<CUserDiyKind> cUserDiyKinds) {
               // asyncKinds.removeAllChangeListeners();
            }
        });
        List<CUserDiyKind> kinds=realm.copyFromRealm(asyncKinds);
        removeKindsOnChangeListener();
        return new ArrayList<>(kinds);
    }


    public void cancleAsycnBillTask(){
        if(insertOrUpdateBillsTask!=null&& !insertOrUpdateBillsTask.isCancelled())
        insertOrUpdateBillsTask.cancel();
    }

    public void cancleAsycnRecordsTask(){
        if(insertOrUpdateRecordsTask != null && !insertOrUpdateRecordsTask.isCancelled()){
            insertOrUpdateRecordsTask.cancel();
        }
    }

    public void cancleAsycnKindsTask(){
        if(insertOrUpdateKindsTask !=null && ! insertOrUpdateKindsTask.isCancelled()){
            insertOrUpdateKindsTask.cancel();
        }
    }

    public void removeBillsOnChangeListener(){
        if(asyncBills!=null){
            asyncBills.removeAllChangeListeners();
        }
    }


    public void removeRecordsOnChangeListener(){
        if(asyncRecords!=null){
            asyncRecords.removeAllChangeListeners();
        }
    }

    public void removeKindsOnChangeListener(){
        if(asyncKinds!=null){
            asyncKinds.removeAllChangeListeners();
        }
    }




}
