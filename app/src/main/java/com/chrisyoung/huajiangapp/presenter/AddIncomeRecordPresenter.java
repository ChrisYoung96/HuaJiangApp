package com.chrisyoung.huajiangapp.presenter;

import android.content.Context;

import com.chrisyoung.huajiangapp.biz.IBillManageBiz;
import com.chrisyoung.huajiangapp.biz.IRecordManageBiz;
import com.chrisyoung.huajiangapp.biz.impl.BillManageBiz;
import com.chrisyoung.huajiangapp.biz.impl.RecordManageBizImpl;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.uitils.DateFormatUtil;
import com.chrisyoung.huajiangapp.view.vinterface.IAddCostRecordView;
import com.chrisyoung.huajiangapp.view.vinterface.IAddIncomeRecordView;

public class AddIncomeRecordPresenter {
    private IAddIncomeRecordView addIncomeRecordView;
    private IRecordManageBiz recordManageBiz;
    private IBillManageBiz billManageBiz;
    private Context context;
    private CRecord record;

    public AddIncomeRecordPresenter(IAddIncomeRecordView view, Context context) {
        this.addIncomeRecordView = view;
        this.context = context;
        recordManageBiz = new RecordManageBizImpl();
        billManageBiz = new BillManageBiz();
    }
    public void AddRecord(String bId,String rMoney,String rType,String rkind,String rDate,String rWay,String rDesc){
        record=new CRecord();
        record.setbId(bId);
        record.setrMoney(Double.valueOf(rMoney));
        record.setrKind(rkind);
        record.setrTime(DateFormatUtil.stringtoDateAndTime(rDate));
        record.setrWay(rWay);
        record.setrType(rType);
        record.setrDesc(rDesc);
        CBill b=billManageBiz.queryBill(bId);
        if(b!=null){
            if(recordManageBiz.addRecord(b,record)){
                addIncomeRecordView.showAddResult("成功");
               addIncomeRecordView.cleareText();
            }else{
                addIncomeRecordView.showAddResult("添加失败");
            }
        }else{
            addIncomeRecordView.showAddResult("账本不存在,请添加");
        }

    }
}
