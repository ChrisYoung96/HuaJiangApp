package com.chrisyoung.huajiangapp.uitils;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmHelper {

    private static RealmHelper realmHelper=new RealmHelper();

    private void config(String name,int version){
        RealmConfiguration config=new RealmConfiguration.Builder()
                .name(name)
                .schemaVersion(version)
                .deleteRealmIfMigrationNeeded()//声明版本冲突时自动删除原数据库(当调用了该方法时，上面的方法将失效)
                .build();

        Realm.setDefaultConfiguration(config);
    }

    private Realm openRealm1(){
        return Realm.getDefaultInstance();
    }

    private void closeRealm1(Realm realm){
        realm.close();
    }

    public static void configRealm(String realmName,int realmVersion){
        realmHelper.config(realmName,realmVersion);
    }

    public static Realm openRealm(){
        return realmHelper.openRealm1();
    }

    public static void closeRealm(Realm realm){
        realmHelper.closeRealm1(realm);
    }                                               

}
