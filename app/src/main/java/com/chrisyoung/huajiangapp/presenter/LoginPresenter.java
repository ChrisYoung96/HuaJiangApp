package com.chrisyoung.huajiangapp.presenter;

import android.content.Context;

import com.chrisyoung.huajiangapp.biz.IUserBiz;
import com.chrisyoung.huajiangapp.biz.impl.UserInfoBiz;
import com.chrisyoung.huajiangapp.constant.ResultCode;
import com.chrisyoung.huajiangapp.constant.UserConfig;
import com.chrisyoung.huajiangapp.domain.CUser;
import com.chrisyoung.huajiangapp.dto.AppUser;
import com.chrisyoung.huajiangapp.network.DataManager;
import com.chrisyoung.huajiangapp.network.HttpResult;
import com.chrisyoung.huajiangapp.uitils.NetUtil;
import com.chrisyoung.huajiangapp.uitils.SharedPreferenceUtil;
import com.chrisyoung.huajiangapp.view.vinterface.ILoginInternetView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter {
    private ILoginInternetView loginView;
    private DataManager dataManager;
    private Context context;
    private IUserBiz userBiz;
    private String token;
    public LoginPresenter(LifecycleProvider<ActivityEvent> provider, ILoginInternetView loginView, Context context) {
        super(provider);
        this.loginView=loginView;
        this.context=context;
        dataManager=DataManager.getInstance();
        userBiz=new UserInfoBiz();
    }



    public void getLoginResult(String indentify, String credential){
        if(NetUtil.isConnected(context)){
            Observable<HttpResult<String>> getToken=dataManager.login(indentify, credential);


            loginView.showProgressDialog();


            getToken.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(loginView.bindLifecycle())
                    .doOnNext(new Consumer<HttpResult<String>>() {
                        @Override
                        public void accept(HttpResult<String> result) throws Exception {
                            if(result.getCode()==ResultCode.SUCCESS.code()){
                                token=result.getData();
                                SharedPreferenceUtil.put(context,UserConfig.TOKEN,token);
                            }else{
                                return;
                            }


                        }
                    })
                    .observeOn(Schedulers.io())
                    .flatMap(new Function<HttpResult<String>, ObservableSource<HttpResult<AppUser>>>() {
                        @Override
                        public ObservableSource<HttpResult<AppUser>> apply(HttpResult<String> result) throws Exception {
                            return dataManager.getUid(token);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(loginView.bindLifecycle())
                    .subscribe(new Consumer<HttpResult<AppUser>>() {
                        @Override
                        public void accept(HttpResult<AppUser> result) throws Exception {
                            AppUser uId = result.getData();
                            System.out.println(result.getCode());
                            SharedPreferenceUtil.put(context, UserConfig.USER_ID, uId.getuId());
                            SharedPreferenceUtil.put(context, UserConfig.USER_NAME, uId.getuName());
                            CUser user = new CUser();
                            user.setuId(uId.getuId());
                            user.setuName(uId.getuName());
                            user.setuPhone(uId.getuPhone());
                            user.setuSex(uId.getuSex());
                            user.setuMail(uId.getuMail());
                            user.setuBirthday(uId.getuBirthday());
                            user.setuPhoto(uId.getuPhoto());
                            userBiz.updateUserInfo(user);
                            loginView.hideProgressDialog();
                            loginView.jump2MainActivity(uId.getuId());
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            loginView.hideProgressDialog();
                            loginView.showError("用户名或密码错误");
                        }
                    });



        }else {
            loginView.showError("网络未连接");
        }

    }


    public void closeRealm(){
       userBiz.closeRealm();
    }

}
