package com.chrisyoung.huajiangapp.presenter;

import com.chrisyoung.huajiangapp.biz.IUserBiz;
import com.chrisyoung.huajiangapp.biz.impl.UserInfoBiz;
import com.chrisyoung.huajiangapp.domain.CUser;
import com.chrisyoung.huajiangapp.view.vinterface.IMineInfoView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

public class UserInfoPresenter  {
    private IMineInfoView mineInfoView;
    private IUserBiz userBiz;

    public UserInfoPresenter(IMineInfoView mineInfoView){
        this.mineInfoView=mineInfoView;
        userBiz=new UserInfoBiz();
    }


    public void initView(String uId){
        CUser user=userBiz.getUserInfo(uId);
        mineInfoView.initView(user);


    }

    public void logOut(){

    }
}
