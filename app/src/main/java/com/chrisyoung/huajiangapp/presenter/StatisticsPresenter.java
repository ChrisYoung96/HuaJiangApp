package com.chrisyoung.huajiangapp.presenter;

import android.graphics.Color;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.biz.IBillManageBiz;
import com.chrisyoung.huajiangapp.biz.IRecordManageBiz;
import com.chrisyoung.huajiangapp.biz.IScynDataBiz;
import com.chrisyoung.huajiangapp.biz.impl.RecordManageBizImpl;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.domain.RViewModel;
import com.chrisyoung.huajiangapp.uitils.DateFormatUtil;
import com.chrisyoung.huajiangapp.view.vinterface.BaseView;
import com.chrisyoung.huajiangapp.view.vinterface.IStasisticsView;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.qmuiteam.qmui.util.QMUIColorHelper;

import java.security.CryptoPrimitive;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatisticsPresenter {
    private IBillManageBiz billManageBiz;
    private IRecordManageBiz recordManageBiz;
    private IStasisticsView view;


    public StatisticsPresenter(IStasisticsView view) {
        this.view = view;
        recordManageBiz=new RecordManageBizImpl();
    }


    public void init(String bId,long date,String type){
        initFirstLayer(bId, date, type);
        showLineChart(bId, date, type);
        showPieChart(bId, date, type);
        showBarChart(bId, date, type);
    }

    private void initFirstLayer(String bId, long date, String type){
        Date start=DateFormatUtil.getStartOfMonth(date);
        Date end=DateFormatUtil.getEndOfMonth(date);
        Double avrgMoney=recordManageBiz.avrgAllMoneyInAMonth(bId,start,end,type);
        Double sumMoney=recordManageBiz.sumAllMoneyInAMonth(bId,start,end,type);
        DecimalFormat df = new DecimalFormat("#.00");
        view.showFirstLayer(df.format(sumMoney),df.format(avrgMoney));

    }


    private void showLineChart(String bId, long date, String type){
        Date start=DateFormatUtil.getStartOfMonth(date);
        Date end=DateFormatUtil.getEndOfMonth(date);
        ArrayList<RViewModel> records=new ArrayList<>();
        ArrayList<Entry> datas=new ArrayList<>();
        ArrayList<String> xVals=new ArrayList<>();
        records=recordManageBiz.showRecordsInAMonth(bId,start,end,type);
        if(records!=null&&!records.isEmpty()){
            for (int index=0;index<records.size();index++) {
                RViewModel r=records.get(index);
                if(type.equals("支出")){
                    Entry entry=new Entry(index,r.getTotalCost().floatValue());
                    datas.add(entry);

                }else if(type.equals("收入")){
                    Entry entry=new Entry(index,r.getTotalIncome().floatValue());
                    datas.add(entry);
                }
                xVals.add(DateFormatUtil.getMothAndDay(r.getDay()));
            }
            LineDataSet dataSet=new LineDataSet(datas,"");
            dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            dataSet.setLineWidth(2f);  //设置折线宽度


            LineData lineData=new LineData(dataSet);
            view.showLineChart(lineData,xVals);
        }

    }


    private void showPieChart(String bId, long date, String type){
        Date start=DateFormatUtil.getStartOfMonth(date);
        Date end=DateFormatUtil.getEndOfMonth(date);
        ArrayList<PieEntry> pieEntries=new ArrayList<>();
        ArrayList<String> x=new ArrayList<>();
        ArrayList<CRecord> ways=recordManageBiz.getAllWay(bId,start,end,type);
        ArrayList<CRecord> totals=recordManageBiz.showAllMoneyOfAWay(bId,start,end,type);
        if(ways!=null&&!ways.isEmpty()){
            for(int i=0;i<ways.size();i++){
                float total=totals.get(i).getrMoney().floatValue();
                PieEntry entry=new PieEntry(total,ways.get(i).getrWay());
                x.add(ways.get(i).getrWay());
                pieEntries.add(entry);
            }

        }
        PieDataSet dataSet=new PieDataSet(pieEntries,"");
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.red(1111));
        dataSet.setColors(colors);

        PieData peiData=new PieData(dataSet);
        view.showPieChart(peiData,x);

    }


    private void showBarChart(String bId, long date, String type){
        Date start=DateFormatUtil.getStartOfMonth(date);
        Date end=DateFormatUtil.getEndOfMonth(date);
        ArrayList<BarEntry> barEntries=new ArrayList<>();
       ArrayList<String> x=new ArrayList<>();
        ArrayList<CRecord> kinds=recordManageBiz.getAllKind(bId,start,end,type);
        ArrayList<CRecord> totals=recordManageBiz.showAllMoneyOfAKind(bId,start,end,type);
        if(kinds!=null&&!kinds.isEmpty()){
            for(int i=0;i<kinds.size();i++){
                float total=totals.get(i).getrMoney().floatValue();
                BarEntry entry=new BarEntry(i,total);
                x.add(kinds.get(i).getrKind());
                barEntries.add(entry);
            }
        }
        BarDataSet barDataSet=new BarDataSet(barEntries,"");
        barDataSet.setValueTextSize(1f);
        BarData barData=new BarData(barDataSet);
        barData.setBarWidth(0.5f);
        barData.getGroupWidth(0.05f,0.05f);

        view.showBarChart(barData,x);
    }

}
