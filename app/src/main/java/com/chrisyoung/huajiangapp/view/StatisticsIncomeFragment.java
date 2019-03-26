package com.chrisyoung.huajiangapp.view;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.presenter.StatisticsPresenter;
import com.chrisyoung.huajiangapp.uitils.DateFormatUtil;
import com.chrisyoung.huajiangapp.view.vinterface.IStasisticsView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
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
 * Use the {@link StatisticsIncomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsIncomeFragment extends BaseFragment implements OnDateSetListener, IStasisticsView, ChartFragment.OnBillChanageListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.txtSiTotalIncomeMoney)
    TextView txtSiTotalIncomeMoney;
    @BindView(R.id.txtSiAvrgIncomeMoney)
    TextView txtSiAvrgIncomeMoney;
    @BindView(R.id.btnSIChooseIncomeDate)
    Button btnSIChooseIncomeDate;
    @BindView(R.id.inComeLineChart)
    LineChart incomeLineChart;
    @BindView(R.id.incomePieChart)
    PieChart incomePieChart;
    @BindView(R.id.incomeBarChart)
    BarChart incomeBarChart;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String bId;
    private String type;
    private StatisticsPresenter presenter;
    
    private OnFragmentInteractionListener mListener;

    TimePickerDialog mDialogYearMonth;

    public StatisticsIncomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticsIncomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticsIncomeFragment newInstance(String param1, String param2) {
        StatisticsIncomeFragment fragment = new StatisticsIncomeFragment();
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
            bId = getArguments().getString(ARG_PARAM1);
            type = getArguments().getString(ARG_PARAM2);
        }
    }

    private void init(){
        presenter=new StatisticsPresenter(this);
        presenter.init(bId,System.currentTimeMillis(),type);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics_income_view, container, false);
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
    public void showFirstLayer(String sum, String avrg) {
        txtSiAvrgIncomeMoney.setText(avrg);
        txtSiTotalIncomeMoney.setText(sum);
    }

    @Override
    public void showPieChart(PieData data, ArrayList<String> xvals) {
        data.setDrawValues(true);
        data.setValueTextSize(10f);
        Description description=new Description();
        description.setText("");
        incomePieChart.setDescription(description);
        incomePieChart.setUsePercentValues(true);
        incomePieChart.setCenterText("消费类型统计");
        incomePieChart.setCenterTextSize(15f);
        incomePieChart.setEntryLabelColor(Color.BLACK);
        incomePieChart.clear();
        incomePieChart.setData(data);
        incomePieChart.invalidate();
    }

    @Override
    public void showLineChart(LineData data, ArrayList<String> xvals) {
        incomeLineChart.setNoDataText("暂无数据");
        //折线图不显示数值
        data.setDrawValues(true);
        data.setValueTextSize(10f);

        //得到X轴
        XAxis xAxis =incomeLineChart.getXAxis();
        //设置X轴的位置（默认在上方)
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置X轴坐标之间的最小间隔
        xAxis.setGranularity(1f);
        //设置X轴的刻度数量，第二个参数为true,将会画出明确数量（带有小数点），但是可能值导致不均匀，默认（6，false）
        xAxis.setLabelCount(6, false);
        //设置X轴的值（最小值、最大值、然后会根据设置的刻度数量自动分配刻度显示）
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum((float) xvals.size());
        //不显示网格线
        xAxis.setDrawGridLines(false);
        // 标签倾斜
        if(xvals.size()>0){
            //设置X轴值为字符串
            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    int index=(int)value;
                    if(index<xvals.size()){
                        return xvals.get(index);
                    }
                    return String.valueOf(index);
                }
            });

        }

        xAxis.setAxisLineWidth(1f);

        YAxis rightYAxis=incomeLineChart.getAxisRight();
        rightYAxis.setEnabled(false);
        rightYAxis.setDrawGridLines(false);

        YAxis leftYAxis=incomeLineChart.getAxisLeft();
        leftYAxis.setDrawGridLines(false);
        leftYAxis.setAxisLineWidth(2f);
        Description description=new Description();
        description.setText("");
        incomeLineChart.setDescription(description);
        incomeLineChart.clear();
        incomeLineChart.setData(data);
        incomeLineChart.invalidate();
    }

    @Override
    public void showBarChart(BarData data, ArrayList<String> xvals) {
        incomeBarChart.setDrawBarShadow(false);
        incomeBarChart.setDrawValueAboveBar(true);
        incomeBarChart.getDescription().setEnabled(false);
        incomeBarChart.setNoDataText("无数据");
        incomeBarChart.setPinchZoom(false);
        incomeBarChart.setDrawGridBackground(false);
        incomeBarChart.getAxisRight().setEnabled(false);


        XAxis xAxis=incomeBarChart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int index=(int)value;
                if(index<xvals.size()){
                    return xvals.get(index);
                }
                return String.valueOf(index);
            }
        });
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setCenterAxisLabels(false);
        xAxis.setGranularity(1f);

        YAxis yAxis=incomeBarChart.getAxisLeft();
        yAxis.setDrawAxisLine(true);
        yAxis.setDrawGridLines(false);
        incomeBarChart.clear();
        incomeBarChart.setData(data);
        incomeBarChart.invalidate();
    }


    @Override
    public void refreshPieChart(PieData data, ArrayList<String> xvals) {

    }

    @Override
    public void refreshLineChart() {

    }

    @Override
    public void showResult(String result) {

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
        Date date=new Date(millseconds);
        StringBuffer stringBuffer=new StringBuffer(DateFormatUtil.getYear(date)+"年"+DateFormatUtil.getMonth(date)+"月");
        btnSIChooseIncomeDate.setText(stringBuffer);
        presenter.init(bId,millseconds,type);
    }

    @Override
    public void refresh(String bId) {
        presenter.init(bId,System.currentTimeMillis(),"收入");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnSIChooseIncomeDate)
    public void onViewClicked() {
        initDatePicker();
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
