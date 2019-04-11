package com.chrisyoung.huajiangapp.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.constant.UserConfig;
import com.chrisyoung.huajiangapp.uitils.SharedPreferenceUtil;
import com.chrisyoung.huajiangapp.uitils.ToastUtil;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

public class MainActivity extends BaseActivity implements ShowRecordFragment.OnFragmentInteractionListener, ChartFragment.OnFragmentInteractionListener, BillFragment.OnFragmentInteractionListener, MineFragment.OnFragmentInteractionListener, StatisticsCostFragment.OnFragmentInteractionListener, StatisticsIncomeFragment.OnFragmentInteractionListener {
    public static final int RECORD = R.id.records;
    public static final int CHART = R.id.chart;
    public static final int BILL = R.id.bill;
    public static final int MINE = R.id.mine;

    private RadioGroup mTabRadioGroup;
    private SparseArray<Fragment> mFragmentSparseArray;
    private String uId="";
    private String curBId="";
    private RadioButton btnAddBill;
    private RadioButton btnRecord;
    private RadioButton btnChart;
    private RadioButton btnMine;
    private int tabNo=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        curBId=(String) SharedPreferenceUtil.get(this,UserConfig.CUR_BID,curBId);
        uId=(String) SharedPreferenceUtil.get(this,UserConfig.USER_ID,uId);
//        curBId = "385ca5a9ee1340148ce3977b99e6660b";
//        uId = "94d5f9cbd27b4526a9b90176f44037d7";
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
        tabNo = getIntent().getIntExtra("tabNo",0);
        initView();

    }

    private void initView() {
        btnAddBill = findViewById(R.id.bill);
        btnRecord = findViewById(R.id.records);
        btnChart = findViewById(R.id.chart);
        btnMine = findViewById(R.id.mine);
        mTabRadioGroup = findViewById(R.id.tabs_rg);
        mFragmentSparseArray = new SparseArray<>();
        mFragmentSparseArray.append(R.id.records, ShowRecordFragment.newInstance(curBId, uId));
        mFragmentSparseArray.append(R.id.chart, ChartFragment.newInstance(uId, curBId));
        mFragmentSparseArray.append(R.id.bill, BillFragment.newInstance(uId, "sdf"));
        mFragmentSparseArray.append(R.id.mine, MineFragment.newInstance(uId, "jkjl"));
        mTabRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 具体的fragment切换逻辑可以根据应用调整，例如使用show()/hide()
                tabNo=checkedId;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        mFragmentSparseArray.get(checkedId)).commit();
            }
        });


        if(tabNo==0){
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                    mFragmentSparseArray.get(R.id.records)).commit();
        }
        switch (tabNo) {
            case RECORD:
                btnRecord.setChecked(true);
                break;
            case CHART:
                btnChart.setChecked(true);
                break;
            case BILL:
                btnAddBill.setChecked(true);
                break;
            case MINE:
                btnMine.setChecked(true);
                break;
        }

        findViewById(R.id.sign_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddRecordActivity.class);
                intent.putExtra("uId", uId);
                intent.putExtra("curBId", curBId);
                intent.putExtra("tabNo", tabNo);
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
