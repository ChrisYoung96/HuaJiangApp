package com.chrisyoung.huajiangapp.biz.impl;

import com.chrisyoung.huajiangapp.biz.IRecordManageBiz;
import com.chrisyoung.huajiangapp.constant.StatusCode;
import com.chrisyoung.huajiangapp.dao.IRecordDao;
import com.chrisyoung.huajiangapp.dao.impl.RecordDaoImpl;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.domain.RViewModel;
import com.chrisyoung.huajiangapp.uitils.DateFormatUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class RecordManageBizImpl implements IRecordManageBiz {
    private ArrayList<RViewModel> rViewModels;
    private IRecordDao recordDao;

    public RecordManageBizImpl() {
        recordDao = new RecordDaoImpl();
        rViewModels = new ArrayList<>();
    }

    @Override
    public CRecord getRecord(String rId) {
        return recordDao.showARecord(rId);
    }

    @Override
    public ArrayList<RViewModel> showRecordsInAMonth(String bId, Date start, Date end,String type) {
        rViewModels.clear();
        ArrayList<CRecord> records = recordDao.showRecordsByMonth(bId,start,end);
        if (records != null && !records.isEmpty()) {
            Date temp = DateFormatUtil.stringToDate("1900-01-01");
            Date startOfDay = DateFormatUtil.getStartOfDay(temp);
            Date endOfDay =DateFormatUtil.getEndOfDay(temp);
            RViewModel rViewModel = new RViewModel();
            for (CRecord r : records
                    ) {
                Date rTime = DateFormatUtil.longDateToshortDate(r.getrTime());
                if (!temp.equals(rTime)) {
                    if (!rViewModel.getRecords().isEmpty()) {
                        if(type.equals("")){
                            rViewModel.setTotalCost(getTotalCost(bId, startOfDay, endOfDay));
                            rViewModel.setTotalIncome(getTotalIncome(bId, startOfDay, endOfDay));
                        }else if(type.equals("收入")) {
                            rViewModel.setTotalIncome(getTotalIncome(bId, startOfDay, endOfDay));
                        }else if(type.equals("支出")){
                            rViewModel.setTotalCost(getTotalCost(bId, startOfDay, endOfDay));
                        }
                        rViewModels.add(rViewModel);
                    }
                    temp=rTime;
                    rViewModel = new RViewModel();
                    rViewModel.setDay(rTime);
                    rViewModel.getRecords().add(r);

                } else {
                    rViewModel.getRecords().add(r);
                }

            }
            rViewModel.setTotalCost(getTotalCost(bId, startOfDay, endOfDay));
            rViewModel.setTotalIncome(getTotalIncome(bId, startOfDay, endOfDay));
            rViewModels.add(rViewModel);
        }
        return rViewModels;

    }


    @Override
    public Double getTotalCostInAMonth(String bId, Date monthStart, Date monthEnd) {
        return recordDao.sumAllMoneyInAMonth(bId,monthStart,monthEnd,"支出");
    }

    @Override
    public Double getTotalIncomeInAMonth(String bId, Date monthStart, Date monthEnd) {
        return recordDao.sumAllMoneyInAMonth(bId,monthStart,monthEnd,"收入");
    }

    @Override
    public boolean deleteRecord(String rId) {
        return recordDao.deleteRecord(rId);
    }

    @Override
    public boolean fakeDeleteRecord(CRecord record) {
        record.setrStatus(StatusCode.DELETE);
        return recordDao.addOrUpdateRecord(record);
    }

    @Override
    public CRecord showRecordInformation(String rId) {
        return recordDao.showARecord(rId);
    }

    @Override
    public boolean updateNewRecord(CRecord record) {
        record.setrStatus(StatusCode.UPDATE);
        int version=record.getrVersion();
        version++;
        record.setrVersion(version);
        return recordDao.addOrUpdateRecord(record);
    }

    @Override
    public boolean addRecord(CBill bill, CRecord newRecord) {
        newRecord.setrVersion(1);
        newRecord.setrStatus(StatusCode.INSERT);
        return recordDao.addRecordForBill(bill,newRecord);
    }


    private Double getTotalCost(String bId, Date start, Date end) {
        return recordDao.sumAllMoneyInAMonth(bId, start, end, "支出");
    }

    private Double getTotalIncome(String bId, Date start, Date end) {
        return recordDao.sumAllMoneyInAMonth(bId, start, end, "收入");
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

    @Override
    public ArrayList<CRecord> showAllMoneyOfAKind(String bId, Date monthStart, Date monthEnd, String type) {
        ArrayList<CRecord> records=recordDao.findAllKind(bId, monthStart, monthEnd, type);
        for (CRecord r:records
             ) {
            r.setrMoney(sumAllMoneyInAMonthByKind(bId,monthStart,monthEnd,type,r.getrKind()));
        }
        records.sort(new Comparator<CRecord>() {
            @Override
            public int compare(CRecord o1, CRecord o2) {
                if(o1.getrMoney()>o2.getrMoney()){
                    return 1;
                }else{
                    return -1;
                }
            }
        });
        return records;
    }

    @Override
    public ArrayList<CRecord> showAllMoneyOfAWay(String bId, Date monthStart, Date monthEnd, String type) {
        ArrayList<CRecord> records=recordDao.findAllWay(bId, monthStart, monthEnd, type);
        for (CRecord r:records
                ) {
            r.setrMoney(sumAllMoneyInAMonthByWay(bId,monthStart,monthEnd,type,r.getrWay()));
        }
        return records;
    }

    @Override
    public ArrayList<CRecord> getAllKind(String bId, Date monthStart, Date monthEnd, String type){
        return recordDao.findAllKind(bId, monthStart, monthEnd, type);
    }

    @Override
    public ArrayList<CRecord> getAllWay(String bId, Date monthStart, Date monthEnd, String type){
        return recordDao.findAllWay(bId, monthStart, monthEnd, type);
    }


}
