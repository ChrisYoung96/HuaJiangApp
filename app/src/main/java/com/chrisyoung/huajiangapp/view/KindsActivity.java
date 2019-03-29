package com.chrisyoung.huajiangapp.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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

public class KindsActivity extends AppCompatActivity implements ShowCostKindFragment.OnFragmentInteractionListener,ShowIncomeKindFragment.OnFragmentInteractionListener {

    @BindView(R.id.btnSKBack)
    ImageButton btnSKBack;
    @BindView(R.id.tabSKSegment)
    QMUITabSegment tabSKSegment;
    @BindView(R.id.contentSKViewPager)
    ViewPager contentSKViewPager;
    List<Fragment> fragments = new ArrayList<>();
    String uId;
    BaseFragmentPagerAdapter baseFragmentPagerAdapter;
    ShowCostKindFragment showCostKindFragment;
    ShowIncomeKindFragment showIncomeKindFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kinds);
        ButterKnife.bind(this);
        init();
    }

    @OnClick(R.id.btnSKBack)
    public void onViewClicked() {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void init() {
        uId = getIntent().getStringExtra("uId");
        showCostKindFragment=ShowCostKindFragment.newInstance("支出",uId);
        showIncomeKindFragment=ShowIncomeKindFragment.newInstance("收入",uId);
        fragments.add(showCostKindFragment);
        fragments.add(showIncomeKindFragment);
        baseFragmentPagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        contentSKViewPager.setAdapter(baseFragmentPagerAdapter);
        tabSKSegment.addTab(new QMUITabSegment.Tab("支出"));
        tabSKSegment.addTab(new QMUITabSegment.Tab("收入"));
        int space = QMUIDisplayHelper.dp2px(this, 16);
        tabSKSegment.setHasIndicator(true);
        tabSKSegment.setIndicatorWidthAdjustContent(true);
        tabSKSegment.setMode(QMUITabSegment.MODE_FIXED);
        tabSKSegment.setItemSpaceInScrollMode(space);
        tabSKSegment.setupWithViewPager(contentSKViewPager, false);
        tabSKSegment.setPadding(space, 0, space, 0);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
