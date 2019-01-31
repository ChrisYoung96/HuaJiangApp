package com.chrisyoung.huajiangapp.dao.impl;

import com.chrisyoung.huajiangapp.dao.BaseDao;
import com.chrisyoung.huajiangapp.dao.IUserDiyKindDao;
import com.chrisyoung.huajiangapp.domain.CUser;
import com.chrisyoung.huajiangapp.domain.CUserDiyKind;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;
import io.realm.RealmResults;

public class UserDiyKindDaoImpl extends BaseDao implements IUserDiyKindDao {
    @Override
    public boolean addOrUpdateKind(CUserDiyKind newKind) {
        return insertOrUpdateRealmObject(newKind);
    }

    public boolean addKindForUser(CUser user,CUserDiyKind newKind){
        realm.beginTransaction();
        boolean result=user.getcUserDiyKinds().add(newKind);
        realm.commitTransaction();
        return result;
    }

    @Override
    public boolean deleteKind(String kId) {
        return deleteRealmObjectWithCondition("kId",kId,CUserDiyKind.class);
    }


    @Override
    public ArrayList<CUserDiyKind> showAllKinds(String uId) {
        return convert(queryRealmObjectsWithCondition("uId",uId,CUserDiyKind.class));
    }

    @Override
    public ArrayList<CUserDiyKind> showKindsNeedSynchronize(String uId) {
        RealmResults<CUserDiyKind> kinds=realm.where(CUserDiyKind.class).equalTo("uId",uId).lessThan("dVersion",9).findAll();
        List<CUserDiyKind> temp=realm.copyFromRealm(kinds);
        return new ArrayList<>(temp);
    }

    private ArrayList<CUserDiyKind> convert(List<? extends RealmObject> datas){
        ArrayList<CUserDiyKind> data=new ArrayList<>();
        for (RealmObject obj:datas
                ) {
            CUserDiyKind r=(CUserDiyKind) obj;
            data.add(r);
        }
        return  data;
    }
}
