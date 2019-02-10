package com.chrisyoung.huajiangapp.dao;

import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CRecord;

import java.util.ArrayList;
import java.util.Date;

public interface IRecordDao {
    boolean addOrUpdateRecord(CRecord newRecord);

    boolean addRecordForBill(CBill bill, CRecord newRecord);

    boolean deleteRecord(String rId);

    CRecord showARecord(String rId);

    ArrayList<CRecord> showAllRecords(String bId);

    ArrayList<CRecord> showRecordsNeedSynchronize(String bId);

    ArrayList<CRecord> showRecordsByMonth(String bId,Date monthStart,Date monthEnd);

    ArrayList<CRecord> showRecordsByDay(String bId,Date dayStart,Date dayEnd);

    ArrayList<CRecord> showRecordsByMonthAndType(String bId,Date monthStart,Date monthEnd,String type);

    ArrayList<CRecord> showRecordsByWayAndMonth(String bId,String way,Date monthStart,Date monthEnd,String type);

    ArrayList<CRecord> showRecordsByKindAndMonth(String bId,Date monthStart,Date monthEnd,String type,String kind);

    ArrayList<CRecord> findMaxRecords(String bId,Date monthStart,Date monthEnd,String type);

    ArrayList<CRecord> findMinRecords(String bId,Date monthStart,Date monthEnd,String type);

    Double sumAllMoneyInAMonth(String bId,Date monthStart,Date monthEnd,String type);

    Double sumAllMoneyOfWayInAMonth(String bId,Date monthStart,Date monthEnd,String type,String way);

    Double sumAllMoneyOfKindInAMonth(String bId,Date monthStart,Date monthEnd,String type,String kind);

    Double avrgAllMoneyInAMonth(String bId,Date monthStart,Date monthEnd,String type);

}
