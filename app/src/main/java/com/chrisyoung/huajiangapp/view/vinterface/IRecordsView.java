package com.chrisyoung.huajiangapp.view.vinterface;

import com.chrisyoung.huajiangapp.domain.CBill;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface IRecordsView extends BaseView {

    void showTotalIncome(BigDecimal num);

    void showTotalCost(BigDecimal num);

    void showBillList(ArrayList<CBill> bills);
}
