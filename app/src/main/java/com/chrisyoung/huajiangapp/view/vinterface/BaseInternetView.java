package com.chrisyoung.huajiangapp.view.vinterface;

public interface BaseInternetView extends BaseView {
    void showProgressDialog();

    void hideProgressDialog();

    void showError(String msg);

    void hideErrorDialog();
}
