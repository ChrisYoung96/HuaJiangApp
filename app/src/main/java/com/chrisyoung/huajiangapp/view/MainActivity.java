package com.chrisyoung.huajiangapp.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.uitils.ToastUtil;

public class MainActivity extends AppCompatActivity implements ShowRecordFragment.OnFragmentInteractionListener,ChartFragment.OnFragmentInteractionListener,BillFragment.OnFragmentInteractionListener,MineFragment.OnFragmentInteractionListener{

    private RadioGroup mTabRadioGroup;
    private SparseArray<Fragment> mFragmentSparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        ToastUtil.showShort(this,getIntent().getStringExtra("uId"));
    }

    private void initView() {
        mTabRadioGroup = findViewById(R.id.tabs_rg);
        mFragmentSparseArray = new SparseArray<>();
        mFragmentSparseArray.append(R.id.today_tab, ShowRecordFragment.newInstance("今日","llal"));
        mFragmentSparseArray.append(R.id.record_tab, ChartFragment.newInstance("ddd","ddd"));
        mFragmentSparseArray.append(R.id.contact_tab, BillFragment.newInstance("通讯录","sdf"));
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
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                mFragmentSparseArray.get(R.id.today_tab)).commit();
        findViewById(R.id.sign_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddRecordActivity.class));
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
