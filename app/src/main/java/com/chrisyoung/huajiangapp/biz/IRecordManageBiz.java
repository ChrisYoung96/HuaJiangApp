package com.chrisyoung.huajiangapp.biz;

import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.domain.RViewModel;

import java.util.ArrayList;
import java.util.Date;

public interface IRecordManageBiz {
    CRecord getRecord(String rId);

    ArrayList<RViewModel> showRecordsInAMonth(String bId, Date start, Date end,String type);

    Double getTotalCostInAMonth(String bId,Date monthStart,Date monthEnd);

    Double getTotalIncomeInAMonth(String bId,Date monthStart,Date monthEnd);

    boolean deleteRecord(String rId);

    boolean fakeDeleteRecord(CRecord record);

    CRecord showRecordInformation(String rId);

    boolean updateNewRecord(CRecord record);

    boolean addRecord(CBill bill, CRecord newRecord);

    Double sumAllMoneyInAMonthByWay(String bId, Date monthStart,Date monthEnd,String type,String way);

    Double sumAllMoneyInAMonthByKind(String bId,Date monthStart,Date monthEnd,String type,String kind);

    Double avrgAllMoneyInAMonth(String bId,Date monthStart,Date monthEnd,String type);

    Double sumAllMoneyInAMonth(String bId,Date monthStart,Date monthEnd,String type);

    ArrayList<CRecord> showAllRecordsInAMonthByWay(String bId,Date monthStart,Date monthEnd,String type,String way);

    ArrayList<CRecord> showAllRecordsInAMonthByKind(String bId,Date monthStart,Date monthEnd,String type,String kind);

    ArrayList<CRecord> showMaxRecordsInAMonth(String bId,Date monthStart,Date monthEnd,String type);

    ArrayList<CRecord> showMinRecordsInAMonth(String bId,Date monthStart,Date monthEnd,String type);

    ArrayList<CRecord> showAllMoneyOfAKind(String bId,Date monthStart,Date monthEnd,String type);

    ArrayList<CRecord> showAllMoneyOfAWay(String bId,Date monthStart,Date monthEnd,String type);

    public ArrayList<CRecord> getAllKind(String bId, Date monthStart, Date monthEnd, String type);

    public ArrayList<CRecord> getAllWay(String bId, Date monthStart, Date monthEnd, String type);

}
