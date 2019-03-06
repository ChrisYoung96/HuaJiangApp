package com.chrisyoung.huajiangapp.presenter;

import com.chrisyoung.huajiangapp.biz.IBillManageBiz;
import com.chrisyoung.huajiangapp.biz.IRecordManageBiz;
import com.chrisyoung.huajiangapp.biz.impl.BillManageBiz;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.view.vinterface.IChartView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;

public class ChartPresenter {
    private IBillManageBiz billManageBiz;
    private IChartView chartView;

    public ChartPresenter(IChartView chartView){
        this.chartView=chartView;
        billManageBiz=new BillManageBiz();
    }

    public void showBillList(String uId){
        ArrayList<CBill> bills=billManageBiz.showAllBills(uId);
        if(bills!=null && !bills.isEmpty()){
            chartView.showBillList(bills);
        }else{
            chartView.showResult("还没有选择账本或没有账本哟");
        }

    }

}
