package com.chrisyoung.huajiangapp.view.vinterface;

import com.trello.rxlifecycle2.LifecycleTransformer;

public interface BaseInternetView extends BaseView {
    void showProgressDialog();

    void hideProgressDialog();

    void showError(String msg);

    void hideErrorDialog();

    LifecycleTransformer bindLifecycle();

}
