package com.chrisyoung.huajiangapp.presenter;

import android.content.Context;

import com.chrisyoung.huajiangapp.network.DataManager;
import com.chrisyoung.huajiangapp.network.HttpResult;
import com.chrisyoung.huajiangapp.uitils.SharedPreferenceUtil;
import com.chrisyoung.huajiangapp.view.ILoginView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter {
    private ILoginView loginView;
    private DataManager dataManager;
    private Context context;
    public LoginPresenter(LifecycleProvider<ActivityEvent> provider, ILoginView loginView, Context context) {
        super(provider);
        this.loginView=loginView;
        this.context=context;
        dataManager=DataManager.getInstance();
    }

    public void getLoginResult(String indentify,String credential){
        loginView.showProgressDialog();

        dataManager.login(indentify, credential)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(getProvider().bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Observer<HttpResult<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {

                        SharedPreferenceUtil.put(context,"token",stringHttpResult.getData());
                        loginView.jump2MainActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loginView.showError(e.getMessage());
                        loginView.hideProgressDialog();

                    }

                    @Override
                    public void onComplete() {
                        loginView.hideProgressDialog();

                    }
                });
    }
}
