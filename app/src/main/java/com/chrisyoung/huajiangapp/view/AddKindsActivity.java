package com.chrisyoung.huajiangapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.domain.CUserDiyKind;
import com.chrisyoung.huajiangapp.presenter.KindPresenter;
import com.chrisyoung.huajiangapp.uitils.ToastUtil;
import com.chrisyoung.huajiangapp.view.vinterface.IKindView;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddKindsActivity extends AppCompatActivity  implements IKindView {
    private String type;
    private String uId;
    private KindPresenter presenter;

    @BindView(R.id.btnAKBack)
    ImageButton btnAKBack;
    @BindView(R.id.txtKindName)
    TextView txtKindName;
    @BindView(R.id.btnAddKind)
    Button btnAddKind;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kinds);
        ButterKnife.bind(this);
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
        type=getIntent().getStringExtra("type");
        uId=getIntent().getStringExtra("uId");
        presenter=new KindPresenter(this);
    }

    @OnClick({R.id.btnAKBack, R.id.btnAddKind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAKBack:
                Intent intent=new Intent(this,MainActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("uId",uId);
                intent.putExtra("tabNo",MainActivity.MINE);
                startActivity(intent);
                break;
            case R.id.btnAddKind:
                addKind();
                break;
        }
    }

    private void addKind(){
        CUserDiyKind kind=new CUserDiyKind();
        kind.setdKind(txtKindName.getText().toString());
        kind.setuId(uId);
        kind.setdType(type);
        presenter.addKind(kind);
    }

    @Override
    public void showKinds(ArrayList<CUserDiyKind> kinds) {

    }

    @Override
    public void showResult(String result) {
        ToastUtil.showShort(this,result);
    }
}
