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

import java.util.Date;

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


    public void updateUserInfo(CUser cUser){
        userBiz.updateUserInfo(cUser);
    }

    public void updateUserName(String uId,String uName){
        CUser user=userBiz.getUserInfo(uId);
        user.setuName(uName);
        userBiz.updateUserInfo(user);
    }

    public void updateUserBirthday(String uId,long millseconds){
        Date date=new Date(millseconds);
        CUser user=userBiz.getUserInfo(uId);
        user.setuBirthday(date);
        userBiz.updateUserInfo(user);
    }

    public void updateUserSex(String uId,String sex){
        CUser user=userBiz.getUserInfo(uId);
        user.setuSex(sex);
        userBiz.updateUserInfo(user);
    }


    public void closeRealm(){
        userBiz.closeRealm();
    }




}
