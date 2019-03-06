package com.chrisyoung.huajiangapp.presenter;

import com.chrisyoung.huajiangapp.biz.IBillManageBiz;
import com.chrisyoung.huajiangapp.biz.IRecordManageBiz;
import com.chrisyoung.huajiangapp.biz.IScynDataBiz;
import com.chrisyoung.huajiangapp.biz.impl.RecordManageBizImpl;
import com.chrisyoung.huajiangapp.view.vinterface.BaseView;
import com.chrisyoung.huajiangapp.view.vinterface.IStasisticsView;

public class StatisticsPresenter {
    private IBillManageBiz billManageBiz;
    private IRecordManageBiz recordManageBiz;
    private IStasisticsView view;

    public StatisticsPresenter(IStasisticsView view) {
        this.view = view;
        recordManageBiz=new RecordManageBizImpl();
    }




}
