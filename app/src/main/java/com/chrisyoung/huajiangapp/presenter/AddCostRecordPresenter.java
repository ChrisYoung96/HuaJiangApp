package com.chrisyoung.huajiangapp.presenter;

import android.content.Context;

import com.chrisyoung.huajiangapp.biz.IBillManageBiz;
import com.chrisyoung.huajiangapp.biz.IRecordManageBiz;
import com.chrisyoung.huajiangapp.biz.impl.BillManageBiz;
import com.chrisyoung.huajiangapp.biz.impl.RecordManageBizImpl;
import com.chrisyoung.huajiangapp.dao.IRecordDao;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.uitils.DateFormatUtil;
import com.chrisyoung.huajiangapp.view.vinterface.IAddCostRecordView;

public class AddCostRecordPresenter {
    private IAddCostRecordView addCostRecordView;
    private IRecordManageBiz recordManageBiz;
    private IBillManageBiz billManageBiz;
    private Context context;
    private CRecord record;

    public AddCostRecordPresenter(IAddCostRecordView view,Context context){
        this.addCostRecordView=view;
        this.context=context;
        recordManageBiz=new RecordManageBizImpl();
        billManageBiz=new BillManageBiz();

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
                addCostRecordView.showAddResult("成功");
                addCostRecordView.cleareText();
            }else{
                addCostRecordView.showAddResult("添加失败");
            }
        }else{
            addCostRecordView.showAddResult("账本不存在,请添加");
        }

    }
}
