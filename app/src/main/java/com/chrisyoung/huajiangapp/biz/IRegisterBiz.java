package com.chrisyoung.huajiangapp.biz;

import com.chrisyoung.huajiangapp.domain.CUser;

public interface IRegisterBiz {
    boolean verifyVerificationCode(String code);

    boolean register(CUser user);
}
