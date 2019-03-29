package com.chrisyoung.huajiangapp.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.ImageButton;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.view.adapter.BaseFragmentPagerAdapter;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddRecordActivity extends FragmentActivity implements AddCostFragment.OnFragmentInteractionListener, AddIncomeFragment.OnFragmentInteractionListener {
    @BindView(R.id.tabSegment)
    QMUITabSegment tabSegment;
    @BindView(R.id.contentViewPager)
    ViewPager contentViewPager;
    List<Fragment> fragments = new ArrayList<>();
    String uId;
    String bId;
    BaseFragmentPagerAdapter baseFragmentPagerAdapter;
    AddIncomeFragment addIncomeFragment;
    AddCostFragment addCostFragment;
    @BindView(R.id.btnAdBack)
    ImageButton btnAdBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        uId = getIntent().getStringExtra("uId");
        bId = getIntent().getStringExtra("curBId");
        addIncomeFragment = AddIncomeFragment.newInstance("收入", bId);
        addCostFragment = AddCostFragment.newInstance("支出", bId,uId);
        fragments.add(addCostFragment);
        fragments.add(addIncomeFragment);
        baseFragmentPagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        contentViewPager.setAdapter(baseFragmentPagerAdapter);
        tabSegment.addTab(new QMUITabSegment.Tab("支出"));
        tabSegment.addTab(new QMUITabSegment.Tab("收入"));
        int space = QMUIDisplayHelper.dp2px(this, 16);
        tabSegment.setHasIndicator(true);
        tabSegment.setIndicatorWidthAdjustContent(true);
        tabSegment.setMode(QMUITabSegment.MODE_FIXED);
        tabSegment.setItemSpaceInScrollMode(space);
        tabSegment.setupWithViewPager(contentViewPager, false);
        tabSegment.setPadding(space, 0, space, 0);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @OnClick(R.id.btnAdBack)
    public void onViewClicked() {
        startActivity(new Intent(this,MainActivity.class));
        this.finish();
    }
}


