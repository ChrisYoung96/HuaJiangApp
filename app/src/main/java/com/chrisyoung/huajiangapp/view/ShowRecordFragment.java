package com.chrisyoung.huajiangapp.view;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.constant.UserConfig;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.RViewModel;
import com.chrisyoung.huajiangapp.presenter.RecordPresenter;
import com.chrisyoung.huajiangapp.presenter.SyncDataPresenter;
import com.chrisyoung.huajiangapp.uitils.DateFormatUtil;
import com.chrisyoung.huajiangapp.uitils.SharedPreferenceUtil;
import com.chrisyoung.huajiangapp.uitils.ToastUtil;
import com.chrisyoung.huajiangapp.view.adapter.ShowRecordModleListViewAdapter;
import com.chrisyoung.huajiangapp.view.vinterface.BaseInternetView;
import com.chrisyoung.huajiangapp.view.vinterface.IRecordsView;
import com.chrisyoung.huajiangapp.view.vinterface.OnViewGenerateListener;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SwipeLayout;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowRecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowRecordFragment extends BaseFragment implements OnDateSetListener, OnViewGenerateListener, IRecordsView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Unbinder unbinder;
    @BindView(R.id.text_r_title)
    TextView textRTitle;
    @BindView(R.id.txtTotalIncome)
    TextView txtTotalIncome;
    @BindView(R.id.txtTotalCost)
    TextView txtTotalCost;
    @BindView(R.id.btn_r_switch)
    ImageButton btnRSwitch;
    @BindView(R.id.btnChooseDate)
    Button btnChooseDate;

    TimePickerDialog mDialogYearMonth;
    @BindView(R.id.recordRefreshLayout)
    QMUIPullRefreshLayout recordRefreshLayout;

    private ArrayList<RViewModel> records;

    private RecordPresenter recordPresenter;

    private SyncDataPresenter syncDataPresenter;

    // TODO: Rename and change types of parameters
    private String bId = "";
    private String uId;
    private String curBName = "";
    private String curMonth = "";
    private String token="";
    private SwipeLayout swipeLayout;
    @BindView(R.id.recordModelListView)
    ListView listView;

    private ShowRecordModleListViewAdapter adapter;
    private long month;


    private OnFragmentInteractionListener mListener;

    public ShowRecordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowRecordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowRecordFragment newInstance(String param1, String param2) {
        ShowRecordFragment fragment = new ShowRecordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    private void init() {
        syncDataPresenter=new SyncDataPresenter(new MainActivity(),getContext(),this);
        month = System.currentTimeMillis();
        bId = (String) SharedPreferenceUtil.get(getContext(), UserConfig.CUR_BID, bId);
        curBName = (String) SharedPreferenceUtil.get(getContext(), UserConfig.CUR_BNAME, curBName);
        token=(String) SharedPreferenceUtil.get(getContext(),UserConfig.TOKEN,token);
        curMonth = DateFormatUtil.getYearAndMonth(month) + "▼";
        btnChooseDate.setText(curMonth);
        if (curBName.equals("")) {
            textRTitle.setText("无账本");
        } else {
            textRTitle.setText(curBName);
        }

        recordRefreshLayout.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                if(records.isEmpty()){
                    recordRefreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recordPresenter.syncRecordDataFromServer(token,bId);
                            recordRefreshLayout.finishRefresh();
                        }
                    },1000);

                }

            }
        });
        loadData(month);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bId = getArguments().getString(ARG_PARAM1);
            uId = getArguments().getString(ARG_PARAM2);
            recordPresenter = new RecordPresenter(new MainActivity(),this);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_show_record, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        init();
        return rootView;
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
        recordPresenter.closeRealm();
        unbinder.unbind();
    }


    private void initDatePicker() {
        mDialogYearMonth = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH)
                .setThemeColor(getContext().getColor(R.color.titleBackground))
                .setTitleStringId("选择日期")
                .setCallBack(this)
                .setWheelItemTextNormalColorId(getContext().getColor(R.color.timepickerbackground))
                .build();
        assert this.getFragmentManager() != null;
        mDialogYearMonth.show(this.getFragmentManager(), "year_month");
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        txtTotalCost.setText("");
        txtTotalIncome.setText("");
        btnChooseDate.setText("");
        month = millseconds;
        btnChooseDate.setText(DateFormatUtil.getYearAndMonth(millseconds) + "▼");
        loadData(month);

    }

    @Override
    public void loadData(long millseconds) {
        if (records != null) {
            records.clear();
        }
        Date date = new Date(millseconds);
        records = recordPresenter.showRecords(bId, millseconds);
        if (records != null && !records.isEmpty()) {
            adapter = new ShowRecordModleListViewAdapter(records, getContext(), this);
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
        } else {
            ToastUtil.showShort(getContext(), "当月账本中还没有记录呦！");
        }


    }

    @OnClick({R.id.btn_r_switch, R.id.btnChooseDate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_r_switch:
                recordPresenter.showBillList(uId);
                break;
            case R.id.btnChooseDate:
                initDatePicker();
                break;
        }
    }

    @Override
    public void initView(View v) {
        swipeLayout = v.findViewById(R.id.list_record_item_layout);
        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada)
                        .duration(500)
                        .delay(100)
                        .playOn(layout.findViewById(R.id.btnDeleteRecord));

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

        v.findViewById(R.id.btnDeleteRecord).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rId = v.getTag().toString();
                recordPresenter.deleteRecord(rId);
                loadData(month);
            }
        });

    }

    @Override
    public void showResult(String msg) {
        ToastUtil.showShort(getContext(), msg);
    }

    @Override
    public void showTotalIncome(BigDecimal num) {
        if(!num.equals(BigDecimal.valueOf(0.00))){
            DecimalFormat df1 = new DecimalFormat("#.00");
            txtTotalIncome.setText(df1.format(num));
        }else{
            txtTotalIncome.setText(String.valueOf(num));
        }


    }

    @Override
    public void showTotalCost(BigDecimal num) {
        if(!num.equals(BigDecimal.valueOf(0.00))){
            DecimalFormat df1 = new DecimalFormat("#.00");
            txtTotalCost.setText(df1.format(num));
        }else{
            txtTotalCost.setText(String.valueOf(num));
        }

    }

    @Override
    public void showBillList(ArrayList<CBill> bills) {
        QMUIBottomSheet.BottomListSheetBuilder bottomSheetBuilder = new QMUIBottomSheet.BottomListSheetBuilder(getActivity());
        if (bills != null && !bills.isEmpty()) {
            for (CBill b : bills
                    ) {
                bottomSheetBuilder.addItem(b.getbName(), b.getbId());
            }
            bottomSheetBuilder.setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                @Override
                public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                    dialog.dismiss();
                    bId = tag;
                    textRTitle.setText(bills.get(position).getbName());
                    SharedPreferenceUtil.put(Objects.requireNonNull(getContext()), UserConfig.CUR_BID, bId);
                    SharedPreferenceUtil.put(getContext(), UserConfig.CUR_BNAME, bills.get(position).getbName());
                    loadData(System.currentTimeMillis());
                }
            });
            bottomSheetBuilder.build().show();
        }
    }


    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void hideErrorDialog() {

    }

    @Override
    public LifecycleTransformer bindLifecycle() {
        return bindToLifecycle();
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
