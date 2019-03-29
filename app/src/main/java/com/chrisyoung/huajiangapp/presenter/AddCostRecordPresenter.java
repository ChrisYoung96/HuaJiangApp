package com.chrisyoung.huajiangapp.presenter;

import com.chrisyoung.huajiangapp.biz.IBillManageBiz;
import com.chrisyoung.huajiangapp.biz.IDiyKindManageBiz;
import com.chrisyoung.huajiangapp.biz.IRecordManageBiz;
import com.chrisyoung.huajiangapp.biz.impl.BillManageBiz;
import com.chrisyoung.huajiangapp.biz.impl.DiyKindManageBiz;
import com.chrisyoung.huajiangapp.biz.impl.RecordManageBizImpl;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.domain.CUserDiyKind;
import com.chrisyoung.huajiangapp.uitils.DateFormatUtil;
import com.chrisyoung.huajiangapp.view.vinterface.IAddOrModifyRecordView;

import java.util.ArrayList;

public class AddCostRecordPresenter {
    private IAddOrModifyRecordView addCostRecordView;
    private IRecordManageBiz recordManageBiz;
    private IBillManageBiz billManageBiz;
    private IDiyKindManageBiz diyKindManageBiz;
    private CRecord record;

    public AddCostRecordPresenter(IAddOrModifyRecordView view){
        this.addCostRecordView=view;
        recordManageBiz=new RecordManageBizImpl();
        billManageBiz=new BillManageBiz();
        diyKindManageBiz=new DiyKindManageBiz();

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
                addCostRecordView.showResult("成功");
                addCostRecordView.cleareText();
            }else{
                addCostRecordView.showResult("添加失败");
            }
        }else{
            addCostRecordView.showResult("账本不存在,请添加");
        }

    }

    public ArrayList<CUserDiyKind> loadDiyKind(String uId){
        return diyKindManageBiz.showKind(uId,"支出");

    }
}
