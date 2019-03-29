package com.chrisyoung.huajiangapp.biz.impl;

import com.chrisyoung.huajiangapp.biz.IUserBiz;
import com.chrisyoung.huajiangapp.dao.IUserDao;
import com.chrisyoung.huajiangapp.dao.impl.UserDaoImpl;
import com.chrisyoung.huajiangapp.domain.CUser;

public class UserInfoBiz implements IUserBiz {
    private IUserDao userDao=new UserDaoImpl();
    @Override
    public CUser getUserInfo(String uId) {
        return userDao.showUserInfo(uId);
    }

    @Override
    public void logOut(String uId) {

    }
}
