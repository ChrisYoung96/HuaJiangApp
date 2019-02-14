package com.chrisyoung.huajiangapp.view.vinterface;

public interface BaseView {
    void showProgressDialog();

    void hideProgressDialog();

    void showError(String msg);

    void hideErrorDialog();
}
