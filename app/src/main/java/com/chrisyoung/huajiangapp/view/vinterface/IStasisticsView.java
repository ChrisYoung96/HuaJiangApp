package com.chrisyoung.huajiangapp.view.vinterface;

import com.chrisyoung.huajiangapp.domain.CRecord;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.PieData;

import java.util.ArrayList;

public interface IStasisticsView extends BaseView {
    void showFirstLayer(String sum,String avrg);

    void showPieChart(PieData data, ArrayList<String> xvals);

    void showLineChart(LineData data,ArrayList<String> xvals);

    void showBarChart(BarData data,ArrayList<String> xvals);

    void refreshPieChart(PieData data, ArrayList<String> xvals);

    void refreshLineChart();
}
