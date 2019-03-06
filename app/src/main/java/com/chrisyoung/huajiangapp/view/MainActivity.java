package com.chrisyoung.huajiangapp.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.uitils.SharedPreferenceUtil;
import com.chrisyoung.huajiangapp.uitils.ToastUtil;

public class MainActivity extends BaseActivity implements ShowRecordFragment.OnFragmentInteractionListener,ChartFragment.OnFragmentInteractionListener,BillFragment.OnFragmentInteractionListener,MineFragment.OnFragmentInteractionListener{

    private RadioGroup mTabRadioGroup;
    private SparseArray<Fragment> mFragmentSparseArray;
    private String uId;
    private String curBId;
    private RadioButton btnAddBill;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        uId=getIntent().getStringExtra("uId");
        //curBId=(String) SharedPreferenceUtil.get(this,"curBId",curBId);
        curBId="385ca5a9ee1340148ce3977b99e6660b";
        uId="94d5f9cbd27b4526a9b90176f44037d7";
        //initView();



       // ToastUtil.showShort(this,getIntent().getStringExtra("uId"));
    }

    private void initView() {
        btnAddBill=findViewById(R.id.contact_tab);
        mTabRadioGroup = findViewById(R.id.tabs_rg);
        mFragmentSparseArray = new SparseArray<>();
        mFragmentSparseArray.append(R.id.today_tab, ShowRecordFragment.newInstance(curBId,uId));
        mFragmentSparseArray.append(R.id.record_tab, ChartFragment.newInstance(curBId,"dd"));
        mFragmentSparseArray.append(R.id.contact_tab, BillFragment.newInstance(uId,"sdf"));
        mFragmentSparseArray.append(R.id.settings_tab, MineFragment.newInstance("设置","jkjl"));
        mTabRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 具体的fragment切换逻辑可以根据应用调整，例如使用show()/hide()
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        mFragmentSparseArray.get(checkedId)).commit();
            }
        });


        // 默认显示第一个
        if(curBId==null){
            ToastUtil.showShort(this,"请创建账本");
            btnAddBill.setChecked(true);

        }else{
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                    mFragmentSparseArray.get(R.id.today_tab)).commit();
        }

        findViewById(R.id.sign_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddRecordActivity.class);
                intent.putExtra("uId",uId);
                intent.putExtra("curBId",curBId);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }


}
