package com.chrisyoung.huajiangapp.presenter;

import com.chrisyoung.huajiangapp.biz.IRecordManageBiz;
import com.chrisyoung.huajiangapp.biz.impl.RecordManageBizImpl;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.domain.RViewModel;
import com.chrisyoung.huajiangapp.uitils.DateFormatUtil;
import com.chrisyoung.huajiangapp.view.vinterface.IRecordsView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.Date;

public class RecordPresenter {
    private IRecordManageBiz recordManageBiz;
    private IRecordsView recordsView;

    public RecordPresenter(IRecordsView recordsView) {
        this.recordsView = recordsView;
        recordManageBiz=new RecordManageBizImpl();
    }

    public ArrayList<RViewModel> showRecords(String bId, long curMonth){
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
}
