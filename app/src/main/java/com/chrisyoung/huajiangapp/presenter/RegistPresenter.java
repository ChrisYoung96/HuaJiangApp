package com.chrisyoung.huajiangapp.presenter;

import android.content.Context;

import com.chrisyoung.huajiangapp.dto.UserAuths;
import com.chrisyoung.huajiangapp.dto.VerificationCode;
import com.chrisyoung.huajiangapp.network.DataManager;
import com.chrisyoung.huajiangapp.network.HttpResult;
import com.chrisyoung.huajiangapp.uitils.ToastUtil;
import com.chrisyoung.huajiangapp.view.vinterface.IRegisterInternetView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.sql.Time;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegistPresenter extends BasePresenter{
    private IRegisterInternetView registerView;
    private Context context;
    private UserAuths auths;
    private DataManager dataManager;
    private VerificationCode code;


    public RegistPresenter(LifecycleProvider<ActivityEvent> provider, Context context, IRegisterInternetView registerView) {
        super(provider);
        this.registerView=registerView;
        this.context=context;
        dataManager=DataManager.getInstance();
    }

    public void Register(String identify,String credential,String c){
        if(!isCodeExpired(code)){
            registerView.showError("code is expired");
        }else{
            if(verifyCode(c)){
                UserAuths auths=new UserAuths();
                auths.setCredential(credential);
                auths.setIdentify(identify);
                auths.setIdentityType("phone");
                dataManager.register(auths)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<HttpResult<String>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(HttpResult<String> result) {
                                registerView.jump2LoginActivity();

                            }

                            @Override
                            public void onError(Throwable e) {
                                registerView.showError(e.getMessage());

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        }


    }

    public void getCode(String phone){
        dataManager.getCode(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpResult<VerificationCode>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        registerView.refreshBtnTextIn30Seconds();
                    }

                    @Override
                    public void onNext(HttpResult<VerificationCode> verificationCodeHttpResult) {
                        code=verificationCodeHttpResult.getData();
                        ToastUtil.showShort(context,code.getCode());


                    }

                    @Override
                    public void onError(Throwable e) {
                        registerView.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private boolean verifyCode(String tcode){
        return tcode.equals(code.getCode());

    }

   private boolean isCodeExpired(VerificationCode code){
       Time t=new Time(System.currentTimeMillis());
        return t.getTime()- code.getCreateTime()>30*1000;
    }
}
