package com.chrisyoung.huajiangapp.view.vinterface;

import com.chrisyoung.huajiangapp.domain.CBill;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface IRecordsView extends BaseView,BaseInternetView {

    void showTotalIncome(BigDecimal num);

    void showTotalCost(BigDecimal num);

    void showBillList(ArrayList<CBill> bills);

    void loadData(long milsecond);


}
