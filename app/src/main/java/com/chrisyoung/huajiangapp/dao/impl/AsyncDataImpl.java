package com.chrisyoung.huajiangapp.dao.impl;

import android.content.Context;

import com.chrisyoung.huajiangapp.dao.BaseDao;
import com.chrisyoung.huajiangapp.dao.IScynData;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.domain.CUserDiyKind;
import com.chrisyoung.huajiangapp.uitils.ToastUtil;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import io.realm.Realm;
import io.realm.RealmAsyncTask;


public class AsyncDataImpl extends BaseDao implements IScynData {
    private RealmAsyncTask insertOrUpdateTask;

    @Override
    public boolean asyncAddOrUpdateBills(ArrayList<CBill> bills) {
        AtomicBoolean result= new AtomicBoolean(false);
        insertOrUpdateTask = realm.executeTransactionAsync(
                realm1 -> realm1.copyToRealmOrUpdate(bills),
                () -> result.set(true),
                error -> result.set(false));
        insertOrUpdateTask.cancel();
        return result.get();
    }

    @Override
    public boolean asyncAddOrUpdateRecords(ArrayList<CRecord> records) {
        AtomicBoolean result= new AtomicBoolean(false);
        insertOrUpdateTask = realm.executeTransactionAsync(
                realm1 -> realm1.copyToRealmOrUpdate(records),
                () -> result.set(true),
                error ->result.set(false));
        insertOrUpdateTask.cancel();
        return result.get();
    }

    @Override
    public boolean asyncAddorUpdateKinds(ArrayList<CUserDiyKind> kinds) {
        AtomicBoolean result= new AtomicBoolean(false);
        insertOrUpdateTask = realm.executeTransactionAsync(
                (Realm realm1) -> {
                    realm1.copyToRealmOrUpdate(kinds);
                },
                () -> result.set(true),
                error -> result.set(false));
        insertOrUpdateTask.cancel();
        return result.get();
    }
}
