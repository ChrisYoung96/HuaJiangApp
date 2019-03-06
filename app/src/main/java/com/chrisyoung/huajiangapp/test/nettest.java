package com.chrisyoung.huajiangapp.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.network.HttpResult;
import com.chrisyoung.huajiangapp.presenter.LoginPresenter;
import com.chrisyoung.huajiangapp.uitils.ToastUtil;
import com.chrisyoung.huajiangapp.view.BaseActivity;
import com.chrisyoung.huajiangapp.view.vinterface.ILoginInternetView;
import com.chrisyoung.huajiangapp.view.TestActivity;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class nettest extends BaseActivity implements ILoginInternetView {

    @BindView(R.id.btnTestLogin)
    Button btnTestLogin;
    @BindView(R.id.btnTestRegist)
    Button btnTestRegist;
    @BindView(R.id.txtTestData)
    TextView txtTestData;

    private QMUITipDialog qmuiTipDialog;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nettest);
        ButterKnife.bind(this);
        loginPresenter=new LoginPresenter(this,this,this);
        qmuiTipDialog=new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("loading")
                .create();
    }

    @OnClick({R.id.btnTestLogin, R.id.btnTestRegist})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnTestLogin:
                loginPresenter.getLoginResult("13181891144","111111");
            case R.id.btnTestRegist:
                break;
        }
    }

    @Override
    public void jump2MainActivity(String prapm) {
        Intent i=new Intent(this, TestActivity.class);
        this.startActivity(i);
    }



    @Override
    public void showProgressDialog() {
        qmuiTipDialog.show();

    }

    @Override
    public void hideProgressDialog() {
        qmuiTipDialog.hide();

    }

    @Override
    public void showError(String msg) {
        ToastUtil.showShort(this,msg);
    }

    @Override
    public void hideErrorDialog() {

    }

    @Override
    public void showResult(String result) {

    }
}
