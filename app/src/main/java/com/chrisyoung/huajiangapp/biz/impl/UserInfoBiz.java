package com.chrisyoung.huajiangapp.biz.impl;

import com.chrisyoung.huajiangapp.biz.IUserBiz;
import com.chrisyoung.huajiangapp.dao.IUserDao;
import com.chrisyoung.huajiangapp.dao.impl.UserDaoImpl;
import com.chrisyoung.huajiangapp.domain.CUser;
import com.chrisyoung.huajiangapp.uitils.SharedPreferenceUtil;

public class UserInfoBiz implements IUserBiz {
    private IUserDao userDao=new UserDaoImpl();
    @Override
    public CUser getUserInfo(String uId) {
        return userDao.showUserInfo(uId);
    }

    @Override
    public void logOut(String uId) {
    }

    @Override
    public boolean updateUserInfo(CUser user) {
       return userDao.addOrUpdateUser(user);
    }

    @Override
    public void closeRealm() {
        userDao.closeRealm();
    }
}
