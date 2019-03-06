package com.chrisyoung.huajiangapp.view.vinterface;

import com.chrisyoung.huajiangapp.domain.CBill;

import java.util.ArrayList;

public interface IChartView extends BaseView {
    void showBillList(ArrayList<CBill> bills);
}
