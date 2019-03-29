package com.chrisyoung.huajiangapp.biz;

import com.chrisyoung.huajiangapp.domain.CUser;

public interface IUserBiz {
    CUser getUserInfo(String uId);

    void logOut(String uId);

}
