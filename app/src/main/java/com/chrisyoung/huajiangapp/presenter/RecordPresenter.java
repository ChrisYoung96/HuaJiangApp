package com.chrisyoung.huajiangapp.presenter;

import android.provider.ContactsContract;
import android.util.Log;

import com.chrisyoung.huajiangapp.biz.IBillManageBiz;
import com.chrisyoung.huajiangapp.biz.IRecordManageBiz;
import com.chrisyoung.huajiangapp.biz.ISyncDataBiz;
import com.chrisyoung.huajiangapp.biz.impl.BillManageBiz;
import com.chrisyoung.huajiangapp.biz.impl.RecordManageBizImpl;
import com.chrisyoung.huajiangapp.biz.impl.SyncDataBiz;
import com.chrisyoung.huajiangapp.constant.ResultCode;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.domain.RViewModel;
import com.chrisyoung.huajiangapp.dto.Record;
import com.chrisyoung.huajiangapp.dto.SychronizeDataItem;
import com.chrisyoung.huajiangapp.network.DataManager;
import com.chrisyoung.huajiangapp.network.HttpResult;
import com.chrisyoung.huajiangapp.uitils.DateFormatUtil;
import com.chrisyoung.huajiangapp.uitils.EntityConvertUtil;
import com.chrisyoung.huajiangapp.view.vinterface.IRecordsView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Sort;

import static android.content.ContentValues.TAG;

public class RecordPresenter extends BasePresenter {
    private IRecordManageBiz recordManageBiz;
    private IRecordsView recordsView;
    private IBillManageBiz billManageBiz;
    private ISyncDataBiz syncDataBiz;
    private DataManager dataManager;



    public RecordPresenter(LifecycleProvider<ActivityEvent> provider,IRecordsView recordsView) {
        super(provider);
        this.recordsView = recordsView;
        recordManageBiz=new RecordManageBizImpl();
        billManageBiz=new BillManageBiz();
        dataManager=DataManager.getInstance();
    }

    public ArrayList<RViewModel> showRecords(String bId, long curMonth){
        Date start=DateFormatUtil.getStartOfMonth(curMonth);
        Date end=DateFormatUtil.getEndOfMonth(curMonth);
        BigDecimal cost=BigDecimal.valueOf(recordManageBiz.sumAllMoneyInAMonth(bId,start,end,"支出"));
        BigDecimal income=BigDecimal.valueOf(recordManageBiz.sumAllMoneyInAMonth(bId,start,end,"收入"));
        cost.setScale(2,BigDecimal.ROUND_UP);
        income.setScale(2,BigDecimal.ROUND_UP);
        recordsView.showTotalIncome(income);
        recordsView.showTotalCost(cost);
        return recordManageBiz.showRecordsInAMonth(bId,start,end,"",Sort.DESCENDING);
    }

    public void  deleteRecord(String rId){
        if(recordManageBiz.fakeDeleteRecord(rId)){
            recordsView.showResult("成功");
        }else {
            recordsView.showResult("失败");
        }
    }


    public void showBillList(String uId){
        ArrayList<CBill> bills=billManageBiz.showAllBills(uId);
        recordsView.showBillList(bills);
    }



    public void syncRecordDataFromServer(String token, String bId) {
        syncDataBiz=new SyncDataBiz();
        dataManager.synchronizedRecordsS2C(token, bId)
                .subscribeOn(Schedulers.io())
                .map(new Function<HttpResult<LinkedList<SychronizeDataItem<Record>>>, LinkedList<SychronizeDataItem<CRecord>>>() {
                    @Override
                    public LinkedList<SychronizeDataItem<CRecord>> apply(HttpResult<LinkedList<SychronizeDataItem<Record>>> linkedListHttpResult) throws Exception {
                        LinkedList<SychronizeDataItem<Record>> srecords = linkedListHttpResult.getData();
                        LinkedList<SychronizeDataItem<CRecord>> crecords1 = new LinkedList<>();
                        if (linkedListHttpResult.getCode().equals(ResultCode.SUCCESS.code())) {
                            if (srecords != null && !srecords.isEmpty()) {
                                while (!srecords.isEmpty()) {
                                    SychronizeDataItem<Record> record = srecords.poll();
                                    if (record.getData() != null) {
                                        CRecord cRecord = EntityConvertUtil.SRecord2CRecord(record.getData());
                                        if (cRecord != null) {
                                            SychronizeDataItem<CRecord> sychronizeDataItem = new SychronizeDataItem<>();
                                            sychronizeDataItem.setData(cRecord);
                                            sychronizeDataItem.setOptCode(record.getOptCode());
                                            crecords1.add(sychronizeDataItem);
                                        }
                                    }
                                }
                                return crecords1;
                            }

                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(recordsView.bindLifecycle())
                .subscribe(new Consumer<LinkedList<SychronizeDataItem<CRecord>>>() {
                    @Override
                    public void accept(LinkedList<SychronizeDataItem<CRecord>> sychronizeDataItems) throws Exception {
                        if(syncDataBiz.synchronizeRecordS2C(sychronizeDataItems)){
                            recordsView.loadData(System.currentTimeMillis());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "RecordError: ", throwable);
                    }
                });

    }

    public void closeRealm(){
        recordManageBiz.closeRealm();
        billManageBiz.closeRealm();
    }

}
