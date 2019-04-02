package com.chrisyoung.huajiangapp.presenter;

import android.content.Context;

import com.chrisyoung.huajiangapp.constant.UserConfig;
import com.chrisyoung.huajiangapp.network.DataManager;
import com.chrisyoung.huajiangapp.network.HttpResult;
import com.chrisyoung.huajiangapp.uitils.NetUtil;
import com.chrisyoung.huajiangapp.uitils.SharedPreferenceUtil;
import com.chrisyoung.huajiangapp.view.vinterface.ILoginInternetView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter {
    private ILoginInternetView loginView;
    private DataManager dataManager;
    private Context context;
    private String token;
    public LoginPresenter(LifecycleProvider<ActivityEvent> provider, ILoginInternetView loginView, Context context) {
        super(provider);
        this.loginView=loginView;
        this.context=context;
        dataManager=DataManager.getInstance();
    }

    public void getLoginResult(String indentify,String credential){
        if(NetUtil.isConnected(context)){
            Observable<HttpResult<String>> getToken=dataManager.login(indentify, credential);


            loginView.showProgressDialog();

            getToken.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(new Consumer<HttpResult<String>>() {
                        @Override
                        public void accept(HttpResult<String> result) throws Exception {
                            token=result.getData();
                            SharedPreferenceUtil.put(context,UserConfig.TOKEN,token);

                        }
                    })
                    .observeOn(Schedulers.io())
                    .flatMap(new Function<HttpResult<String>, ObservableSource<HttpResult<String>>>() {
                        @Override
                        public ObservableSource<HttpResult<String>> apply(HttpResult<String> result) throws Exception {
                            return dataManager.getUid(token);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<HttpResult<String>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(HttpResult<String> result) {
                            String uId=result.getData();
                            SharedPreferenceUtil.put(context,UserConfig.USER_ID,uId);
                            loginView.hideProgressDialog();
                            loginView.jump2MainActivity(uId);
                        }

                        @Override
                        public void onError(Throwable e) {
                            loginView.showError(e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });


        }else {
            loginView.showError("网络未连接");
        }

    }
}
