package com.chrisyoung.huajiangapp.biz.impl;

import com.chrisyoung.huajiangapp.biz.IStatisticsBiz;
import com.chrisyoung.huajiangapp.dao.IRecordDao;
import com.chrisyoung.huajiangapp.dao.impl.RecordDaoImpl;
import com.chrisyoung.huajiangapp.domain.CRecord;

import java.util.ArrayList;
import java.util.Date;

public class StatisticsBiz implements IStatisticsBiz {
    private IRecordDao recordDao;

    public StatisticsBiz(){
        recordDao=new RecordDaoImpl();
    }
    @Override
    public Double sumAllMoneyInAMonthByWay(String bId, Date monthStart, Date monthEnd, String type,String way) {
        return recordDao.sumAllMoneyOfWayInAMonth(bId, monthStart, monthEnd, type, way);
    }

    @Override
    public Double sumAllMoneyInAMonthByKind(String bId, Date monthStart, Date monthEnd, String type,String kind) {
        return recordDao.sumAllMoneyOfKindInAMonth(bId, monthStart, monthEnd, type, kind);
    }

    @Override
    public Double avrgAllMoneyInAMonth(String bId, Date monthStart, Date monthEnd, String type) {
        return recordDao.avrgAllMoneyInAMonth(bId, monthStart, monthEnd, type);
    }

    @Override
    public Double sumAllMoneyInAMonth(String bId, Date monthStart, Date monthEnd, String type) {
        return recordDao.sumAllMoneyInAMonth(bId, monthStart, monthEnd, type);
    }

    @Override
    public ArrayList<CRecord> showAllRecordsInAMonthByWay(String bId, Date monthStart, Date monthEnd, String type, String way) {
        return recordDao.showRecordsByWayAndMonth(bId, way, monthStart, monthEnd, type);
    }

    @Override
    public ArrayList<CRecord> showAllRecordsInAMonthByKind(String bId, Date monthStart, Date monthEnd, String type, String kind) {
        return recordDao.showRecordsByKindAndMonth(bId, monthStart, monthEnd, type, kind);
    }

    @Override
    public ArrayList<CRecord> showMaxRecordsInAMonth(String bId, Date monthStart, Date monthEnd, String type) {
        return recordDao.findMaxRecords(bId, monthStart, monthEnd, type);
    }

    @Override
    public ArrayList<CRecord> showMinRecordsInAMonth(String bId, Date monthStart, Date monthEnd, String type) {
        return recordDao.findMinRecords(bId, monthStart, monthEnd, type);
    }


}
