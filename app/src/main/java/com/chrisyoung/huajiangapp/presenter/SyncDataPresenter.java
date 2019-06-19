package com.chrisyoung.huajiangapp.presenter;

import android.content.Context;
import android.util.Log;

import com.chrisyoung.huajiangapp.biz.ISyncDataBiz;
import com.chrisyoung.huajiangapp.biz.impl.SyncDataBiz;
import com.chrisyoung.huajiangapp.constant.ResultCode;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.domain.CUser;
import com.chrisyoung.huajiangapp.domain.CUserDiyKind;
import com.chrisyoung.huajiangapp.dto.AppUser;
import com.chrisyoung.huajiangapp.dto.Bill;
import com.chrisyoung.huajiangapp.dto.Record;
import com.chrisyoung.huajiangapp.dto.SychronizeDataItem;
import com.chrisyoung.huajiangapp.dto.UserDiy;
import com.chrisyoung.huajiangapp.network.DataManager;
import com.chrisyoung.huajiangapp.network.HttpResult;
import com.chrisyoung.huajiangapp.uitils.EntityConvertUtil;
import com.chrisyoung.huajiangapp.uitils.SharedPreferenceUtil;
import com.chrisyoung.huajiangapp.uitils.ToastUtil;
import com.chrisyoung.huajiangapp.view.vinterface.BaseInternetView;
import com.chrisyoung.huajiangapp.view.vinterface.IMainView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.LinkedList;
import java.util.Objects;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class SyncDataPresenter extends BasePresenter {
    private BaseInternetView mainView;
    private Context context;
    private ISyncDataBiz syncDataBiz;
    private DataManager dataManager;
    private LinkedList<SychronizeDataItem<CBill>> cbills;
    LinkedList<SychronizeDataItem<CRecord>> crecords;

    public SyncDataPresenter(LifecycleProvider<ActivityEvent> provider, Context context, BaseInternetView mainView) {
        super(provider);
        this.context = context;
        dataManager = DataManager.getInstance();
        this.mainView = mainView;
        cbills = new LinkedList<>();
        crecords = new LinkedList<>();
    }


    public void syncBillDataFromServer(String token, String uId) {
        syncDataBiz = new SyncDataBiz();

        dataManager.synchronizeBillsS2C(token, uId)
                .subscribeOn(Schedulers.io())
                .map(new Function<HttpResult<LinkedList<SychronizeDataItem<Bill>>>, LinkedList<SychronizeDataItem<CBill>>>() {
                    @Override
                    public LinkedList<SychronizeDataItem<CBill>> apply(HttpResult<LinkedList<SychronizeDataItem<Bill>>> linkedListHttpResult) throws Exception {
                        LinkedList<SychronizeDataItem<Bill>> linkedList = linkedListHttpResult.getData();
                        LinkedList<SychronizeDataItem<CBill>> ibills = new LinkedList<>();
                        if (linkedListHttpResult.getCode() == ResultCode.SUCCESS.code()) {
                            if (linkedList != null && !linkedList.isEmpty()) {
                                while (!linkedList.isEmpty()) {
                                    SychronizeDataItem<Bill> sb = linkedList.poll();
                                    assert sb != null;
                                    CBill cb = EntityConvertUtil.SBill2CBill(sb.getData());
                                    SychronizeDataItem<CBill> billSychronizeDataItem = new SychronizeDataItem<>();
                                    billSychronizeDataItem.setData(cb);
                                    billSychronizeDataItem.setOptCode(sb.getOptCode());
                                    ibills.add(billSychronizeDataItem);
                                }
                                return ibills;
                            }
                        }

                        return null;
                    }
                })
                .compose(mainView.bindLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LinkedList<SychronizeDataItem<CBill>>>() {
                    @Override
                    public void accept(LinkedList<SychronizeDataItem<CBill>> sychronizeDataItems) throws Exception {
                        syncDataBiz.synchronizeBillS2C(sychronizeDataItems);
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "BillError:", throwable);
                    }
                });


        dataManager.sycnchronizedKindsS2C(token, uId)
                .subscribeOn(Schedulers.io())
                .map(new Function<HttpResult<LinkedList<SychronizeDataItem<UserDiy>>>, LinkedList<SychronizeDataItem<CUserDiyKind>>>() {
                    @Override
                    public LinkedList<SychronizeDataItem<CUserDiyKind>> apply(HttpResult<LinkedList<SychronizeDataItem<UserDiy>>> linkedListHttpResult) throws Exception {
                        LinkedList<SychronizeDataItem<CUserDiyKind>> cUserDiyKinds = new LinkedList<>();
                        LinkedList<SychronizeDataItem<UserDiy>> userDiys = linkedListHttpResult.getData();
                        if (linkedListHttpResult.getCode().equals(ResultCode.SUCCESS.code())) {
                            if (userDiys != null && !userDiys.isEmpty()) {
                                while (!userDiys.isEmpty()) {
                                    SychronizeDataItem<UserDiy> userDiy = userDiys.poll();
                                    CUserDiyKind kind = EntityConvertUtil.SUserDiyKind2CUserDiyKind(Objects.requireNonNull(userDiy.getData()));
                                    SychronizeDataItem<CUserDiyKind> userDiyKind = new SychronizeDataItem<>();
                                    userDiyKind.setOptCode(userDiy.getOptCode());
                                    userDiyKind.setData(kind);
                                    cUserDiyKinds.add(userDiyKind);
                                }
                                return cUserDiyKinds;
                            }


                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mainView.bindLifecycle())
                .subscribe(new Consumer<LinkedList<SychronizeDataItem<CUserDiyKind>>>() {
                    @Override
                    public void accept(LinkedList<SychronizeDataItem<CUserDiyKind>> sychronizeDataItems) throws Exception {
                        if ( syncDataBiz.synchronizeKindS2C(sychronizeDataItems)) {
                            Log.i(TAG, "服务器类别同步成功");
                        } else {
                            Log.i(TAG, "服务器类别同步失败");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "KindError", throwable);
                    }
                });


    }


    public void syncDateToServer(String token, String uId) {

        syncDataBiz = new SyncDataBiz();

        LinkedList<SychronizeDataItem<CBill>> cbills = syncDataBiz.synchronizeBillC2S(uId);
        LinkedList<SychronizeDataItem<Bill>> bills = new LinkedList<>();
        LinkedList<SychronizeDataItem<CRecord>> crecords = new LinkedList<>();


        if (cbills != null && !cbills.isEmpty()) {
          for(int i=0;i<cbills.size();i++) {
                SychronizeDataItem<CBill> cbill = cbills.get(i);
                if (cbill != null && cbill.getData() != null) {
                    SychronizeDataItem<Bill> bill = new SychronizeDataItem<>();
                    bill.setData(EntityConvertUtil.CBill2SBill(cbill.getData()));
                    bill.setOptCode(cbill.getOptCode());
                    bills.add(bill);
                    crecords.addAll(syncDataBiz.synchronizeRecordC2S(cbill.getData().getbId()));
                }

            }
        }else{
            crecords.addAll(syncDataBiz.synchronizeRecordC2S());
        }


        LinkedList<SychronizeDataItem<Record>> srecords = new LinkedList<>();
        if (!crecords.isEmpty()) {
           for(int i=0;i<crecords.size();i++) {
                SychronizeDataItem<CRecord> crecord = crecords.get(i);
                SychronizeDataItem<Record> record = new SychronizeDataItem<>();
                record.setData(EntityConvertUtil.CRecord2SRecord(crecord.getData()));
                record.setOptCode(crecord.getOptCode());
                srecords.add(record);
            }

        }

        if (!bills.isEmpty()) {
            dataManager.synchronizeBillsC2S(token, bills)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .compose(mainView.bindLifecycle())
                    .doOnNext(new Consumer<HttpResult<String>>() {
                        @Override
                        public void accept(HttpResult<String> result) throws Exception {
                            if (result.getCode().equals(ResultCode.SUCCESS.code())) {
                                Log.i(TAG, "客户端账本同步成功");
                                SyncDataBiz biz=new SyncDataBiz();
                                biz.updateBillSattus(cbills);
                                biz.closeRealm();
                            }
                        }
                    })
                    .observeOn(Schedulers.io())
                    .flatMap(new Function<HttpResult<String>, ObservableSource<HttpResult<String>>>() {
                        @Override
                        public ObservableSource<HttpResult<String>> apply(HttpResult<String> result) throws Exception {
                            return dataManager.synchronizeRecordsC2S(token,srecords);
                        }
                    })
                    .observeOn(Schedulers.io())
                    .subscribe(new Consumer<HttpResult<String>>() {
                        @Override
                        public void accept(HttpResult<String> result) throws Exception {
                            Log.i(TAG, "客户端记录同步成功");
                            SyncDataBiz biz=new SyncDataBiz();
                            biz.updateRecordSattus(crecords);
                            biz.closeRealm();
                            syncDataBiz.closeRealm();

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.i(TAG, "客户端记录同步失败");


                        }
                    });
        }else {
            dataManager.synchronizeRecordsC2S(token,srecords)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .compose(mainView.bindLifecycle())
                    .subscribe(new Consumer<HttpResult<String>>() {
                        @Override
                        public void accept(HttpResult<String> result) throws Exception {
                            SyncDataBiz biz=new SyncDataBiz();
                            biz.updateRecordSattus(crecords);
                            biz.closeRealm();
                            Log.i(TAG, "客户端记录同步成功(無賬本)");

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                        }
                    });

        }


        LinkedList<SychronizeDataItem<CUserDiyKind>> ckinds = syncDataBiz.synchronizeKindC2S(uId);
        LinkedList<SychronizeDataItem<UserDiy>> skinds = new LinkedList<>();
        if (ckinds != null && !ckinds.isEmpty()) {
            for(int i=0;i<ckinds.size();i++) {
                SychronizeDataItem<CUserDiyKind> ckind = ckinds.get(i);
                if (ckind.getData() != null) {
                    SychronizeDataItem<UserDiy> kind = new SychronizeDataItem<>();
                    kind.setData(EntityConvertUtil.CUserDiyKind2SUserDiy(ckind.getData()));
                    kind.setOptCode(ckind.getOptCode());
                    skinds.add(kind);
                }
            }
        }

        if (!skinds.isEmpty()) {
            dataManager.synchronizeKindsC2S(token, skinds)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .compose(mainView.bindLifecycle())
                    .subscribe(new Consumer<HttpResult<String>>() {
                        @Override
                        public void accept(HttpResult<String> result) throws Exception {
                            if (result.getCode().equals(ResultCode.SUCCESS.code())) {
                                SyncDataBiz biz=new SyncDataBiz();
                                biz.updateKindSattus(ckinds);
                                biz.closeRealm();
                                Log.i(TAG, "客户端类别同步成功");

                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.i(TAG, throwable.getMessage());

                    }
                    });
        }


        CUser cUser=syncDataBiz.synchronizeUserInfo(uId);
        AppUser appUser=new AppUser();
        appUser=EntityConvertUtil.CUser2SUser(cUser);
        dataManager.modifyInfo(token,appUser)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .compose(mainView.bindLifecycle())
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.i(TAG, "用户信息同步成功");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, throwable.getMessage());
                    }
                });

        mainView.showResult("同步成功");

    }





}



