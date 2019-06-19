package com.chrisyoung.huajiangapp.presenter;

import android.content.Context;
import android.util.Log;

import com.chrisyoung.huajiangapp.biz.IBillManageBiz;
import com.chrisyoung.huajiangapp.biz.ISyncDataBiz;
import com.chrisyoung.huajiangapp.biz.impl.BillManageBiz;
import com.chrisyoung.huajiangapp.biz.impl.SyncDataBiz;
import com.chrisyoung.huajiangapp.constant.ResultCode;
import com.chrisyoung.huajiangapp.constant.StatusCode;
import com.chrisyoung.huajiangapp.dao.IUserDao;
import com.chrisyoung.huajiangapp.dao.impl.UserDaoImpl;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CUser;
import com.chrisyoung.huajiangapp.dto.Bill;
import com.chrisyoung.huajiangapp.dto.SychronizeDataItem;
import com.chrisyoung.huajiangapp.network.DataManager;
import com.chrisyoung.huajiangapp.network.HttpResult;
import com.chrisyoung.huajiangapp.uitils.EntityConvertUtil;
import com.chrisyoung.huajiangapp.view.vinterface.IAddBillView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.LinkedList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class BillPresenter extends BasePresenter{
    private IAddBillView view;
    private IBillManageBiz billManageBiz;
    private IUserDao userDao;
    private DataManager dataManager;
    private ISyncDataBiz syncDataBiz;


    public BillPresenter(IAddBillView view,LifecycleProvider<ActivityEvent> provider) {
        super(provider);
        this.view=view;
        billManageBiz=new BillManageBiz();
        userDao=new UserDaoImpl();
        dataManager=DataManager.getInstance();

    }

    public ArrayList<CBill> showBills(String uId){
        return billManageBiz.showAllBills(uId);
    }

    public void AddBill(CBill newBill,String uId){
        CUser user=userDao.showUserInfo(uId);
        if(user!=null){
            if(billManageBiz.addBill(user,newBill)){
                view.showResult("添加成功");
                view.resetUI();
            }else{
                view.showResult("添加失败");
            }
        }

    }


    public void updateBill(CBill bill){
        if(billManageBiz.updateBill(bill)){
            view.showResult("修改成功");
            view.resetUI();
        }else{
            view.showResult("修改失败");
        }
    }


    public void deleteBill(String bid){
        if(billManageBiz.fakeDeleteBill(bid)){
            view.showResult("删除成功");
        }else{
            view.showResult("删除失败");
        }
    }

    public CBill showBill(String bid){
        return billManageBiz.queryBill(bid);
    }

    public void closeRealm(){
        billManageBiz.closeRealm();
    }


    public void synchBillDataFromServer(String token,String uId){
        syncDataBiz=new SyncDataBiz();
        dataManager.synchronizeBillsS2C(token,uId)
                .subscribeOn(Schedulers.io())
                .map(new Function<HttpResult<LinkedList<SychronizeDataItem<Bill>>>, LinkedList<SychronizeDataItem<CBill>>>() {
                    @Override
                    public LinkedList<SychronizeDataItem<CBill>> apply(HttpResult<LinkedList<SychronizeDataItem<Bill>>> linkedListHttpResult) throws Exception {
                        LinkedList<SychronizeDataItem<Bill>> sbills=linkedListHttpResult.getData();
                        LinkedList<SychronizeDataItem<CBill>> cbills=new LinkedList<>();
                        if(linkedListHttpResult.getCode().equals(ResultCode.SUCCESS.code())){
                            if(sbills!=null && ! sbills.isEmpty()){
                                while (!sbills.isEmpty()){
                                    SychronizeDataItem<Bill> bill=sbills.poll();
                                    if(bill.getData()!=null){
                                        CBill cBill=EntityConvertUtil.SBill2CBill(bill.getData());
                                        if(cBill!=null){
                                            SychronizeDataItem<CBill> cBillSychronizeDataItem=new SychronizeDataItem<>();
                                            cBillSychronizeDataItem.setData(cBill);
                                            cBillSychronizeDataItem.setOptCode(StatusCode.INSERT);
                                            cbills.add(cBillSychronizeDataItem);
                                        }
                                    }
                                }
                            }
                        }
                        return cbills;

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(view.bindLifecycle())
                .subscribe(new Consumer<LinkedList<SychronizeDataItem<CBill>>>() {
                    @Override
                    public void accept(LinkedList<SychronizeDataItem<CBill>> sychronizeDataItems) throws Exception {
                        if(syncDataBiz.synchronizeBillS2C(sychronizeDataItems)){
                            view.resetUI();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "BillError: ", throwable);
                    }
                });
    }
}
