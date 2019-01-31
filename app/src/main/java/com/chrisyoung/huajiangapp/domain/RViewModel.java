package com.chrisyoung.huajiangapp.domain;

import java.util.ArrayList;
import java.util.Date;

public class RViewModel {
    private Date day;
    private Double totalCost;
    private Double totalIncome;
    private ArrayList<CRecord> records;

    public RViewModel() {
        records=new ArrayList<>();
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public ArrayList<CRecord> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<CRecord> records) {
        this.records = records;
    }
}
