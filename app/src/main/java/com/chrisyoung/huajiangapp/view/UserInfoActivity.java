package com.chrisyoung.huajiangapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.domain.CUser;
import com.chrisyoung.huajiangapp.presenter.UserInfoPresenter;
import com.chrisyoung.huajiangapp.uitils.DateFormatUtil;
import com.chrisyoung.huajiangapp.view.vinterface.IMineInfoView;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInfoActivity extends BaseActivity implements IMineInfoView {
    private String uId;
    private UserInfoPresenter presenter;

    @BindView(R.id.btnMineBack)
    ImageButton btnMineBack;
    @BindView(R.id.btnLogout)
    Button btnLogout;
    @BindView(R.id.infoGroupListView)
    QMUIGroupListView infoGroupListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        uId = getIntent().getStringExtra("uId");
        presenter=new UserInfoPresenter(this);
        presenter.initView(uId);
    }





    @Override
    public void initView(CUser user) {
        if(user==null){
            user=new CUser();
            user.setuPhone(" ");
            user.setuBirthday(DateFormatUtil.stringToDate("1970-1-1"));
            user.setuName(" ");
            user.setuSex("男");
            user.setuPhone(" ");
        }
        QMUICommonListItemView headPhoto = infoGroupListView.createItemView(
                "头像");
        headPhoto.setOrientation(QMUICommonListItemView.VERTICAL);
        headPhoto.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.account);
        headPhoto.addAccessoryCustomView(imageView);


        QMUICommonListItemView myName = infoGroupListView.createItemView("用户名");
        myName.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        myName.setDetailText(user.getuName());
        myName.setOrientation(QMUICommonListItemView.HORIZONTAL);

        QMUICommonListItemView mySex = infoGroupListView.createItemView("性别");
        mySex.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        mySex.setDetailText(user.getuSex());


        QMUICommonListItemView myBirthday = infoGroupListView.createItemView("生日");
        myBirthday.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        myBirthday.setDetailText(DateFormatUtil.dateToString(user.getuBirthday()));
        myBirthday.setOrientation(QMUICommonListItemView.HORIZONTAL);

        QMUICommonListItemView myPhone = infoGroupListView.createItemView("电话");
        myPhone.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        myPhone.setDetailText(user.getuPhone());
        myPhone.setOrientation(QMUICommonListItemView.HORIZONTAL);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof QMUICommonListItemView) {
                    CharSequence text = ((QMUICommonListItemView) v).getText();


                }
            }
        };


        QMUIGroupListView.newSection(this)
                .addItemView(headPhoto, onClickListener)
                .addItemView(myName, onClickListener)
                .addItemView(mySex, onClickListener)
                .addItemView(myBirthday, onClickListener)
                .addItemView(myPhone, onClickListener)
                .addTo(infoGroupListView);


        if(uId.equals("")||uId==null){
            btnLogout.setText("登录");
        }
    }

    @Override
    public void showResult(String result) {

    }

    @OnClick({R.id.btnMineBack, R.id.btnLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnMineBack:
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnLogout:
                if(uId.equals("")||uId==null){
                    Intent intent1=new Intent(this,LoginActivity.class);
                    startActivity(intent1);
                    fileList();
                }else{
                    presenter.logOut();
                }
                break;
        }
    }
}
