package com.chrisyoung.huajiangapp.biz;

import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.domain.RViewModel;

import java.util.ArrayList;
import java.util.Date;

public interface IRecordManageBiz {
    ArrayList<RViewModel> showRecordsInAMonth(String bId, Date start, Date end);

    Double getTotalCostInAMonth(String bId,Date monthStart,Date monthEnd);

    Double getTotalIncomeInAMonth(String bId,Date monthStart,Date monthEnd);

    boolean deleteRecord(String rId);

    CRecord showRecordInformation(String rId);

    boolean updateNewRecord(CRecord record);

    boolean addRecord(String bId,CRecord newRecord);

}
