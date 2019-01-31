package com.chrisyoung.huajiangapp.biz;

import com.chrisyoung.huajiangapp.domain.CRecord;

import java.util.ArrayList;
import java.util.Date;

public interface IStatisticsBiz {
    Double sumAllMoneyInAMonthByWay(String bId, Date monthStart,Date monthEnd,String type);

    Double sumAllMoneyInAMonthByKind(String bId,Date monthStart,Date monthEnd,String type);

    ArrayList<CRecord> showAllRecordsInAMonthByWay(String bId,Date monthStart,Date monthEnd,String type,String way);

    ArrayList<CRecord> showAllRecordsInAMonthByKind(String bId,Date monthStart,Date monthEnd,String type,String kind);

    ArrayList<CRecord> showAllRecordInAMonth(String bId,Date monthStart,Date monthEnd,String type);
}
