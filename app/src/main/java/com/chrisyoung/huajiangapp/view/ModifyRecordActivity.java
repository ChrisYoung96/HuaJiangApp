package com.chrisyoung.huajiangapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.presenter.ModifyRecordPresenter;
import com.chrisyoung.huajiangapp.uitils.DateFormatUtil;
import com.chrisyoung.huajiangapp.uitils.ToastUtil;
import com.chrisyoung.huajiangapp.view.vinterface.IAddOrModifyRecordView;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.MenuSheetView;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyRecordActivity extends AppCompatActivity implements OnDateSetListener, IAddOrModifyRecordView {


    @BindView(R.id.txtUrCostMoney)
    EditText txtUrCostMoney;
    @BindView(R.id.imgUrCostKindImg)
    ImageView imgUrCostKindImg;
    @BindView(R.id.txtUrCostRecordDate)
    TextView txtUrCostRecordDate;

    @BindView(R.id.textView10)
    TextView textView10;
    @BindView(R.id.txtUrCostRecordDesc)
    EditText txtUrCostRecordDesc;

    @BindView(R.id.btsUrChooseCostKind)
    BottomSheetLayout btsUrChooseCostKind;

    TimePickerDialog mDialogYearMonth;
    String rId;
    @BindView(R.id.txtUrRID)
    TextView txtUrRID;
    @BindView(R.id.txtUrBID)
    TextView txtUrBID;
    @BindView(R.id.text_ur_title)
    TextView textUrTitle;
    @BindView(R.id.btnUrBack)
    ImageButton btnUrBack;
    @BindView(R.id.btnUrChooseCostKind)
    Button btnUrChooseKind;
    @BindView(R.id.btnUrChooseCostDate)
    QMUIAlphaImageButton btnUrChooseDate;
    @BindView(R.id.btnUrChooseCostWay)
    Button btnUrChooseWay;
    @BindView(R.id.btnModifyRecord)
    Button btnModifyRecord;
    @BindView(R.id.txtUrVersion)
    TextView txtUrVersion;


    private ModifyRecordPresenter recordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_record);
        ButterKnife.bind(this);
        rId = getIntent().getStringExtra("rId");
        recordPresenter = new ModifyRecordPresenter(this);
        btsUrChooseCostKind.setPeekOnDismiss(true);
        if (rId != null && !rId.equals("")) {
            recordPresenter.getRecord(rId);
        }

    }


    private void showChoosCostKindMenuAndChoose(final MenuSheetView.MenuType menuSheetType) {
        MenuSheetView menuSheetView = new MenuSheetView(this, menuSheetType, " ", new MenuSheetView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                btnUrChooseKind.setText(item.getTitle());
                imgUrCostKindImg.setImageDrawable(item.getIcon());
                if (btsUrChooseCostKind.isSheetShowing()) {
                    btsUrChooseCostKind.dismissSheet();
                }

                return true;
            }
        });
        menuSheetView.inflateMenu(R.menu.menu_choose_cost_kind);

        btsUrChooseCostKind.showWithSheetView(menuSheetView);
    }

    private void showChoosCostWayMenuAndChoose(final MenuSheetView.MenuType menuSheetType) {
        MenuSheetView menuSheetView = new MenuSheetView(this, menuSheetType, " ", new MenuSheetView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                btnUrChooseWay.setText(item.getTitle());
                if (btsUrChooseCostKind.isSheetShowing()) {
                    btsUrChooseCostKind.dismissSheet();
                }

                return true;
            }
        });
        menuSheetView.inflateMenu(R.menu.menu_choose_cost_way);

        btsUrChooseCostKind.showWithSheetView(menuSheetView);
    }

    private void showBottomSheetOfKinds() {
        showChoosCostKindMenuAndChoose(MenuSheetView.MenuType.GRID);
    }


    private void showBottomeSheetOfWays() {
        showChoosCostWayMenuAndChoose(MenuSheetView.MenuType.LIST);
    }

    private void showDatePicker() {
        mDialogYearMonth = new TimePickerDialog.Builder()
                .setType(Type.ALL)
                .setThemeColor(this.getColor(R.color.titleBackground))
                .setTitleStringId("选择日期")
                .setCallBack(this)
                .setWheelItemTextNormalColorId(this.getColor(R.color.timepickerbackground))
                .build();
        assert this.getFragmentManager() != null;
        mDialogYearMonth.show(getSupportFragmentManager(), "All");
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        Date date = new Date(millseconds);
        StringBuffer s = new StringBuffer().append(DateFormatUtil.dateAndTimeToString(date));
        txtUrCostRecordDate.setText(s);
    }


    @Override
    public void cleareText() {

    }

    @Override
    public void showRecord(CRecord cRecord) {
        txtUrRID.setText(cRecord.getrId());
        txtUrBID.setText(cRecord.getbId());
        txtUrCostMoney.setText(String.valueOf(cRecord.getrMoney()));
        txtUrCostRecordDate.setText(DateFormatUtil.dateAndTimeToString(cRecord.getrTime()));
        txtUrCostRecordDesc.setText(cRecord.getrDesc());
        btnUrChooseKind.setText(cRecord.getrKind());
        btnUrChooseWay.setText(cRecord.getrWay());
        textUrTitle.setText(cRecord.getrType());
        txtUrVersion.setText(String.valueOf(cRecord.getrVersion()));
    }

    @Override
    public void jump2MainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    @OnClick({R.id.btnUrBack, R.id.btnUrChooseCostKind, R.id.btnUrChooseCostDate, R.id.btnUrChooseCostWay, R.id.btnModifyRecord})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnUrBack:
                jump2MainActivity();
                break;
            case R.id.btnUrChooseCostKind:
                showBottomSheetOfKinds();
                break;
            case R.id.btnUrChooseCostDate:
                showDatePicker();
                break;
            case R.id.btnUrChooseCostWay:
                showBottomeSheetOfWays();
                break;
            case R.id.btnModifyRecord:
                recordPresenter.updateRecord(txtUrRID.getText().toString()
                        , txtUrBID.getText().toString()
                        , txtUrCostMoney.getText().toString()
                        , textUrTitle.getText().toString()
                        , btnUrChooseKind.getText().toString()
                        , txtUrCostRecordDate.getText().toString()
                        , btnUrChooseWay.getText().toString()
                        , txtUrCostRecordDesc.getText().toString()
                        , Integer.parseInt(txtUrVersion.getText().toString()));
                break;
        }
    }

    @Override
    public void showResult(String result) {
        ToastUtil.showShort(this, result);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recordPresenter.closeRealm();
    }
}
