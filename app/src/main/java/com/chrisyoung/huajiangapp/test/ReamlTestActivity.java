package com.chrisyoung.huajiangapp.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.biz.impl.RecordManageBizImpl;
import com.chrisyoung.huajiangapp.dao.impl.BillDaoImpl;
import com.chrisyoung.huajiangapp.dao.impl.RecordDaoImpl;
import com.chrisyoung.huajiangapp.dao.impl.UserDaoImpl;
import com.chrisyoung.huajiangapp.dao.impl.UserDiyKindDaoImpl;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.domain.CUser;
import com.chrisyoung.huajiangapp.domain.CUserDiyKind;
import com.chrisyoung.huajiangapp.domain.RViewModel;
import com.chrisyoung.huajiangapp.uitils.DateFormatUtil;
import com.chrisyoung.huajiangapp.uitils.ToastUtil;

import java.util.ArrayList;
import java.util.Date;

import io.realm.RealmList;

public class ReamlTestActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnAddUser;
    private Button btnShowUser;
    private Button btnAddBill;
    private Button btnShowBill;
    private Button btnDeleteBill;
    private Button btnAddKind;
    private Button btnShowKind;
    private Button btnDeleteKind;
    private Button btnAddRecord;
    private Button btnShowRecord;
    private Button btnShowViewRecord;
    private Button btnSum;
    private TextView txtView;
    private UserDaoImpl userDao;
    private BillDaoImpl billDao;
    private RecordDaoImpl recordDao;
    private UserDiyKindDaoImpl userDiyKindDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reaml_test);
        userDao=new UserDaoImpl();
        userDiyKindDao=new UserDiyKindDaoImpl();
        billDao=new BillDaoImpl();
        recordDao=new RecordDaoImpl();
        initView();
    }

    private void initView(){
        btnAddUser=this.findViewById(R.id.btnAddUser2);
        btnShowUser=this.findViewById(R.id.btnShowUser);
        btnAddBill=this.findViewById(R.id.btnAddBill);
        btnShowBill=this.findViewById(R.id.btnShowBill);
        btnDeleteBill=this.findViewById(R.id.btnDelBill);
        btnAddKind=this.findViewById(R.id.btnAddKind);
        btnShowKind=this.findViewById(R.id.btnShowKind);
        btnDeleteKind=this.findViewById(R.id.btnDeleteKind);
        btnAddRecord=this.findViewById(R.id.btnAddRecord);
        btnSum=this.findViewById(R.id.btnSum);
        btnShowRecord=this.findViewById(R.id.btnShowRecords);
        txtView=this.findViewById(R.id.txtView);
        btnShowViewRecord=this.findViewById(R.id.btnShowViewRecord);

        btnAddUser.setOnClickListener(this);
        btnShowUser.setOnClickListener(this);
        btnAddBill.setOnClickListener(this);
        btnShowBill.setOnClickListener(this);
        btnDeleteBill.setOnClickListener(this);
        btnAddKind.setOnClickListener(this);
        btnShowKind.setOnClickListener(this);
        btnDeleteKind.setOnClickListener(this);
        btnAddRecord.setOnClickListener(this);
        btnShowRecord.setOnClickListener(this);
        btnSum.setOnClickListener(this);
        btnShowViewRecord.setOnClickListener(this);
    }

    private void testAddUser(){
        CUser user=new CUser();
        user.setuName("test1");
        user.setuSex("男");
        Date d=DateFormatUtil.stringToDate("1998-01-01");
        user.setuBirthday(d);
        user.setuPhone("123456");
        user.setBills(new RealmList<>());
        user.setcUserDiyKinds(new RealmList<>());

        if(userDao.addOrUpdateUser(user)){
            ToastUtil.showLong(this,"成功");
        }else{
            ToastUtil.showLong(this,"失败");
        }
    }

    private void testShowUserInfo(){
        CUser u=userDao.showUserInfo("7178c6a24e1140b2a648a78b1a07c207");


    }

    private void testAddBills(){
        CUser user=userDao.showUserInfo("94d5f9cbd27b4526a9b90176f44037d7");
        CBill b1=new CBill();
        b1.setuId("94d5f9cbd27b4526a9b90176f44037d7");
        b1.setbName("book1");
        b1.setbDate(DateFormatUtil.stringToDate("2018-01-01"));
        b1.setcRecords(new RealmList<>());
        billDao.addBillForUser(user,b1);
        CBill b2=new CBill();
        b2.setuId(user.getuId());
        b2.setbName("book2");
        b2.setbDate(DateFormatUtil.stringToDate("2018-11-01"));
        b2.setcRecords(new RealmList<>());
        billDao.addBillForUser(user,b2);
        CBill b3=new CBill();
        b3.setuId(user.getuId());
        b3.setbName("book3");
        b3.setbDate(DateFormatUtil.stringToDate("2018-01-01"));
        b3.setcRecords(new RealmList<>());
        billDao.addBillForUser(user,b3);
        ToastUtil.showShort(this,"成功");
    }

    private void testShowBills(){
        ArrayList<CBill> bills=billDao.showAllBills("6354b0d876244d8686c5a9b65b765a55");
        for (CBill b:
             bills) {
            ToastUtil.showShort(this, b.getbId());
        }
    }

    private void testDeleteBill(){
        String bid="be5b7ba455bc4b41a1f56d39069dc74f";
        if(billDao.deleteBill(bid)){
            ToastUtil.showShort(this,"success");
        }
    }

    private void testAddKind() {
        CUser user=userDao.showUserInfo("94d5f9cbd27b4526a9b90176f44037d7");
        String uId = user.getuId();
        CUserDiyKind kind = new CUserDiyKind();
        kind.setuId(uId);
        kind.setdType(" 支出");
        kind.setdKind("吃饭");
        if (userDiyKindDao.addKindForUser(user,kind)) {
            ToastUtil.showShort(this, "success");
        }
    }

    private void testShowKind(){
        String uid="94d5f9cbd27b4526a9b90176f44037d7";
        ArrayList<CUserDiyKind> kinds=userDiyKindDao.showAllKinds(uid);
        for(CUserDiyKind k:kinds){
            ToastUtil.showShort(this,k.getdId());
        }
    }

    private void testAddRecord(){
        CBill b=billDao.findBill("385ca5a9ee1340148ce3977b99e6660b");
        CRecord r1=new CRecord();
        r1.setbId("385ca5a9ee1340148ce3977b99e6660b");
        r1.setrType("支出");
        r1.setrKind("购物");
        r1.setrMoney(332.21);
        r1.setrTime(DateFormatUtil.stringtoDateAndTime("2018-02-25 15:13:49"));
        r1.setrWay("银行卡");
        recordDao.addRecordForBill(b,r1);

        CRecord r2=new CRecord();
        r2.setbId("385ca5a9ee1340148ce3977b99e6660b");
        r2.setrType("支出");
        r2.setrKind("红包");
        r2.setrMoney(22.21);
        r2.setrTime(DateFormatUtil.stringtoDateAndTime("2018-02-05 15:22:21"));
        r2.setrWay("微信");
        recordDao.addRecordForBill(b,r2);

        CRecord r3=new CRecord();
        r3.setbId("385ca5a9ee1340148ce3977b99e6660b");
        r3.setrType("收入");
        r3.setrKind("红包");
        r3.setrMoney(14.41);
        r3.setrTime(DateFormatUtil.stringtoDateAndTime("2018-03-05 15:23:21"));
        r3.setrWay("支付宝");
        recordDao.addRecordForBill(b,r3);

        CRecord r4=new CRecord();
        r4.setbId("385ca5a9ee1340148ce3977b99e6660b");
        r4.setrType("支出");
        r4.setrKind("购物");
        r4.setrMoney(52.41);
        r4.setrTime(DateFormatUtil.stringtoDateAndTime("2018-03-25 20:22:21"));
        r4.setrWay("微信");
        recordDao.addRecordForBill(b,r4);

        CRecord r5=new CRecord();
        r5.setbId("385ca5a9ee1340148ce3977b99e6660b");
        r5.setrType("支出");
        r5.setrKind("购物");
        r5.setrMoney(166.41);
        r5.setrTime(DateFormatUtil.stringtoDateAndTime("2018-04-01 20:24:21"));
        r5.setrWay("支付宝");
        recordDao.addRecordForBill(b,r5);

        CRecord r6=new CRecord();
        r6.setbId("385ca5a9ee1340148ce3977b99e6660b");
        r6.setrType("支出");
        r6.setrKind("娱乐");
        r6.setrMoney(16.41);
        r6.setrTime(DateFormatUtil.stringtoDateAndTime("2018-04-11 20:25:21"));
        r6.setrWay("微信");
        recordDao.addRecordForBill(b,r6);


        if(recordDao.addRecordForBill(b,r1)){
            ToastUtil.showShort(this,"success");
        }
    }

    private void testShowRecord(){
        String bId="385ca5a9ee1340148ce3977b99e6660b";
        ArrayList<CRecord> records=recordDao.showAllRecords(bId);
        txtView.setText(showRecordResults(records));
    }

    private void testShowRecordByMonth(){
        String bId="385ca5a9ee1340148ce3977b99e6660b";
        Date startOfMonth=DateFormatUtil.getStartOfMonth("2018","2");
        Date endOfMonth=DateFormatUtil.getEndOfMonth("2018","2");
        ArrayList<CRecord> records=recordDao.showRecordsByMonth(bId,startOfMonth,endOfMonth);
        txtView.setText(showRecordResults(records));
    }

    private void testShowRecordByMonthAndType(){
        String bId="385ca5a9ee1340148ce3977b99e6660b";
        Date startOfMonth=DateFormatUtil.getStartOfMonth("2018","2");
        Date endOfMonth=DateFormatUtil.getEndOfMonth("2018","2");
        ArrayList<CRecord> records=recordDao.showRecordsByMonthAndType(bId,startOfMonth,endOfMonth,"收入");
        txtView.setText(showRecordResults(records));
    }

    private void testShowRecordByWay(){
        String bId="385ca5a9ee1340148ce3977b99e6660b";
        Date startOfMonth=DateFormatUtil.getStartOfMonth("2018","2");
        Date endOfMonth=DateFormatUtil.getEndOfMonth("2018","2");
        ArrayList<CRecord> records=recordDao.showRecordsByWayAndMonth(bId,"支付宝",startOfMonth,endOfMonth,"支出");
        txtView.setText(showRecordResults(records));
    }

    private StringBuilder showRecordResults(ArrayList<CRecord> records){
        StringBuilder s= new StringBuilder();
        for (CRecord r :
                records) {
            s.append(r.getrType()).append(" ").append(r.getrKind()).append(" ").append(r.getrMoney()).append(" ").append(r.getrWay()).append(" ").append(DateFormatUtil.dateAndTimeToString(r.getrTime())).append("\n");
        }
        return s;
    }

    private void testSumAllMoneyInAMonth(){
        String bId="385ca5a9ee1340148ce3977b99e6660b";
        Date startOfMonth=DateFormatUtil.getStartOfMonth("2018","2");
        Date endOfMonth=DateFormatUtil.getEndOfMonth("2018","2");
        Double result=recordDao.sumAllMoneyInAMonth(bId,startOfMonth,endOfMonth,"支出");
        txtView.append(result.toString());
    }

    private void testsumAllMoneyOfWayInAMonth(){
        String bId="385ca5a9ee1340148ce3977b99e6660b";
        Date startOfMonth=DateFormatUtil.getStartOfMonth("2018","3");
        Date endOfMonth=DateFormatUtil.getEndOfMonth("2018","3");
        Double result=recordDao.sumAllMoneyOfWayInAMonth(bId,startOfMonth,endOfMonth,"支出","微信");
        txtView.append(result.toString());
    }

    private void testsumAllMoneyOfKindInAMonth(){
        String bId="385ca5a9ee1340148ce3977b99e6660b";
        Date startOfMonth=DateFormatUtil.getStartOfMonth("2018","3");
        Date endOfMonth=DateFormatUtil.getEndOfMonth("2018","3");
        Double result=recordDao.sumAllMoneyOfKindInAMonth(bId,startOfMonth,endOfMonth,"支出","购物");
        txtView.append(result.toString());
    }

    private void testShowViewRecord(){
        String bId="385ca5a9ee1340148ce3977b99e6660b";
        Date startOfMonth=DateFormatUtil.getStartOfMonth("2018","2");
        Date endOfMonth=DateFormatUtil.getEndOfMonth("2018","2");
        ArrayList<RViewModel> rViewModels=new RecordManageBizImpl().showRecordsInAMonth(bId,startOfMonth,endOfMonth,"");
        txtView.setText("");
        for (RViewModel r :
                rViewModels) {
            txtView.append(DateFormatUtil.dateToString(r.getDay())+" 支出："+r.getTotalCost().toString()+" 收入："+r.getTotalIncome().toString()+"\n");
            for (CRecord c :
                    r.getRecords()) {
                txtView.append(c.getrWay()+" "+c.getrKind()+" "+c.getrMoney().toString()+"\n");
            }
        }

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==btnAddUser.getId()){
            testAddUser();
        }else if(view.getId()==btnShowUser.getId()){
            testShowUserInfo();
        }else if(view.getId()==btnAddBill.getId()){
            testAddBills();
        }else if(view.getId()==btnShowBill.getId()){
            testShowBills();
        }else if(view.getId()==btnDeleteBill.getId()){
            testDeleteBill();
        }else if(view.getId()==btnAddKind.getId()){
            testAddKind();
        }else if(view.getId()==btnShowKind.getId()){
            testShowKind();

        }else if(view.getId()==btnDeleteKind.getId()){

        }else if(view.getId()==btnAddRecord.getId()){
            testAddRecord();
        }else if(view.getId()==btnShowRecord.getId()){
            //testShowRecord();
            testShowRecordByMonth();
            //testShowRecordByMonthAndType();
            //testShowRecordByWay();
        }else if(view.getId()==btnSum.getId()){
            //testSumAllMoneyInAMonth();
            testsumAllMoneyOfWayInAMonth();
            //testsumAllMoneyOfKindInAMonth();
        }else if(view.getId()==btnShowViewRecord.getId()){
            testShowViewRecord();
        }
    }
}
