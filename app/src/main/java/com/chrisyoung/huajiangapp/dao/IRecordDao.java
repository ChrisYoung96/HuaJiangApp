package com.chrisyoung.huajiangapp.dao;

import com.chrisyoung.huajiangapp.domain.CRecord;

import java.util.ArrayList;
import java.util.Date;

public interface IRecordDao {
    boolean addOrUpdateRecord(CRecord newRecord);

    boolean deleteRecord(String bId);

    ArrayList<CRecord> showAllRecords(String bId);

    ArrayList<CRecord> showRecordsByMonth(String bId,Date monthStart,Date monthEnd);

    ArrayList<CRecord> showRecordsByDay(String bId,Date dayStart,Date dayEnd);

    ArrayList<CRecord> showRecordsByMonthAndType(String bId,Date monthStart,Date monthEnd,String type);

    ArrayList<CRecord> showRecordsByWayAndMonth(String bId,String way,Date monthStart,Date monthEnd,String type);


    Double sumAllMoneyInAMonth(String bId,Date monthStart,Date monthEnd,String type);

    Double sumAllMoneyOfWayInAMonth(String bId,Date monthStart,Date monthEnd,String type,String way);

    Double sumAllMoneyOfKindInAMonth(String bId,Date monthStart,Date monthEnd,String type,String kind);
}
