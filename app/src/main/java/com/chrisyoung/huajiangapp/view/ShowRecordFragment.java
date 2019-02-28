package com.chrisyoung.huajiangapp.view;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.domain.RViewModel;
import com.chrisyoung.huajiangapp.presenter.RecordPresenter;
import com.chrisyoung.huajiangapp.uitils.DateFormatUtil;
import com.chrisyoung.huajiangapp.uitils.ToastUtil;
import com.chrisyoung.huajiangapp.uitils.adapter.ShowRecordListViewAdapter;
import com.chrisyoung.huajiangapp.uitils.adapter.ShowRecordModleListViewAdapter;
import com.chrisyoung.huajiangapp.view.vinterface.IRecordsView;
import com.chrisyoung.huajiangapp.view.vinterface.OnViewGenerateListener;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SwipeLayout;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

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
    private Layout title;
    TimePickerDialog mDialogYearMonth;

    private ArrayList<RViewModel> records;

    private RecordPresenter recordPresenter;

    // TODO: Rename and change types of parameters
    private String bId;
    private String mParam2;
    private String curMonth;
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
        month = System.currentTimeMillis();
        curMonth = DateFormatUtil.getYearAndMonth(month);
        btnChooseDate.setText(curMonth);
        records = recordPresenter.showRecords(bId, System.currentTimeMillis());
        if (records != null) {
            adapter = new ShowRecordModleListViewAdapter(records, getContext(), this);
            listView.setAdapter(adapter);
        }


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bId = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            recordPresenter = new RecordPresenter(this);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_show_record, container, false);
        unbinder = ButterKnife.bind(this, rootView);
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
        month = millseconds;

        btnChooseDate.setText(DateFormatUtil.dateToString(new Date(millseconds)).substring(0, 4) + "年" + DateFormatUtil.dateToString(new Date(millseconds)).substring(5, 7) + "月▼");
        refresh(month);

    }

    private void refresh(long millseconds) {
        if(records!=null){
            records.clear();
        }

        records = recordPresenter.showRecords(bId, millseconds);
        adapter = new ShowRecordModleListViewAdapter(records, getContext(), this);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);


    }

    @OnClick({R.id.btn_r_switch, R.id.btnChooseDate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_r_switch:
                break;
            case R.id.btnChooseDate:
                btnChooseDate.setText("");
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
                refresh(month);
            }
        });

    }

    @Override
    public void showResult(String msg) {
        ToastUtil.showShort(getContext(), msg);
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
