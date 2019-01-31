package com.chrisyoung.huajiangapp.biz.impl;

import com.chrisyoung.huajiangapp.biz.IRecordManageBiz;
import com.chrisyoung.huajiangapp.dao.IRecordDao;
import com.chrisyoung.huajiangapp.dao.impl.RecordDaoImpl;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.domain.RViewModel;
import com.chrisyoung.huajiangapp.uitils.DateFormatUtil;

import java.util.ArrayList;
import java.util.Date;

public class RecordManageBizImpl implements IRecordManageBiz {
    private ArrayList<RViewModel> rViewModels;
    private IRecordDao recordDao;

    public RecordManageBizImpl() {
        recordDao = new RecordDaoImpl();
        rViewModels = new ArrayList<>();
    }

    @Override
    public ArrayList<RViewModel> showRecordsInAMonth(String bId, Date start, Date end) {
        ArrayList<CRecord> records = recordDao.showRecordsByMonth(bId,start,end);
        if (records != null) {
            Date temp = DateFormatUtil.stringToDate("1900-01-01");
            RViewModel rViewModel = new RViewModel();
            for (CRecord r : records
                    ) {
                Date rTime = DateFormatUtil.longDateToshortDate(r.getrTime());
                if (!temp.equals(rTime)) {
                    if (!rViewModel.getRecords().isEmpty()) {
                        rViewModel.setTotalCost(getTotalCost(bId, DateFormatUtil.getStartOfDay(temp), DateFormatUtil.getEndOfDay(temp)));
                        rViewModel.setTotalIncome(getTotalIncome(bId, DateFormatUtil.getStartOfDay(temp), DateFormatUtil.getEndOfDay(temp)));
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
            rViewModel.setTotalCost(getTotalCost(bId, DateFormatUtil.getStartOfDay(temp), DateFormatUtil.getEndOfDay(temp)));
            rViewModel.setTotalIncome(getTotalIncome(bId, DateFormatUtil.getStartOfDay(temp), DateFormatUtil.getEndOfDay(temp)));
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
    public CRecord showRecordInformation(String rId) {
        return recordDao.showARecord(rId);
    }

    @Override
    public boolean updateNewRecord(CRecord record) {
        return false;
    }

    @Override
    public boolean addRecord(String bId, CRecord newRecord) {
        return false;
    }


    private Double getTotalCost(String bId, Date start, Date end) {
        return recordDao.sumAllMoneyInAMonth(bId, start, end, "支出");
    }

    private Double getTotalIncome(String bId, Date start, Date end) {
        return recordDao.sumAllMoneyInAMonth(bId, start, end, "收入");
    }


}
