package com.chrisyoung.huajiangapp.presenter;

import com.chrisyoung.huajiangapp.biz.IBillManageBiz;
import com.chrisyoung.huajiangapp.biz.IRecordManageBiz;
import com.chrisyoung.huajiangapp.biz.impl.RecordManageBizImpl;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.uitils.DateFormatUtil;
import com.chrisyoung.huajiangapp.view.vinterface.IAddOrModifyRecordView;

public class ModifyRecordPresenter {
    private IAddOrModifyRecordView modifyRecordView;
    private IRecordManageBiz recordManageBiz;
    private CRecord record;


    public ModifyRecordPresenter(IAddOrModifyRecordView modifyRecordView) {
        this.modifyRecordView = modifyRecordView;
        recordManageBiz=new RecordManageBizImpl();
    }

    public void getRecord(String rId){
        modifyRecordView.showRecord(recordManageBiz.getRecord(rId));
    }

    public void updateRecord(String rId,String bId,String rMoney,String rType,String rkind,String rDate,String rWay,String rDesc){
        record=new CRecord();
        record.setrId(rId);
        record.setbId(bId);
        record.setrMoney(Double.valueOf(rMoney));
        record.setrKind(rkind);
        record.setrTime(DateFormatUtil.stringtoDateAndTime(rDate));
        record.setrWay(rWay);
        record.setrType(rType);
        record.setrDesc(rDesc);
        if(recordManageBiz.updateNewRecord(record)){
            modifyRecordView.showResult("成功");
            modifyRecordView.jump2MainActivity();
        }else{
            modifyRecordView.showResult("修改失败，数据错误");
        }
    }

}
