package com.chrisyoung.huajiangapp.dao;

import android.util.Log;

import com.chrisyoung.huajiangapp.uitils.RealmHelper;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class BaseDao {
    protected Realm realm;

    public BaseDao() {
        this.realm = RealmHelper.openRealm();
    }

    public boolean insertOrUpdateRealmObject(RealmObject obj) {
        boolean result = false;
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(obj);
            result = true;
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //RealmHelper.closeRealm(realm);
        return result;
    }

    public boolean insertOrUpdateRealmObjects(List<? extends RealmObject> objs) {
        boolean result = false;
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(objs);
            result = true;
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //RealmHelper.closeRealm(realm);
        return result;
    }

    public List<? extends RealmObject> queryAllRealmObjects(Class<? extends RealmObject> clazz) {
        RealmResults<? extends RealmObject> realmResults = realm.where(clazz).findAll();
        List<? extends RealmObject> results = realm.copyFromRealm(realmResults);
        //RealmHelper.closeRealm(realm);
        return results;
    }

    public List<? extends RealmObject> queryRealmObjectsWithCondition(String condition, String value, Class<? extends RealmObject> clazz) {
        RealmResults<? extends RealmObject> realmResults = realm.where(clazz)
                .equalTo(condition, value)
                .findAll();
        List<? extends RealmObject> results = realm.copyFromRealm(realmResults);
        //.closeRealm(realm);
        return results;
    }

    public RealmObject queryRealmObjectWithCondition(String condition, String value, Class<? extends RealmObject> clazz) {
        RealmObject result = realm.where(clazz).equalTo(condition, value).findFirst();

        return result;
    }

    public boolean deleteRealmObjectWithCondition(String condition, String value, Class<? extends RealmObject> clazz) {
        RealmObject object = queryRealmObjectWithCondition(condition, value, clazz);
        if (object == null) {
            return false;
        }
        try {
            realm.beginTransaction();
            object.deleteFromRealm();
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public void closeRealm(){
        RealmHelper.closeRealm(realm);
    }


}
