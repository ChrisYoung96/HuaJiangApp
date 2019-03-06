package com.chrisyoung.huajiangapp.presenter;

import com.chrisyoung.huajiangapp.biz.IBillManageBiz;
import com.chrisyoung.huajiangapp.biz.IRecordManageBiz;
import com.chrisyoung.huajiangapp.biz.impl.BillManageBiz;
import com.chrisyoung.huajiangapp.biz.impl.RecordManageBizImpl;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.RViewModel;
import com.chrisyoung.huajiangapp.uitils.DateFormatUtil;
import com.chrisyoung.huajiangapp.view.vinterface.IRecordsView;

import java.math.BigDecimal;
import java.util.ArrayList;

public class RecordPresenter {
    private IRecordManageBiz recordManageBiz;
    private IRecordsView recordsView;
    private IBillManageBiz billManageBiz;

    public RecordPresenter(IRecordsView recordsView) {
        this.recordsView = recordsView;
        recordManageBiz=new RecordManageBizImpl();
        billManageBiz=new BillManageBiz();
    }

    public ArrayList<RViewModel> showRecords(String bId, long curMonth){
        BigDecimal cost=BigDecimal.valueOf(recordManageBiz.sumAllMoneyInAMonth(bId,DateFormatUtil.getStartOfMonth(curMonth),DateFormatUtil.getEndOfMonth(curMonth),"支出"));
        BigDecimal income=BigDecimal.valueOf(recordManageBiz.sumAllMoneyInAMonth(bId,DateFormatUtil.getStartOfMonth(curMonth),DateFormatUtil.getEndOfMonth(curMonth),"收入"));
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
