package com.chrisyoung.huajiangapp.view;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.domain.CUserDiyKind;
import com.chrisyoung.huajiangapp.presenter.AddCostRecordPresenter;
import com.chrisyoung.huajiangapp.uitils.DateFormatUtil;
import com.chrisyoung.huajiangapp.uitils.ToastUtil;
import com.chrisyoung.huajiangapp.view.vinterface.IAddOrModifyRecordView;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.MenuSheetView;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddCostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCostFragment extends BaseFragment implements IAddOrModifyRecordView, OnDateSetListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    @BindView(R.id.txtCostMoney)
    EditText txtCostMoney;
    @BindView(R.id.imgCostKindImg)
    ImageView imgCostKindImg;
    @BindView(R.id.btnChooseCostKind)
    Button btnChooseCostKind;
    @BindView(R.id.txtCostRecordDate)
    TextView txtCostRecordDate;
    @BindView(R.id.btnChooseCostDate)
    QMUIAlphaImageButton btnChooseCostDate;
    @BindView(R.id.btnChooseCostWay)
    Button btnChooseCostWay;
    @BindView(R.id.txtCostRecordDesc)
    EditText txtCostRecordDesc;
    @BindView(R.id.addCostRecord)
    Button addCostRecord;
    Unbinder unbinder;
    @BindView(R.id.btsChooseCostKind)
    BottomSheetLayout btsChooseCostKind;


    TimePickerDialog mDialogYearMonth;

    // TODO: Rename and change types of parameters
    private String bId;
    private String rType;
    private String uId;

    private OnFragmentInteractionListener mListener;

    private AddCostRecordPresenter presenter=new AddCostRecordPresenter(this);

    public AddCostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddCostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCostFragment newInstance(String param1, String param2,String param3) {
        AddCostFragment fragment = new AddCostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3,param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rType = getArguments().getString(ARG_PARAM1);
            bId = getArguments().getString(ARG_PARAM2);
            uId=getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_cost, container, false);
        unbinder = ButterKnife.bind(this, view);
        btsChooseCostKind.setPeekOnDismiss(true);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnChooseCostKind, R.id.btnChooseCostDate, R.id.btnChooseCostWay, R.id.addCostRecord})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnChooseCostKind:
                showBottomSheetOfKinds();
                break;
            case R.id.btnChooseCostDate:
                showDatePicker();
                break;
            case R.id.btnChooseCostWay:
                showBottomeSheetOfWays();
                break;
            case R.id.addCostRecord:
                presenter.AddRecord(bId,txtCostMoney.getText().toString()
                ,rType,btnChooseCostKind.getText().toString()
                ,txtCostRecordDate.getText().toString()
                ,btnChooseCostWay.getText().toString()
                ,txtCostRecordDesc.getText().toString());
                cleareText();
                break;
        }
    }

    private void showChoosCostKindMenuAndChoose(final MenuSheetView.MenuType menuSheetType) {
        MenuSheetView menuSheetView = new MenuSheetView(getContext(), menuSheetType, " ", new MenuSheetView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                btnChooseCostKind.setText(item.getTitle());
                imgCostKindImg.setImageDrawable(item.getIcon());
                if (btsChooseCostKind.isSheetShowing()) {
                    btsChooseCostKind.dismissSheet();
                }

                return true;
            }
        });
        menuSheetView.inflateMenu(R.menu.menu_choose_cost_kind);
        ArrayList<CUserDiyKind> kinds=presenter.loadDiyKind(uId);
        for (CUserDiyKind k :
                kinds) {
            Menu menu = menuSheetView.getMenu();
            menu.add(k.getdKind()).setIcon(R.mipmap.tab_bill_default);
        }
        menuSheetView.updateMenu();

        btsChooseCostKind.showWithSheetView(menuSheetView);
    }

    private void showChoosCostWayMenuAndChoose(final MenuSheetView.MenuType menuSheetType) {
        MenuSheetView menuSheetView = new MenuSheetView(getContext(), menuSheetType, " ", new MenuSheetView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                btnChooseCostWay.setText(item.getTitle());
                if (btsChooseCostKind.isSheetShowing()) {
                    btsChooseCostKind.dismissSheet();
                }

                return true;
            }
        });
        menuSheetView.inflateMenu(R.menu.menu_choose_cost_way);



        btsChooseCostKind.showWithSheetView(menuSheetView);
    }

    private void showDatePicker() {
        mDialogYearMonth = new TimePickerDialog.Builder()
                .setType(Type.ALL)
                .setThemeColor(getContext().getColor(R.color.titleBackground))
                .setTitleStringId("选择日期")
                .setCallBack(this)
                .setWheelItemTextNormalColorId(getContext().getColor(R.color.timepickerbackground))
                .build();
        assert this.getFragmentManager() != null;
        mDialogYearMonth.show(this.getFragmentManager(), "All");
    }


    private void showBottomSheetOfKinds() {
        showChoosCostKindMenuAndChoose(MenuSheetView.MenuType.GRID);
    }


    private void showBottomeSheetOfWays() {
        showChoosCostWayMenuAndChoose(MenuSheetView.MenuType.LIST);
    }


    @Override
    public void cleareText() {
        txtCostMoney.setHint(getString(R.string.jine));
        btnChooseCostKind.setText(getString(R.string.xiaofei));
        btnChooseCostWay.setText(getString(R.string.zhifubao));
        txtCostRecordDesc.setHint(getString(R.string.beizhu));
    }

    @Override
    public void showRecord(CRecord cRecord) {

    }

    @Override
    public void jump2MainActivity() {
        Intent intent=new Intent(getContext(),MainActivity.class);
        startActivity(intent);
        getActivity().finish();

    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        Date date = new Date(millseconds);
        String s = DateFormatUtil.dateAndTimeToString(date);
        txtCostRecordDate.setText(s);

    }

    @Override
    public void showResult(String result) {
        ToastUtil.showShort(getContext(), result);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
