package com.chrisyoung.huajiangapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.constant.StatusCode;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.presenter.BillPresenter;
import com.chrisyoung.huajiangapp.uitils.DateFormatUtil;
import com.chrisyoung.huajiangapp.uitils.ToastUtil;
import com.chrisyoung.huajiangapp.view.vinterface.IAddBillView;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyBillActivity extends BaseActivity implements IAddBillView,OnDateSetListener {

    @BindView(R.id.text_mr_title)
    TextView textMrTitle;
    @BindView(R.id.btnBack2ShowBill)
    ImageButton btnBack2ShowBill;
    @BindView(R.id.txtBillName)
    EditText txtBillName;
    @BindView(R.id.txtBillDate)
    TextView txtBillDate;
    @BindView(R.id.btnChooseBillDate)
    ImageButton btnChooseBillDate;
    @BindView(R.id.txtBillDesc)
    EditText txtBillDesc;
    @BindView(R.id.btnAddBill)
    Button btnAddBill;
    private String uId;
    private String bId;
    private int action;
    TimePickerDialog mDialogYearMonth;

    private BillPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_bill);
        ButterKnife.bind(this);
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
        presenter = new BillPresenter(this);
        action = getIntent().getIntExtra("action", 1);
        if (action == StatusCode.INSERT) {
            uId=getIntent().getStringExtra("uId");
            textMrTitle.setText("添加账本");
            clearUI();

        } else {
            textMrTitle.setText("账本详情");
            bId = getIntent().getStringExtra("bId");
            CBill b = presenter.showBill(bId);
            txtBillName.setText(b.getbName());
            txtBillDate.setText(DateFormatUtil.dateToString(b.getbDate()));
            txtBillDesc.setText(b.getbDesc());
            uId=b.getuId();
        }

    }

    @Override
    public void showResult(String result) {

        ToastUtil.showShort(this, result);

    }

    @Override
    public void resetUI() {
        clearUI();
    }

    @OnClick({R.id.btnBack2ShowBill, R.id.btnChooseBillDate, R.id.btnAddBill})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBack2ShowBill:
                Intent intent1=new Intent(this, MainActivity.class);
                intent1.putExtra("tabNo",MainActivity.BILL);
                startActivity(intent1);
                this.finish();
                break;
            case R.id.btnChooseBillDate:
                showDatePicker();
                break;
            case R.id.btnAddBill:
                if(action==StatusCode.INSERT){
                    addBill();

                }else{
                    updateBill();
                }
                Intent intent=new Intent(this, MainActivity.class);
                intent.putExtra("tabNo",MainActivity.BILL);
                startActivity(intent);
                this.finish();
                break;
        }
    }

    private void showDatePicker() {

        mDialogYearMonth = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setThemeColor(this.getColor(R.color.titleBackground))
                .setTitleStringId("选择日期")
                .setCallBack(this)
                .setWheelItemTextNormalColorId(this.getColor(R.color.timepickerbackground))
                .build();
        assert this.getFragmentManager() != null;
        mDialogYearMonth.show(getSupportFragmentManager(), "year_month_day");


    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        txtBillDate.setText(DateFormatUtil.dateToString(new Date(millseconds)));
    }

    private void addBill(){
        CBill b=new CBill();
        b.setbName(txtBillName.getText().toString());
        b.setuId(uId);
        b.setbDate(DateFormatUtil.stringToDate(txtBillDate.getText().toString()));
        b.setbDesc(txtBillDesc.getText().toString());
        presenter.AddBill(b,uId);

    }

    private void updateBill(){
        CBill b=new CBill();
        b.setbId(bId);
        b.setbName(txtBillName.getText().toString());
        b.setuId(uId);
        b.setbDate(DateFormatUtil.stringToDate(txtBillDate.getText().toString()));
        b.setbDesc(txtBillDesc.getText().toString());
        presenter.updateBill(b);
    }

    public void clearUI(){
        txtBillName.setText("");
        txtBillDate.setText(DateFormatUtil.dateToString(new Date(System.currentTimeMillis())));
        txtBillDesc.setText("");
    }

}
