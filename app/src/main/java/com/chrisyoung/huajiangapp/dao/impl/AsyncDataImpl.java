package com.chrisyoung.huajiangapp.dao.impl;

import android.content.Context;

import com.chrisyoung.huajiangapp.dao.BaseDao;
import com.chrisyoung.huajiangapp.dao.IAscynData;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.domain.CUserDiyKind;
import com.chrisyoung.huajiangapp.uitils.ToastUtil;

import java.util.ArrayList;

import io.realm.RealmAsyncTask;


public class AsyncDataImpl extends BaseDao implements IAscynData {
    private RealmAsyncTask insertOrUpdateTask;

    @Override
    public void asyncAddOrUpdateBills(ArrayList<CBill> bills, Context context) {
        insertOrUpdateTask = realm.executeTransactionAsync(
                realm1 -> realm1.copyToRealmOrUpdate(bills),
                () -> ToastUtil.showShort(context, "账本数据同步成功"),
                error -> ToastUtil.showShort(context, "同步失败"));
        insertOrUpdateTask.cancel();
    }

    @Override
    public void asyncAddOrUpdateRecords(ArrayList<CRecord> records, Context context) {
        insertOrUpdateTask = realm.executeTransactionAsync(
                realm1 -> realm1.copyToRealmOrUpdate(records),
                () -> ToastUtil.showShort(context, "记录数据同步成功"),
                error -> ToastUtil.showShort(context, "同步失败"));
        insertOrUpdateTask.cancel();
    }

    @Override
    public void asyncAddorUpdateKinds(ArrayList<CUserDiyKind> kinds, Context context) {
        insertOrUpdateTask = realm.executeTransactionAsync(
                realm1 -> realm1.copyToRealmOrUpdate(kinds),
                () -> ToastUtil.showShort(context, "自定义类别数据同步成功"),
                error -> ToastUtil.showShort(context, "同步失败"));
        insertOrUpdateTask.cancel();
    }
}
