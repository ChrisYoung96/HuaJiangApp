package com.chrisyoung.huajiangapp.dao;

import com.chrisyoung.huajiangapp.domain.CUser;

public interface IUserDao {
    boolean addOrUpdateUser(CUser newUser);

    CUser showUserInfo(String uId);
}
