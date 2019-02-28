package com.chrisyoung.huajiangapp.presenter;

import android.content.Context;

import com.chrisyoung.huajiangapp.biz.IBillManageBiz;
import com.chrisyoung.huajiangapp.biz.impl.BillManageBiz;
import com.chrisyoung.huajiangapp.dao.IUserDao;
import com.chrisyoung.huajiangapp.dao.impl.UserDaoImpl;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CUser;
import com.chrisyoung.huajiangapp.view.vinterface.IAddBillView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;

public class BillPresenter {
    private IAddBillView view;
    private IBillManageBiz billManageBiz;
    private IUserDao userDao;

    public BillPresenter(IAddBillView view){
        this.view=view;
        billManageBiz=new BillManageBiz();
        userDao=new UserDaoImpl();
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
        if(billManageBiz.deleteBill(bid)){
            view.showResult("删除成功");
        }else{
            view.showResult("删除失败");
        }
    }

    public CBill showBill(String bid){
        return billManageBiz.queryBill(bid);
    }
}
