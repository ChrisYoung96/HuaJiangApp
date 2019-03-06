package com.chrisyoung.huajiangapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.presenter.RegistPresenter;
import com.chrisyoung.huajiangapp.uitils.ToastUtil;
import com.chrisyoung.huajiangapp.view.vinterface.IRegisterInternetView;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements IRegisterInternetView {

    @BindView(R.id.btbReBack)
    ImageButton btbReBack;
    @BindView(R.id.et_register_identify)
    EditText etRegisterIdentify;
    @BindView(R.id.et_register_password)
    EditText etRegisterPassword;
    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.btnGetCode)
    Button btnGetCode;
    @BindView(R.id.btnRegister)
    Button btnRegister;

    private RegistPresenter registPresenter;

    private QMUITipDialog loadDialog;
    private QMUITipDialog erroDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        registPresenter=new RegistPresenter(this,this,this);
    }

    @OnClick({R.id.btbReBack, R.id.btnGetCode, R.id.btnRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btbReBack:
                startActivity(new Intent(this, LoginActivity.class));
            case R.id.btnGetCode:
                registPresenter.getCode(etRegisterIdentify.getText().toString());
                break;
            case R.id.btnRegister:
                registPresenter.Register(etRegisterIdentify.getText().toString(), etRegisterPassword.getText().toString(), code.getText().toString());
                break;
        }
    }


    @Override
    public void jump2LoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void refreshBtnTextIn30Seconds() {
        CountDownTimer timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btnGetCode.setEnabled(false);
                btnGetCode.setText("已发送(" + millisUntilFinished / 1000 + ")");

            }

            @Override
            public void onFinish() {
                btnGetCode.setEnabled(true);
                btnGetCode.setText("重新获取验证码");

            }
        }.start();

    }

    @Override
    public void showProgressDialog() {
        loadDialog = new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("Loading")
                .create();
        loadDialog.show();

    }

    @Override
    public void hideProgressDialog() {
        loadDialog.hide();

    }

    @Override
    public void showError(String msg) {
        erroDialog = new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord("msg")
                .create();
        erroDialog.show();

    }

    @Override
    public void hideErrorDialog() {
        erroDialog.hide();
    }


    @Override
    public void showResult(String result) {
        ToastUtil.showShort(this, result);
    }
}
