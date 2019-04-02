package com.chrisyoung.huajiangapp.view;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.constant.UserConfig;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.presenter.ChartPresenter;
import com.chrisyoung.huajiangapp.uitils.SharedPreferenceUtil;
import com.chrisyoung.huajiangapp.view.adapter.BaseFragmentPagerAdapter;
import com.chrisyoung.huajiangapp.view.vinterface.IChartView;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import java.util.ArrayList;
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
 * Use the {@link ChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChartFragment extends BaseFragment implements IChartView ,OnDateSetListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.text_sv_title)
    TextView textSvTitle;
    @BindView(R.id.btnSvSwitch)
    ImageButton btnSvSwitch;
    @BindView(R.id.tabStatisticsSegment)
    QMUITabSegment tabStatisticsSegment;
    @BindView(R.id.contentStatisticsViewPager)
    ViewPager contentStatisticsViewPager;
    Unbinder unbinder;

    StatisticsCostFragment statisticsCostFragment;
    StatisticsIncomeFragment statisticsIncomeView;
    ArrayList<android.support.v4.app.Fragment> fragments=new ArrayList<>();
    BaseFragmentPagerAdapter baseFragmentPagerAdapter;

    ChartPresenter chartPresenter;



    // TODO: Rename and change types of parameters
    private String uId;
    private String curBId;

    private OnFragmentInteractionListener mListener;

    public ChartFragment() {
        // Required empty public constructor
    }


    private void init(){
        String bName="";
        bName=(String)SharedPreferenceUtil.get(getContext(),UserConfig.CUR_BNAME,bName);
        textSvTitle.setText(bName);
        fragments.clear();
        tabStatisticsSegment.reset();
        statisticsCostFragment =StatisticsCostFragment.newInstance(curBId,"支出");
        statisticsIncomeView=StatisticsIncomeFragment.newInstance(curBId,"收入");
        fragments.add(statisticsCostFragment);
        fragments.add(statisticsIncomeView);
        baseFragmentPagerAdapter=new BaseFragmentPagerAdapter(getChildFragmentManager(),fragments);
        contentStatisticsViewPager.setAdapter(baseFragmentPagerAdapter);
        tabStatisticsSegment.reset();
        tabStatisticsSegment.addTab(new QMUITabSegment.Tab("支出"));
        tabStatisticsSegment.addTab(new QMUITabSegment.Tab("收入"));
        int space = QMUIDisplayHelper.dp2px(Objects.requireNonNull(getContext()), 16);
        tabStatisticsSegment.setHasIndicator(true);
        tabStatisticsSegment.setIndicatorWidthAdjustContent(true);
        tabStatisticsSegment.setMode(QMUITabSegment.MODE_FIXED);
        tabStatisticsSegment.setItemSpaceInScrollMode(space);
        tabStatisticsSegment.setupWithViewPager(contentStatisticsViewPager, false);
        tabStatisticsSegment.setPadding(space, 0, space, 0);
        tabStatisticsSegment.notifyDataChanged();
        chartPresenter=new ChartPresenter(this);


    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChartFragment newInstance(String param1, String param2) {
        ChartFragment fragment = new ChartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            uId = getArguments().getString(ARG_PARAM1);
            curBId = getArguments().getString(ARG_PARAM2);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
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

    @OnClick(R.id.btnSvSwitch)
    public void onViewClicked() {
        chartPresenter.showBillList(uId);
    }




    @Override
    public void showBillList(ArrayList<CBill> bills) {
        QMUIBottomSheet.BottomListSheetBuilder bottomSheetBuilder=new QMUIBottomSheet.BottomListSheetBuilder(getActivity());
        if(bills!=null && !bills.isEmpty()){
            for (CBill b:bills
                    ) {
                bottomSheetBuilder.addItem(b.getbName(),b.getbId());
            }
            bottomSheetBuilder.setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                @Override
                public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                    dialog.dismiss();
                    curBId=bills.get(position).getbId();
                    textSvTitle.setText(bills.get(position).getbName());
                    statisticsCostFragment.refresh(curBId);
                }
            });
            bottomSheetBuilder.build().show();
        }
    }

    @Override
    public void showResult(String result) {

    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {

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

    public interface OnBillChanageListener{
        void refresh(String bId);
    }
}
