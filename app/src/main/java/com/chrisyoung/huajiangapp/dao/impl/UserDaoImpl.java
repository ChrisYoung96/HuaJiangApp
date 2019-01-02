package com.chrisyoung.huajiangapp.dao.impl;

import com.chrisyoung.huajiangapp.dao.BaseDao;
import com.chrisyoung.huajiangapp.dao.IUserDao;
import com.chrisyoung.huajiangapp.domain.CUser;

import java.util.ArrayList;

public class UserDaoImpl extends BaseDao implements IUserDao {

    public UserDaoImpl() {
        super();
    }

    @Override
    public boolean addOrUpdateUser(CUser newUser) {
        return insertOrUpdateRealmObject(newUser);
    }

    @Override
    public CUser showUserInfo(String uId) {
        return (CUser) queryRealmObjectWithCondition("uId",uId,CUser.class);
    }
}
