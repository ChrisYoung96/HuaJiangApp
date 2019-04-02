package com.chrisyoung.huajiangapp.presenter;

import android.content.Context;
import android.content.Intent;

import com.chrisyoung.huajiangapp.biz.IUserBiz;
import com.chrisyoung.huajiangapp.biz.impl.UserInfoBiz;
import com.chrisyoung.huajiangapp.constant.UserConfig;
import com.chrisyoung.huajiangapp.domain.CUser;
import com.chrisyoung.huajiangapp.uitils.SharedPreferenceUtil;
import com.chrisyoung.huajiangapp.view.LoginActivity;
import com.chrisyoung.huajiangapp.view.vinterface.IMineInfoView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

public class UserInfoPresenter  {
    private IMineInfoView mineInfoView;
    private IUserBiz userBiz;
    private Context context;

    public UserInfoPresenter(IMineInfoView mineInfoView,Context context){
        this.mineInfoView=mineInfoView;
        userBiz=new UserInfoBiz();
        this.context=context;
    }


    public void initView(String uId){
        CUser user=userBiz.getUserInfo(uId);
        mineInfoView.initView(user);


    }

    public void logOut(){
        SharedPreferenceUtil.put(context,UserConfig.USER_ID,"");
        SharedPreferenceUtil.put(context,UserConfig.CUR_BNAME,"");
        SharedPreferenceUtil.put(context,UserConfig.CUR_BID,"");
        SharedPreferenceUtil.put(context,UserConfig.USER_NAME,"");
        SharedPreferenceUtil.put(context,UserConfig.AUTO_SYNC,false);
        mineInfoView.jum2LoginActivity();
    }
}
