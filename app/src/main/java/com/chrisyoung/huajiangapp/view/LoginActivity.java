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
import com.chrisyoung.huajiangapp.uitils.EditTextClearTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        EditTextClearTools.addClearListener(etUserName,ivUnameClear);
        EditTextClearTools.addClearListener(etPassword,ivPwdClear);

    }


    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }
}
