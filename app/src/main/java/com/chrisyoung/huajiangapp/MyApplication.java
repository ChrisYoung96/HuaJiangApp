package com.chrisyoung.huajiangapp;

import android.app.Application;

import com.chrisyoung.huajiangapp.uitils.RealmHelper;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmHelper.configRealm("apprealm",0);
    }
}
