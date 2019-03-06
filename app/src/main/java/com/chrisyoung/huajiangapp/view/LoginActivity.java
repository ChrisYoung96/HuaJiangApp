package com.chrisyoung.huajiangapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.network.HttpResult;
import com.chrisyoung.huajiangapp.presenter.LoginPresenter;
import com.chrisyoung.huajiangapp.uitils.EditTextClearTools;
import com.chrisyoung.huajiangapp.uitils.SharedPreferenceUtil;
import com.chrisyoung.huajiangapp.uitils.ToastUtil;
import com.chrisyoung.huajiangapp.view.vinterface.ILoginInternetView;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements ILoginInternetView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.viewName)
    View viewName;
    @BindView(R.id.et_userName)
    EditText etUserName;
    @BindView(R.id.rl_userName)
    RelativeLayout rlUserName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.rl_userPassword)
    RelativeLayout rlUserPassword;
    @BindView(R.id.cb_checkbox)
    CheckBox cbCheckbox;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.iv_unameClear)
    ImageView ivUnameClear;
    @BindView(R.id.iv_pwdClear)
    ImageView ivPwdClear;
    @BindView(R.id.iv_userIconPwd)
    ImageView ivUserIconPwd;
    @BindView(R.id.viewPwd)
    View viewPwd;
    @BindView(R.id.btn_regist)
    QMUIButton btnRegist;

    QMUITipDialog dialog;

    QMUITipDialog edialog;

    LoginPresenter loginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        EditTextClearTools.addClearListener(etUserName, ivUnameClear);
        EditTextClearTools.addClearListener(etPassword, ivPwdClear);
       dialog=new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("loading")
                .create();
       loginPresenter=new LoginPresenter(this,this,this);
        String token=null;
        token= (String) SharedPreferenceUtil.get(this,"token",token);
        if(token!=null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
       }

    }


    @OnClick({R.id.btn_login, R.id.btn_regist})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                loginPresenter.getLoginResult(etUserName.getText().toString(),etPassword.getText().toString());
                break;
            case R.id.btn_regist:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        }

    }


    @Override
    public void jump2MainActivity(String parm) {
        Intent intent=new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("uId",parm);
        startActivity(intent);
    }


    @Override
    public void showProgressDialog() {
        dialog.show();

    }

    @Override
    public void hideProgressDialog() {
        dialog.hide();

    }

    @Override
    public void showError(String msg) {
        edialog=new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_INFO)
                .setTipWord(msg)
                .create();
        edialog.show();
    }

    @Override
    public void hideErrorDialog() {
        edialog.hide();
    }

    @Override
    public void showResult(String result) {
        ToastUtil.showShort(this, result);
    }
}
