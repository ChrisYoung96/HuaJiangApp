package com.chrisyoung.huajiangapp.presenter;

import com.chrisyoung.huajiangapp.biz.IBillManageBiz;
import com.chrisyoung.huajiangapp.biz.IRecordManageBiz;
import com.chrisyoung.huajiangapp.biz.IStatisticsBiz;
import com.chrisyoung.huajiangapp.biz.impl.BillManageBiz;
import com.chrisyoung.huajiangapp.biz.impl.RecordManageBizImpl;
import com.chrisyoung.huajiangapp.biz.impl.StatisticsBiz;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.domain.RViewModel;
import com.chrisyoung.huajiangapp.uitils.DateFormatUtil;
import com.chrisyoung.huajiangapp.view.vinterface.IRecordsView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class RecordPresenter {
    private IRecordManageBiz recordManageBiz;
    private IRecordsView recordsView;
    private IStatisticsBiz statisticsBiz;
    private IBillManageBiz billManageBiz;

    public RecordPresenter(IRecordsView recordsView) {
        this.recordsView = recordsView;
        recordManageBiz=new RecordManageBizImpl();
        statisticsBiz=new StatisticsBiz();
        billManageBiz=new BillManageBiz();
    }

    public ArrayList<RViewModel> showRecords(String bId, long curMonth){
        BigDecimal cost=BigDecimal.valueOf(statisticsBiz.sumAllMoneyInAMonth(bId,DateFormatUtil.getStartOfMonth(curMonth),DateFormatUtil.getEndOfMonth(curMonth),"支出"));
        BigDecimal income=BigDecimal.valueOf(statisticsBiz.sumAllMoneyInAMonth(bId,DateFormatUtil.getStartOfMonth(curMonth),DateFormatUtil.getEndOfMonth(curMonth),"收入"));
        cost.setScale(2,BigDecimal.ROUND_UP);
        income.setScale(2,BigDecimal.ROUND_UP);
        recordsView.showTotalIncome(income);
        recordsView.showTotalCost(cost);
        return recordManageBiz.showRecordsInAMonth(bId,DateFormatUtil.getStartOfMonth(curMonth),DateFormatUtil.getEndOfMonth(curMonth));
    }

    public void  deleteRecord(String rId){
        if(recordManageBiz.deleteRecord(rId)){
            recordsView.showResult("成功");
        }else {
            recordsView.showResult("失败");
        }
    }

    public void modifyRecord(){

    }

    public void showBillList(String uId){
        ArrayList<CBill> bills=billManageBiz.showAllBills(uId);
        recordsView.showBillList(bills);
    }


}
